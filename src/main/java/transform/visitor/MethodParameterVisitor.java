package transform.visitor;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import transform.adaptor.MethodParameterAdapter;

import static org.objectweb.asm.Opcodes.*;

public class MethodParameterVisitor extends ClassVisitor {
    public MethodParameterVisitor(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);

        boolean isAbstractMethod = (access & ACC_ABSTRACT) != 0;
        boolean isNativeMethod = (access & ACC_NATIVE) != 0;

        if (isAbstractMethod || isNativeMethod) {
            return mv;
        }
        if (mv != null && !name.equals("<init>")) {
            mv = new MethodParameterAdapter(api,mv,access,name,descriptor);
        }
        return mv;
    }
}
