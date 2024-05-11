package adris.altoclef.ui;

import net.minecraft.client.resource.language.I18n;

import java.util.function.Supplier;

public class LocalizationAltoClef {
    public static Supplier<String> no_chain_running;

    public static void init() {
        no_chain_running = () -> I18n.translate("altoclef.status.chain.no_chain_running");
    }
}
