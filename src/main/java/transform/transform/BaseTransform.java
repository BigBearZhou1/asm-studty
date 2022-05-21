package transform.transform;


import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import utils.FileUtils;

public class BaseTransform {
    public static void main(String[] args) {
        String relativePath = "sample/Base.class";
        String filePath = FileUtils.getFilePath(relativePath);
        byte[] bytes1 = FileUtils.readBytes(filePath);

        ClassReader cr = new ClassReader(bytes1);

        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

        int api = Opcodes.ASM9;

        ClassVisitor cv = new ClassVisitor(api, cw) {
        };

        int parsingOptions = ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES;

        cr.accept(cv, parsingOptions);

        byte[] bytes2 = cw.toByteArray();

        FileUtils.writeBytes(filePath, bytes2);
    }
}
