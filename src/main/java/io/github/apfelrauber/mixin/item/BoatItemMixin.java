package io.github.apfelrauber.mixin.item;

import io.github.apfelrauber.util.IEntityDataSaver;
import io.github.apfelrauber.util.OwnerData;
import io.github.apfelrauber.util.PlacedBoatsData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.BoatItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(BoatItem.class)
public class BoatItemMixin {
    @Inject(method="use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void useMixin(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir, ItemStack itemStack, HitResult hitResult, Vec3d vec3d, double d, List list, BoatEntity boatEntity) {
        PlacedBoatsData.addBoat((IEntityDataSaver) user, boatEntity, world.getGameRules(), (ServerWorld) world);
        OwnerData.setOwner((IEntityDataSaver) boatEntity, user);
    }
}