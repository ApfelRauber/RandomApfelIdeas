package io.github.apfelrauber.util;

import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

import java.util.UUID;

public class OwnerData {
    public static UUID setOwner(IEntityDataSaver owned, PlayerEntity owner){ //currently Boats and Dispensers can be owned
        NbtCompound nbt = owned.getPersistentData();
        UUID ownerUUID = owner.getUuid();

        //sync data

        nbt.putUuid("owner", ownerUUID);
        return ownerUUID;
    }

    public static UUID getOwnerUUID(IEntityDataSaver owned){
        NbtCompound nbt = owned.getPersistentData();
        return nbt.getUuid("owner");
    }
}
