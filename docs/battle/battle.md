# Battle

## Table of Contents

- [Overview](#overview)
- [Battle Flow](#battle-flow)
- [Combat Mechanics](#combat-mechanics)
    - [Basic Attacks](#basic-attacks)
    - [Energy and Active Skills](#energy-and-active-skills)
    - [Passive Skills](#passive-skills)
    - [Status Effects](#status-effects)
- [Team Composition Strategy](#team-composition-strategy)
- [Mechanics](#mechanics)
- [Other ideas](#other-ideas)

## Overview

The battle system will be a **turn-based** combat system where two teams of (up to 6) dinosaurs fight.  
The battle progresses automatically with each dinosaur taking turns to attack.

## Battle Flow

1. **Team Setup**: The player selects **up to 6 dinosaurs** to fight in the battle. Dinosaurs are positioned
   strategically based on their role (high HP on front-line, support on back-line)
2. **Turn Order**: Turns are determined by each dinosaur’s **Speed** stat. The fastest dino attacks first, followed by
   the next fastest, and so on.
3. **Basic Attacks & Energy Gain**: Every dino starts the battle by performing a [Basic Attack](#basic-attacks), which
   grants **Energy**.
4. **Skill Activation**: When a dino reaches 100 Energy, it unleashes its [Active Skill](#energy-and-active-skills), which
   has unique effects like damage, crowd control, healing, or buffs.
5. **Rounds**: Each round consists of **one attack per dinosaur** (excluding [Passive Skills](#passive-skills)).
6. **Victory Conditions**: The battle ends when all dinosaurs on one team are defeated.

## Combat Mechanics

### **Basic Attacks**

- Each dinosaur has a **basic attack** that deals normal damage and generates **Energy**.
- Basic attacks do not apply special effects (unless modified by passive skills).

### **Energy and Active Skills**

- Every attack grants **Energy**.
- When a dino reaches 100 Energy, it unleashes its **Active Skill** on their next turn.
- Active Skills can have various effects:
    - **High damage** to single or multiple enemies.
    - **Debuffs** like stun, poison, bleed, or accuracy reduction.
    - **Buffs** like healing, shields, or attack boosts.

### **Passive Skills**

- Dinosaurs have **three (might change) passive skills**, which provide additional effects such as:
    - Increasing stats (Attack, HP, Speed, etc.).
    - Granting special effects when attacking or getting hit.
    - Reacting to enemy skills (e.g. counterattacks, healing, or damage mitigation).

### **Status Effects**

- **Stun**: The dino skips its turn.
- **Bleed**: Deals damage over time based on target’s HP.
- **Poison**: Similar to Bleed but can stack.
- **Burn**: Deals flat damage each round.
- **Slow**: Reduces Speed, delaying turns.
- **Taunt**: Forces enemies to attack the taunting dino (e.g. an allied Kentrosaurus might have a special state in which
  it uses its spikes to reflect all enemies' attack, to any allied dino).
- **Dodge**: A chance to completely avoid an attack.

## Team Composition Strategy

- **Front-line (Tanky Dinos)**: Absorb damage and apply crowd control.
- **Back-line (Damage Dealers & Healers)**: Provide high DPS or sustain.

## Mechanics

- Front-line dinos have a higher chance of getting attacked

## Other ideas

1. **([Passive skills](#passive-skills)) Circumstantial boosts**:
    - Some dinos might have passive abilities which give them boosts in certain circumstances, e.g.:
      - Spinosaurus: has a water boost when the battle takes place in a water-rich environment
      - Velociraptor: has a buff when there are multiple dinos of the same species on your team lineup (pack bonus)
