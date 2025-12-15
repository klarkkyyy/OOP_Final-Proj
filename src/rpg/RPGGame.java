package rpg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * RPGGame class - Main game controller
 * Demonstrates all 4 OOP pillars working together:
 * - ENCAPSULATION: Protected game state
 * - INHERITANCE: Character and Enemy hierarchies
 * - POLYMORPHISM: Different combat behaviors
 * - ABSTRACTION: Interfaces and abstract classes
 */
public class RPGGame extends JFrame {
    private GameState gameState; // Encapsulation
    private Character player; // Inheritance
    private Enemy currentEnemy; // Inheritance
    private JTextArea gameLog;
    private JLabel playerStatusLabel;
    private JLabel enemyStatusLabel;
    private JLabel gameStateLabel;
    private JButton attackButton;
    private JButton specialButton;
    private JButton nextEnemyButton;
    
    public RPGGame() {
        // Show character selection screen first
        SwingUtilities.invokeLater(() -> {
            new CharacterSelectionScreen().setVisible(true);
        });
    }
    
    public RPGGame(Character selectedCharacter) {
        initializeGame(selectedCharacter);
        createGUI();
        updateDisplay();
    }
    
    private void initializeGame(Character selectedCharacter) {
        gameState = new GameState();
        this.player = selectedCharacter;
    }
    
    private void createGUI() {
        setTitle("RPG Game - OOP Four Pillars Demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Top panel - Character info
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        String characterIcon = "⚔️";
        if (player instanceof Mage) characterIcon = "🔮";
        else if (player instanceof Rogue) characterIcon = "🗡️";
        JLabel characterInfoLabel = new JLabel(characterIcon + " Playing as: " + player.getCharacterType());
        characterInfoLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        topPanel.add(characterInfoLabel);
        add(topPanel, BorderLayout.NORTH);
        
        // Center panel - Game status
        JPanel centerPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        gameStateLabel = new JLabel();
        gameStateLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        centerPanel.add(gameStateLabel);
        
        playerStatusLabel = new JLabel();
        playerStatusLabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        centerPanel.add(playerStatusLabel);
        
        enemyStatusLabel = new JLabel();
        enemyStatusLabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        centerPanel.add(enemyStatusLabel);
        
        add(centerPanel, BorderLayout.CENTER);
        
        // Game log
        gameLog = new JTextArea(10, 40);
        gameLog.setEditable(false);
        gameLog.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
        gameLog.setBackground(new Color(240, 240, 240));
        JScrollPane scrollPane = new JScrollPane(gameLog);
        add(scrollPane, BorderLayout.SOUTH);
        
        // Control panel
        JPanel controlPanel = new JPanel(new FlowLayout());
        
        attackButton = new JButton("Attack");
        attackButton.addActionListener(new AttackListener());
        controlPanel.add(attackButton);
        
        specialButton = new JButton("Special Ability");
        specialButton.addActionListener(new SpecialListener());
        controlPanel.add(specialButton);
        
        nextEnemyButton = new JButton("Find Next Enemy");
        nextEnemyButton.addActionListener(new NextEnemyListener());
        controlPanel.add(nextEnemyButton);
        
        add(controlPanel, BorderLayout.WEST);
        
        // Spawn first enemy
        spawnEnemy();
        
        pack();
        setSize(700, 600);
        setLocationRelativeTo(null);
        
        addToLog("=== Welcome to the RPG Game! ===");
        addToLog("Playing as: " + player.getCharacterType());
        addToLog("Special Ability: " + player.getSpecialAbility());
        addToLog("");
        addToLog("This game demonstrates the 4 Pillars of OOP:");
        addToLog("1. ENCAPSULATION: Protected game states");
        addToLog("2. INHERITANCE: Character and Enemy hierarchies");
        addToLog("3. POLYMORPHISM: Varied combat behaviors");
        addToLog("4. ABSTRACTION: Shared game rules");
        addToLog("");
        addToLog("Click 'Find Next Enemy' to start fighting!");
    }
    
    private void spawnEnemy() {
        currentEnemy = EnemyFactory.createRandomEnemy(gameState.getLevel());
        addToLog("A wild " + currentEnemy.getName() + " appears! (" + currentEnemy.getEnemyType() + ")");
        updateDisplay();
    }
    
    private void updateDisplay() {
        // Update game state
        gameStateLabel.setText(String.format(
            "Level: %d | EXP: %d | Gold: %d | Enemies Defeated: %d/5",
            gameState.getLevel(), gameState.getExperience(), 
            gameState.getGold(), gameState.getEnemiesDefeated()
        ));
        
        // Update player status
        if (player != null) {
            playerStatusLabel.setText("Player: " + player.getStatus());
        }
        
        // Update enemy status
        if (currentEnemy != null) {
            enemyStatusLabel.setText("Enemy: " + currentEnemy.getStatus());
        }
        
        // Update button states
        attackButton.setEnabled(currentEnemy != null && currentEnemy.isAlive() && player.isAlive());
        specialButton.setEnabled(currentEnemy != null && currentEnemy.isAlive() && player.isAlive());
        nextEnemyButton.setEnabled(currentEnemy == null || !currentEnemy.isAlive());
        
        // Check victory condition
        if (gameState.isVictory()) {
            addToLog("=== VICTORY! You have defeated 5 enemies! ===");
            JOptionPane.showMessageDialog(this, "Congratulations! You won the game!");
        }
    }
    
    private void addToLog(String message) {
        gameLog.append(message + "\n");
        gameLog.setCaretPosition(gameLog.getDocument().getLength());
    }
    
    // Action Listeners
    private class AttackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (currentEnemy == null || !currentEnemy.isAlive() || !player.isAlive()) {
                return;
            }
            
            // Polymorphism - different characters attack differently
            int damage = player.attack(currentEnemy);
            addToLog(player.getName() + " attacks " + currentEnemy.getName() + " for " + damage + " damage!");
            
            if (!currentEnemy.isAlive()) {
                addToLog(currentEnemy.getName() + " is defeated!");
                addToLog("You gained " + currentEnemy.getExpReward() + " EXP and " + 
                        currentEnemy.getGoldReward() + " gold!");
                
                // Update game state (encapsulation)
                gameState.addExperience(currentEnemy.getExpReward());
                gameState.addGold(currentEnemy.getGoldReward());
                gameState.incrementEnemiesDefeated();
                
                currentEnemy = null;
            } else {
                // Enemy counter-attacks (polymorphism)
                int enemyDamage = currentEnemy.attack(player);
                addToLog(currentEnemy.getName() + " attacks " + player.getName() + 
                        " for " + enemyDamage + " damage!");
                
                if (!player.isAlive()) {
                    addToLog("=== GAME OVER ===");
                    gameState.setGameOver(true);
                    JOptionPane.showMessageDialog(RPGGame.this, "Game Over! You were defeated.");
                }
            }
            
            updateDisplay();
        }
    }
    
    private class SpecialListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (currentEnemy == null || !currentEnemy.isAlive() || !player.isAlive()) {
                return;
            }
            
            addToLog("Special Ability: " + player.getSpecialAbility());
            
            // Polymorphism - each character type has different special behavior
            int damage = player.attack(currentEnemy);
            addToLog(player.getName() + " uses special ability on " + currentEnemy.getName() + 
                    " for " + damage + " damage!");
            
            if (!currentEnemy.isAlive()) {
                addToLog(currentEnemy.getName() + " is defeated!");
                addToLog("You gained " + currentEnemy.getExpReward() + " EXP and " + 
                        currentEnemy.getGoldReward() + " gold!");
                
                gameState.addExperience(currentEnemy.getExpReward());
                gameState.addGold(currentEnemy.getGoldReward());
                gameState.incrementEnemiesDefeated();
                
                currentEnemy = null;
            } else {
                int enemyDamage = currentEnemy.attack(player);
                addToLog(currentEnemy.getName() + " attacks " + player.getName() + 
                        " for " + enemyDamage + " damage!");
                
                if (!player.isAlive()) {
                    addToLog("=== GAME OVER ===");
                    gameState.setGameOver(true);
                    JOptionPane.showMessageDialog(RPGGame.this, "Game Over! You were defeated.");
                }
            }
            
            updateDisplay();
        }
    }
    
    private class NextEnemyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (currentEnemy == null || !currentEnemy.isAlive()) {
                spawnEnemy();
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            // Show character selection screen
            new CharacterSelectionScreen().setVisible(true);
        });
    }
}

