// Base Hero class - Demonstrates ABSTRACTION (Pillar 1 of OOP)
// This abstract class defines the common structure for all heroes
public abstract class Hero {
    private String name;
    protected int maxHealth; // Protected so subclasses can access
    protected int currentHealth; // Protected so subclasses can access
    protected int attack; // Protected so subclasses can access
    private int mana;
    private String description;
    private int upgradePoints; // Private field - demonstrates ENCAPSULATION
    
    // Constructor (Initialize hero attributes)
    public Hero(String name, int health, int attack, int mana, String description) {
        this.name = name;
        this.maxHealth = health;
        this.currentHealth = health;
        this.attack = attack;
        this.mana = mana;
        this.description = description;
        this.upgradePoints = 0; 
    }
    
    // Getters - Demonstrates ENCAPSULATION (Pillar 2 of OOP)
    public String getName() {
        return name;
    }
    
    public int getHealth() {
        return currentHealth;
    }
    
    public int getMaxHealth() {
        return maxHealth;
    }
    
    public int getAttack() {
        return attack;
    }
    
    public int getMana() {
        return mana;
    }
    
    public String getDescription() {
        return description;
    }
    
    // Abstract method - must be implemented by subclasses
    // This demonstrates POLYMORPHISM - same method name, different implementations
    public abstract int useSpecialAbility();
    
    // Redemption - final ability, all classes have it but with different implementations
    // This also demonstrates POLYMORPHISM
    public abstract int redemption();
    
    public void takeDamage(int damage) {
        currentHealth = Math.max(0, currentHealth - damage);
    }
    
    public boolean isDefeated() {
        return currentHealth <= 0;
    }
    
    public int attack() {
        return attack;
    }
    
    // ENCAPSULATION: Private field accessed only through controlled methods
    public void addUpgradePoints(int points) {
        if (points > 0) {
            this.upgradePoints += points;
        }
    }
    
    public int getUpgradePoints() {
        return upgradePoints;
    }
    
    // Upgrade methods - demonstrate ENCAPSULATION
    // Users can't directly modify stats, must use these methods
    // Internal validation and logic is hidden (ABSTRACTION)
    public boolean upgradeAttack() {
        if (upgradePoints > 0 && attack < 100) { // Max limit check
            attack += 5;
            upgradePoints--;
            return true;
        }
        return false; // Upgrade failed - validation hidden from user
    }
    
    public boolean upgradeHealth() {
        if (upgradePoints > 0 && maxHealth < 300) {
            maxHealth += 20;
            currentHealth += 20; // Also heal when upgrading health
            upgradePoints--;
            return true;
        }
        return false;
    }
    
    public boolean upgradeMana() {
        if (upgradePoints > 0 && mana < 100) {
            mana += 10;
            upgradePoints--;
            return true;
        }
        return false;
    }
    
    // Method to get upgrade status (abstraction - hides implementation)
    public String getUpgradeStatus() {
        return String.format("Upgrade Points: %d\nAttack: %d (Max: 100)\nHealth: %d/%d (Max: 300)\nMana: %d (Max: 100)",
                           upgradePoints, attack, currentHealth, maxHealth, mana);
    }
    
    public String getHealthBar() {
        int barLength = 20;
        int filled = (int) ((double) currentHealth / maxHealth * barLength);
        StringBuilder bar = new StringBuilder("[");
        for (int i = 0; i < barLength; i++) {
            if (i < filled) {
                bar.append("█");
            } else {
                bar.append("░");
            }
        }
        bar.append("] ").append(currentHealth).append("/").append(maxHealth);
        return bar.toString();
    }
    
    // Display stats
    public String getStats() {
        return String.format("Class: %s\nHealth: %d/%d\nAttack: %d\nMana: %d\nDescription: %s", 
                           name, currentHealth, maxHealth, attack, mana, description);
    }
}

