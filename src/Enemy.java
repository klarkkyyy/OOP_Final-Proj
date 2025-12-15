// Enemy class for battle system
public class Enemy {
    private String name;
    private int health;
    private int attack;
    private int maxHealth;
    
    public Enemy(String name, int health, int attack) {
        this.name = name;
        this.health = health;
        this.maxHealth = health;
        this.attack = attack;
    }
    
    public String getName() {
        return name;
    }
    
    public int getHealth() {
        return health;
    }
    
    public int getMaxHealth() {
        return maxHealth;
    }
    
    public int getAttack() {
        return attack;
    }
    
    public void takeDamage(int damage) {
        health = Math.max(0, health - damage);
    }
    
    public boolean isDefeated() {
        return health <= 0;
    }
    
    public int attack() {
        return attack;
    }
    
    public String getHealthBar() {
        int barLength = 20;
        int filled = (int) ((double) health / maxHealth * barLength);
        StringBuilder bar = new StringBuilder("[");
        for (int i = 0; i < barLength; i++) {
            if (i < filled) {
                bar.append("█");
            } else {
                bar.append("░");
            }
        }
        bar.append("] ").append(health).append("/").append(maxHealth);
        return bar.toString();
    }
}


