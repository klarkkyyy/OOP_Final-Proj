package rpg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * CharacterSelectionScreen - RPG-style character selection screen
 * Shows each character with their stats, description, and visual representation
 */
public class CharacterSelectionScreen extends JFrame {
    private Character selectedCharacter;
    private JButton startButton;
    private JPanel cardsPanel; // Make it accessible to inner class
    
    public CharacterSelectionScreen() {
        setTitle("Select Your Character");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Title
        JLabel titleLabel = new JLabel("Choose Your Hero", JLabel.CENTER);
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 28));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);
        
        // Character cards panel
        cardsPanel = new JPanel(new GridLayout(1, 3, 20, 20));
        cardsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        cardsPanel.setBackground(new Color(240, 240, 245));
        
        // Create character cards
        CharacterCard warriorCard = new CharacterCard(new Warrior("Warrior"), 
            "⚔️", new Color(139, 69, 19), "A mighty warrior with high health and defense. Perfect for players who prefer tanking damage and dealing consistent strikes.");
        CharacterCard mageCard = new CharacterCard(new Mage("Mage"), 
            "🔮", new Color(75, 0, 130), "A powerful spellcaster with devastating magic attacks. Low defense but high damage output.");
        CharacterCard rogueCard = new CharacterCard(new Rogue("Rogue"), 
            "🗡️", new Color(0, 100, 0), "A swift assassin with high critical hit chance. Balanced stats with deadly precision strikes.");
        
        cardsPanel.add(warriorCard);
        cardsPanel.add(mageCard);
        cardsPanel.add(rogueCard);
        
        add(cardsPanel, BorderLayout.CENTER);
        
        // Bottom panel with start button
        JPanel bottomPanel = new JPanel(new FlowLayout());
        startButton = new JButton("Start Game");
        startButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        startButton.setEnabled(false);
        startButton.setPreferredSize(new Dimension(200, 50));
        startButton.addActionListener(e -> {
            if (selectedCharacter != null) {
                dispose();
                // Start the main game with selected character
                SwingUtilities.invokeLater(() -> {
                    new RPGGame(selectedCharacter).setVisible(true);
                });
            }
        });
        bottomPanel.add(startButton);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(bottomPanel, BorderLayout.SOUTH);
        
        setSize(900, 600);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(240, 240, 245));
    }
    
    /**
     * Inner class for character selection cards
     */
    private class CharacterCard extends JPanel {
        private Character character;
        private JButton selectButton;
        private boolean isSelected = false;
        
        public CharacterCard(Character character, String icon, Color themeColor, String description) {
            this.character = character;
            setLayout(new BorderLayout());
            setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
            ));
            setBackground(Color.WHITE);
            setPreferredSize(new Dimension(250, 400));
            
            // Character icon and name
            JPanel headerPanel = new JPanel(new BorderLayout());
            headerPanel.setBackground(Color.WHITE);
            
            // Try to load character image, fallback to emoji
            JLabel iconLabel = loadCharacterImage(character, icon);
            headerPanel.add(iconLabel, BorderLayout.CENTER);
            
            JLabel nameLabel = new JLabel(character.getCharacterType(), JLabel.CENTER);
            nameLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
            nameLabel.setForeground(themeColor);
            headerPanel.add(nameLabel, BorderLayout.SOUTH);
            
            add(headerPanel, BorderLayout.NORTH);
            
            // Stats panel - make it scrollable
            JPanel statsPanel = new JPanel(new GridLayout(0, 1, 5, 5));
            statsPanel.setBackground(new Color(250, 250, 255));
            statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            
            statsPanel.add(createStatLabel("❤️ Health", character.getMaxHealth() + "", themeColor));
            statsPanel.add(createStatLabel("⚔️ Attack", character.getAttackPower() + "", themeColor));
            statsPanel.add(createStatLabel("🛡️ Defense", character.getDefense() + "", themeColor));
            
            // Special ability might be long, so use HTML with word wrapping
            String specialText = character.getSpecialAbility();
            JLabel specialLabel = new JLabel(String.format(
                "<html><div style='width:200px;'><b style='color:#333'>✨ Special:</b> <span style='color:rgb(%d,%d,%d);font-weight:bold'>%s</span></div></html>",
                themeColor.getRed(), themeColor.getGreen(), themeColor.getBlue(), specialText));
            specialLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
            specialLabel.setForeground(new Color(50, 50, 50));
            specialLabel.setVerticalAlignment(JLabel.TOP);
            statsPanel.add(specialLabel);
            
            // Wrap stats panel in scroll pane
            JScrollPane statsScroll = new JScrollPane(statsPanel);
            statsScroll.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));
            statsScroll.setBackground(new Color(250, 250, 255));
            statsScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            statsScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            statsScroll.getVerticalScrollBar().setUnitIncrement(10);
            
            add(statsScroll, BorderLayout.CENTER);
            
            // Description
            JTextArea descArea = new JTextArea(description);
            descArea.setEditable(false);
            descArea.setLineWrap(true);
            descArea.setWrapStyleWord(true);
            descArea.setBackground(new Color(245, 245, 250));
            descArea.setForeground(new Color(50, 50, 50));
            descArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 11));
            descArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
            JScrollPane descScroll = new JScrollPane(descArea);
            descScroll.setPreferredSize(new Dimension(0, 80));
            descScroll.setBorder(null);
            descScroll.setBackground(Color.WHITE);
            add(descScroll, BorderLayout.SOUTH);
            
            // Select button - use lighter colors for black text visibility
            selectButton = new JButton("Select");
            // Make button color lighter so black text is visible
            // Use lighter version of theme color (add white to make it lighter)
            Color buttonColor = new Color(
                Math.min(255, themeColor.getRed() + 80),
                Math.min(255, themeColor.getGreen() + 80),
                Math.min(255, themeColor.getBlue() + 80)
            );
            // Ensure it's light enough for black text - if too dark, make it lighter
            float[] hsb = Color.RGBtoHSB(buttonColor.getRed(), buttonColor.getGreen(), buttonColor.getBlue(), null);
            if (hsb[2] < 0.6f) { // If brightness < 60%, make it lighter
                buttonColor = Color.getHSBColor(hsb[0], Math.min(0.7f, hsb[1]), Math.min(0.9f, hsb[2] + 0.3f));
            }
            
            selectButton.setBackground(buttonColor);
            // Use black text for maximum visibility on light/dark backgrounds
            selectButton.setForeground(Color.BLACK);
            selectButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
            selectButton.setFocusPainted(false);
            // Add a darker border for better definition
            Color borderColor = buttonColor.darker();
            selectButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor, 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
            ));
            // Ensure the button is opaque and content area is filled
            selectButton.setOpaque(true);
            selectButton.setContentAreaFilled(true);
            selectButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Deselect all other cards
                    for (Component comp : cardsPanel.getComponents()) {
                        if (comp instanceof CharacterCard && comp != CharacterCard.this) {
                            ((CharacterCard) comp).deselect();
                        }
                    }
                    // Select this card
                    select();
                }
            });
            
            JPanel buttonPanel = new JPanel();
            buttonPanel.setBackground(Color.WHITE);
            buttonPanel.add(selectButton);
            add(buttonPanel, BorderLayout.SOUTH);
        }
        
        /**
         * Loads character image if available, otherwise uses emoji icon
         */
        private JLabel loadCharacterImage(Character character, String fallbackIcon) {
            JLabel iconLabel = new JLabel("", JLabel.CENTER);
            
            // Use ImageLoader utility to find and load the image
            ImageIcon imageIcon = ImageLoader.loadCharacterImage(character.getCharacterType());
            
            if (imageIcon != null) {
                // Scale the image
                ImageIcon scaledIcon = ImageLoader.scaleImage(imageIcon, 150, 150);
                if (scaledIcon != null) {
                    iconLabel.setIcon(scaledIcon);
                    return iconLabel;
                }
            }
            
            // Fallback to emoji icon
            iconLabel.setText(fallbackIcon);
            iconLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 80));
            return iconLabel;
        }
        
        private JLabel createStatLabel(String label, String value, Color color) {
            JLabel statLabel = new JLabel(String.format("<html><b style='color:#333'>%s:</b> <span style='color:rgb(%d,%d,%d);font-weight:bold'>%s</span></html>", 
                label, color.getRed(), color.getGreen(), color.getBlue(), value));
            statLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
            statLabel.setForeground(new Color(50, 50, 50));
            return statLabel;
        }
        
        public void select() {
            isSelected = true;
            selectedCharacter = character;
            setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 215, 0), 3),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
            ));
            selectButton.setText("Selected ✓");
            selectButton.setBackground(new Color(255, 215, 0));
            selectButton.setForeground(Color.BLACK); // Already black, keeping it
            startButton.setEnabled(true);
        }
        
        public void deselect() {
            isSelected = false;
            Color themeColor;
            if (character instanceof Warrior) {
                themeColor = new Color(139, 69, 19);
            } else if (character instanceof Mage) {
                themeColor = new Color(75, 0, 130);
            } else {
                themeColor = new Color(0, 100, 0);
            }
            setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
            ));
            selectButton.setText("Select");
            // Use lighter button color for black text visibility
            Color buttonColor = new Color(
                Math.min(255, themeColor.getRed() + 80),
                Math.min(255, themeColor.getGreen() + 80),
                Math.min(255, themeColor.getBlue() + 80)
            );
            // Ensure it's light enough for black text
            float[] hsb = Color.RGBtoHSB(buttonColor.getRed(), buttonColor.getGreen(), buttonColor.getBlue(), null);
            if (hsb[2] < 0.6f) {
                buttonColor = Color.getHSBColor(hsb[0], Math.min(0.7f, hsb[1]), Math.min(0.9f, hsb[2] + 0.3f));
            }
            selectButton.setBackground(buttonColor);
            // Use black text for better visibility
            selectButton.setForeground(Color.BLACK);
            selectButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
            Color borderColor = buttonColor.darker();
            selectButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor, 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
            ));
            selectButton.setOpaque(true);
            selectButton.setContentAreaFilled(true);
        }
    }
}

