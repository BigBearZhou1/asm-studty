package transform.transform;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import transform.visitor.FieldDeleteClassVisitor;
import utils.FileUtils;

import java.io.File;

public class FieldDeleteTransformCore {
    public static void main(String[] args) {
        String fileName = "sample/FieldDeleteTest.class";
        String filePath = FileUtils.getFilePath(fileName);
        byte[] src = FileUtils.readBytes(filePath);

        ClassReader cr = new ClassReader(src);

        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

        FieldDeleteClassVisitor cv = new FieldDeleteClassVisitor(Opcodes.ASM9, cw, "strValue", "Ljava/lang/String;");

        int parsingOptions = ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES;

        cr.accept(cv, parsingOptions);

        byte[] dest = cw.toByteArray();

        FileUtils.writeBytes(filePath, dest);
    }
}
