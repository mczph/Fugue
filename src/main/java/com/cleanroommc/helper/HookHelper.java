package com.cleanroommc.helper;

import scala.tools.asm.Opcodes;

public class HookHelper {
    public static boolean isInterface (int opcode) {
        return opcode == Opcodes.INVOKEINTERFACE;
    }
}
