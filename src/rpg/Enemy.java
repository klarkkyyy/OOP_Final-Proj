package rpg;

/**
 * Enemy class demonstrates INHERITANCE
 * - Base class for all enemies
 * - Implements Combatable interface (abstraction)
 * - Provides common enemy functionality
 */
public abstract class Enemy implements Combatable {
    // Encapsulation - private fields
    private String name;
    private int health;
    private int maxHealth;
    private int attackPower;
    private int defense;
    private int goldReward;
    private int expReward;
    
    // Protected field for inheritance
    protected String enemyType;
    
    public Enemy(String name, int maxHealth, int attackPower, int defense, int goldReward, int expReward) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.attackPower = attackPower;
        this.defense = defense;
        this.goldReward = goldReward;
        this.expReward = expReward;
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
    
    public int getGoldReward() {
        return goldReward;
    }
    
    public int getExpReward() {
        return expReward;
    }
    
    public String getEnemyType() {
        return enemyType;
    }
    
    // Protected setter for inheritance
    protected void setHealth(int health) {
        this.health = Math.max(0, Math.min(health, maxHealth));
    }
    
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
    
    public String getStatus() {
        return String.format("%s (%s) - HP: %d/%d | ATK: %d | DEF: %d",
                name, enemyType, health, maxHealth, attackPower, defense);
    }
}

