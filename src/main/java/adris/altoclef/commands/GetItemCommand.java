package adris.altoclef.commands;

import adris.altoclef.AltoClef;
import adris.altoclef.commandsystem.Arg;
import adris.altoclef.commandsystem.ArgParser;
import adris.altoclef.commandsystem.Command;
import adris.altoclef.commandsystem.CommandException;
import adris.altoclef.tasks.misc.GetItemTask;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;

public class GetItemCommand extends Command {
    public GetItemCommand() throws CommandException {
        super("get", "Get specific item by breaking blocks",
                new Arg(String.class, "item", "stone", 0),
                new Arg(String.class, "block", "stone", 1),
                new Arg(Integer.class, "count", "2", 2));
    }

    @Override
    protected void call(AltoClef mod, ArgParser parser) throws CommandException {

        String itemStr = parser.get(String.class);
        String blockStr = parser.get(String.class);

        Item item = getItem(mod, itemStr);
        if (item == null) return;

        Block block = getBlock(mod, blockStr);
        if (block == null) return;

        String targetCount;
        //TODO parser.get with class type is unstable
        try {
            targetCount = parser.get(String.class);
        } catch (ClassCastException e) {
            targetCount = parser.get(Integer.class).toString();
        }
        mod.runUserTask(new GetItemTask(item, block, Integer.parseInt(targetCount)), this::finish);
    }

    public static @Nullable Block getBlock(AltoClef mod, String blockStr) {
        Field[] declaredFields = Blocks.class.getDeclaredFields();
        Block block = null;

        for (Field field : declaredFields) {
            field.setAccessible(true);
            try {
                if (field.getName().equalsIgnoreCase(blockStr)) {
                    block = (Block) field.get(Blocks.class);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            field.setAccessible(false);
        }

        block = Registries.BLOCK.get(new Identifier(Identifier.DEFAULT_NAMESPACE, blockStr));

        if (block == null || block == Blocks.AIR) {
            mod.logWarning("Block named: " + blockStr + " not found :(");
            return null;
        }
        return block;
    }

    public static @Nullable Item getItem(AltoClef mod, String itemString) {
        Field[] declaredFields = Items.class.getDeclaredFields();
        Item item = null;

        for (Field field : declaredFields) {
            field.setAccessible(true);
            try {
                if (field.getName().equalsIgnoreCase(itemString)) {
                    item = (Item) field.get(Item.class);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            field.setAccessible(false);
        }

        item = Registries.ITEM.get(new Identifier(Identifier.DEFAULT_NAMESPACE, itemString));

        if (item == null || item == Items.AIR) {
            mod.logWarning("Item named: " + itemString + " not found :(");
            return null;
        }
        return item;
    }
}
