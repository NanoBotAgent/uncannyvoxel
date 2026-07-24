package com.uncannyvoxel.registry;

import com.uncannyvoxel.item.DesaturatedEyeItem;
import com.uncannyvoxel.item.LumenScalpelItem;
import com.uncannyvoxel.item.TetherStakeItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class ModItems {

    public static final Item DESATURATED_EYE = register("desaturated_eye", new DesaturatedEyeItem(new Item.Settings().maxCount(16)));
    public static final Item LUMEN_SCALPEL = register("lumen_scalpel", new LumenScalpelItem(new Item.Settings().maxCount(1).maxDamage(100)));
    public static final Item TETHER_STAKE = register("tether_stake", new TetherStakeItem(new Item.Settings().maxCount(16)));

    public static void registerBlockItem(String name, net.minecraft.block.Block block) {
        Item item = new net.minecraft.item.BlockItem(block, new Item.Settings());
        Registry.register(Registries.ITEM, Identifier.of("uncannyvoxel", name), item);
    }

    private static Item register(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of("uncannyvoxel", name), item);
    }

    public static void init() {}
}