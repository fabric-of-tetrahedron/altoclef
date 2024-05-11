package adris.altoclef.commands.random;

import adris.altoclef.AltoClef;
import adris.altoclef.commands.BlockScanner;
import adris.altoclef.commands.GetItemCommand;
import adris.altoclef.commandsystem.Arg;
import adris.altoclef.commandsystem.ArgParser;
import adris.altoclef.commandsystem.Command;
import adris.altoclef.commandsystem.CommandException;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

import java.lang.reflect.Field;
import java.util.HashSet;

public class ScanCommand extends Command {

    public ScanCommand() throws CommandException {
        super("scan", "Locates nearest block", new Arg<>(String.class, "block", "DIRT", 0));
    }

    @Override
    protected void call(AltoClef mod, ArgParser parser) throws CommandException {
        String blockStr = parser.get(String.class);

        Field[] declaredFields = Blocks.class.getDeclaredFields();
        Block block = GetItemCommand.getBlock(mod, blockStr);

        BlockScanner blockScanner = mod.getBlockScanner();
        mod.log(blockScanner.getNearestBlock(block,mod.getPlayer().getPos())+"");
    }

}