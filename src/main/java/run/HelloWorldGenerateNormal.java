package run;


import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import utils.FileUtils;

import static org.objectweb.asm.Opcodes.*;

public class HelloWorldGenerateNormal {
    public static void main(String[] args) {
        String relativePath = "sample/HelloWorld.class";
        String filePath = FileUtils.getFilePath(relativePath);

        byte[] bytes = dump();

        FileUtils.writeBytes(filePath, bytes);
    }

    private static byte[] dump() {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

        //1. visit
        cw.visit(V1_8, ACC_PUBLIC + ACC_SUPER, "sample/HelloWorld", null, "java/lang/Object", null);

        //2. visitMethod
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);

        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        mv.visitInsn(RETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();

        cw.visitEnd();

        //3. ClassWriter 返回字节数组=》classfile
        return cw.toByteArray();

    }
}
