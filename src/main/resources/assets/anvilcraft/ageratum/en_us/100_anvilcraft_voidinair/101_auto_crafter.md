---
navigation:
  title: "§2Auto Crafter"
  icon: "voidinair:auto_crafter"
items:
  - voidinair:auto_crafter
---

# <ref item="voidinair:auto_crafter"/>

## Obtaining

Sneak-use a <ref item="minecraft:crafting_table"/> on any [Batch Crafting Device](../004_block/101_batch_craft.md). The crafting table is consumed, the previous workstation item is returned, and the device becomes an Auto Crafter.

It can be converted back into a Batch Crafter with this recipe:

<recipe id="voidinair:batch_crafter_convert"/>

## Function

- Continuously consumes 4 kW and attempts to craft once every 20 game ticks by default
- Works while it has no redstone signal; applying a signal pauses crafting
- Uses its nine inventory slots as a 3x3 crafting grid
- If the same input arrangement matches multiple recipes, use the cycle button beside the result to choose one
- Each operation crafts as many times as the smallest occupied input stack allows, then consumes all inputs in one batch
- Outputs into an inventory on its configured output face, or ejects the result into the world when no inventory is present
- Waits without consuming inputs when the output inventory has insufficient space or the ejection area is blocked

## Filters

- Enable filters to preserve a recipe layout while ingredients are supplied automatically
- Set a ghost item on each required slot and disable unused slots
- Scroll over a filtered slot to set its stack limit; hold Shift to change the limit faster
