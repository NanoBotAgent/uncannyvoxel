package com.uncannyvoxel.registry;

import com.uncannyvoxel.item.DesaturatedEyeItem;
import com.uncannyvoxel.item.LumenScalpelItem;
import com.uncannyvoxel.item.TetherStakeItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public final class ModItems {

    public static final Item DESATURATED_EYE = register("desaturated_eye",
            DesaturatedEyeItem::new, new Item.Properties().stacksTo(16));
    public static final Item LUMEN_SCALPEL = register("lumen_scalpel",
            LumenScalpelItem::new, new Item.Properties().stacksTo(1).durability(100));
    public static final Item TETHER_STAKE = register("tether_stake",
            TetherStakeItem::new, new Item.Properties().stacksTo(16));

    public static void registerBlockItem(String name, Block block) {
        Identifier id = Identifier.of("uncannyvoxel", name);
        Item item = new BlockItem(block, new Item.Properties());
        BuiltInRegistries.ITEM.register(id, item);
    }

    private static Item register(String name, java.util.function.Function<Item.Properties, ? extends Item> factory, Item.Properties props) {
        Identifier id = Identifier.of("uncannyvoxel", name);
        Item item = factory.apply(props);
        BuiltInRegistries.ITEM.register(id, item);
        return item;
    }

    public static void init() {}
}
