import javax.swing.*;
import java.awt.*;
import java.util.regex.Pattern;

public class Main {
    private JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private Hero selectedHero;
    private JTextArea codeInput;
    private JTextArea instructionArea;
    private JLabel feedbackLabel;
    
    public Main() {
        frame = new JFrame();
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        // Create different screens
        createWelcomeScreen();
        createClassSelectionScreen();
        // Battle screen will be created when needed
        
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("RPG - Learn OOP!");
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    private void createWelcomeScreen() {
        JPanel welcomePanel = new JPanel(new BorderLayout());
        welcomePanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        welcomePanel.setBackground(new Color(240, 248, 255));
        
        // Welcome text
        JTextArea welcomeText = new JTextArea();
        welcomeText.setText("Welcome to the OOP RPG Adventure!\n\n" +
                           "In this game, you'll learn the Four Pillars of Object-Oriented Programming:\n\n" +
                           "1. ABSTRACTION - Hiding complex details, showing only what's needed\n" +
                           "2. ENCAPSULATION - Bundling data and methods together\n" +
                           "3. INHERITANCE - Creating new classes based on existing ones\n" +
                           "4. POLYMORPHISM - Using objects of different types through a common interface\n\n" +
                           "Get ready to code your way through this adventure!");
        welcomeText.setFont(new Font("Arial", Font.PLAIN, 16));
        welcomeText.setEditable(false);
        welcomeText.setOpaque(false);
        welcomeText.setLineWrap(true);
        welcomeText.setWrapStyleWord(true);
        
        // Next button
        JButton nextButton = new JButton("Next →");
        nextButton.setFont(new Font("Arial", Font.BOLD, 14));
        nextButton.setPreferredSize(new Dimension(120, 40));
        nextButton.addActionListener(e -> cardLayout.show(mainPanel, "classSelection"));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(nextButton);
        
        welcomePanel.add(welcomeText, BorderLayout.CENTER);
        welcomePanel.add(buttonPanel, BorderLayout.SOUTH);
        
        mainPanel.add(welcomePanel, "welcome");
    }
    
    private void createClassSelectionScreen() {
        JPanel selectionPanel = new JPanel(new BorderLayout());
        selectionPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        selectionPanel.setBackground(new Color(255, 250, 240));
        
        // Title
        JLabel titleLabel = new JLabel("Choose Your Hero Class", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        
        // Class stats panel
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 20, 20));
        
        // Warrior stats
        JPanel warriorPanel = createClassPanel("Warrior", 
            "Health: 150\nAttack: 25\nMana: 10\n\nA brave fighter with high health\nand strong physical attacks",
            new Color(200, 100, 100));
        
        // Mage stats
        JPanel magePanel = createClassPanel("Mage",
            "Health: 80\nAttack: 15\nMana: 50\n\nA powerful spellcaster with\nhigh mana and magical attacks",
            new Color(100, 100, 200));
        
        // Assassin stats
        JPanel assassinPanel = createClassPanel("Assassin",
            "Health: 100\nAttack: 30\nMana: 20\n\nA stealthy fighter with high\nattack and balanced stats",
            new Color(150, 100, 150));
        
        statsPanel.add(warriorPanel);
        statsPanel.add(magePanel);
        statsPanel.add(assassinPanel);
        
        // Instruction area
        instructionArea = new JTextArea();
        instructionArea.setText("INSTRUCTIONS:\n\n" +
                               "To select your hero, you need to create an object!\n\n" +
                               "Type your code in the box below. For example:\n\n" +
                               "To choose Warrior:  Hero hero = new Warrior();\n" +
                               "To choose Mage:     Hero hero = new Mage();\n" +
                               "To choose Assassin: Hero hero = new Assassin();\n\n" +
                               "This demonstrates INHERITANCE - you're creating objects\n" +
                               "of different classes (Warrior, Mage, Assassin) but storing\n" +
                               "them in a variable of the parent type (Hero).");
        instructionArea.setFont(new Font("Courier", Font.PLAIN, 12));
        instructionArea.setEditable(false);
        instructionArea.setOpaque(false);
        instructionArea.setLineWrap(true);
        instructionArea.setWrapStyleWord(true);
        instructionArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLUE, 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        
        // Code input area
        codeInput = new JTextArea(5, 40);
        codeInput.setFont(new Font("Courier", Font.PLAIN, 14));
        codeInput.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        
        // Feedback label
        feedbackLabel = new JLabel(" ", JLabel.CENTER);
        feedbackLabel.setFont(new Font("Arial", Font.BOLD, 14));
        feedbackLabel.setForeground(Color.RED);
        
        // Submit button
        JButton submitButton = new JButton("Submit Code");
        submitButton.setFont(new Font("Arial", Font.BOLD, 14));
        submitButton.setPreferredSize(new Dimension(150, 40));
        submitButton.addActionListener(e -> validateCode());
        
        // Layout
        JPanel inputPanel = new JPanel(new BorderLayout(10, 10));
        inputPanel.setOpaque(false);
        inputPanel.add(new JLabel("Type your code here:"), BorderLayout.NORTH);
        inputPanel.add(new JScrollPane(codeInput), BorderLayout.CENTER);
        inputPanel.add(submitButton, BorderLayout.SOUTH);
        
        JPanel centerPanel = new JPanel(new BorderLayout(20, 20));
        centerPanel.setOpaque(false);
        centerPanel.add(statsPanel, BorderLayout.NORTH);
        centerPanel.add(instructionArea, BorderLayout.CENTER);
        centerPanel.add(inputPanel, BorderLayout.SOUTH);
        
        selectionPanel.add(titleLabel, BorderLayout.NORTH);
        selectionPanel.add(centerPanel, BorderLayout.CENTER);
        selectionPanel.add(feedbackLabel, BorderLayout.SOUTH);
        
        mainPanel.add(selectionPanel, "classSelection");
    }
    
    private JPanel createClassPanel(String className, String stats, Color bgColor) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        panel.setBackground(bgColor);
        
        JLabel nameLabel = new JLabel(className, JLabel.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        JTextArea statsArea = new JTextArea(stats);
        statsArea.setFont(new Font("Arial", Font.PLAIN, 12));
        statsArea.setEditable(false);
        statsArea.setOpaque(false);
        statsArea.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
        
        panel.add(nameLabel, BorderLayout.NORTH);
        panel.add(statsArea, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void validateCode() {
        String code = codeInput.getText().trim();

        Pattern warriorPattern = Pattern.compile(".*new\\s+Warrior\\s*\\(\\s*\\).*", Pattern.CASE_INSENSITIVE);
        Pattern magePattern = Pattern.compile(".*new\\s+Mage\\s*\\(\\s*\\).*", Pattern.CASE_INSENSITIVE);
        Pattern assassinPattern = Pattern.compile(".*new\\s+Assassin\\s*\\(\\s*\\).*", Pattern.CASE_INSENSITIVE);
        
        if (warriorPattern.matcher(code).matches()) {
            selectedHero = new Warrior();
            showSuccess("Warrior selected!");
        } else if (magePattern.matcher(code).matches()) {
            selectedHero = new Mage();
            showSuccess("Mage selected!");
        } else if (assassinPattern.matcher(code).matches()) {
            selectedHero = new Assassin();
            showSuccess("Assassin selected!");
        } else {
            feedbackLabel.setForeground(Color.RED);
            feedbackLabel.setText("Invalid code! Make sure you're creating an object with 'new ClassName()'");
        }
    }
    
    private void showSuccess(String message) {
        feedbackLabel.setForeground(new Color(0, 150, 0));
        feedbackLabel.setText("✅ " + message);
        
        // Show hero stats and navigate to battle
        int option = JOptionPane.showConfirmDialog(frame,
            "Hero Created Successfully!\n\n" + selectedHero.getStats() + "\n\n" +
            "You've learned INHERITANCE:\n" +
            "- You created a " + selectedHero.getName() + " object\n" +
            "- " + selectedHero.getName() + " extends the Hero class\n" +
            "- This allows code reuse and polymorphism!\n\n" +
            "Ready to battle?",
            "Success!", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        
        if (option == JOptionPane.YES_OPTION) {
            initializeBattle();
        }
    }
    
    private Enemy currentEnemy;
    private JTextArea battleLog;
    private JTextArea battleCodeInput;
    private JLabel battleFeedbackLabel;
    private int battlePhase = 0; // 0=initial, 1=after first one-shot, 2=after class switch (can use ability), 3=redemption phase
    private int classesUsed = 1; // Track how many classes have been used
    
    private void initializeBattle() {
        // Remove battle panel if it already exists
        // CardLayout has welcome (0), classSelection (1), and potentially battle (2)
        if (mainPanel.getComponentCount() > 2) {
            mainPanel.remove(2);
        }
        
        JPanel battlePanel = new JPanel(new BorderLayout());
        battlePanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        battlePanel.setBackground(new Color(245, 245, 220));
        
        // Title
        JLabel titleLabel = new JLabel("BATTLE ARENA - Learn POLYMORPHISM!", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(139, 0, 0));
        
        // Create enemy (one-shot attack)
        currentEnemy = new Enemy("Dark Goblin", 120, 999);
        
        // Reset battle phase
        battlePhase = 0;
        classesUsed = 1;
        
        // Initialize battle log first
        battleLog = new JTextArea(8, 50);
        battleLog.setFont(new Font("Courier", Font.PLAIN, 12));
        battleLog.setEditable(false);
        battleLog.setBackground(new Color(255, 255, 240));
        
        // Instructions (will be updated based on battle phase)
        JTextArea battleInstructions = new JTextArea();
        battleInstructions.setName("battleInstructions");
        battleInstructions.setFont(new Font("Courier", Font.PLAIN, 11));
        battleInstructions.setEditable(false);
        battleInstructions.setOpaque(false);
        battleInstructions.setLineWrap(true);
        battleInstructions.setWrapStyleWord(true);
        battleInstructions.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLUE, 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        // Initialize with phase 0 instructions
        updateBattleInstructions(battleInstructions);
        
        // Code input
        battleCodeInput = new JTextArea(3, 40);
        battleCodeInput.setFont(new Font("Courier", Font.PLAIN, 14));
        battleCodeInput.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        
        // Submit button
        JButton useAbilityButton = new JButton("Use Special Ability!");
        useAbilityButton.setFont(new Font("Arial", Font.BOLD, 14));
        useAbilityButton.setPreferredSize(new Dimension(200, 40));
        useAbilityButton.addActionListener(e -> validateBattleCode());
        
        // Battle log already initialized above, startScriptedBattle() was called there
        
        // Layout
        JPanel inputPanel = new JPanel(new BorderLayout(10, 10));
        inputPanel.setOpaque(false);
        inputPanel.add(new JLabel("Type your code to use special ability:"), BorderLayout.NORTH);
        inputPanel.add(new JScrollPane(battleCodeInput), BorderLayout.CENTER);
        inputPanel.add(useAbilityButton, BorderLayout.SOUTH);
        
        JPanel centerPanel = new JPanel(new BorderLayout(20, 20));
        centerPanel.setOpaque(false);
        centerPanel.add(battleInstructions, BorderLayout.CENTER);
        centerPanel.add(inputPanel, BorderLayout.SOUTH);
        
        battleFeedbackLabel = new JLabel(" ", JLabel.CENTER);
        battleFeedbackLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setOpaque(false);
        rightPanel.add(new JLabel("Battle Log:", JLabel.CENTER), BorderLayout.NORTH);
        rightPanel.add(new JScrollPane(battleLog), BorderLayout.CENTER);
        rightPanel.add(battleFeedbackLabel, BorderLayout.SOUTH);
        
        JPanel mainBattleLayout = new JPanel(new BorderLayout(20, 20));
        mainBattleLayout.setOpaque(false);
        mainBattleLayout.add(centerPanel, BorderLayout.CENTER);
        mainBattleLayout.add(rightPanel, BorderLayout.EAST);
        
        battlePanel.add(titleLabel, BorderLayout.NORTH);
        battlePanel.add(mainBattleLayout, BorderLayout.CENTER);
        battlePanel.setName("battle");
        
        mainPanel.add(battlePanel, "battle");
        
        // Force CardLayout to show the battle panel
        cardLayout.show(mainPanel, "battle");
        
        // Update the UI
        mainPanel.revalidate();
        mainPanel.repaint();
        frame.revalidate();
        frame.repaint();
        
        // Start the scripted battle sequence AFTER all UI components are created
        startScriptedBattle();
    }
    
    private void validateBattleCode() {
        String code = battleCodeInput.getText().trim();
        
        // Pattern to match class switching: hero = new ClassName();
        Pattern classSwitchPattern = Pattern.compile(".*hero\\s*=\\s*new\\s+(Warrior|Mage|Assassin)\\s*\\(\\s*\\).*", Pattern.CASE_INSENSITIVE);
        
        // Pattern to match special ability: hero.useSpecialAbility()
        Pattern abilityPattern = Pattern.compile(".*hero\\.useSpecialAbility\\(\\s*\\).*", Pattern.CASE_INSENSITIVE);
        
        if (classSwitchPattern.matcher(code).matches() && (battlePhase == 1 || battlePhase == 2)) {
            // Class switching phase - extract which class
            if (code.matches(".*new\\s+Warrior.*")) {
                switchHeroClass("Warrior");
            } else if (code.matches(".*new\\s+Mage.*")) {
                switchHeroClass("Mage");
            } else if (code.matches(".*new\\s+Assassin.*")) {
                switchHeroClass("Assassin");
            }
            
            battleLog.append("🔄 " + selectedHero.getName() + " class switch activated!\n");
            battleLog.append("✨ The hero transforms into a " + selectedHero.getName() + "!\n");
            battleLog.append("💡 Notice: Same variable 'hero', different class = POLYMORPHISM!\n\n");
            
            battleFeedbackLabel.setForeground(new Color(0, 150, 0));
            battleFeedbackLabel.setText("✅ Class switched! Now use your special ability before the goblin attacks!");
            
            // Allow user to use special ability before goblin attacks
            classesUsed++;
            if (classesUsed < 3) {
                battlePhase = 2; // Allow ability use, then goblin will attack
                battleLog.append("💡 You can now use your special ability with the new class!\n");
                battleLog.append("Type: int damage = hero.useSpecialAbility();\n\n");
                updateBattleInstructions(findBattleInstructions());
            } else {
                battlePhase = 3; // Move to redemption phase
                battleLog.append("💭 The hero thinks: 'This is my last chance... I must use my ultimate ability!'\n");
                battleLog.append("✨ A mysterious power awakens within - REDEMPTION!\n");
                battleLog.append("Now use your special ability to unleash it!\n\n");
                updateBattleInstructions(findBattleInstructions());
            }
            
            battleCodeInput.setText("");
            
        } else if (abilityPattern.matcher(code).matches() && battlePhase == 2) {
            // User uses special ability after class switch (before goblin attacks)
            int damage = selectedHero.useSpecialAbility();
            String abilityName = getAbilityName(selectedHero);
            
            currentEnemy.takeDamage(damage);
            
            String message = String.format("%s uses %s and deals %d damage to %s!",
                                          selectedHero.getName(), abilityName, damage, currentEnemy.getName());
            
            battleLog.append(message + "\n");
            battleLog.append(currentEnemy.getName() + " health: " + currentEnemy.getHealth() + "\n\n");
            
            battleFeedbackLabel.setForeground(new Color(0, 150, 0));
            battleFeedbackLabel.setText("✅ Special ability used! Notice how different classes have different abilities!");
            
            // Now goblin attacks and one-shots
            int enemyDamage = currentEnemy.attack();
            selectedHero.takeDamage(enemyDamage);
            battleLog.append("💥 " + currentEnemy.getName() + " unleashes another devastating attack!\n");
            battleLog.append("💀 " + selectedHero.getName() + " takes " + enemyDamage + " damage and is defeated again!\n");
            battleLog.append("⚠️ The hero must switch classes once more!\n\n");
            updateBattleInstructions(findBattleInstructions());
            
            battleCodeInput.setText("");
            
        } else if (abilityPattern.matcher(code).matches()) {
            if (battlePhase == 0) {
                // Normal battle phase - user can fight
                int damage = selectedHero.useSpecialAbility();
                String abilityName = getAbilityName(selectedHero);
                
                currentEnemy.takeDamage(damage);
                
                String message = String.format("%s uses %s and deals %d damage to %s!",
                                              selectedHero.getName(), abilityName, damage, currentEnemy.getName());
                
                battleLog.append(message + "\n");
                battleLog.append(currentEnemy.getName() + " health: " + currentEnemy.getHealth() + "\n\n");
                
                battleFeedbackLabel.setForeground(new Color(0, 150, 0));
                battleFeedbackLabel.setText("POLYMORPHISM in action! Same method call, different result!");
                
                // Enemy attacks back (one-shot attack)
                if (!currentEnemy.isDefeated()) {
                    int enemyDamage = currentEnemy.attack();
                    selectedHero.takeDamage(enemyDamage);
                    battleLog.append(currentEnemy.getName() + " attacks for " + enemyDamage + " damage!\n");
                    battleLog.append(selectedHero.getName() + " health: " + selectedHero.getHealth() + "\n\n");
                    
                    // Hero is always one-shotted - trigger scripted sequence
                    if (selectedHero.isDefeated()) {
                        battleLog.append("💀 " + selectedHero.getName() + " has been defeated!\n");
                        battleLog.append("💬 \"Oh no! This Goblin is strong!\"\n");
                        battleLog.append("💭 The hero thinks: 'I need to switch classes! This is my emergency plan!'\n");
                        battleLog.append("🔄 Emergency class switch ability activated!\n\n");
                        battlePhase = 1; // Move to scripted sequence
                        updateBattleInstructions(findBattleInstructions());
                    }
                }
                
                // Check win/lose conditions
                if (currentEnemy.isDefeated()) {
                    battleLog.append("🎉 VICTORY! " + currentEnemy.getName() + " defeated!\n\n");
                    battleLog.append("You've learned POLYMORPHISM:\n");
                    battleLog.append("- You called hero.useSpecialAbility()\n");
                    battleLog.append("- The same method call produced different results\n");
                    battleLog.append("- Each class (Warrior/Mage/Assassin) has its own implementation\n");
                    battleLog.append("- Java automatically calls the correct version at runtime!\n");
                    battleCodeInput.setEditable(false);
                }
                
            } else if (battlePhase == 3) {
                // Redemption phase - use redemption() but user typed useSpecialAbility()
                int damage = selectedHero.redemption();
                String className = selectedHero.getName();
                
                // Add redemption narrative
                battleLog.append("💭 " + className + " whispers: 'This is it... my final stand!'\n");
                battleLog.append("✨ REDEMPTION activates! The hero channels all remaining power!\n");
                battleLog.append("⚡ " + className + " unleashes the ultimate attack!\n");
                
                currentEnemy.takeDamage(damage);
                
                battleLog.append("💥 " + damage + " damage dealt! " + currentEnemy.getName() + " is obliterated!\n\n");
                battleLog.append("🎉 VICTORY! The " + currentEnemy.getName() + " has been defeated!\n\n");
                
                battleLog.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
                battleLog.append("POLYMORPHISM DEMONSTRATED:\n");
                battleLog.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
                battleLog.append("1. Same variable 'hero' held 3 different class types\n");
                battleLog.append("   (Warrior → Mage → Assassin)\n");
                battleLog.append("2. Same method call 'hero.useSpecialAbility()'\n");
                battleLog.append("   produced different behaviors each time\n");
                battleLog.append("3. In the final phase, it called redemption()\n");
                battleLog.append("   - Each class has its own redemption() implementation\n");
                battleLog.append("   - Java automatically called the correct version!\n");
                battleLog.append("4. Behavior changed at RUNTIME based on actual object type\n");
                battleLog.append("   This is POLYMORPHISM in action! 🎯\n");
                
                battleFeedbackLabel.setForeground(new Color(0, 150, 0));
                battleFeedbackLabel.setText("🎉 VICTORY! You've mastered POLYMORPHISM!");
                battleCodeInput.setEditable(false);
                
                // Navigate to upgrade shop after a delay
                Timer timer = new Timer(2000, e -> {
                    selectedHero.addUpgradePoints(3); // Give 3 upgrade points
                    createUpgradeShop();
                    cardLayout.show(mainPanel, "upgradeShop");
                });
                timer.setRepeats(false);
                timer.start();
            } else {
                battleFeedbackLabel.setForeground(Color.RED);
                battleFeedbackLabel.setText("⚠️ You're defeated! Use class switch first: hero = new Mage();");
            }
            battleCodeInput.setText("");
        } else {
            battleFeedbackLabel.setForeground(Color.RED);
            if (battlePhase == 1 || battlePhase == 2) {
                battleFeedbackLabel.setText("❌ Invalid code! Try: hero = new Mage(); (or Warrior/Assassin)");
            } else {
                battleFeedbackLabel.setText("❌ Invalid code! Try: int damage = hero.useSpecialAbility();");
            }
        }
    }
    
    private void startScriptedBattle() {
        battleLog.setText("Battle Log:\n" + selectedHero.getName() + " vs " + currentEnemy.getName() + "\n");
        battleLog.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        battleLog.append("The battle begins! Use your special ability to fight!\n");
        // Update instructions for phase 0
        JTextArea instructions = findBattleInstructions();
        if (instructions != null) {
            updateBattleInstructions(instructions);
        }
    }
    
    private String getAbilityName(Hero hero) {
        if (hero instanceof Warrior) {
            return "Power Strike";
        } else if (hero instanceof Mage) {
            return "Fireball";
        } else if (hero instanceof Assassin) {
            return "Backstab";
        }
        return "Special Ability";
    }
    
    private void switchHeroClass(String newClass) {
        // Preserve some state if needed, but reset health for new class
        if (newClass.equalsIgnoreCase("Warrior")) {
            selectedHero = new Warrior();
        } else if (newClass.equalsIgnoreCase("Mage")) {
            selectedHero = new Mage();
        } else if (newClass.equalsIgnoreCase("Assassin")) {
            selectedHero = new Assassin();
        }
        
        // Reset hero to full health for the new class
        selectedHero.currentHealth = selectedHero.maxHealth;
        
        // Visual display removed - no update needed
        updateBattleDisplay();
    }
    
    private void updateBattleInstructions(JTextArea instructions) {
        if (battlePhase == 0) {
            instructions.setText("INSTRUCTIONS - POLYMORPHISM IN ACTION:\n\n" +
                               "You can use your special ability by calling: hero.useSpecialAbility()\n\n" +
                               "Even though 'hero' is of type Hero, it will call the correct\n" +
                               "implementation based on your actual class:\n" +
                               "- Warrior: Power Strike (2x damage)\n" +
                               "- Mage: Fireball (3x damage)\n" +
                               "- Assassin: Backstab (2.5x damage)\n\n" +
                               "This is POLYMORPHISM - same method call, different behaviors!\n\n" +
                               "Type: int damage = hero.useSpecialAbility();");
        } else if (battlePhase == 1) {
            instructions.setText("INSTRUCTIONS - POLYMORPHISM IN ACTION:\n\n" +
                               "Phase 2: The Goblin has defeated you!\n\n" +
                               "💡 Emergency Plan: Use class switch!\n" +
                               "Type your code to switch classes:\n\n" +
                               "To switch to Warrior:  hero = new Warrior();\n" +
                               "To switch to Mage:     hero = new Mage();\n" +
                               "To switch to Assassin: hero = new Assassin();\n\n" +
                               "This demonstrates POLYMORPHISM:\n" +
                               "- Same variable 'hero' can hold different class types\n" +
                               "- The variable type (Hero) stays the same\n" +
                               "- But the actual object type changes!\n");
        } else if (battlePhase == 2) {
            instructions.setText("INSTRUCTIONS - POLYMORPHISM IN ACTION:\n\n" +
                               "Phase 2: You've switched classes!\n\n" +
                               "💡 First, use your special ability with the new class:\n" +
                               "Type: int damage = hero.useSpecialAbility();\n\n" +
                               "After using your ability, the goblin will attack.\n" +
                               "Then you'll need to switch to another class.\n\n" +
                               "Notice: Same method call 'hero.useSpecialAbility()'\n" +
                               "but different classes produce different results!\n" +
                               "This is POLYMORPHISM! 🎯\n");
        } else if (battlePhase == 3) {
            instructions.setText("INSTRUCTIONS - FINAL PHASE - REDEMPTION:\n\n" +
                               "Phase 3: This is your last chance!\n\n" +
                               "💡 Use your special ability:\n\n" +
                               "Type: int damage = hero.useSpecialAbility();\n\n" +
                               "💭 The hero's REDEMPTION ability will activate!\n" +
                               "Even though you type 'useSpecialAbility()',\n" +
                               "it will call the 'redemption()' method internally.\n\n" +
                               "Each class has its own redemption() implementation!\n" +
                               "Same method call, different class = POLYMORPHISM! 🎯\n");
        }
        instructions.setFont(new Font("Courier", Font.PLAIN, 11));
        instructions.setEditable(false);
        instructions.setOpaque(false);
        instructions.setLineWrap(true);
        instructions.setWrapStyleWord(true);
        instructions.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLUE, 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
    }
    
    private JTextArea findBattleInstructions() {
        // Find the battle instructions component
        Component[] components = mainPanel.getComponents();
        for (Component comp : components) {
            if (comp instanceof JPanel) {
                JTextArea found = findTextAreaInPanel((JPanel)comp, "battleInstructions");
                if (found != null) return found;
            }
        }
        return null;
    }
    
    private JTextArea findTextAreaInPanel(JPanel panel, String name) {
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JTextArea && name.equals(((JTextArea)comp).getName())) {
                return (JTextArea)comp;
            }
            if (comp instanceof JPanel) {
                JTextArea found = findTextAreaInPanel((JPanel)comp, name);
                if (found != null) return found;
            }
        }
        return null;
    }
    
    private void updateBattleDisplay() {
        // Health bars removed - no update needed
    }
    
    private void createUpgradeShop() {
        // Remove upgrade shop if it already exists
        Component[] components = mainPanel.getComponents();
        for (int i = components.length - 1; i >= 0; i--) {
            Component comp = components[i];
            if (comp.getName() != null && comp.getName().equals("upgradeShop")) {
                mainPanel.remove(i);
                break;
            }
        }
        
        JPanel upgradePanel = new JPanel(new BorderLayout());
        upgradePanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        upgradePanel.setBackground(new Color(240, 248, 255));
        upgradePanel.setName("upgradeShop");
        
        // Title
        JLabel titleLabel = new JLabel("UPGRADE SHOP - Learn ENCAPSULATION & ABSTRACTION!", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 100, 200));
        
        // Current stats display
        JTextArea statsArea = new JTextArea();
        statsArea.setText("Current Hero Stats:\n" + selectedHero.getStats() + "\n\n" + selectedHero.getUpgradeStatus());
        statsArea.setFont(new Font("Courier", Font.PLAIN, 12));
        statsArea.setEditable(false);
        statsArea.setBackground(new Color(255, 255, 240));
        statsArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        
        // Instructions
        JTextArea instructions = new JTextArea();
        instructions.setName("upgradeInstructions");
        updateUpgradeInstructions(instructions);
        instructions.setFont(new Font("Courier", Font.PLAIN, 11));
        instructions.setEditable(false);
        instructions.setOpaque(false);
        instructions.setLineWrap(true);
        instructions.setWrapStyleWord(true);
        instructions.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLUE, 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        
        // Code input
        JTextArea upgradeCodeInput = new JTextArea(4, 40);
        upgradeCodeInput.setFont(new Font("Courier", Font.PLAIN, 14));
        upgradeCodeInput.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        
        // Feedback label
        JLabel upgradeFeedbackLabel = new JLabel(" ", JLabel.CENTER);
        upgradeFeedbackLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        // Submit button
        JButton upgradeButton = new JButton("Apply Upgrade");
        upgradeButton.setFont(new Font("Arial", Font.BOLD, 14));
        upgradeButton.setPreferredSize(new Dimension(150, 40));
        upgradeButton.addActionListener(e -> validateUpgradeCode(upgradeCodeInput, statsArea, upgradeFeedbackLabel, instructions));
        
        // Layout
        JPanel leftPanel = new JPanel(new BorderLayout(20, 20));
        leftPanel.setOpaque(false);
        leftPanel.add(new JLabel("Hero Stats:", JLabel.CENTER), BorderLayout.NORTH);
        leftPanel.add(new JScrollPane(statsArea), BorderLayout.CENTER);
        
        JPanel rightPanel = new JPanel(new BorderLayout(20, 20));
        rightPanel.setOpaque(false);
        rightPanel.add(instructions, BorderLayout.NORTH);
        
        JPanel inputPanel = new JPanel(new BorderLayout(10, 10));
        inputPanel.setOpaque(false);
        inputPanel.add(new JLabel("Type your upgrade code:"), BorderLayout.NORTH);
        inputPanel.add(new JScrollPane(upgradeCodeInput), BorderLayout.CENTER);
        inputPanel.add(upgradeButton, BorderLayout.SOUTH);
        
        rightPanel.add(inputPanel, BorderLayout.CENTER);
        rightPanel.add(upgradeFeedbackLabel, BorderLayout.SOUTH);
        
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 30, 30));
        centerPanel.setOpaque(false);
        centerPanel.add(leftPanel);
        centerPanel.add(rightPanel);
        
        upgradePanel.add(titleLabel, BorderLayout.NORTH);
        upgradePanel.add(centerPanel, BorderLayout.CENTER);
        
        mainPanel.add(upgradePanel, "upgradeShop");
    }
    
    private void updateUpgradeInstructions(JTextArea instructions) {
        instructions.setText("INSTRUCTIONS - ENCAPSULATION & ABSTRACTION:\n\n" +
                           "You have " + selectedHero.getUpgradePoints() + " upgrade points!\n\n" +
                           "💡 ENCAPSULATION: Stats are PRIVATE - you can't modify them directly!\n" +
                           "   You must use methods to upgrade:\n\n" +
                           "   hero.upgradeAttack();  (increases attack by 5)\n" +
                           "   hero.upgradeHealth();  (increases health by 20)\n" +
                           "   hero.upgradeMana();    (increases mana by 10)\n\n" +
                           "💡 ABSTRACTION: The upgrade logic is hidden from you!\n" +
                           "   - You don't see the validation checks\n" +
                           "   - You don't see the max limits\n" +
                           "   - You just call the method and it works!\n\n" +
                           "Type your upgrade code below:");
    }
    
    private void validateUpgradeCode(JTextArea codeInput, JTextArea statsArea, JLabel feedbackLabel, JTextArea instructions) {
        String code = codeInput.getText().trim();
        
        // Patterns to match upgrade methods
        Pattern upgradeAttackPattern = Pattern.compile(".*hero\\.upgradeAttack\\(\\s*\\).*", Pattern.CASE_INSENSITIVE);
        Pattern upgradeHealthPattern = Pattern.compile(".*hero\\.upgradeHealth\\(\\s*\\).*", Pattern.CASE_INSENSITIVE);
        Pattern upgradeManaPattern = Pattern.compile(".*hero\\.upgradeMana\\(\\s*\\).*", Pattern.CASE_INSENSITIVE);
        
        boolean success = false;
        String message = "";
        
        if (upgradeAttackPattern.matcher(code).matches()) {
            boolean result = selectedHero.upgradeAttack();
            if (result) {
                success = true;
                message = "✅ Attack upgraded! +5 Attack";
            } else {
                message = "❌ Upgrade failed! Check if you have upgrade points or if attack is at max (100)";
            }
        } else if (upgradeHealthPattern.matcher(code).matches()) {
            boolean result = selectedHero.upgradeHealth();
            if (result) {
                success = true;
                message = "✅ Health upgraded! +20 Health";
            } else {
                message = "❌ Upgrade failed! Check if you have upgrade points or if health is at max (300)";
            }
        } else if (upgradeManaPattern.matcher(code).matches()) {
            boolean result = selectedHero.upgradeMana();
            if (result) {
                success = true;
                message = "✅ Mana upgraded! +10 Mana";
            } else {
                message = "❌ Upgrade failed! Check if you have upgrade points or if mana is at max (100)";
            }
        } else {
            message = "❌ Invalid code! Try: hero.upgradeAttack(); or hero.upgradeHealth(); or hero.upgradeMana();";
        }
        
        if (success) {
            feedbackLabel.setForeground(new Color(0, 150, 0));
            // Update stats display
            statsArea.setText("Current Hero Stats:\n" + selectedHero.getStats() + "\n\n" + selectedHero.getUpgradeStatus());
            // Update instructions
            updateUpgradeInstructions(instructions);
            
            if (selectedHero.getUpgradePoints() == 0) {
                message += "\n\n🎉 All upgrade points used! You've learned ENCAPSULATION & ABSTRACTION!";
                codeInput.setEditable(false);
            }
        } else {
            feedbackLabel.setForeground(Color.RED);
        }
        
        feedbackLabel.setText(message);
        codeInput.setText("");
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main());
    }
}
