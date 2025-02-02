package com.cleanroommc.transformer;

import com.cleanroommc.FugueLoadingPlugin;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.Launch;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class EntityPlayerRayTraceTransformer implements IClassTransformer {
    
    public EntityPlayerRayTraceTransformer() {
        FugueLoadingPlugin.registerToKnownTransformer("shouldersurfing", this);
    }
    @Override
    public byte[] transform(String s, String s1, byte[] bytes) {
        if (bytes == null)
        {
            return null;
        }

        if (!s1.equals("com.teamderpy.shouldersurfing.asm.transformers.EntityPlayerRayTrace"))
        {
            return bytes;
        }


        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);
        boolean modified = false;
        AbstractInsnNode prevLine = null;
        if (classNode.methods != null)
        {
            for (MethodNode methodNode : classNode.methods)
            {
                if (methodNode.name.equals("transform")) {
                    InsnList instructions = methodNode.instructions;
                    if (instructions != null)
                    {
                        for (AbstractInsnNode insnNode : instructions)
                        {
                            if (insnNode.getOpcode() == Opcodes.LDC && insnNode instanceof LdcInsnNode ldcInsnNode)
                            {
                                if (ldcInsnNode.cst.equals("com/teamderpy/shouldersurfing/asm/InjectionDelegation"))
                                {
                                    ldcInsnNode.cst = "L" + ldcInsnNode.cst + ";";
                                    modified = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        if (modified)
        {
            Launch.classLoader.unRegisterSuperTransformer(this);
            ClassWriter classWriter = new ClassWriter(0);

            classNode.accept(classWriter);
            return classWriter.toByteArray();
        }
        return bytes;
    }
}
