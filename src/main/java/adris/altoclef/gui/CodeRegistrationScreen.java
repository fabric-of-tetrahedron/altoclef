package adris.altoclef.gui;

import com.chaosthedude.notes.gui.NotesTextField;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.openhft.compiler.CompilerUtils;

import java.lang.reflect.InvocationTargetException;

public class CodeRegistrationScreen extends Screen {
    public final Screen parent;

    public NotesTextField textArea;
    public Class aClass;

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
        textArea = new NotesTextField(this.textRenderer, 20, 20, width - 40, height - 60, 5);
        addDrawableChild(textArea);
        addDrawableChild(ButtonWidget.builder(Text.of("Compile"), b -> {
            try {
                aClass = CompilerUtils.CACHED_COMPILER.loadFromJava("Main", textArea.getText());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }).dimensions(20, height - 40, 60, 20).build());
        addDrawableChild(ButtonWidget.builder(Text.of("Execute main function"), b -> {
            if (aClass != null) {
                try {
                    aClass.getDeclaredMethod("main", String[].class).invoke(null, (Object) new String[]{});
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }).dimensions(100, height - 40, 120, 20).build());
    }
}
