A modpack development tool that allows developers to designate any item or block to generate a ruined "relic" version, which can be found in individually configurable structures and restored to its original form!

Heavily WIP.

Instructions for developers:
You can interact with this mod entirely through the config files, no need to alter the jar. In the configs you will find several configs under /RelicArchaeology/.

To designate an block as a relic, open relic_blocks.json and fill out the template. You must provide a unique identifier (relicBlockName), conventionally prefixed with "relic_". Then, fill out the mod id, block id, and meta value (set to 0 if not relevant).

The process for items is similar. Provide a unique identifier (relicName), mod id, item id, and meta.

Once you launch the game after adding your relics, check /RelicArchaeology/lang/. In here, the en_US.cfg file will be automatically populated with missing entries for any relic items or blocks you have added. Give them an English-localized name (or they will appear ingame as REPLACE_ME).


