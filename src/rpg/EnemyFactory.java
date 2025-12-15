package rpg;

import java.util.Random;

/**
 * EnemyFactory class demonstrates ABSTRACTION
 * - Hides creation logic
 * - Provides simple interface for creating enemies
 */
public class EnemyFactory {
    private static Random random = new Random();
    
    /**
     * Creates a random enemy based on level
     * Abstraction - hides the complexity of enemy creation
     */
    public static Enemy createRandomEnemy(int level) {
        int enemyType = random.nextInt(100);
        
        if (level >= 5) {
            // Boss level
            return new Dragon();
        } else if (level >= 3 || enemyType < 30) {
            // Strong enemy
            return new Orc();
        } else {
            // Weak enemy
            return new Goblin();
        }
    }
}

