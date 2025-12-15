# RPG Game - Four Pillars of OOP Demonstration

A complete Java-based RPG game that demonstrates all four pillars of Object-Oriented Programming (OOP).

## Four Pillars Demonstrated

### 1. **ENCAPSULATION**
- **GameState.java**: Private fields with controlled access through public getters and protected methods
- Protected game states that can only be modified through controlled methods
- Private health, attack, and defense values in Character and Enemy classes

### 2. **INHERITANCE**
- **Character.java**: Base class for all playable characters
  - **Warrior.java**: Extends Character with high health and defense
  - **Mage.java**: Extends Character with high attack but low defense
  - **Rogue.java**: Extends Character with critical hit abilities
- **Enemy.java**: Base class for all enemies
  - **Goblin.java**: Weak enemy
  - **Orc.java**: Strong enemy with special attacks
  - **Dragon.java**: Boss enemy with fire breath

### 3. **POLYMORPHISM**
- Different attack behaviors for each character type:
  - Warriors: 30% chance for power strike (1.5x damage)
  - Mages: Magic attacks ignore defense
  - Rogues: 40% chance for critical hit (2x damage)
- Different attack behaviors for each enemy type:
  - Orcs: 25% chance for extra damage
  - Dragons: Always deals fire damage

### 4. **ABSTRACTION**
- **Combatable.java**: Interface defining combat contract
- **Character.java**: Abstract class with abstract method `getSpecialAbility()`
- **EnemyFactory.java**: Hides complexity of enemy creation

## Project Structure

```
src/rpg/
├── GameState.java              (Encapsulation)
├── Combatable.java             (Abstraction - Interface)
├── Character.java              (Inheritance & Abstraction)
│   ├── Warrior.java            (Inheritance & Polymorphism)
│   ├── Mage.java               (Inheritance & Polymorphism)
│   └── Rogue.java              (Inheritance & Polymorphism)
├── Enemy.java                  (Inheritance)
│   ├── Goblin.java             (Inheritance)
│   ├── Orc.java                (Inheritance & Polymorphism)
│   └── Dragon.java             (Inheritance & Polymorphism)
├── EnemyFactory.java           (Abstraction)
├── CharacterSelectionScreen.java (Character Selection UI)
└── RPGGame.java                (Main Game - All 4 Pillars)

```

## How to Compile and Run

### Using Command Line:

1. Navigate to the project directory:
```bash
cd OOP_Final-Proj
```

2. Compile all Java files:
```bash
javac -d bin src/rpg/*.java
```

3. Run the game:
```bash
java -cp bin rpg.RPGGame
```

### Using an IDE (Eclipse, IntelliJ, VS Code):

1. Import the project folder
2. Set `src` as the source folder
3. Run `RPGGame.java` as the main class

## Game Features

- **Character Selection Screen**: Beautiful RPG-style character selection at startup
  - Visual character cards with stats and descriptions
  - Each character has unique appearance (icons) and color theme
  - Detailed stat display (Health, Attack, Defense, Special Ability)
- **Character Classes**: 
  - **Warrior** ⚔️: High health (120) and defense (10), power strikes
  - **Mage** 🔮: High attack (30), low defense (5), magic attacks ignore defense
  - **Rogue** 🗡️: Balanced stats, high critical hit chance (40%)
- **Combat System**: Turn-based combat with different attack behaviors
- **Progression System**: Gain experience, level up, and collect gold
- **Enemy Variety**: Fight different enemies with unique behaviors
- **Victory Condition**: Defeat 5 enemies to win
- **GUI Interface**: Swing-based graphical user interface

## Learning Objectives

This project demonstrates:
- How encapsulation protects data and controls access
- How inheritance creates class hierarchies
- How polymorphism allows different behaviors through the same interface
- How abstraction simplifies complex systems

## Gameplay

1. **Character Selection**: At startup, choose your character class
   - View each character's stats, special abilities, and descriptions
   - Click "Select" on your preferred character
   - Click "Start Game" to begin
2. **Combat**: Click "Find Next Enemy" to encounter an enemy
3. **Fighting**: Use "Attack" or "Special Ability" to fight
   - Each character has unique combat behaviors (polymorphism)
4. **Progression**: Defeat enemies to gain experience and gold
5. **Victory**: Level up and defeat 5 enemies to win!

## Graphics Enhancement

See `GRAPHICS_GUIDE.md` for information on:
- What image assets you need for enhanced graphics
- How to add character portraits and sprites
- Tools and libraries for game graphics
- Implementation examples

