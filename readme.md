# RPGarnet
RPG plugin for Spigot 1.19.3
Version 1.0.0

There are 8 different statistic that can be improved during the normal gameplay:
- [Armor](https://github.com/MattiaBiancini/RPGarnet#Armor)
- [Attack Speed](https://github.com/MattiaBiancini/RPGarnet#Attack-Speed)
- [Damage](https://github.com/MattiaBiancini/RPGarnet#Damage)
- [Evasion](https://github.com/MattiaBiancini/RPGarnet#Evasion)
- [Health](https://github.com/MattiaBiancini/RPGarnet#Health)
- [Knockback Resistance](https://github.com/MattiaBiancini/RPGarnet#Knockback-Resistance)
- [Luck](https://github.com/MattiaBiancini/RPGarnet#Luck)
- [Movement Speed](https://github.com/MattiaBiancini/RPGarnet#Movement-Speed)

## Summary other content:
- [Custom Item](https://github.com/MattiaBiancini/RPGarnet#Custom-Item)
- [Game Mechanics](https://github.com/MattiaBiancini/RPGarnet#Game-Mechanics)
- [Table Chart](https://github.com/MattiaBiancini/RPGarnet#Table-Chart)

*Note: In case a player dies, all experience accumulated for each statistic is reset or, if the experience was already 0, the statistic is reduced by 1 level.*

## Armor
This statistic modifies the player's base **armor**. Armor pieces added to the armor slot will increase the player's armor value. The base value of this stat do not change the original  vanilla minecraft value.

You gain **1 experience point** for every **half-heart of true damage you take**, regardless of which hostile mob attacked you.
| Armor Info | Values |
| ------------ | ------------ |
| Base | 0 |
| Additive | 0.5 |
| Max Level | 30 |

## Attack Speed
This statistic modifies the player's **attack speed**. Weapons, such as swords or axes or bows, affect this value. Until level 1, it will not be possible to deal damage with any tool or weapon, but you must wait until level 2 to be able to strike with a sword. Compared to the original value, this statistic has been halved at the beginning and then reaches the same value at level 4. 

You gain **1 experience point** for every **half-heart of damage you deal to hostile mobs** plus an additional value on mob killing (if not spawned by spawner).
| Attack Speed Info | Values |
| ------------ | ------------ |
| Base | 2 |
| Additive | 1.5 |
| Max Level | 50 |

## Damage
This statistic modifies the player's base **damage**. Weapons, such as swords or axes or bows, affect this value. Compared to the original value, this statistic has been halved at the beginning and then reaches the same value at level 2.

You gain **1 experience point** for every **half-heart of damage you deal to hostile mobs** plus an additional value on mob killing (if not spawned by spawner).
| Damage Info | Values |
| ------------ | ------------ |
| Base | 1 |
| Additive | 1.5 |
| Max Level | 100 |

## Evasion
This statistic modifies the player's **dodging ability**. When an hostile mob deal damage to you, you have a probability divided by the damade dealt to dodge that damage. The max value of this statistic is 15%

You gain **1 experience point** for every **half-heart of damage you dodge from hostile mobs**.
| Evasion Info | Values |
| ------------ | ------------ |
| Base | 1% |
| Additive | 0.5% |
| Max Level | 30 |

## Health
This statistic modifies the player's base **HP**. Compared to the original value, this statistic has been halved at the beginning and then reaches the same value at level 10.

You gain **1 experience point** for every **half-heart of damage you would take**, regardless of which hostile mob attacked you. 
| Health Info | Values |
| ------------ | ------------ |
| Base | 10 |
| Additive | 1 |
| Max Level | 50 |

## Knockback Resistance
This statistic modifies how much **knockback you take from attacks** of any type. Usually, you can only obtain this statistic with diamond or netherite armor. Armor modifications will still work and will be added to your base knockback resistance.

You gain **1 experience point** for every **half-heart of true damage you take**, regardless of which hostile mob attacked you.
| Knockback Resistance Info | Values |
| ------------ | ------------ |
| Base | 0 |
| Additive | 0.05 |
| Max Level | 20 |

## Luck
This statistic modifies the player's **luck**. This means that when the game generates loot for a dungeon chest, it will be more favorable towards you. Additionally, **every 10 levels of Luck**, you will receive an extra drop in addition to the classic drop influenced by the Fortune or Looting enchantment.

You can **gain experience** for this statistic in various ways:
- Mining ores
- Harvesting crops by right-clicking
- Shearing sheep
- Fishing with a fishing rod

| Luck Info | Values |
| ------------ | ------------ |
| Base | 0 |
| Additive | 0.5 |
| Max Level | 100 |

## Movement Speed
This statistic modifies the player's **movement speed**. At level 0, the value of this statistic is lower than the game's base value by about 2/3. To regain the base value, you need to reach level 5.

To **gain experience points**, you need to eat.
*Note: Some foods can remove experience points*
| Movement Speed Info | Values |
| ------------ | ------------ |
| Base | 0.075 |
| Additive | 0.005 |
| Max Level | 32 |

# Custom Item
## Totem of Protection
This item when is in your inventory or in your chest, prevent you from losing statistic upon death.

| Totem of Protection | Info |
| ------------ | ------------ |
| Obtainable | Killing Warden (drop x1) |
| Max Stack Size | 1 |

## Spawner Transporter
This item allows you to move spawner in the world.

| Spawner Transporter | Info |
| ------------ | ------------ |
| Obtainable | ![](https://imgur.com/szeATLx.png) |
| Max Stack Size | 1 |

## Enchanted Smithing Table
This item allows you to upgrade every gear to his next level

| Smithing Plus Egg | Info |
| ------------ | ------------ |
| Obtainable | ![](https://imgur.com/49OoGWA.png) |
| Max Stack Size | 64 |

# Game Mechanics

## Cobblestone Generator
The **cobblestone generator** will generate **Deepslate** and **Cobble Deepslate** when is below y = 0.

## Ender Dragon
**Ender Dragon** will drop **Dragon Egg** each time it dies.

## Smithing Plus Mechanic
This is the gui of the smithing plus. It allows you to upgrade you gear moving enchant from one level to the other. Once you allow the craft you will get the gear upgraded with the enchant and the old one without it.

![](https://imgur.com/Wy5VFdp.png)

The slots are:
- **Ingredient 1**: Yellow Glass Pane
- **Ingredient 2**: Yellow Glass Pane
- **Result**: Lime Glass Pane
- **Confirm Crafting**: Lime Terracotta
- **Cancel Crafting**: Red Terracotta
- **Destroy Table**: Barrier (_Gives you back the egg_)

To use you have to click your **gear** (armor, tool or weapon) that is **not** made by netherite or diamond. Then select the same item of the next level.

| Gear Level | Next Level |
| ------------ | ------------ |
| Wood | Stone |
| Leather | Gold |
| Chainmail | Gold |
| Stone | Gold |
| Stone | Iron |
| Gold | Iron |
| Iron | Diamond |

## Table Chart
### Attack Speed & Damage
| Mob | Experience |
| ------------ | ------------ |     
| Zombie | 1 |
| Husk | 1 |
| Zombie Villager | 1 |
| Creeper | 1 |
| Spider | 1 |
| Endermite | 1 |
| Piglin | 1 |
| Zombified Piglin | 1 |
| Drowned | 1 |
| Magma Cube | 1 |
| Phantom | 1 |
| Silverfish | 1 |
| Vex | 1 |
| Skeleton | 2 |
| Cave Spider | 2 |
| Guardian | 2 |
| Illusioner | 2 |
| Shulker | 2 |
| Vindicator | 2 |
| Wither Skeleton | 3 |
| Piglin Brute | 3 |
| Witch | 4 |
| Pillager | 5 |
| Blaze | 7 |
| Ghast | 8 |
| Evoker | 10 |
| Elder Guardian | 25 |
| Ender Dragon | 40 |
| Wither | 50 |
| Warden | 60 |

### Luck
| Ore | Experience |
| ------------ | ------------ |
| Coal | 5 |
| Deep Coal | 6 |
| Copper | 6 |
| Deep Copper | 7 |
| Nether Gold | 9 |
| Lapis | 10 |
| Deep Lapis | 11 |
| Redstone | 15 |
| Nether Quartz | 15 |
| Deep Redstone | 17 |
| Iron | 20 |
| Deep Iron | 25 |
| Gold | 40 |
| Deep Gold | 45 |
| Diamond | 50 |
| Deep Diamond | 55 |
| Emerald | 60 |
| Deep Emerald | 65 |
| Ancient Debris | 100 |

| Fishing | Experience |
| ------------ | ------------ |
| Cod | 1 |
| Salmon | 1 |
| Tripwire Hook | 3 |
| Leather | 3 |
| Pufferfish | 5 |
| Nautilus Shell | 8 |
| Enchanted Book | 10 |

| Other | Experience |
| ------------ | ------------ |
| Crop | 2 |
| Shear | 2 |

###Movement Speed
| Food | Experience |
| ------------ | ------------ |
| Spider Eye | -20 |
| Pufferfish | -15 |
| Poisonous Potato | -15 |
| Rotten Flesh | -10 |
| Beef | -5 |
| Chicken | -5 |
| Cod | -5 |
| Mutton | -5 |
| Porkchop | -5 |
| Rabbit | -5 |
| Salmon | -5 |
| Potato | -5 |
| Dried Kelp | -5 |
| Melon Slice | 1 |
| Beetroot | 1 |
| Cake | 2 |
| Sweet Berry | 4 |
| Golden Apple | 5 |
| Apple | 5 |
| Tropical Fish | 5 |
| Cookie | 5 |
| Pumpkin Pie | 8 |
| Bread | 10 |
| Carrot | 10 |
| Beetroot Soup | 10 |
| Baked Potato | 15 |
| Glow Berries | 20 |
| Cooked Salmon | 25 |
| Cooked Chicken | 25 |
| Cooked Salmon | 25 |
| Cooked Cod | 25 |
| Cooked Mutton | 25 |
| Cooked Beef | 30 |
| Cooked Porkchop | 35 |
| Cooked Rabbit | 40 |
| Mushroom Stew | 50 |
| Golden Carrot | 80 |
| Chorus Fruit | Random between 1 and 10 |

## Blood Moon
During the **Blood Moon Event** every mob that spawns has the following stats

| Stats | Base Value Increment |
| ------------ | ------------ |
| Damage | 5x |
| Movement Speed | 1.5x |
| Armor | 20 |
| Follow Range | 10x |
| Knockback Resistance | 0.6 |
| HP | 3x |

In Addiction every mob acquires a new ability

| Mob | Ability |
| ------------ | ------------ |
| Zombie | Probability to have armor 30%<br>Can steal player item in main hand |
| Skeleton | Gives you Poison II for 3 sec<br>Has 0.1% of probability to deal you 50HP of damage (Headshot) |
| Creeper | Spawns powered<br>Explosion Radius is 3x |
| Spider | Gives you Nausea II for 5 sec |
| Cave Spider | Gives you Wither II for 1.5 sec |
| Phantom | Duplicates when hits you |
| Slime | Duplicates when hits you |
| Magma Cube | Duplicates when hits you |
| Enderman | Teleports you at Y = 250 |
| Guardian | Removes all your air |
| Wither Skeleton | Increase HP by the damage until 1024 HP, then he acquires Resistance II for 2.5 sec |

*NB: During the Blood Moon is impossible to sleep!*

## Reverse Crafting
Now Quartz Block, Amethyst Block and Clay Block has their reverse crafting.

## Not-Renewable Items
Now there is the crafting for this not-renewable items:
| Item | Recipe |
| ------------ | ------------ |
| Calcite Block | ![](https://imgur.com/QXB1hPc.png) |
| Tuff | ![](https://imgur.com/8tLyY5W.png) |
| Dirt | ![](https://imgur.com/6PIwN6g.png) |

## Woodcutter
Now the stone cutting has same recipe for logs and planks
