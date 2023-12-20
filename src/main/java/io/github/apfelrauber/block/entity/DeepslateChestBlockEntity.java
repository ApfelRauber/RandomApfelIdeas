package io.github.apfelrauber.block.entity;

import io.github.apfelrauber.screen.DeepslateChestScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class DeepslateChestBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(9, ItemStack.EMPTY);

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 4;
    private int cooldown = 0;
    private int maxCooldown = 20;
    private int slotSeed;

    public DeepslateChestBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DEEPLSATE_CHEST, pos, state);

        this.slotSeed = pos.getX()*9876 + pos.getY()*1234 + pos.getZ()*5678;

        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> DeepslateChestBlockEntity.this.progress;
                    case 1 -> DeepslateChestBlockEntity.this.maxProgress;
                    case 2 -> DeepslateChestBlockEntity.this.cooldown;
                    case 3 -> DeepslateChestBlockEntity.this.maxCooldown;
                    case 4 -> DeepslateChestBlockEntity.this.slotSeed;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch(index) {
                    case 0: DeepslateChestBlockEntity.this.progress = value; break;
                    case 1: DeepslateChestBlockEntity.this.maxProgress = value; break;
                    case 2: DeepslateChestBlockEntity.this.cooldown = value; break;
                    case 3: DeepslateChestBlockEntity.this.maxCooldown = value; break;
                    case 4: DeepslateChestBlockEntity.this.slotSeed = value; break;
                }
            }

            @Override
            public int size() {
                return 5;
            }
        };
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.inventory;
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Deepslate Chest");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new DeepslateChestScreenHandler(syncId, playerInventory, this, this.propertyDelegate, this.pos);
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("deepslate_chest.progress", progress);
        nbt.putInt("deepslate_chest.cooldown", cooldown);
        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        Inventories.readNbt(nbt, inventory);
        super.readNbt(nbt);
        progress = nbt.getInt("deepslate_chest.progress");
        cooldown = nbt.getInt("deepslate_chest.cooldown");
    }

    public static void tick(World world, BlockPos blockPos, BlockState blockState, DeepslateChestBlockEntity entity) {
        if (world.isClient()) return;

        if (entity.cooldown > 0) entity.cooldown++;
        if (entity.cooldown == entity.maxCooldown){
            entity.cooldown=0;
            if (entity.progress < entity.maxProgress) entity.progress++;
        }
    }
}
