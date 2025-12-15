package rpg;

/**
 * GameState class demonstrates ENCAPSULATION
 * - Private fields with controlled access
 * - Protected methods for internal state management
 * - Public getters for safe external access
 */
public class GameState {
    // Private fields - encapsulation
    private int level;
    private int experience;
    private int gold;
    private boolean gameOver;
    private boolean victory;
    
    // Protected field for internal game mechanics
    protected int enemiesDefeated;
    
    public GameState() {
        this.level = 1;
        this.experience = 0;
        this.gold = 100;
        this.gameOver = false;
        this.victory = false;
        this.enemiesDefeated = 0;
    }
    
    // Public getters - controlled access to private data
    public int getLevel() {
        return level;
    }
    
    public int getExperience() {
        return experience;
    }
    
    public int getGold() {
        return gold;
    }
    
    public boolean isGameOver() {
        return gameOver;
    }
    
    public boolean isVictory() {
        return victory;
    }
    
    public int getEnemiesDefeated() {
        return enemiesDefeated;
    }
    
    // Protected methods - internal state management
    protected void addExperience(int exp) {
        this.experience += exp;
        checkLevelUp();
    }
    
    protected void addGold(int amount) {
        this.gold += amount;
    }
    
    protected void spendGold(int amount) {
        if (this.gold >= amount) {
            this.gold -= amount;
        }
    }
    
    protected void incrementEnemiesDefeated() {
        this.enemiesDefeated++;
        if (this.enemiesDefeated >= 5) {
            this.victory = true;
        }
    }
    
    protected void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
    
    private void checkLevelUp() {
        int expNeeded = level * 100;
        if (experience >= expNeeded) {
            level++;
            experience -= expNeeded;
        }
    }
}

