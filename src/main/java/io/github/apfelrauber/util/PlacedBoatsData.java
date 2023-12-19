package io.github.apfelrauber.util;

import io.github.apfelrauber.RandomIdeasGameRules;
import net.minecraft.entity.Entity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.GameRules;

import java.util.UUID;

public class PlacedBoatsData {
    public static NbtList addBoat(IEntityDataSaver player, BoatEntity newBoat, GameRules gameRules, ServerWorld world){
        NbtCompound nbt = player.getPersistentData();
        NbtList boats = (NbtList) nbt.get("placed_boats");
        if(boats == null) boats = new NbtList();
        int maxPlayerBoats = gameRules.getInt(RandomIdeasGameRules.MAX_PLAYER_BOATS);

        if(boats.size() >= maxPlayerBoats) killOldestBoat(player, world);

        NbtCompound boatCompound = new NbtCompound();
        boatCompound.putUuid("boatUUID", newBoat.getUuid());
        boats.add(boatCompound);

        //sync data

        nbt.put("placed_boats", boats);
        return boats;
    }

    public static NbtList killOldestBoat(IEntityDataSaver player, ServerWorld world){
        NbtCompound nbt = player.getPersistentData();
        NbtList boats = (NbtList) nbt.get("placed_boats");
        NbtCompound nbtCompound;
        UUID boatUUID;
        Entity boatEntity;

        nbtCompound = boats.getCompound(0);
        boatUUID = nbtCompound.getUuid("boatUUID");
        boatEntity = world.getEntity(boatUUID);

        boats.remove(0);
        boatEntity.kill();

        //sync data

        nbt.put("placed_boats", boats);
        return boats;
    }

    public static NbtList removeSpecificBoatFromOwner(IEntityDataSaver player, BoatEntity boat, ServerWorld world){
        NbtCompound nbt = player.getPersistentData();
        NbtList boats = (NbtList) nbt.get("placed_boats");

        NbtCompound boatCompound = new NbtCompound();
        UUID boatUUID = boat.getUuid();
        boatCompound.putUuid("boatUUID", boatUUID);

        boats.remove(boatCompound);


        //sync data

        nbt.put("placed_boats", boats);
        return boats;
    }
}
