---
navigation:
  title: "§5Void Fountain"
  icon: "voidinair:void_fountain"
items:
  - voidinair:void_fountain
---

# <ref item="voidinair:void_fountain"/>

## Creation

<row halign="center">
<item id="anvilcraft:mineral_fountain"/>
<item id="anvilcraft:impact_pile"/>
<item id="anvilcraft:void_matter_block"/>
<item id="anvilcraft:transcendence_anvil"/>
</row>

1. Place a <ref item="anvilcraft:mineral_fountain"/> between 1 and 6 blocks above the world's minimum build height
2. Place an <ref item="anvilcraft:impact_pile"/> directly above it, then a <ref item="anvilcraft:void_matter_block"/> above the pile
3. Drop a <ref item="anvilcraft:transcendence_anvil"/> from a height of at least 20 blocks so it lands on the Void Matter Block
4. The anvil, Void Matter Block, and Impact Pile are consumed, and the Mineral Fountain becomes a Void Fountain

## Production

- By default, attempts to place one block directly above itself every 600 game ticks (30 seconds)
- Works only near the world bottom and only when the space above is air
- Leaving at least one horizontal side as air enables normal production
- Four different solid neighbors stop production
- If all four horizontal neighbors have the same block state, the fountain has a 3% chance to copy that block

|   Roll    | Result                                                          |
|:---------:|:----------------------------------------------------------------|
| First 20% | <ref item="anvilcraft:void_stone"/>                             |
| Next 20%  | <ref item="anvilcraft:earth_core_shard_ore"/>                   |
|  Next 3%  | Copy the surrounding block, when the four-side pattern is valid |
| Remainder | A random [Void Decay](../001_feature/101_void_decay.md) result  |

<info>
When the four-side copy pattern is not valid, its 3% portion also uses the Void Decay result pool.
</info>
