package transform.adaptor;

import org.objectweb.asm.Type;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;


import static org.objectweb.asm.Opcodes.*;

public class MethodParameterAdapter extends MethodVisitor {
    int access;
    String name;
    String descriptor;

    public MethodParameterAdapter(int api, MethodVisitor mv, int access, String name, String descriptor) {
        super(api, mv);
        this.access = access;
        this.name = name;
        this.descriptor = descriptor;
    }

    @Override
    public void visitCode() {
        boolean isStatic = (access & Opcodes.ACC_STATIC) != 0;
        int slotIndex = isStatic ? 0 : 1;

        Type methodType = Type.getMethodType(descriptor);
        Type[] argumentTypes = methodType.getArgumentTypes();

        for (Type argumentType : argumentTypes) {
            int sort = argumentType.getSort();
            int size = argumentType.getSize();

            String desc = argumentType.getDescriptor();
            int opcode = argumentType.getOpcode(ILOAD);
            super.visitVarInsn(opcode, slotIndex);

            if (sort >= Type.BOOLEAN && sort <= Type.DOUBLE) {
                String methodDesc = String.format("(%s)V", descriptor);
                printValueOnStack(methodDesc);
            } else {
                printValueOnStack("Ljava/lang/Object;)V");
            }
            slotIndex += size;
        }
        super.visitCode();
    }

    @Override
    public void visitInsn(int opcode) {
        if ((opcode >= IRETURN && opcode <= RETURN) || opcode == ATHROW) {
            if (opcode >= IRETURN && opcode <= DRETURN) {
                Type methodType = Type.getMethodType(descriptor);
                Type returnType = methodType.getReturnType();

                int size = returnType.getSize();
                String descriptor = returnType.getDescriptor();

                if (size == 1) {
                    super.visitInsn(DUP);
                } else {
                    super.visitInsn(DUP2);
                }

                String methodDesc = String.format("(%s)V", descriptor);
                printValueOnStack(methodDesc);
            } else if (opcode == ARETURN) {
                super.visitInsn(DUP);
                printValueOnStack("(Ljava/lang/Object:)V");
            } else if (opcode == RETURN) {
                printMessage("    return void");
            } else {
                printMessage("    abnormal return");
            }
        }

        super.visitInsn(opcode);
    }

    private void printValueOnStack(String methodDesc) {
        super.visitMethodInsn(INVOKESTATIC, "sample/ParameterUtils", "printValueOnStack", descriptor, false);
    }

    private void printMessage(String str) {
        super.visitLdcInsn(str);
        super.visitMethodInsn(INVOKESTATIC, "sample/ParameterUtils", "printText", "(Ljava/lang/String;)V", false);
    }
}
