package adris.altoclef.tasks.misc;

import adris.altoclef.AltoClef;
import adris.altoclef.tasks.ResourceTask;
import adris.altoclef.tasks.resources.MineAndCollectTask;
import adris.altoclef.tasksystem.Task;
import adris.altoclef.util.MiningRequirement;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class GetItemTask extends ResourceTask {
    public Item item;
    public Block block;

    public GetItemTask(Item item, int targetCount) {
        this(item, Block.getBlockFromItem(item), targetCount);
    }

    public GetItemTask(Block block, int targetCount) {
        this(block.asItem(), block, targetCount);
    }

    public GetItemTask(Item item, Block block, int targetCount) {
        super(item, targetCount);
        this.item = item;
        this.block = block;
    }

    @Override
    protected boolean shouldAvoidPickingUp(AltoClef mod) {
        return false;
    }

    @Override
    protected void onResourceStart(AltoClef mod) {
        mod.getBehaviour().push();
    }

    @Override
    protected Task onResourceTick(AltoClef mod) {
        return new MineAndCollectTask(
                itemTargets, MiningRequirement.getMinimumRequirementForBlock(block));
    }

    @Override
    public boolean isFinished(AltoClef mod) {
        return mod.getItemStorage().getItemCount(item) >= getTargetCount();
    }

    @Override
    protected void onResourceStop(AltoClef mod, Task interruptTask) {
        mod.getBehaviour().pop();
    }

    @Override
    protected boolean isEqualResource(ResourceTask other) {
        return other instanceof GetItemTask;
    }

    @Override
    protected String toDebugStringName() {
        return "Collecting " + getTargetCount() + " " + item.getName();
    }

    private int getTargetCount() {
        return itemTargets[0].getTargetCount();
    }
}
