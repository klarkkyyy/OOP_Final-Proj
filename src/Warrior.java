// Warrior class - Demonstrates INHERITANCE (Pillar 3 of OOP)
// Warrior extends Hero, inheriting all its properties and methods
public class Warrior extends Hero {
    
    public Warrior() {
        super("Warrior", 150, 25, 10, "A brave fighter with high health and strong physical attacks");
    }
    
    @Override
    public int useSpecialAbility() {
        // Warrior's Power Strike: Deals 2x attack damage
        return attack * 2;
    }
    
    public String getSpecialAbilityName() {
        return "Power Strike";
    }
    
    @Override
    public int redemption() {
        // Warrior's Redemption: Ultimate one-shot attack
        return 999;
    }
}

