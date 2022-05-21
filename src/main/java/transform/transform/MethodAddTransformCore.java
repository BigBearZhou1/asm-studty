package transform.transform;

import org.objectweb.asm.*;
import transform.visitor.MethodAddClassVisitor;
import utils.FileUtils;

import static org.objectweb.asm.Opcodes.*;

public class MethodAddTransformCore {
    public static void main(String[] args) {
        String fileName = "sample/MethodTest.class";
        String filePath = FileUtils.getFilePath(fileName);
        byte[] src = FileUtils.readBytes(filePath);

        ClassReader cr = new ClassReader(src);

        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

        MethodAddClassVisitor cv = new MethodAddClassVisitor(ASM9, cw, ACC_PUBLIC, "mul", "(II)I", null, null) {

            @Override
            public void generateMethod(MethodVisitor mv) {
                mv.visitCode();
                mv.visitVarInsn(ILOAD, 1);
                mv.visitVarInsn(ILOAD, 2);
                mv.visitInsn(IMUL);
                mv.visitInsn(IRETURN);
                mv.visitMaxs(2, 3);
                mv.visitEnd();
            }
        };

        int parsingOptions = ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES;
        cr.accept(cv, parsingOptions);
        byte[] dest = cw.toByteArray();
        FileUtils.writeBytes(filePath, dest);
    }
}
