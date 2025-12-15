package rpg;

/**
 * Mage class demonstrates INHERITANCE and POLYMORPHISM
 * - Extends Character (inheritance)
 * - Overrides attack method with unique behavior (polymorphism)
 */
public class Mage extends Character {
    
    public Mage(String name) {
        super(name, 80, 30, 5);
        this.characterType = "Mage";
    }
    
    @Override
    public String getSpecialAbility() {
        return "Fireball - Ignores 50% of defense";
    }
    
    // Polymorphism - unique attack behavior
    @Override
    public int attack(Combatable target) {
        // Mage's special: Magic attacks ignore half of defense
        int baseDamage = getAttackPower();
        int damage = baseDamage + (int)(baseDamage * 0.5); // Extra magic damage
        target.takeDamage(damage);
        return damage;
    }
}

