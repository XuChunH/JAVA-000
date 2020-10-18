import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;

/**
 * @author yehui
 * @date 2020/10/18
 */
public class HelloClassloader extends ClassLoader {

    /**
     * Finds the class with the specified <a href="#name">binary name</a>.
     * This method should be overridden by class loader implementations that
     * follow the delegation model for loading classes, and will be invoked by
     * the {@link #loadClass <tt>loadClass</tt>} method after checking the
     * parent class loader for the requested class.  The default implementation
     * throws a <tt>ClassNotFoundException</tt>.
     *
     * @param name The <a href="#name">binary name</a> of the class
     * @return The resulting <tt>Class</tt> object
     * @throws ClassNotFoundException If the class could not be found
     * @since 1.2
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        if (!"Hello".equals(name)) {
            throw new IllegalArgumentException("不支持的类名. name=" + name);
        }

        final String path = getResource("Hello.xlass").getFile();
        try(FileInputStream fileInputStream = new FileInputStream(path);
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            // 先不考虑大文件的问题
            int value = -1;
            while ((value = fileInputStream.read()) != -1) {
                byteArrayOutputStream.write(255 - value);
            }
            final byte[] bytes = byteArrayOutputStream.toByteArray();
            return defineClass("Hello", bytes, 0, bytes.length);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

    }

    /**
     * Finds the resource with the given name.  A resource is some data
     * (images, audio, text, etc) that can be accessed by class code in a way
     * that is independent of the location of the code.
     *
     * <p> The name of a resource is a '<tt>/</tt>'-separated path name that
     * identifies the resource.
     *
     * <p> This method will first search the parent class loader for the
     * resource; if the parent is <tt>null</tt> the path of the class loader
     * built-in to the virtual machine is searched.  That failing, this method
     * will invoke {@link #findResource(String)} to find the resource.  </p>
     *
     * @param name The resource name
     * @return A <tt>URL</tt> object for reading the resource, or
     * <tt>null</tt> if the resource could not be found or the invoker
     * doesn't have adequate  privileges to get the resource.
     * @apiNote When overriding this method it is recommended that an
     * implementation ensures that any delegation is consistent with the {@link
     * #getResources(String) getResources(String)} method.
     * @since 1.1
     */
    @Override
    public URL getResource(String name) {
        // 这个会先找 parentClassLoader 找
        return super.getResource(name);
    }

    /**
     * Finds the resource with the given name. Class loader implementations
     * should override this method to specify where to find resources.
     *
     * @param name The resource name
     * @return A <tt>URL</tt> object for reading the resource, or
     * <tt>null</tt> if the resource could not be found
     * @since 1.2
     */
    @Override
    protected URL findResource(String name) {
        // 这个写自定义的一些逻辑
        return super.findResource(name);
    }

    /**
     * Loads the class with the specified <a href="#name">binary name</a>.
     * This method searches for classes in the same manner as the {@link
     * #loadClass(String, boolean)} method.  It is invoked by the Java virtual
     * machine to resolve class references.  Invoking this method is equivalent
     * to invoking {@link #loadClass(String, boolean) <tt>loadClass(name,
     * false)</tt>}.
     *
     * @param name The <a href="#name">binary name</a> of the class
     * @return The resulting <tt>Class</tt> object
     * @throws ClassNotFoundException If the class was not found
     */
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        // 这里是双亲委派模型, 可以覆盖这个方法实现自己的逻辑
        return super.loadClass(name);
    }

    public static void main(String[] args)
        throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

        final HelloClassloader helloClassloader = new HelloClassloader();
        final Class<?> helloClass = helloClassloader.loadClass("Hello");
        final Object helloObject = helloClass.newInstance();

        for (Method method : helloClass.getMethods()) {
            System.out.println(method.getName());
        }

        final Object returnValue = helloClass.getMethod("hello")
            .invoke(helloObject);
        System.out.println(returnValue);
    }
}
