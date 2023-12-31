package io.github.apfelrauber.block;

import io.github.apfelrauber.RandomIdeasMain;
import io.github.apfelrauber.block.custom.DeepslateChestBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static final Block DEEPSLATE_CHEST = registerBlock("deepslate_chest",
            new DeepslateChestBlock(FabricBlockSettings.create().mapColor(MapColor.DEEPSLATE_GRAY).
                    requiresTool().strength(3.0F, 6.0F).nonOpaque().sounds(BlockSoundGroup.DEEPSLATE)));

    public static Block registerBlock(String name, Block block){
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(RandomIdeasMain.MOD_ID, name), block);
    }

    public static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(RandomIdeasMain.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerModBlocks() {
        RandomIdeasMain.LOGGER.debug("Registering ModBlocks for " + RandomIdeasMain.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(content -> {
            content.add(DEEPSLATE_CHEST);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(content -> {
            content.add(DEEPSLATE_CHEST);
        });
    }
}
