import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
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
        // Play OST background music
        playBackgroundMusic("ost.wav", true);
        
        JPanel welcomePanel = new JPanel(new BorderLayout());
        welcomePanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        welcomePanel.setBackground(new Color(240, 248, 255));

        JLabel titleLabel = new JLabel("Welcome to the OOP RPG Adventure!", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));

        JTextArea welcomeText = new JTextArea();
        welcomeText.setText(
                "In this game, you'll learn the Four Pillars of Object-Oriented Programming:\n\n" +
                "1. ABSTRACTION  - Hiding complex details, showing only what's needed\n" +
                "2. ENCAPSULATION - Bundling data and methods together\n" +
                "3. INHERITANCE   - Creating new classes based on existing ones\n" +
                "4. POLYMORPHISM  - Using objects of different types through a common interface\n\n" +
                "Get ready to code your way through this adventure!"
        );
        welcomeText.setFont(new Font("Arial", Font.PLAIN, 16));
        welcomeText.setEditable(false);
        welcomeText.setOpaque(false);
        welcomeText.setLineWrap(true);
        welcomeText.setWrapStyleWord(true);

        // Load and add lock_in.gif
        ImageIcon lockInGif = null;
        try {
            // Try to load from classpath first
            java.net.URL gifUrl = getClass().getResource("/lock_in.gif");
            if (gifUrl == null) {
                // Try working directory
                File gifFile = new File("lock_in.gif");
                if (gifFile.exists()) {
                    lockInGif = new ImageIcon(gifFile.getAbsolutePath());
                }
            } else {
                lockInGif = new ImageIcon(gifUrl);
            }
        } catch (Exception e) {
            System.err.println("Error loading lock_in.gif: " + e.getMessage());
        }
        
        JLabel gifLabel = null;
        if (lockInGif != null) {
            gifLabel = new JLabel(lockInGif);
            gifLabel.setHorizontalAlignment(JLabel.CENTER);
        }
        
        JButton nextButton = new JButton("Next →");
        nextButton.setFont(new Font("Arial", Font.BOLD, 14));
        nextButton.setPreferredSize(new Dimension(120, 40));
        nextButton.addActionListener(e -> cardLayout.show(mainPanel, "classSelection"));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(nextButton);
        
        // Create a panel to hold text and GIF vertically
        JPanel centerContentPanel = new JPanel(new BorderLayout(10, 10));
        centerContentPanel.setOpaque(false);
        centerContentPanel.add(new JScrollPane(welcomeText), BorderLayout.CENTER);
        if (gifLabel != null) {
            centerContentPanel.add(gifLabel, BorderLayout.SOUTH);
        }

        welcomePanel.add(titleLabel, BorderLayout.NORTH);
        welcomePanel.add(centerContentPanel, BorderLayout.CENTER);
        welcomePanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(welcomePanel, "welcome");
    }
    
    private void createClassSelectionScreen() {
        // Play OST background music (continues from welcome screen)
        playBackgroundMusic("ost.wav", true);
        
        JPanel selectionPanel = new JPanel(new BorderLayout());
        selectionPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        selectionPanel.setBackground(new Color(255, 250, 240));
        
        // Title
        JLabel titleLabel = new JLabel("Choose Your Hero Class", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        
        // Class icons
        ImageIcon warriorIcon = loadClassIcon("warrior.png", "Warrior", new Color(200, 100, 100));
        ImageIcon mageIcon = loadClassIcon("mage.png", "Mage", new Color(100, 100, 200));
        ImageIcon assassinIcon = loadClassIcon("rogue.png", "Rogue", new Color(150, 100, 150));
        
        // Class stats panel
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 20, 20));
        
        // Warrior stats
        JPanel warriorPanel = createClassPanel("Warrior", 
            "Health: 150\nAttack: 25\nMana: 10\n\nA brave fighter with high health\nand strong physical attacks",
            new Color(200, 100, 100), warriorIcon);
        
        // Mage stats
        JPanel magePanel = createClassPanel("Mage",
            "Health: 80\nAttack: 15\nMana: 50\n\nA powerful spellcaster with\nhigh mana and magical attacks",
            new Color(100, 100, 200), mageIcon);
        
        // Assassin stats
        JPanel assassinPanel = createClassPanel("Assassin",
            "Health: 100\nAttack: 30\nMana: 20\n\nA stealthy fighter with high\nattack and balanced stats",
            new Color(150, 100, 150), assassinIcon);
        
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

        JScrollPane instructionScrollPane = new JScrollPane(instructionArea);
        instructionScrollPane.setBorder(null);
        instructionScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        instructionScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        centerPanel.add(instructionScrollPane, BorderLayout.CENTER);

        centerPanel.add(inputPanel, BorderLayout.SOUTH);
        
        selectionPanel.add(titleLabel, BorderLayout.NORTH);
        selectionPanel.add(centerPanel, BorderLayout.CENTER);
        selectionPanel.add(feedbackLabel, BorderLayout.SOUTH);
        
        mainPanel.add(selectionPanel, "classSelection");
    }
    
    private JPanel createClassPanel(String className, String stats, Color bgColor, ImageIcon icon) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        panel.setBackground(bgColor);

        JLabel nameLabel = new JLabel(className, JLabel.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);

        if (icon != null) {
            JLabel imageLabel = new JLabel(icon);
            imageLabel.setHorizontalAlignment(JLabel.CENTER);
            centerPanel.add(imageLabel, BorderLayout.NORTH);
        }

        // Split stats into first 3 lines (HP, ATK, Mana) and the rest as description
        String[] lines = stats.split("\\n");
        String healthText = lines.length > 0 ? lines[0] : "";
        String attackText = lines.length > 1 ? lines[1] : "";
        String manaText   = lines.length > 2 ? lines[2] : "";

        StringBuilder descBuilder = new StringBuilder();
        for (int i = 3; i < lines.length; i++) {
            if (descBuilder.length() > 0) descBuilder.append("\n");
            descBuilder.append(lines[i]);
        }
        String descText = descBuilder.toString();

        Color statColor = getStatsColorForClass(className);

        JPanel statsPanel = new JPanel();
        statsPanel.setOpaque(false);
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
        statsPanel.add(createStatRow(createSymbolIcon("♥", new Color(220, 20, 60)), healthText, statColor));
        statsPanel.add(createStatRow(createSymbolIcon("⚔", new Color(60, 60, 60)), attackText, statColor));
        statsPanel.add(createStatRow(createSymbolIcon("★", new Color(65, 105, 225)), manaText, statColor));

        JTextArea descArea = new JTextArea(descText);
        descArea.setFont(new Font("Arial", Font.PLAIN, 12));
        descArea.setEditable(false);
        descArea.setOpaque(false);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setForeground(statColor);
        descArea.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));

        JPanel statsAndDesc = new JPanel();
        statsAndDesc.setOpaque(false);
        statsAndDesc.setLayout(new BoxLayout(statsAndDesc, BoxLayout.Y_AXIS));
        statsAndDesc.add(statsPanel);
        statsAndDesc.add(descArea);

        centerPanel.add(statsAndDesc, BorderLayout.CENTER);

        panel.add(nameLabel, BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createStatRow(ImageIcon icon, String text, Color textColor) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        row.setOpaque(false);

        JLabel iconLabel = new JLabel(icon);
        JLabel textLabel = new JLabel(text);
        textLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        textLabel.setForeground(textColor);

        row.add(iconLabel);
        row.add(textLabel);
        return row;
    }

    private ImageIcon createSymbolIcon(String symbol, Color baseColor) {
        int size = 18;
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(baseColor.darker());
        g2.fillOval(0, 0, size, size);
        g2.setColor(baseColor);
        g2.fillOval(2, 2, size - 4, size - 4);
        g2.setFont(new Font("Segoe UI", Font.BOLD, 11));
        FontMetrics fm = g2.getFontMetrics();
        int x = (size - fm.stringWidth(symbol)) / 2;
        int y = (size - fm.getHeight()) / 2 + fm.getAscent();
        g2.setColor(Color.WHITE);
        g2.drawString(symbol, x, y);
        g2.dispose();
        return new ImageIcon(image);
    }

    private Color getStatsColorForClass(String className) {
        if ("Warrior".equalsIgnoreCase(className)) {
            return new Color(255, 240, 220); // warm, fits warrior panel
        } else if ("Mage".equalsIgnoreCase(className)) {
            return new Color(225, 235, 255); // cool, fits mage panel
        } else if ("Assassin".equalsIgnoreCase(className)) {
            return new Color(245, 225, 255); // subtle purple tint
        }
        return Color.WHITE;
    }

    private ImageIcon loadClassIcon(String fileName, String label, Color fallbackColor) {
        int size = 160;
        // Try classpath
        try (InputStream stream = getClass().getResourceAsStream("/" + fileName)) {
            if (stream != null) {
                BufferedImage img = ImageIO.read(stream);
                if (img != null) {
                    Image scaled = img.getScaledInstance(size, size, Image.SCALE_SMOOTH);
                    return new ImageIcon(scaled);
                }
            }
        } catch (Exception ignored) {}
        // Try working directory
        try {
            File file = new File(fileName);
            if (file.exists()) {
                BufferedImage img = ImageIO.read(file);
                if (img != null) {
                    Image scaled = img.getScaledInstance(size, size, Image.SCALE_SMOOTH);
                    return new ImageIcon(scaled);
                }
            }
        } catch (Exception ignored) {}
        // Fallback placeholder
        return new ImageIcon(createPlaceholderImage(label, fallbackColor, size));
    }

    private Image createPlaceholderImage(String label, Color baseColor, int size) {
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(baseColor.darker());
        g2.fillRoundRect(0, 0, size, size, 24, 24);
        g2.setColor(baseColor);
        g2.fillRoundRect(6, 6, size - 12, size - 12, 18, 18);
        g2.setFont(new Font("Segoe UI", Font.BOLD, 22));
        FontMetrics fm = g2.getFontMetrics();
        int x = (size - fm.stringWidth(label)) / 2;
        int y = (size - fm.getHeight()) / 2 + fm.getAscent();
        g2.setColor(Color.WHITE);
        g2.drawString(label, x, y);
        g2.dispose();
        return image;
    }

    private JPanel createBattleVisualPanel() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);

        JPanel row = new JPanel(new GridLayout(1, 3, 20, 0));
        row.setOpaque(false);

        // Hero side
        heroImageLabel = new JLabel(getHeroBattleIcon());
        heroImageLabel.setHorizontalAlignment(JLabel.CENTER);
        heroNameLabel = new JLabel(selectedHero.getName(), JLabel.CENTER);
        heroNameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        heroHPLabel = new JLabel("HP: " + selectedHero.getHealth(), JLabel.CENTER);
        heroHPLabel.setFont(new Font("Arial", Font.BOLD, 14));
        heroHPLabel.setForeground(new Color(0, 150, 0));

        JPanel heroInfoPanel = new JPanel();
        heroInfoPanel.setLayout(new BoxLayout(heroInfoPanel, BoxLayout.Y_AXIS));
        heroInfoPanel.setOpaque(false);
        heroInfoPanel.add(heroNameLabel);
        heroInfoPanel.add(heroHPLabel);

        JPanel heroPanel = new JPanel(new BorderLayout());
        heroPanel.setOpaque(false);
        heroPanel.add(heroImageLabel, BorderLayout.CENTER);
        heroPanel.add(heroInfoPanel, BorderLayout.SOUTH);

        // VS label
        JLabel vsLabel = new JLabel("VS", JLabel.CENTER);
        vsLabel.setFont(new Font("Arial", Font.BOLD, 20));
        vsLabel.setForeground(Color.DARK_GRAY);

        // Enemy side
        enemyImageLabel = new JLabel(loadClassIcon("goblin.png", "Goblin", new Color(85, 107, 47)));
        enemyImageLabel.setHorizontalAlignment(JLabel.CENTER);
        JLabel enemyNameLabel = new JLabel(currentEnemy.getName(), JLabel.CENTER);
        enemyNameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        enemyHPLabel = new JLabel("HP: " + currentEnemy.getHealth(), JLabel.CENTER);
        enemyHPLabel.setFont(new Font("Arial", Font.BOLD, 14));
        enemyHPLabel.setForeground(new Color(200, 0, 0));

        JPanel enemyInfoPanel = new JPanel();
        enemyInfoPanel.setLayout(new BoxLayout(enemyInfoPanel, BoxLayout.Y_AXIS));
        enemyInfoPanel.setOpaque(false);
        enemyInfoPanel.add(enemyNameLabel);
        enemyInfoPanel.add(enemyHPLabel);

        JPanel enemyPanel = new JPanel(new BorderLayout());
        enemyPanel.setOpaque(false);
        enemyPanel.add(enemyImageLabel, BorderLayout.CENTER);
        enemyPanel.add(enemyInfoPanel, BorderLayout.SOUTH);

        row.add(heroPanel);
        row.add(vsLabel);
        row.add(enemyPanel);

        wrapper.add(row, BorderLayout.CENTER);
        return wrapper;
    }

    private ImageIcon getHeroBattleIcon() {
        if (selectedHero instanceof Warrior) {
            return loadClassIcon("warrior.png", "Warrior", new Color(200, 100, 100));
        } else if (selectedHero instanceof Mage) {
            return loadClassIcon("mage.png", "Mage", new Color(100, 100, 200));
        } else if (selectedHero instanceof Assassin) {
            return loadClassIcon("rogue.png", "Assassin", new Color(150, 100, 150));
        }
        return loadClassIcon("hero.jpg", "Hero", new Color(120, 120, 120));
    }
    
    private void validateCode() {
        String code = codeInput.getText().trim();

        Pattern warriorPattern = Pattern.compile(".*new\\s+Warrior\\s*\\(\\s*\\).*", Pattern.CASE_INSENSITIVE);
        Pattern magePattern = Pattern.compile(".*new\\s+Mage\\s*\\(\\s*\\).*", Pattern.CASE_INSENSITIVE);
        Pattern assassinPattern = Pattern.compile(".*new\\s+Assassin\\s*\\(\\s*\\).*", Pattern.CASE_INSENSITIVE);
        
        if (warriorPattern.matcher(code).matches()) {
            selectedHero = new Warrior();
            playSoundEffect("success.wav");
            showSuccess("Warrior selected!");
        } else if (magePattern.matcher(code).matches()) {
            selectedHero = new Mage();
            playSoundEffect("success.wav");
            showSuccess("Mage selected!");
        } else if (assassinPattern.matcher(code).matches()) {
            selectedHero = new Assassin();
            playSoundEffect("success.wav");
            showSuccess("Assassin selected!");
        } else {
            playSoundEffect("fail.wav");
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
    private JTextArea battleInstructionsArea;
    private JLabel heroImageLabel;
    private JLabel heroNameLabel;
    private JLabel heroHPLabel;
    private JLabel enemyImageLabel;
    private JLabel enemyHPLabel;
    private JButton nextButton; // Button to proceed to upgrade shop after victory
    private JPanel battleInputPanel; // Input panel to hide/show after victory
    private Clip currentBackgroundMusic; // Track current background music to stop it when switching screens
    private int battlePhase = 0; // 0=initial, 1=after first one-shot, 2=after class switch (can use ability), 3=redemption phase
    private int classesUsed = 1; // Track how many classes have been used
    private String originalClass; // Track the original class selected
    private java.util.Set<String> usedClasses = new java.util.HashSet<>(); // Track which classes have been used
    
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
        currentEnemy = new Enemy("Goblin", 150, 999);
        
        // Reset battle phase
        battlePhase = 0;
        classesUsed = 1;
        // Track original class and initialize used classes
        if (selectedHero instanceof Warrior) {
            originalClass = "Warrior";
        } else if (selectedHero instanceof Mage) {
            originalClass = "Mage";
        } else if (selectedHero instanceof Assassin) {
            originalClass = "Assassin";
        }
        usedClasses.clear();
        usedClasses.add(originalClass);
        
        // Play battle background music
        playBackgroundMusic("battle.wav", true);
        
        // Initialize battle log first
        battleLog = new JTextArea(5, 38);
        battleLog.setFont(new Font("Courier", Font.PLAIN, 12));
        battleLog.setEditable(false);
        battleLog.setBackground(new Color(255, 255, 240));
        
        // Instructions (will be updated based on battle phase)
        battleInstructionsArea = new JTextArea();
        battleInstructionsArea.setName("battleInstructions");
        battleInstructionsArea.setFont(new Font("Courier", Font.PLAIN, 11));
        battleInstructionsArea.setEditable(false);
        battleInstructionsArea.setOpaque(false);
        battleInstructionsArea.setLineWrap(true);
        battleInstructionsArea.setWrapStyleWord(true);
        battleInstructionsArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLUE, 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        // Initialize with phase 0 instructions
        updateBattleInstructions(battleInstructionsArea);
        
        // Code input
        battleCodeInput = new JTextArea(3, 48);
        battleCodeInput.setFont(new Font("Courier", Font.PLAIN, 14));
        battleCodeInput.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        
        // Submit button
        JButton useAbilityButton = new JButton("Use Special Ability!");
        useAbilityButton.setFont(new Font("Arial", Font.BOLD, 14));
        useAbilityButton.setPreferredSize(new Dimension(200, 40));
        useAbilityButton.addActionListener(e -> validateBattleCode());
        
        // Next button (initially hidden, shown after victory)
        nextButton = new JButton("Next →");
        nextButton.setFont(new Font("Arial", Font.BOLD, 16));
        nextButton.setPreferredSize(new Dimension(150, 45));
        nextButton.setVisible(false);
        nextButton.addActionListener(e -> {
            stopBackgroundMusic(); // Stop victory music before going to upgrade shop
            selectedHero.addUpgradePoints(3); // Give 3 upgrade points
            createUpgradeShop();
            cardLayout.show(mainPanel, "upgradeShop");
        });
        
        // Battle log already initialized above, startScriptedBattle() was called there
        
        // Layout
        battleInputPanel = new JPanel(new BorderLayout(10, 10));
        battleInputPanel.setOpaque(false);
        battleInputPanel.add(new JLabel("Type your code to use special ability:"), BorderLayout.NORTH);
        battleInputPanel.add(new JScrollPane(battleCodeInput), BorderLayout.CENTER);
        battleInputPanel.add(useAbilityButton, BorderLayout.SOUTH);
        
        JPanel centerPanel = new JPanel(new BorderLayout(20, 20));
        centerPanel.setOpaque(false);
        JScrollPane instructionsScrollPane = new JScrollPane(battleInstructionsArea);
        instructionsScrollPane.setBorder(null);
        instructionsScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        instructionsScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        centerPanel.add(instructionsScrollPane, BorderLayout.CENTER);
        centerPanel.add(battleInputPanel, BorderLayout.SOUTH);
        
        battleFeedbackLabel = new JLabel(" ", JLabel.CENTER);
        battleFeedbackLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        // Panel to hold feedback label and next button
        JPanel feedbackPanel = new JPanel(new BorderLayout(10, 10));
        feedbackPanel.setOpaque(false);
        feedbackPanel.add(battleFeedbackLabel, BorderLayout.CENTER);
        JPanel nextButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        nextButtonPanel.setOpaque(false);
        nextButtonPanel.add(nextButton);
        feedbackPanel.add(nextButtonPanel, BorderLayout.SOUTH);
        
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setOpaque(false);
        rightPanel.setPreferredSize(new Dimension(320, 0)); // Constrain width to make it narrower
        rightPanel.add(new JLabel("Battle Log:", JLabel.CENTER), BorderLayout.NORTH);
        rightPanel.add(new JScrollPane(battleLog), BorderLayout.CENTER);
        rightPanel.add(feedbackPanel, BorderLayout.SOUTH);
        
        JPanel mainBattleLayout = new JPanel(new BorderLayout(20, 20));
        mainBattleLayout.setOpaque(false);
        mainBattleLayout.add(createBattleVisualPanel(), BorderLayout.NORTH);
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
        
        if (battlePhase == 1) {
            // Class switching phase - require exact match: hero = new ClassName();
            String exactWarrior = "hero = new Warrior();";
            String exactMage = "hero = new Mage();";
            String exactAssassin = "hero = new Assassin();";
            
            if (code.equals(exactWarrior)) {
                if (usedClasses.contains("Warrior")) {
                    battleFeedbackLabel.setForeground(Color.RED);
                    battleFeedbackLabel.setText("❌ Warrior has already been used! Choose a different class.");
                    return;
                }
                switchHeroClass("Warrior");
                usedClasses.add("Warrior");
            } else if (code.equals(exactMage)) {
                if (usedClasses.contains("Mage")) {
                    battleFeedbackLabel.setForeground(Color.RED);
                    battleFeedbackLabel.setText("❌ Mage has already been used! Choose a different class.");
                    return;
                }
                switchHeroClass("Mage");
                usedClasses.add("Mage");
            } else if (code.equals(exactAssassin)) {
                if (usedClasses.contains("Assassin")) {
                    battleFeedbackLabel.setForeground(Color.RED);
                    battleFeedbackLabel.setText("❌ Assassin has already been used! Choose a different class.");
                    return;
                }
                switchHeroClass("Assassin");
                usedClasses.add("Assassin");
            } else {
                // Get available classes
                java.util.List<String> available = new java.util.ArrayList<>();
                if (!usedClasses.contains("Warrior")) available.add("Warrior");
                if (!usedClasses.contains("Mage")) available.add("Mage");
                if (!usedClasses.contains("Assassin")) available.add("Assassin");
                
                if (available.isEmpty()) {
                    battleFeedbackLabel.setForeground(Color.RED);
                    battleFeedbackLabel.setText("❌ All classes have been used!");
                } else {
                    // Show all available classes
                    StringBuilder availableClasses = new StringBuilder();
                    for (int i = 0; i < available.size(); i++) {
                        if (i > 0) availableClasses.append(" or ");
                        availableClasses.append("hero = new ").append(available.get(i)).append("();");
                    }
                    battleFeedbackLabel.setForeground(Color.RED);
                    battleFeedbackLabel.setText("❌ Invalid code! Use exactly: " + availableClasses.toString());
                }
                return;
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
                battleLog.append("Type: damage = hero.useSpecialAbility();\n\n");
                updateBattleInstructions(findBattleInstructions());
            } else {
                battlePhase = 3; // Move to redemption phase
                battleLog.append("💭 The hero thinks: 'This is my last chance... I must use my ultimate ability!'\n");
                battleLog.append("✨ A mysterious power awakens within - REDEMPTION!\n");
                battleLog.append("Now use your special ability to unleash it!\n\n");
                updateBattleInstructions(findBattleInstructions());
            }
            
            battleCodeInput.setText("");
            
        } else if (battlePhase == 2) {
            // Phase 2: User uses special ability after class switch - require exact match
            String exactAbility = "damage = hero.useSpecialAbility();";
            if (!code.equals(exactAbility)) {
                battleFeedbackLabel.setForeground(Color.RED);
                battleFeedbackLabel.setText("❌ Invalid code! Use exactly: damage = hero.useSpecialAbility();");
                return;
            }
            
            // User uses special ability after class switch (before goblin attacks)
            int damage = selectedHero.useSpecialAbility();
            playAttackSound(selectedHero);
            String abilityName = getAbilityName(selectedHero);
            
            currentEnemy.takeDamage(damage);
            playSoundEffect("goblin.wav");
            updateEnemyHP();
            
            String message = String.format("%s uses %s and deals %d damage to %s!",
                                          selectedHero.getName(), abilityName, damage, currentEnemy.getName());
            
            battleLog.append(message + "\n");
            battleLog.append(currentEnemy.getName() + " health: " + currentEnemy.getHealth() + "\n\n");
            
            battleFeedbackLabel.setForeground(new Color(0, 150, 0));
            battleFeedbackLabel.setText("✅ Special ability used! Notice how different classes have different abilities!");
            
            // Now goblin attacks and one-shots
            int enemyDamage = currentEnemy.attack();
            selectedHero.takeDamage(enemyDamage);
            updateHeroHP();
            battleLog.append("💥 " + currentEnemy.getName() + " unleashes another devastating attack!\n");
            battleLog.append("💀 " + selectedHero.getName() + " takes " + enemyDamage + " damage and is defeated again!\n");
            battleLog.append("⚠️ The hero must switch classes once more!\n\n");
            updateBattleInstructions(findBattleInstructions());
            // Replace hero image with death.png when defeated
            if (heroImageLabel != null) {
                heroImageLabel.setIcon(loadClassIcon("death.png", "Defeated", new Color(100, 100, 100)));
            }
            
            battlePhase = 1; // Go back to class switching phase
            battleCodeInput.setText("");
            
        } else if (battlePhase == 0) {
            // Phase 0: Normal battle phase - require exact match (without int since variable already exists)
            String exactAbility = "damage = hero.useSpecialAbility();";
            if (!code.equals(exactAbility)) {
                battleFeedbackLabel.setForeground(Color.RED);
                battleFeedbackLabel.setText("❌ Invalid code! Use exactly: damage = hero.useSpecialAbility();");
                return;
            }
            
            // Normal battle phase - user can fight
            int damage = selectedHero.useSpecialAbility();
            playAttackSound(selectedHero);
                String abilityName = getAbilityName(selectedHero);
                
                currentEnemy.takeDamage(damage);
                playSoundEffect("goblin.wav");
                updateEnemyHP();
                
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
                    updateHeroHP();
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
                        // Replace hero image with death.png when defeated
                        if (heroImageLabel != null) {
                            heroImageLabel.setIcon(loadClassIcon("death.png", "Defeated", new Color(100, 100, 100)));
                        }
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
            // Phase 3: Redemption phase - require exact match
            String exactAbility = "damage = hero.useSpecialAbility();";
            if (!code.equals(exactAbility)) {
                battleFeedbackLabel.setForeground(Color.RED);
                battleFeedbackLabel.setText("❌ Invalid code! Use exactly: damage = hero.useSpecialAbility();");
                return;
            }
            
            // Redemption phase - use redemption() but user typed useSpecialAbility()
            int damage = selectedHero.redemption();
            playAttackSound(selectedHero);
            String className = selectedHero.getName();
            
            // Add redemption narrative
            battleLog.append("💭 " + className + " whispers: 'This is it... my final stand!'\n");
            battleLog.append("✨ REDEMPTION activates! The hero channels all remaining power!\n");
            battleLog.append("⚡ " + className + " unleashes the ultimate attack!\n");
            
            currentEnemy.takeDamage(damage);
            playSoundEffect("goblin.wav");
            updateEnemyHP();
            
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
            
            // Stop battle music and play victory music (loops until next button is clicked)
            stopBackgroundMusic();
            playBackgroundMusic("victory.wav", true);
            
            // Hide input panel and show next button
            if (battleInputPanel != null) {
                battleInputPanel.setVisible(false);
            }
            if (nextButton != null) {
                nextButton.setVisible(true);
            }
            
            battleCodeInput.setText("");
        } else {
            // Invalid code for current phase
            battleFeedbackLabel.setForeground(Color.RED);
            if (battlePhase == 1) {
                // Get available classes
                java.util.List<String> available = new java.util.ArrayList<>();
                if (!usedClasses.contains("Warrior")) available.add("Warrior");
                if (!usedClasses.contains("Mage")) available.add("Mage");
                if (!usedClasses.contains("Assassin")) available.add("Assassin");
                
                if (available.isEmpty()) {
                    battleFeedbackLabel.setForeground(Color.RED);
                    battleFeedbackLabel.setText("❌ All classes have been used!");
                } else {
                    // Show all available classes
                    StringBuilder availableClasses = new StringBuilder();
                    for (int i = 0; i < available.size(); i++) {
                        if (i > 0) availableClasses.append(" or ");
                        availableClasses.append("hero = new ").append(available.get(i)).append("();");
                    }
                    battleFeedbackLabel.setForeground(Color.RED);
                    battleFeedbackLabel.setText("❌ Invalid code! Use exactly: " + availableClasses.toString());
                }
            } else if (battlePhase == 2) {
                battleFeedbackLabel.setForeground(Color.RED);
                battleFeedbackLabel.setText("❌ Invalid code! Use exactly: damage = hero.useSpecialAbility();");
            } else if (battlePhase == 0) {
                battleFeedbackLabel.setForeground(Color.RED);
                battleFeedbackLabel.setText("❌ Invalid code! Use exactly: damage = hero.useSpecialAbility();");
            } else if (battlePhase == 3) {
                battleFeedbackLabel.setForeground(Color.RED);
                battleFeedbackLabel.setText("❌ Invalid code! Use exactly: damage = hero.useSpecialAbility();");
            }
        }
    }
    
    private void startScriptedBattle() {
        battleLog.setText("Battle Log:\n" + selectedHero.getName() + " vs " + currentEnemy.getName() + "\n");
        battleLog.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        battleLog.append("The battle begins! Use your special ability to fight!\n");
        // Update instructions for phase 0
        updateBattleInstructions(battleInstructionsArea);
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
    
    private void stopBackgroundMusic() {
        if (currentBackgroundMusic != null && currentBackgroundMusic.isRunning()) {
            currentBackgroundMusic.stop();
            currentBackgroundMusic.close();
            currentBackgroundMusic = null;
        }
    }
    
    private void playBackgroundMusic(String soundFile, boolean loop) {
        stopBackgroundMusic(); // Stop any existing background music
        
        new Thread(() -> {
            try {
                File audioFile = new File(soundFile);
                if (!audioFile.exists()) {
                    InputStream audioStream = getClass().getResourceAsStream("/" + soundFile);
                    if (audioStream == null) {
                        return;
                    }
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioStream);
                    currentBackgroundMusic = AudioSystem.getClip();
                    currentBackgroundMusic.open(audioInputStream);
                    if (loop) {
                        currentBackgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
                    } else {
                        currentBackgroundMusic.start();
                    }
                } else {
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
                    currentBackgroundMusic = AudioSystem.getClip();
                    currentBackgroundMusic.open(audioInputStream);
                    if (loop) {
                        currentBackgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
                    } else {
                        currentBackgroundMusic.start();
                    }
                }
            } catch (Exception e) {
                System.err.println("Error playing background music " + soundFile + ": " + e.getMessage());
            }
        }).start();
    }
    
    private void playSoundEffect(String soundFile) {
        new Thread(() -> {
            try {
                File audioFile = new File(soundFile);
                if (!audioFile.exists()) {
                    InputStream audioStream = getClass().getResourceAsStream("/" + soundFile);
                    if (audioStream == null) {
                        return;
                    }
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioStream);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.start();
                    clip.addLineListener(event -> {
                        if (event.getType() == LineEvent.Type.STOP) {
                            clip.close();
                        }
                    });
                } else {
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.start();
                    clip.addLineListener(event -> {
                        if (event.getType() == LineEvent.Type.STOP) {
                            clip.close();
                        }
                    });
                }
            } catch (Exception e) {
                System.err.println("Error playing sound effect " + soundFile + ": " + e.getMessage());
            }
        }).start();
    }
    
    private void playAttackSound(Hero hero) {
        final String soundFile;
        if (hero instanceof Warrior) {
            soundFile = "sword_clash.wav";
        } else if (hero instanceof Mage) {
            soundFile = "fireball.wav";
        } else if (hero instanceof Assassin) {
            soundFile = "dagger_attack.wav";
        } else {
            return; // No sound for unknown class
        }
        
        // Play sound in a separate thread to avoid blocking
        new Thread(() -> {
            try {
                File audioFile = new File(soundFile);
                if (!audioFile.exists()) {
                    // Try classpath as fallback
                    InputStream audioStream = getClass().getResourceAsStream("/" + soundFile);
                    if (audioStream == null) {
                        return; // File not found
                    }
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioStream);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.start();
                    // Keep reference to prevent garbage collection
                    clip.addLineListener(event -> {
                        if (event.getType() == LineEvent.Type.STOP) {
                            clip.close();
                        }
                    });
                } else {
                    // Load from file system
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.start();
                    // Keep reference to prevent garbage collection
                    clip.addLineListener(event -> {
                        if (event.getType() == LineEvent.Type.STOP) {
                            clip.close();
                        }
                    });
                }
            } catch (Exception e) {
                // Print error for debugging (can be removed later)
                System.err.println("Error playing sound " + soundFile + ": " + e.getMessage());
            }
        }).start();
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
        
        // Show hero image again when switching to new class
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
                               "Type: damage = hero.useSpecialAbility();");
        } else if (battlePhase == 1) {
            // Get available classes (not yet used)
            java.util.List<String> available = new java.util.ArrayList<>();
            if (!usedClasses.contains("Warrior")) available.add("Warrior");
            if (!usedClasses.contains("Mage")) available.add("Mage");
            if (!usedClasses.contains("Assassin")) available.add("Assassin");
            
            StringBuilder classOptions = new StringBuilder();
            for (String className : available) {
                classOptions.append("To switch to ").append(className).append(": hero = new ").append(className).append("();\n");
            }
            
            instructions.setText("INSTRUCTIONS - POLYMORPHISM IN ACTION:\n\n" +
                               "Phase 2: The Goblin has defeated you!\n\n" +
                               "💡 Emergency Plan: Use class switch!\n" +
                               "Type your code to switch classes:\n\n" +
                               classOptions.toString() + "\n" +
                               "This demonstrates POLYMORPHISM:\n" +
                               "- Same variable 'hero' can hold different class types\n" +
                               "- The variable type (Hero) stays the same\n" +
                               "- But the actual object type changes!\n");
        } else if (battlePhase == 2) {
            instructions.setText("INSTRUCTIONS - POLYMORPHISM IN ACTION:\n\n" +
                               "Phase 2: You've switched classes!\n\n" +
                               "💡 First, use your special ability with the new class:\n" +
                               "Type: damage = hero.useSpecialAbility();\n\n" +
                               "After using your ability, the goblin will attack.\n" +
                               "Then you'll need to switch to another class.\n\n" +
                               "Notice: Same method call 'hero.useSpecialAbility()'\n" +
                               "but different classes produce different results!\n" +
                               "This is POLYMORPHISM! 🎯\n");
        } else if (battlePhase == 3) {
            instructions.setText("INSTRUCTIONS - FINAL PHASE - REDEMPTION:\n\n" +
                               "Phase 3: This is your last chance!\n\n" +
                               "💡 Use your special ability:\n\n" +
                               "Type: damage = hero.useSpecialAbility();\n\n" +
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
        return battleInstructionsArea;
    }
    
    private void updateBattleDisplay() {
        // Update hero battlefield portrait when class changes
        if (heroImageLabel != null) {
            if (selectedHero.isDefeated()) {
                // Show death.png when defeated
                heroImageLabel.setIcon(loadClassIcon("death.png", "Defeated", new Color(100, 100, 100)));
            } else {
                // Show normal hero icon when alive
                heroImageLabel.setIcon(getHeroBattleIcon());
            }
            heroImageLabel.setVisible(true);
        }
        // Update hero name label when class changes
        if (heroNameLabel != null) {
            heroNameLabel.setText(selectedHero.getName());
        }
        // Update hero HP when class changes
        updateHeroHP();
    }
    
    private void updateEnemyHP() {
        // Update enemy HP label
        if (enemyHPLabel != null && currentEnemy != null) {
            enemyHPLabel.setText("HP: " + currentEnemy.getHealth());
        }
    }
    
    private void updateHeroHP() {
        // Update hero HP label
        if (heroHPLabel != null && selectedHero != null) {
            heroHPLabel.setText("HP: " + selectedHero.getHealth());
        }
    }
    
    private void createUpgradeShop() {
        // Play upgrade shop background music
        playBackgroundMusic("upgrade.wav", true);
        
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
        
        // Hero image
        ImageIcon heroIcon = getHeroBattleIcon();
        JLabel heroImageLabel = new JLabel(heroIcon);
        heroImageLabel.setHorizontalAlignment(JLabel.CENTER);
        JLabel heroNameLabel = new JLabel(selectedHero.getName(), JLabel.CENTER);
        heroNameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        heroNameLabel.setForeground(new Color(0, 100, 200));
        
        JPanel heroImagePanel = new JPanel(new BorderLayout());
        heroImagePanel.setOpaque(false);
        heroImagePanel.add(heroImageLabel, BorderLayout.CENTER);
        heroImagePanel.add(heroNameLabel, BorderLayout.SOUTH);
        
        // Layout
        JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
        leftPanel.setOpaque(false);
        leftPanel.add(heroImagePanel, BorderLayout.NORTH);
        
        JPanel statsSection = new JPanel(new BorderLayout(5, 5));
        statsSection.setOpaque(false);
        statsSection.add(new JLabel("Hero Stats:", JLabel.CENTER), BorderLayout.NORTH);
        statsSection.add(new JScrollPane(statsArea), BorderLayout.CENTER);
        leftPanel.add(statsSection, BorderLayout.CENTER);
        
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
                playSoundEffect("upSfx.wav");
                message = "✅ Attack upgraded! +5 Attack";
            } else {
                playSoundEffect("fail.wav");
                message = "❌ Upgrade failed! Check if you have upgrade points or if attack is at max (100)";
            }
        } else if (upgradeHealthPattern.matcher(code).matches()) {
            boolean result = selectedHero.upgradeHealth();
            if (result) {
                success = true;
                playSoundEffect("upSfx.wav");
                message = "✅ Health upgraded! +20 Health";
            } else {
                playSoundEffect("fail.wav");
                message = "❌ Upgrade failed! Check if you have upgrade points or if health is at max (300)";
            }
        } else if (upgradeManaPattern.matcher(code).matches()) {
            boolean result = selectedHero.upgradeMana();
            if (result) {
                success = true;
                playSoundEffect("upSfx.wav");
                message = "✅ Mana upgraded! +10 Mana";
            } else {
                playSoundEffect("fail.wav");
                message = "❌ Upgrade failed! Check if you have upgrade points or if mana is at max (100)";
            }
        } else {
            playSoundEffect("fail.wav");
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
                // Navigate to completion screen after a delay
                Timer timer = new Timer(2000, e -> {
                    showCompletionScreen();
                });
                timer.setRepeats(false);
                timer.start();
            }
        } else {
            feedbackLabel.setForeground(Color.RED);
        }
        
        feedbackLabel.setText(message);
        codeInput.setText("");
    }
    
    private void showCompletionScreen() {
        // Stop any background music and play congrats music (loops until game closes)
        stopBackgroundMusic();
        playBackgroundMusic("congrats.wav", true);
        
        // Remove completion screen if it already exists
        Component[] components = mainPanel.getComponents();
        for (int i = components.length - 1; i >= 0; i--) {
            Component comp = components[i];
            if (comp.getName() != null && comp.getName().equals("completion")) {
                mainPanel.remove(i);
                break;
            }
        }
        
        JPanel completionPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, new Color(234, 244, 255), 0, getHeight(), new Color(210, 228, 255));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        completionPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        completionPanel.setName("completion");
        
        // Title
        JLabel titleLabel = new JLabel("🎉 CONGRATULATIONS! 🎉", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(new Color(30, 90, 170));
        
        JLabel subtitleLabel = new JLabel("You mastered the OOP RPG Adventure", JLabel.CENTER);
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(60, 90, 120));
        
        // Completion message
        JTextArea completionText = new JTextArea();
        completionText.setText("You've successfully completed the OOP RPG Adventure!\n\n" +
                              "Throughout this journey, you've learned the Four Pillars of Object-Oriented Programming:\n\n" +
                              "1. ABSTRACTION - You used methods without knowing their internal implementation\n" +
                              "2. ENCAPSULATION - You accessed private data through public methods\n" +
                              "3. INHERITANCE - You created objects of different classes that extend Hero\n" +
                              "4. POLYMORPHISM - You saw how the same method call produced different results\n\n" +
                              "Your hero: " + selectedHero.getName() + "\n" +
                              selectedHero.getStats() + "\n\n" +
                              "Thank you for playing! Keep coding and learning!");
        completionText.setFont(new Font("Arial", Font.PLAIN, 16));
        completionText.setEditable(false);
        completionText.setOpaque(false);
        completionText.setLineWrap(true);
        completionText.setWrapStyleWord(true);
        completionText.setAlignmentX(Component.CENTER_ALIGNMENT);
        completionText.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel cardPanel = new JPanel(new BorderLayout(10, 10));
        cardPanel.setOpaque(false);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 215, 240), 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(200, 215, 240));
        
        // Close button
        JButton closeButton = new JButton("Close Game");
        closeButton.setFont(new Font("Arial", Font.BOLD, 16));
        closeButton.setPreferredSize(new Dimension(160, 42));
        closeButton.setBackground(new Color(255, 189, 89));
        closeButton.setForeground(new Color(40, 40, 40));
        closeButton.setFocusPainted(false);
        closeButton.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        closeButton.addActionListener(e -> System.exit(0));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        buttonPanel.add(closeButton);
        
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);
        centerPanel.add(Box.createVerticalGlue());
        cardPanel.add(subtitleLabel, BorderLayout.NORTH);
        cardPanel.add(completionText, BorderLayout.CENTER);
        cardPanel.add(separator, BorderLayout.SOUTH);
        centerPanel.add(cardPanel);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(buttonPanel);
        centerPanel.add(Box.createVerticalGlue());
        
        completionPanel.add(titleLabel, BorderLayout.NORTH);
        completionPanel.add(centerPanel, BorderLayout.CENTER);
        
        mainPanel.add(completionPanel, "completion");
        cardLayout.show(mainPanel, "completion");
        
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main());
    }
}
