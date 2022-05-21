package transform.visitor;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;

public class FieldDeleteClassVisitor extends ClassVisitor {
    private final String fieldName;
    private final String fieldDesc;

    public FieldDeleteClassVisitor(int i, ClassVisitor classVisitor,String fieldName,String fieldDesc) {
        super(i, classVisitor);
        this.fieldName = fieldName;
        this.fieldDesc = fieldDesc;
    }

    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        if(name.equals(fieldName)&&fieldDesc.equals(descriptor)){
            return null;
        }
        return super.visitField(access, name, descriptor, signature, value);
    }
}
