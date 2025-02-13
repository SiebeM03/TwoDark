# Dinos

## Table of Contents

- [Occurrence](#occurrence)
    - [Dino rarity](#dino-rarity)
    - [Stats](#stats)
- [Obtaining](#obtaining)
- [Usage](#usage)
    - [PvP dinos](#pvp-dinos)
    - [PvE dinos](#pve-dinos)
    - [Utility dinos](#utility-dinos)
- [Mechanics](#mechanics)
    - [Stats](#stats)
    - [Levelling](#levelling)
    - [Breeding](#breeding)
- [More ideas](#more-ideas)

## Occurrence

- Dinos **spawn randomly** across the map. They are **invisible** to the player until they come close to them.
- Once a player comes close to a wild dino they are granted the option to **either tame or kill** the wild dino:
    - **Taming**: when a dino is successfully tamed, it is added to your inventory/dino list.
    - **Killing**: you can select a few of your dinos to fight the wild dino, your dinos then **battle the wild dino**.
      Dinos might die during this battle (not sure yet if they should actually disappear or not) so choose your team
      wisely.  
      Once the dino is killed you can harvest it to **gain resources**
      ( [meat](../resources.md#meat) / [hide](../resources.md#hide) ). The amount scales depending on the size of the
      killed dino.

### Dino rarity

- **Common**
    - Spawn often in early-game areas
    - Levels vary
- **Rare**
    - Spawn less frequent and in more late-game areas
    - Levels tend to be lower on average

### Stats

- Dinos have certain stats (even wild dinos) for:
    - **Health**: the dino's health
    - **Damage**: the damage dealt by the dino
    - **Critical chance**: the chance an attack turns into a critical attack
    - **Critical damage**: the damage amplifier for critical attacks
    - **Speed**: determines the **order of attack** in a battle, the dino with the highest speed attacks first (not sure
      if this stat should be able to get mutated, might scale too large)

## Obtaining

- Dinos can be tamed with **certain equipment** (tranquilizer arrow with a crossbow or something completely different).
- The dino's stats will only become visible once it is tamed so you might still end up with a dino that has awful stats.

## Usage

Different dinos have **different uses** from fighting to farming to granting buffs.

### PvP dinos

Some dinos are specifically meant for **battle**:

- Giga: high damage, not very tanky
- Rex: solid damage with high HP

### PvE dinos

These dinos are more focused on **farming and utility**

- Ankylosaurus: improved metal harvesting
- Doedicurus: improved stone harvesting

### Utility dinos

Give the player (or other dinos) a **boost** when a utility dino is equipped (e.g. speed boost, dmg boost, ...)

## Mechanics

### Stats

Every dino has **underlying points** for every stat.  
These underlying points are determined once the dino is tamed (see [obtaining](#obtaining)).  
These points are similar for every species (they have the same range).

The actual stats are based on these underlying points, the actual stats will **scale differently for every species**
e.g.:

- A giga with 50 (underlying) points into damage will have 600 actual damage, a rex with 50 points into damage will only
  have 400 actual damage
- A giga with 100 points into health will have 30k actual health, a rex with 100 points into health will have 50k actual
  health.  
  This way different species automatically end up with different usages: giga's are very damage based, they have very
  high damage but lower health while rexes are more of a hybrid with solid damage while also being fairly tanky.
  Therefore, rexes will probably be better used in boss fights since they can take a few more hits than giga's could.

### Levelling

- A dino can obtain XP through various ways:
    - **Over time XP** will slowly be gained
    - **Player interactions** will give small XP increases (e.g. when the player uses a dino for farming)
    - **Battles** (especially bosses) will give large amounts of XP to all dinos that were deployed in the battle.

Every XP level can be **used to increase any stat** of the dino even further.  
Higher XP levels will take more XP and thus longer to obtain.

### Breeding

As mentioned in [stats](#stats), dinos have an underlying point system for each stat.  
These **stats can be improved** in 2 ways:

1. **Leveling**: a certain amount of XP levels can be put into any stat, XP is slowly gained over time or faster through
   battles.
2. **Breed mutations**: when 2 dinos of the **same species breed**, an egg/baby will be spawned. This baby's stats will
   be based on those of the parents. For every stat, it inherits the same amount of points from either parent. There is
   a small chance for the baby to have a **mutation on a random stat**. This mutation will slightly increase the points
   of this stat. Then the player can breed the dino with the mutated stat and try to get another mutation on the same
   stat to continuously improve it step by step.

Breeding 2 dinos will cost both **time and resources**:

- Maybe each breeding interval requires a certain item that is either crafted or maybe only obtainable through
  challenges
- The breeding itself will take time and the egg hatching/gestation (pregnancy) will also take time
- Once the baby is born it will require the player's attention: the player has to make sure it doesn't starve, the
  player will have to do certain missions with it to get imprint and thus better stats on the dino, ...

## More ideas

1. **([Rarity](#dino-rarity)) Legendary dinos**:
    - Very rare spawns (chance at the end of a boss fight, chance inside a dangerous cave, ...)
    - Kinda like shiny pokemons: normal dinos but with a small chance to be "better"
    - Special (harder) way of taming them, higher risk of failing the tame
2. **([Utility dinos](#utility-dinos)) Flying dinos**:
    - Faster traveling
    - Can fly over water
    - Can fly to the top of mountains
3. **([Levelling](#levelling)) Level cap**:
    - Not sure yet if there should be a cap to XP levels.
    - If the higher levels end up requiring large amounts of XP this might not be a problem, then the player can still
      use XP to strengthen their dinos slowly over time