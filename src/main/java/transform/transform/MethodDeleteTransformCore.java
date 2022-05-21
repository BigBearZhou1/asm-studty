package transform.transform;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import transform.visitor.MethodDeleteClassVisitor;
import utils.FileUtils;

import java.io.File;

public class MethodDeleteTransformCore {
    public static void main(String[] args) {
        String fileName = "sample/MethodTest.class";
        String filePath = FileUtils.getFilePath(fileName);
        byte[] src = FileUtils.readBytes(filePath);

        ClassReader cr = new ClassReader(src);

        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

        MethodDeleteClassVisitor cv = new MethodDeleteClassVisitor(Opcodes.ASM9, cw, "add", "(II)I");

        int parsingOptions = ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES;

        cr.accept(cv, parsingOptions);

        byte[] dest = cw.toByteArray();

        FileUtils.writeBytes(filePath, dest);
    }
}
