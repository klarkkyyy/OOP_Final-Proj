package rpg;

/**
 * Goblin class demonstrates INHERITANCE
 * - Extends Enemy (inheritance)
 */
public class Goblin extends Enemy {
    
    public Goblin() {
        super("Goblin", 50, 12, 3, 20, 30);
        this.enemyType = "Weak Foe";
    }
}

