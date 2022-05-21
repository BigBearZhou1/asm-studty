package transform.transform;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import transform.visitor.ClassChangeVersionVisitor;
import utils.FileUtils;

public class VersionTestTransformCore {
    public static void main(String[] args) {
        String relativePath = "sample/VersionTest.class";
        String filePath = FileUtils.getFilePath(relativePath);
        byte[] bytes1 = FileUtils.readBytes(filePath);

        ClassReader cr = new ClassReader(bytes1);

        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

        ClassChangeVersionVisitor cv = new ClassChangeVersionVisitor(Opcodes.ASM9, cw);

        int parsingOptions = ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES;
        cr.accept(cv, parsingOptions);

        byte[] bytes2 = cw.toByteArray();

        FileUtils.writeBytes(filePath, bytes2);
    }
}
