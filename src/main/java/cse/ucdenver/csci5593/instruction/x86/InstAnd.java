package cse.ucdenver.csci5593.instruction.x86;

import cse.ucdenver.csci5593.instruction.BadlyFormattedInstructionException;
import cse.ucdenver.csci5593.instruction.Instruction;
import cse.ucdenver.csci5593.instruction.x86.helpers.FlagHelper;
import cse.ucdenver.csci5593.instruction.x86.helpers.IPHelper;
import cse.ucdenver.csci5593.memory.MemoryManager;
import cse.ucdenver.csci5593.memory.RegisterMemoryModule;
import cse.ucdenver.csci5593.parser.X86InstructionSet;

public class InstAnd extends Instruction {
    public int CPI(MemoryManager memoryManager) throws BadlyFormattedInstructionException {
        return 2;
    }

    public String opCode() {
        return "AND";
    }

    public int execute(MemoryManager memoryManager) throws BadlyFormattedInstructionException {
        if (this.operands.size() != 2) {
            throw new BadlyFormattedInstructionException(this.opCode() + ": Incorrect number of arguments.");
        }

        long result = this.getOperand(0).getValue(memoryManager) & this.getOperand(1).getValue(memoryManager);

        memoryManager.setMemoryValue(this.getOperand(1).getAddress(memoryManager), (int)result);

        this.setFlags(memoryManager, result);

        IPHelper.IncrementIP(memoryManager);

        return 0;
    }

    private void setFlags(MemoryManager memoryManager, long result) {

        memoryManager.resetFlag(RegisterMemoryModule.Flag.CARRY_FLAG);
        memoryManager.resetFlag(RegisterMemoryModule.Flag.OVERFLOW_FLAG);

        if (FlagHelper.GetParityFlag(result)) {
            memoryManager.setFlag(RegisterMemoryModule.Flag.PARITY_FLAG);
        } else {
            memoryManager.resetFlag(RegisterMemoryModule.Flag.PARITY_FLAG);
        }

        if (FlagHelper.GetSignFlag(result)) {
            memoryManager.setFlag(RegisterMemoryModule.Flag.SIGN_FLAG);
        } else {
            memoryManager.resetFlag(RegisterMemoryModule.Flag.SIGN_FLAG);
        }

        if (FlagHelper.GetZeroFlag(result)) {
            memoryManager.setFlag(RegisterMemoryModule.Flag.ZERO_FLAG);
        } else {
            memoryManager.resetFlag(RegisterMemoryModule.Flag.ZERO_FLAG);
        }

    }

    public static void load() {
        X86InstructionSet.RegisterInstruction(InstAnd.class, "AND");
        X86InstructionSet.RegisterInstruction(InstAnd.class, "ANDL");
    }
}
