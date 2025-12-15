package rpg;

/**
 * Combatable interface demonstrates ABSTRACTION
 * - Defines contract for combat behavior
 * - Hides implementation details
 * - Allows different classes to implement combat differently
 */
public interface Combatable {
    /**
     * Perform an attack action
     * @param target The target to attack
     * @return Damage dealt
     */
    int attack(Combatable target);
    
    /**
     * Take damage from an attack
     * @param damage Amount of damage to take
     */
    void takeDamage(int damage);
    
    /**
     * Check if the entity is alive
     * @return true if alive, false otherwise
     */
    boolean isAlive();
    
    /**
     * Get current health
     * @return Current health points
     */
    int getHealth();
    
    /**
     * Get maximum health
     * @return Maximum health points
     */
    int getMaxHealth();
}

