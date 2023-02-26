# RPGarnet
RPG plugin for Spigot 1.19.3

There are 7 different statistic that can be improved during the normal gameplay:
- [Armor](https://github.com/MattiaBiancini/RPGarnet/readme.md "Armor")
- [Attack Speed](https://github.com/MattiaBiancini/RPGarnet/readme.md "Attack Speed")
- [Damage](https://github.com/MattiaBiancini/RPGarnet/readme.md "Damage")
- [Health](https://github.com/MattiaBiancini/RPGarnet/readme.md "Health")
- [Knockback Resistance](https://github.com/MattiaBiancini/RPGarnet/readme.md "Knockback Resistance")
- [Luck](https://github.com/MattiaBiancini/RPGarnet/readme.md "Luck")
- [Movement Speed](https://github.com/MattiaBiancini/RPGarnet/readme.md "Movement Speed")

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
| Additive | 0.5 |
| Max Level | 50 |

## Damage
This statistic modifies the player's base **damage**. Weapons, such as swords or axes or bows, affect this value. Compared to the original value, this statistic has been halved at the beginning and then reaches the same value at level 2.

You gain **1 experience point** for every **half-heart of damage you deal to hostile mobs** plus an additional value on mob killing (if not spawned by spawner).
| Damage Info | Values |
| ------------ | ------------ |
| Base | 1 |
| Additive | 0.5 |
| Max Level | 100 |
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
