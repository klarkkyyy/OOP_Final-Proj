package rpg;

/**
 * Rogue class demonstrates INHERITANCE and POLYMORPHISM
 * - Extends Character (inheritance)
 * - Overrides attack method with unique behavior (polymorphism)
 */
public class Rogue extends Character {
    
    public Rogue(String name) {
        super(name, 90, 20, 8);
        this.characterType = "Rogue";
    }
    
    @Override
    public String getSpecialAbility() {
        return "Critical Hit - 40% chance for 2x damage";
    }
    
    // Polymorphism - unique attack behavior
    @Override
    public int attack(Combatable target) {
        // Rogue's special: High critical hit chance
        if (Math.random() < 0.4) {
            int damage = getAttackPower() * 2;
            target.takeDamage(damage);
            return damage;
        }
        return super.attack(target);
    }
}

