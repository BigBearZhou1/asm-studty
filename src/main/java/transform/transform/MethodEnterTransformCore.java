package transform.transform;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import transform.visitor.MethodEnterVisitor;
import utils.FileUtils;

import java.io.File;

public class MethodEnterTransformCore {
    public static void main(String[] args) {
        String fileName = "sample/HelloWorld.class";
        String filePath = FileUtils.getFilePath(fileName);
        byte[] src = FileUtils.readBytes(filePath);

        ClassReader cr = new ClassReader(src);

        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

        MethodEnterVisitor cv = new MethodEnterVisitor(Opcodes.ASM9, cw);

        int parsingOptions = ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES;

        cr.accept(cv, parsingOptions);

        byte[] bytes = cw.toByteArray();

        FileUtils.writeBytes(filePath,bytes);
    }
}
