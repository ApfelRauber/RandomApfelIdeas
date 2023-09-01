package io.github.apfelrauber.screen;

import io.github.apfelrauber.block.entity.DeepslateChestBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.util.math.Vector2f;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DeepslateChestScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    public final Vector2f[] slotPositions = new Vector2f[9];

    public DeepslateChestScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf packetByteBuf){
        this(syncId, inventory, new SimpleInventory(9), new ArrayPropertyDelegate(6), packetByteBuf.readBlockPos());
    }

    public DeepslateChestScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate delegate, BlockPos pos) {
        super(ModScreenHandlers.DEEPSLATE_CHEST_SCREEN_HANDLER, syncId);
        checkSize(inventory, 9);
        this.inventory = inventory;
        inventory.onOpen(playerInventory.player);
        this.propertyDelegate = delegate;

        generateChestSlots(inventory, pos);
        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);

        this.addProperties(delegate);
    }

    private void generateChestSlots(Inventory inventory, BlockPos pos){
        int slotSeed = pos.getX()*9876 + pos.getY()*1234 + pos.getZ()*5678;
        List<Vector2f> possibleSlots = new ArrayList<>(27);

        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                possibleSlots.add(new Vector2f(8 + l * 18, 15 + i * 18));
            }
        }

        Collections.shuffle(possibleSlots, new Random(slotSeed));

        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(inventory, i, (int) possibleSlots.get(i).getX(), (int) possibleSlots.get(i).getY()));
            slotPositions[i] = possibleSlots.get(i);
        }
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if(slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if(!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true))
                    return ItemStack.EMPTY;
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false))
                return ItemStack.EMPTY;

            if(originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}
