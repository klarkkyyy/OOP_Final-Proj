package rpg;

/**
 * Dragon class demonstrates INHERITANCE and POLYMORPHISM
 * - Extends Enemy (inheritance)
 * - Overrides attack method (polymorphism)
 */
public class Dragon extends Enemy {
    
    public Dragon() {
        super("Dragon", 150, 25, 10, 100, 150);
        this.enemyType = "Boss";
    }
    
    // Polymorphism - unique attack behavior
    @Override
    public int attack(Combatable target) {
        // Dragons have fire breath - always deals extra damage
        int baseDamage = getAttackPower();
        int fireDamage = (int)(baseDamage * 0.4);
        int totalDamage = baseDamage + fireDamage;
        target.takeDamage(totalDamage);
        return totalDamage;
    }
}

