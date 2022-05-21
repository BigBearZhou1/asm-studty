package transform.transform;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import transform.visitor.FieldAddClassVisitor;
import utils.FileUtils;

public class FieldAddTransformCore {
    public static void main(String[] args) {
        String filename = "sample/FieldDeleteTest.class";
        String filePath = FileUtils.getFilePath(filename);
        byte[] src = FileUtils.readBytes(filePath);

        ClassReader cr = new ClassReader(src);

        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

        FieldAddClassVisitor cv = new FieldAddClassVisitor(Opcodes.ACC_PUBLIC, "objValue",
                "Ljava/lang/Object;", Opcodes.ASM9, cw);

        int parsingOption = ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES;
        cr.accept(cv, parsingOption);

        byte[] dest = cw.toByteArray();

        FileUtils.writeBytes(filePath, dest);
    }
}
