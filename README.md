A modpack development tool that allows developers to designate any item or block to generate a ruined "relic" version, which can be found in individually configurable structures and restored to its original form!

Heavily WIP.

Instructions for developers:
You can interact with this mod entirely through the config files, no need to alter the jar. In the configs you will find several configs under config/relicarchaeology/.

### Registering Relics

To designate a block as a relic, open relic_blocks.json and fill out the template. You must provide a unique identifier (relicBlockName), conventionally prefixed with "relic_". Then, fill out the mod id, block id, and meta value (set to 0 if not relevant).

The process for items is similar. Provide a unique identifier (relicName), mod id, item id, and meta.

Once you launch the game after adding your relics, check /RelicArchaeology/lang/. In here, the en_US.cfg file will be automatically populated with missing entries for any relic items or blocks you have added. Give them an English-localized name (or they will appear ingame as REPLACE_ME).

### Registering Structures

For players to find relic blocks, you'll need to assign them to a structure. Structures are encoded character-by-character in config/relicarchaeology/structures. To add a new structure, create a new .json file following the format of the example provided in ruins.json. You can assign a name for the structure, set specific biomes and dimensions in which it can spawn (if you leave these sets empty, the structure may spawn in any biome/dimension). You can assign a rarity - this is on a per-chunk basis, so 0.05 = 5% chance to spawn per chunk.

To build the actual structure, first assign a palette of characters. This is a mapping from a character to a block id, modname included. Next, you can begin building a structure by inputting characters. Each character should be from your palette and will represent a single block.

Special characters (do not remap these!):

-' ' (spacebar) = air

-'R' = Generate random relic block

