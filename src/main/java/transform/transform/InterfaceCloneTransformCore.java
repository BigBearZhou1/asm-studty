package transform.transform;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import transform.visitor.ClassCloneVisitor;
import utils.FileUtils;

public class InterfaceCloneTransformCore {
    public static void main(String[] args) {
        String filename="sample/InterfaceCloneTest.class";
        String filePath = FileUtils.getFilePath(filename);
        byte[] bytes1 = FileUtils.readBytes(filePath);

        ClassReader cr = new ClassReader(bytes1);

        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

        ClassCloneVisitor cv = new ClassCloneVisitor(Opcodes.ASM9, cw);

        int parsingOptions = ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES;
        cr.accept(cv,parsingOptions);

        byte[] bytes = cw.toByteArray();

        FileUtils.writeBytes(filePath,bytes);
    }
}
