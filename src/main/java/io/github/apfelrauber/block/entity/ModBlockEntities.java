package io.github.apfelrauber.block.entity;

import io.github.apfelrauber.RandomIdeasMain;
import io.github.apfelrauber.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static BlockEntityType<DeepslateChestBlockEntity> DEEPLSATE_CHEST;

    public static void registerBlockEntities(){
        DEEPLSATE_CHEST = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(RandomIdeasMain.MOD_ID, "deepslate_chest"),
                FabricBlockEntityTypeBuilder.create(DeepslateChestBlockEntity::new,
                        ModBlocks.DEEPSLATE_CHEST).build(null));
    }
}
