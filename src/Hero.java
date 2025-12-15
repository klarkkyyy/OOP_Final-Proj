// Base Hero class - Demonstrates ABSTRACTION (Pillar 1 of OOP)
// This abstract class defines the common structure for all heroes
public abstract class Hero {
    protected String name;
    protected int health;
    protected int attack;
    protected int mana;
    protected String description;
    
    // Constructor
    public Hero(String name, int health, int attack, int mana, String description) {
        this.name = name;
        this.health = health;
        this.attack = attack;
        this.mana = mana;
        this.description = description;
    }
    
    // Getters - Demonstrates ENCAPSULATION (Pillar 2 of OOP)
    public String getName() {
        return name;
    }
    
    public int getHealth() {
        return health;
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
    public abstract String useSpecialAbility();
    
    // Display stats
    public String getStats() {
        return String.format("Name: %s\nHealth: %d\nAttack: %d\nMana: %d\nDescription: %s", 
                           name, health, attack, mana, description);
    }
}

