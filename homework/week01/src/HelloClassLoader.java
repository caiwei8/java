import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HelloClassLoader extends ClassLoader{
    private static final int X = 255;

    public static void main(String[] args) {
        try {
            Object hello = new HelloClassLoader().findClass("/Users/daitianlei/Desktop/java/weekOne/src/Hello.xlass").newInstance();
            invokeMethod(hello, "hello");
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void invokeMethod(Object obj, String methodName) {
        try {
            Method hello = obj.getClass().getDeclaredMethod(methodName);
            hello.invoke(obj);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes = new byte[0];
        Path path = Paths.get(name);
        try {
            bytes = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i=0; i<bytes.length; i++){
            bytes[i] = (byte)(X - (int)bytes[i]);
        }

        return defineClass("Hello", bytes, 0, bytes.length);
    }


}