// Assassin class - Demonstrates INHERITANCE (Pillar 3 of OOP)
// Assassin extends Hero, inheriting all its properties and methods
public class Assassin extends Hero {
    
    public Assassin() {
        super("Assassin", 100, 30, 20, "A stealthy fighter with high attack and balanced stats");
    }
    
    @Override
    public int useSpecialAbility() {
        // Assassin's Backstab: Deals 2.5x attack damage (critical hit)
        return (int)(attack * 2.5);
    }
    
    public String getSpecialAbilityName() {
        return "Backstab";
    }
    
    @Override
    public int redemption() {
        // Assassin's Redemption: Ultimate one-shot attack
        return 999;
    }
}

