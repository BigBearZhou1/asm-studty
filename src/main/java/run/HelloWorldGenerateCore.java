package run;

import org.objectweb.asm.ClassWriter;
import utils.FileUtils;

import static org.objectweb.asm.Opcodes.*;

public class HelloWorldGenerateCore {
    public static void main(String[] args) {
        String relativePath = "sample/HelloWorld.class";
        String filePath = FileUtils.getFilePath(relativePath);

        byte[] bytes = dump();

        FileUtils.writeBytes(filePath, bytes);
    }

    private static byte[] dump() {
        //(1).创建ClassWriter
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

        //(2).调用visitXXX方法
        cw.visit(V1_8, //version
                ACC_PUBLIC + ACC_ABSTRACT + ACC_INTERFACE, //access 当前类的访问标识 也可使用‘|’
                "sample/HelloWorld", //name 表示当前类的名字
                null, //signature 表示当前类的泛型信息，不存在泛型信息就是null
                "java/lang/Object", //superName 父类
                null);  //interfaces 类实现的其他接口信息

        cw.visitEnd();

        //(3).调用toByteArray()方法
        return cw.toByteArray();
    }
}
