package io.github.apfelrauber.event;

import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.DoorBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class AttackBlockHandler implements AttackBlockCallback {
    @Override
    public ActionResult interact(PlayerEntity player, World world, Hand hand, BlockPos pos, Direction direction) {
        if(player.isSpectator() || player.isCreative() || player.isSneaking() || world.isClient) return ActionResult.PASS;

        if(world.getBlockState(pos).getBlock() instanceof DoorBlock){
            if(((DoorBlock) world.getBlockState(pos).getBlock()).getBlockSetType().equals(BlockSetType.IRON))
                world.playSound(null, pos, SoundEvents.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, SoundCategory.BLOCKS, 1f ,1f);
            else world.playSound(null, pos, SoundEvents.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR, SoundCategory.BLOCKS, 1f ,1f);
        }

        return ActionResult.PASS;
    }
}
