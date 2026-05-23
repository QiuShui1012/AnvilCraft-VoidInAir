---
navigation:
  title: "§2Void Survival"
  icon: "voidinair:totem_of_void"
items:
  - voidinair:totem_of_void
  - voidinair:void_amulet
---

# <ref item="voidinair:totem_of_void"/>

<recipe id="voidinair:totem_of_void"/>

- Triggers when lethal void damage reaches the holder
- Can be held directly or placed in an active Amulet Box
- Clears existing status effects, grants the usual recovery effects plus Slow Falling, and is consumed
- Teleports the rescued entity to the Overworld's maximum build height at the dimension-scaled X and Z coordinates

# <ref item="voidinair:void_amulet"/>

The Void Amulet is acquired and replicated like the other [Additional Amulets](100_amulets.md).

- Prevents void damage while active
- Includes the same rescue and recovery behavior as the Totem of Void
- Is not consumed when its death protection activates

<info>
Coordinates are scaled using normal dimension travel rules. For example, a rescue from the Nether converts X and Z to their Overworld equivalents before teleporting.
</info>
