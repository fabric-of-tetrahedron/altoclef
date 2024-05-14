package adris.altoclef.gui;

import com.chaosthedude.notes.gui.NotesTextField;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class CodeRegistrationScreen extends Screen {
    protected final Screen parent;

    public CodeRegistrationScreen(Screen parent) {
        this(parent, Text.translatable("altoclef.code_registration.title"));
    }

    public CodeRegistrationScreen(Screen parent, Text title) {
        super(title);
        this.parent = parent;
    }

    public void removed() {
        this.client.options.write();
    }

    public void close() {
        this.client.setScreen(this.parent);
    }

    @Override
    protected void init() {
        var textArea = new NotesTextField(this.textRenderer, 20, 20, width - 40, height - 140, 5);
        addDrawableChild(textArea);
    }
}
