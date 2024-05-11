package adris.altoclef.commands;

import adris.altoclef.AltoClef;
import adris.altoclef.commandsystem.Arg;
import adris.altoclef.commandsystem.ArgParser;
import adris.altoclef.commandsystem.Command;
import adris.altoclef.commandsystem.CommandException;
import adris.altoclef.tasks.misc.GetItemTask;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.lang.reflect.Field;

public class GetItemCommand extends Command {
    public GetItemCommand() throws CommandException {
        super("get", "Get specific item",
                new Arg(String.class, "item", "stone", 0),
                new Arg(Integer.class, "count", "2", 0));
    }

    @Override
    protected void call(AltoClef mod, ArgParser parser) throws CommandException {

        String blockStr = parser.get(String.class);

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

        if (block == null) {
            mod.logWarning("Block named: " + blockStr + " not found :(");
            return;
        }

        mod.runUserTask(new GetItemTask(block, Integer.parseInt(parser.get(String.class))), this::finish);
    }
}
