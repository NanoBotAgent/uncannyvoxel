package com.uncannyvoxel.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import com.uncannyvoxel.registry.ModBlockEntities;

public class ChestMimicBlockEntity extends LootableContainerBlockEntity implements net.minecraft.inventory.Inventory {

    private SimpleInventory inventory;
    private int mimicCooldown = 0;
    private boolean mimicTriggered = false;

    public ChestMimicBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CHEST_MIMIC, pos, state);
    }

    @Override
    protected SimpleInventory getInventory() {
        if (inventory == null) {
            inventory = new SimpleInventory(27);
        }
        return inventory;
    }

    public static void tick(World world, BlockPos pos, BlockState state, ChestMimicBlockEntity entity) {
        if (entity.mimicCooldown > 0) {
            entity.mimicCooldown--;
            if (entity.mimicCooldown == 0) {
                entity.mimicTriggered = false;
            }
        }
    }

    public void triggerMimic(PlayerEntity player) {
        if (mimicTriggered || mimicCooldown > 0) return;

        mimicTriggered = true;
        mimicCooldown = 100; // 5 seconds

        // Damage player
        player.damage(world.getDamageSources().magic(), 10.0f);

        // Play sound
        world.playSound(null, pos, com.uncannyvoxel.registry.ModSoundEvents.CHEST_SNAP, net.minecraft.sound.SoundCategory.BLOCKS, 1.0f, 1.0f);

        // Send message
        player.sendMessage(Text.literal("It tasted you."), true);

        markDirty();
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return GenericContainerScreenHandler.createGeneric9x3(syncId, playerInventory, this);
    }

    @Override
    protected Text getContainerName() {
        return Text.translatable("container.uncannyvoxel.chest_mimic");
    }

    @Override
    public int size() {
        return getInventory().size();
    }

    @Override
    public boolean isEmpty() {
        return getInventory().isEmpty();
    }

    @Override
    public ItemStack getStack(int slot) {
        return getInventory().getStack(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return getInventory().removeStack(slot, amount);
    }

    @Override
    public ItemStack removeStack(int slot) {
        return getInventory().removeStack(slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        getInventory().setStack(slot, stack);
    }

    @Override
    public void markDirty() {
        super.markDirty();
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return canPlayerUse(player);
    }

    @Override
    public void clear() {
        getInventory().clear();
    }
}