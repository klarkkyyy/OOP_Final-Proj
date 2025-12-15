package rpg;

/**
 * Orc class demonstrates INHERITANCE and POLYMORPHISM
 * - Extends Enemy (inheritance)
 * - Overrides attack method (polymorphism)
 */
public class Orc extends Enemy {
    
    public Orc() {
        super("Orc", 80, 18, 6, 35, 50);
        this.enemyType = "Strong Foe";
    }
    
    // Polymorphism - unique attack behavior
    @Override
    public int attack(Combatable target) {
        // Orcs have a chance to deal extra damage
        int baseDamage = getAttackPower();
        if (Math.random() < 0.25) {
            baseDamage = (int)(baseDamage * 1.3);
        }
        target.takeDamage(baseDamage);
        return baseDamage;
    }
}

