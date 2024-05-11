package adris.altoclef.tasksystem;

import adris.altoclef.AltoClef;
import adris.altoclef.Debug;
import net.minecraft.client.resource.language.I18n;

import java.util.ArrayList;

import static adris.altoclef.ui.LocalizationAltoClef.no_chain_running;

public class TaskRunner {

    private final ArrayList<TaskChain> chains = new ArrayList<>();
    private final AltoClef mod;
    private boolean active;

    private TaskChain cachedCurrentTaskChain = null;

    public String statusReport = no_chain_running.get();

    public TaskRunner(AltoClef mod) {
        this.mod = mod;
        active = false;
    }

    public void tick() {
        if (!active || !AltoClef.inGame()) {
            statusReport = no_chain_running.get();
            return;
        }

        // Get highest priority chain and run
        TaskChain maxChain = null;
        float maxPriority = Float.NEGATIVE_INFINITY;
        for (TaskChain chain : chains) {
            if (!chain.isActive()) continue;
            float priority = chain.getPriority(mod);
            if (priority > maxPriority) {
                maxPriority = priority;
                maxChain = chain;
            }
        }
        if (cachedCurrentTaskChain != null && maxChain != cachedCurrentTaskChain) {
            cachedCurrentTaskChain.onInterrupt(mod, maxChain);
        }
        cachedCurrentTaskChain = maxChain;
        if (maxChain != null) {
            statusReport = I18n.translate("altoclef.status.chain.chain_priority", maxChain.getName(), maxPriority);
            maxChain.tick(mod);
        } else {
            statusReport = no_chain_running.get();
        }
    }

    public void addTaskChain(TaskChain chain) {
        chains.add(chain);
    }

    public void enable() {
        if (!active) {
            mod.getBehaviour().push();
            mod.getBehaviour().setPauseOnLostFocus(false);
        }
        active = true;
    }

    public void disable() {
        if (active) {
            mod.getBehaviour().pop();
        }
        for (TaskChain chain : chains) {
            chain.stop(mod);
        }
        active = false;

        Debug.logMessage("Stopped");
    }

    public boolean isActive() {
        return active;
    }

    public TaskChain getCurrentTaskChain() {
        return cachedCurrentTaskChain;
    }

    // Kinda jank ngl
    public AltoClef getMod() {
        return mod;
    }
}
