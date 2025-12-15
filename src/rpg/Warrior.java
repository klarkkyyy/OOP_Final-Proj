package rpg;

/**
 * Warrior class demonstrates INHERITANCE and POLYMORPHISM
 * - Extends Character (inheritance)
 * - Overrides attack method with unique behavior (polymorphism)
 */
public class Warrior extends Character {
    
    public Warrior(String name) {
        super(name, 120, 25, 10);
        this.characterType = "Warrior";
    }
    
    @Override
    public String getSpecialAbility() {
        return "Power Strike - Deals 1.5x damage";
    }
    
    // Polymorphism - unique attack behavior
    @Override
    public int attack(Combatable target) {
        // Warrior's special: 30% chance for power strike
        if (Math.random() < 0.3) {
            int damage = (int)(getAttackPower() * 1.5);
            target.takeDamage(damage);
            return damage;
        }
        return super.attack(target);
    }
}

