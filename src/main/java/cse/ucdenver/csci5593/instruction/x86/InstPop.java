package cse.ucdenver.csci5593.instruction.x86;

import cse.ucdenver.csci5593.instruction.BadlyFormattedInstructionException;
import cse.ucdenver.csci5593.instruction.Instruction;
import cse.ucdenver.csci5593.instruction.OperandFlag;
import cse.ucdenver.csci5593.instruction.x86.helpers.IPHelper;
import cse.ucdenver.csci5593.memory.MemoryManager;
import cse.ucdenver.csci5593.parser.X86InstructionSet;

/**
 * Created by max on 4/7/16.
 */
public class InstPop extends Instruction {
    @Override
    public int CPI(MemoryManager memoryManager) throws BadlyFormattedInstructionException {
        return 1;
    }

    @Override
    public String opCode() {
        return "POP";
    }

    @Override
    public int execute(MemoryManager memoryManager) throws BadlyFormattedInstructionException {
        if (this.operands.size() != 1) {
            this.throwException("Incorrect number of arguments.");
        }

        if (!this.getOperand(0).isType(OperandFlag.register)) {
            this.throwException("Operand is not a register");
        }

        int stackPointer = memoryManager.getRegisterValue("%esp");
        int value = memoryManager.getMemoryValue(stackPointer).value;

        memoryManager.setMemoryValue(this.getOperand(0).getAddress(memoryManager), value);
        memoryManager.setRegisterValue("%esp", stackPointer-1);

        IPHelper.IncrementIP(memoryManager);

        return 0;
    }

    public static void load() {
        X86InstructionSet.RegisterInstruction(InstPop.class, "POP");
        X86InstructionSet.RegisterInstruction(InstPop.class, "POPL");
    }
}
