[Curseforge](https://www.curseforge.com/minecraft/mc-mods/mna-freeze-fix)

The problem with the original code is it's iterating through the recipe registry for each spell component, if it just iterated once it wouldn't be so bad but it goes until it finds a match then does it again and again.

The slightly overkill solution I've implemented is not only to use a cache for spell component recipes (SpellCache) but also to run this task asynchronously. Async isn't really needed, running through the recipes once is not all that expensive, but I wanted zero impact for the client.
