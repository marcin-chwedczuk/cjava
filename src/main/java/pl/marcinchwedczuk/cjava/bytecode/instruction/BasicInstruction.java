package pl.marcinchwedczuk.cjava.bytecode.instruction;

public class BasicInstruction extends Instruction {
	public BasicInstruction(int pc, Opcode opcode) {
		super(pc, opcode);
	}

	@Override
	public String toString() {
		return String.format("%4s: %s", getPC(), getOpcode());
	}
}
