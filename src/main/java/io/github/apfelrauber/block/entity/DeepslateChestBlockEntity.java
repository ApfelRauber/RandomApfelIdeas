package io.github.apfelrauber.block.entity;

import io.github.apfelrauber.RandomIdeasMain;
import io.github.apfelrauber.screen.DeepslateChestScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class DeepslateChestBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(9, ItemStack.EMPTY);

    protected final PropertyDelegate propertyDelegate;
    private int progess = 0;
    private int maxProgess = 5;
    private int cooldown = 0;
    private int maxCooldown = 20;
    private int sculkLevel = 0;

    public DeepslateChestBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DEEPLSATE_CHEST, pos, state);

        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                switch (index){
                    case 0: return DeepslateChestBlockEntity.this.progess;
                    case 1: return DeepslateChestBlockEntity.this.maxProgess;
                    case 2: return DeepslateChestBlockEntity.this.cooldown;
                    case 3: return DeepslateChestBlockEntity.this.maxCooldown;
                    case 4: return DeepslateChestBlockEntity.this.sculkLevel;
                    default: return 0;
                }
            }

            @Override
            public void set(int index, int value) {
                switch(index) {
                    case 0: DeepslateChestBlockEntity.this.progess = value; break;
                    case 1: DeepslateChestBlockEntity.this.maxProgess = value; break;
                    case 2: DeepslateChestBlockEntity.this.cooldown = value; break;
                    case 3: DeepslateChestBlockEntity.this.maxCooldown = value; break;
                    case 4: DeepslateChestBlockEntity.this.sculkLevel = value; break;
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
        return new DeepslateChestScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("deepslate_chest.progress", progess);
        nbt.putInt("deepslate_chest.cooldown", cooldown);
        nbt.putInt("deepslate_chest.sculkLevel", sculkLevel);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        Inventories.readNbt(nbt, inventory);
        super.readNbt(nbt);
        progess = nbt.getInt("deepslate_chest.progress");
        cooldown = nbt.getInt("deepslate_chest.cooldown");
        sculkLevel = nbt.getInt("deepslate_chest.sculkLevel");
    }

    public static void tick(World world, BlockPos blockPos, BlockState blockState, DeepslateChestBlockEntity entity) {
        if(world.isClient()) return;

        if(entity.cooldown > 0) entity.cooldown++; //TODO: on right-click increase cooldown by 1, if lower than 1
        RandomIdeasMain.LOGGER.debug("cooldown is" + entity.cooldown);
        if(entity.cooldown > entity.maxCooldown){
            moveLid(entity);
        }
    }

    private static void moveLid(DeepslateChestBlockEntity entity){
        entity.cooldown = 0;

        Random random = new Random();
        int n = random.nextInt(entity.sculkLevel);
        if (n == 0) {
            entity.progess++;
        } else {
            entity.sculkLevel--;
            if(entity.sculkLevel < 0) RandomIdeasMain.LOGGER.warn("sculkLevel of DeepslateChestBlockEntity below 0, should not be possible!", entity);
        }
        RandomIdeasMain.LOGGER.debug("cooldown is" + entity.cooldown);
        RandomIdeasMain.LOGGER.debug("progess is" + entity.progess);
        RandomIdeasMain.LOGGER.debug("sculkLevel is" + entity.sculkLevel);
    }
}
