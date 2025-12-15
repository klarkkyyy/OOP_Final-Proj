import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
                               "Type your code in the box below. For example:\n" +
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
        
        // Patterns to match valid code
        Pattern warriorPattern = Pattern.compile(".*new\\s+Warrior\\s*\\(\\s*\\).*", Pattern.CASE_INSENSITIVE);
        Pattern magePattern = Pattern.compile(".*new\\s+Mage\\s*\\(\\s*\\).*", Pattern.CASE_INSENSITIVE);
        Pattern assassinPattern = Pattern.compile(".*new\\s+Assassin\\s*\\(\\s*\\).*", Pattern.CASE_INSENSITIVE);
        
        if (warriorPattern.matcher(code).matches()) {
            selectedHero = new Warrior();
            showSuccess("Warrior selected! " + selectedHero.useSpecialAbility());
        } else if (magePattern.matcher(code).matches()) {
            selectedHero = new Mage();
            showSuccess("Mage selected! " + selectedHero.useSpecialAbility());
        } else if (assassinPattern.matcher(code).matches()) {
            selectedHero = new Assassin();
            showSuccess("Assassin selected! " + selectedHero.useSpecialAbility());
        } else {
            feedbackLabel.setForeground(Color.RED);
            feedbackLabel.setText("❌ Invalid code! Make sure you're creating an object with 'new ClassName()'");
        }
    }
    
    private void showSuccess(String message) {
        feedbackLabel.setForeground(new Color(0, 150, 0));
        feedbackLabel.setText("✅ " + message);
        
        // Show hero stats
        JOptionPane.showMessageDialog(frame,
            "Hero Created Successfully!\n\n" + selectedHero.getStats() + "\n\n" +
            "You've learned INHERITANCE:\n" +
            "- You created a " + selectedHero.getName() + " object\n" +
            "- " + selectedHero.getName() + " extends the Hero class\n" +
            "- This allows code reuse and polymorphism!",
            "Success!", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main());
    }
}
