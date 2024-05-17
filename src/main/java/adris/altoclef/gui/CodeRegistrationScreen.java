package adris.altoclef.gui;

import com.chaosthedude.notes.gui.NotesTextField;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.openhft.compiler.CompilerUtils;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

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
                String text = textArea.getText();
                ASTParser parser = ASTParser.newParser(AST.JLS17);
                parser.setKind(ASTParser.K_COMPILATION_UNIT);

                Map<String, String> options = new HashMap<>();
                options.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_17);
                options.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JavaCore.VERSION_17);
                options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_17);
                parser.setCompilerOptions(options);

                parser.setSource(text.toCharArray());
                CompilationUnit cu = (CompilationUnit) parser.createAST(null);

                String className = null;

                var packageName = cu.getPackage().getName().getFullyQualifiedName();

                System.out.println("Package name: " + packageName);

                for (Object type : cu.types()) {
                    if (type instanceof TypeDeclaration) {
                        TypeDeclaration td = (TypeDeclaration) type;

                        className = td.getName().getFullyQualifiedName();
                        System.out.println("Class name: " + className);
                    }
                }
                aClass = CompilerUtils.CACHED_COMPILER.loadFromJava(packageName + "." + className, text);
            } catch (ClassNotFoundException e) {
//                throw new RuntimeException(e);
                e.printStackTrace();
            }
        }).dimensions(20, height - 30, 60, 20).build());
        addDrawableChild(ButtonWidget.builder(Text.of("Execute main function"), b -> {
            if (aClass != null) {
                try {
                    aClass.getDeclaredMethod("main", String[].class).invoke(null, (Object) new String[]{});
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException |
                         RuntimeException e) {
//                    throw new RuntimeException(e);
                    e.printStackTrace();
                }
            }
        }).dimensions(100, height - 30, 120, 20).build());
    }
}
