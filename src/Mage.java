// Mage class - Demonstrates INHERITANCE (Pillar 3 of OOP)
// Mage extends Hero, inheriting all its properties and methods
public class Mage extends Hero {
    
    public Mage() {
        super("Mage", 80, 15, 50, "A powerful spellcaster with high mana and magical attacks");
    }
    
    @Override
    public int useSpecialAbility() {
        // Mage's Fireball: Deals 3x attack damage (magical)
        return attack * 3;
    }
    
    public String getSpecialAbilityName() {
        return "Fireball";
    }
    
    @Override
    public int redemption() {
        // Mage's Redemption: Ultimate one-shot attack
        return 999;
    }
}

