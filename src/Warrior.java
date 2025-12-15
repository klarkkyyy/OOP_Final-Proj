// Warrior class - Demonstrates INHERITANCE (Pillar 3 of OOP)
// Warrior extends Hero, inheriting all its properties and methods
public class Warrior extends Hero {
    
    public Warrior() {
        super("Warrior", 150, 25, 10, "A brave fighter with high health and strong physical attacks");
    }
    
    @Override
    public String useSpecialAbility() {
        return "Warrior uses 'Power Strike' - deals massive physical damage!";
    }
}

