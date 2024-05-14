package net.openhft.compiler;

public class MyClassLoader extends ClassLoader {
    public MyClassLoader() {
    }

    public MyClassLoader(ClassLoader parent) {
        super(parent);
    }

    protected final Class<?> defineClass1(String name, byte[] b, int off, int len) {
        return defineClass(name, b, off, len);
    }
}
