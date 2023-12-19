package io.github.apfelrauber.mixin.item;

import io.github.apfelrauber.RandomIdeasMain;
import io.github.apfelrauber.util.IEntityDataSaver;
import io.github.apfelrauber.util.OwnerData;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(BlockItem.class)
public class BlockItemMixin {

    @Inject(method= "place(Lnet/minecraft/item/ItemPlacementContext;)Lnet/minecraft/util/ActionResult;", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/ActionResult;success(Z)Lnet/minecraft/util/ActionResult;", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILHARD)
    public void placeMixin(ItemPlacementContext context, CallbackInfoReturnable<ActionResult> cir, ItemPlacementContext itemPlacementContext, BlockState blockState, BlockPos blockPos, World world){
        PlayerEntity owner = context.getPlayer();
        BlockEntity placedBlockEntity = world.getBlockEntity(blockPos);
        if (placedBlockEntity == null || owner == null) return;
        if(placedBlockEntity.getType().equals(BlockEntityType.DISPENSER)){
            //NbtCompound ownerCompound = new NbtCompound();
            //ownerCompound.putUuid("owner", owner.getUuid());
            //placedBlockEntity.readNbt(ownerCompound); // TODO: DOESNT WORK
            //placedBlockEntity.markDirty();
            OwnerData.setOwner((IEntityDataSaver) placedBlockEntity, owner);
        }
    }
}
