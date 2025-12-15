package rpg;

/**
 * Character class demonstrates INHERITANCE and ABSTRACTION
 * - Base class for all playable characters
 * - Implements Combatable interface (abstraction)
 * - Provides common functionality for subclasses
 */
public abstract class Character implements Combatable {
    // Encapsulation - private fields
    private String name;
    private int health;
    private int maxHealth;
    private int attackPower;
    private int defense;
    
    // Protected field for inheritance
    protected String characterType;
    
    public Character(String name, int maxHealth, int attackPower, int defense) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.attackPower = attackPower;
        this.defense = defense;
    }
    
    // Public getters - encapsulation
    public String getName() {
        return name;
    }
    
    @Override
    public int getHealth() {
        return health;
    }
    
    @Override
    public int getMaxHealth() {
        return maxHealth;
    }
    
    public int getAttackPower() {
        return attackPower;
    }
    
    public int getDefense() {
        return defense;
    }
    
    public String getCharacterType() {
        return characterType;
    }
    
    // Protected setter for inheritance
    protected void setHealth(int health) {
        this.health = Math.max(0, Math.min(health, maxHealth));
    }
    
    protected void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }
    
    // Abstract method - must be implemented by subclasses (abstraction)
    public abstract String getSpecialAbility();
    
    // Polymorphism - can be overridden by subclasses
    @Override
    public int attack(Combatable target) {
        int damage = attackPower;
        target.takeDamage(damage);
        return damage;
    }
    
    @Override
    public void takeDamage(int damage) {
        int actualDamage = Math.max(1, damage - defense);
        setHealth(health - actualDamage);
    }
    
    @Override
    public boolean isAlive() {
        return health > 0;
    }
    
    // Protected method for healing (inheritance)
    protected void heal(int amount) {
        setHealth(health + amount);
    }
    
    public String getStatus() {
        return String.format("%s (%s) - HP: %d/%d | ATK: %d | DEF: %d",
                name, characterType, health, maxHealth, attackPower, defense);
    }
}

