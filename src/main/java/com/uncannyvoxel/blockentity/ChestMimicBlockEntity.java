package com.uncannyvoxel.blockentity;

import com.uncannyvoxel.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;

public class ChestMimicBlockEntity extends RandomizableContainerBlockEntity {

    private NonNullList<ItemStack> items = NonNullList.withSize(27, ItemStack.EMPTY);
    private int mimicCooldown = 0;
    private boolean mimicTriggered = false;

    public ChestMimicBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CHEST_MIMIC, pos, state);
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> list) {
        this.items = list;
    }

    @Override
    public int getContainerSize() {
        return 27;
    }

    @Override
    public boolean isEmpty() {
        return items.stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    public ItemStack getItem(int slot) {
        return items.get(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        return Container.removeItem(items, slot, amount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return Container.takeItem(items, slot);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        items.set(slot, stack);
    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override
    public void clearContent() {
        items.clear();
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ChestMimicBlockEntity entity) {
        if (entity.mimicCooldown > 0) {
            entity.mimicCooldown--;
            if (entity.mimicCooldown == 0) {
                entity.mimicTriggered = false;
            }
        }
    }

    public void triggerMimic(Player player) {
        if (mimicTriggered || mimicCooldown > 0) return;

        mimicTriggered = true;
        mimicCooldown = 100;

        if (level instanceof ServerLevel serverLevel) {
            player.hurt(serverLevel.damageSources().magic(), 10.0f);
            serverLevel.playSound(null, getBlockPos(), com.uncannyvoxel.registry.ModSoundEvents.CHEST_SNAP, SoundSource.BLOCKS, 1.0f, 1.0f);
        }

        player.sendSystemMessage(Component.literal("It tasted you."));

        setChanged();
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.uncannyvoxel.chest_mimic");
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory playerInventory) {
        return ChestMenu.threeRows(containerId, playerInventory, this);
    }

    @Override
    protected void saveAdditional(ValueOutput tag) {
        super.saveAdditional(tag);
        tag.putInt("mimicCooldown", mimicCooldown);
        tag.putBoolean("mimicTriggered", mimicTriggered);
    }

    @Override
    protected void loadAdditional(ValueInput tag) {
        super.loadAdditional(tag);
        mimicCooldown = tag.getIntOr("mimicCooldown").orElse(0);
        mimicTriggered = tag.getBooleanOr("mimicTriggered").orElse(false);
    }
}
