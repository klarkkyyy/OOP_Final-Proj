// Assassin class - Demonstrates INHERITANCE (Pillar 3 of OOP)
// Assassin extends Hero, inheriting all its properties and methods
public class Assassin extends Hero {
    
    public Assassin() {
        super("Assassin", 100, 30, 20, "A stealthy fighter with high attack and balanced stats");
    }
    
    @Override
    public String useSpecialAbility() {
        return "Assassin uses 'Backstab' - critical hit with increased damage!";
    }
}

