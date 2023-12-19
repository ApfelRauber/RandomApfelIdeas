package io.github.apfelrauber.mixin.block.entity;

import io.github.apfelrauber.util.IEntityDataSaver;
import io.github.apfelrauber.util.OwnerData;
import net.minecraft.block.dispenser.BoatDispenserBehavior;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.UUID;

@Mixin(BoatDispenserBehavior.class)
public class BoatDispenserBehaviorMixin {
    @Inject(method="dispenseSilently", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z"), locals = LocalCapture.CAPTURE_FAILHARD)
    public void dispenseSilently(BlockPointer pointer, ItemStack stack, CallbackInfoReturnable<ItemStack> cir, Direction direction, World world, double d, double e, double f, double g, BlockPos blockPos, double h, BoatEntity boatEntity) {
        if(boatEntity == null) return;
        BlockEntity dispenserEntity = world.getBlockEntity(blockPos);

        UUID ownerUUID = OwnerData.getOwnerUUID((IEntityDataSaver) dispenserEntity);
        PlayerEntity owner = world.getPlayerByUuid(ownerUUID);

        OwnerData.setOwner((IEntityDataSaver) boatEntity, owner);
    }
}
