package transform.visitor;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

public abstract class MethodAddClassVisitor extends ClassVisitor {
    private int methodAccess;
    private String methodName;
    private String methodDesc;
    private String methodSignature;
    private String[] exceptions;
    private boolean isAlreadyHave;

    public MethodAddClassVisitor(int api, ClassVisitor cv, int access, String name,
                                 String descriptor, String signature, String[] exceptions) {
        super(api, cv);
        this.methodAccess = access;
        this.methodName = name;
        this.methodDesc = descriptor;
        this.methodSignature = signature;
        this.exceptions = exceptions;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        if (name.equals(methodName) && descriptor.equals(methodDesc)) {
            isAlreadyHave = true;
        }
        return super.visitMethod(access, name, descriptor, signature, exceptions);
    }

    @Override
    public void visitEnd() {
        if (!isAlreadyHave) {
            MethodVisitor mv = cv.visitMethod(methodAccess, methodName, methodDesc, methodSignature, exceptions);
            if (mv != null) {
                generateMethod(mv);
            }
        }

        super.visitEnd();
    }

    public abstract void generateMethod(MethodVisitor mv);
}
