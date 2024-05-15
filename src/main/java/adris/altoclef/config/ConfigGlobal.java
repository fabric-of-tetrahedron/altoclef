package adris.altoclef.config;

import adris.altoclef.AltoClef;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = AltoClef.ID)
public class ConfigGlobal implements ConfigData {

    @ConfigEntry.Gui.Excluded
    public static ConfigHolder<ConfigGlobal> configHolder;

    @ConfigEntry.Gui.Excluded
    public static ConfigGlobal instance;

    public static void initConfig() {
        instance = AutoConfig.getConfigHolder(ConfigGlobal.class).getConfig();
        instance.onConfigChanged();
    }

    public void onConfigChanged() {
    }
}
