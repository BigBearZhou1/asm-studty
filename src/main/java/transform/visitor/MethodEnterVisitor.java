package transform.visitor;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import transform.adaptor.MethodEnterAdaptor;

public class MethodEnterVisitor extends ClassVisitor {
    public MethodEnterVisitor(int api,ClassVisitor cv){
        super(api,cv);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);

        boolean isAbstractmethod = (access & Opcodes.ACC_ABSTRACT) == Opcodes.ACC_ABSTRACT;
        boolean isNativeMethod = (access & Opcodes.ACC_NATIVE) == Opcodes.ACC_NATIVE;
        if(isAbstractmethod || isNativeMethod){
            return mv;
        }

        if(mv!=null && !name.equals("<init>")){
            mv = new MethodEnterAdaptor(api,mv);
        }
        return mv;
    }
}
