// Mage class - Demonstrates INHERITANCE (Pillar 3 of OOP)
// Mage extends Hero, inheriting all its properties and methods
public class Mage extends Hero {
    
    public Mage() {
        super("Mage", 80, 15, 50, "A powerful spellcaster with high mana and magical attacks");
    }
    
    @Override
    public String useSpecialAbility() {
        return "Mage casts 'Fireball' - deals magical damage to all enemies!";
    }
}

