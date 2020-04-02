# PiTweaks - Anvil Tweak Only Fork

## No Repair Cost Tweak

Removes the cumulative XP cost in an anvil.  The effect is that all anvil actions cost the same as if they had never been in an anvil.

Implementation: When an item is inserted into or removed from an anvil, the "RepairCost" NBT is cleared on the item.  The anvil will attempt to add a repair cost to the output, but the mod will clear that too.  If you disable this tweak, items that have been cleared will start accumulating repair cost as normal.
