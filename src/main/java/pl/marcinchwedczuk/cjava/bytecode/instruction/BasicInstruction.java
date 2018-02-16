package pl.marcinchwedczuk.cjava.bytecode.instruction;

public class BasicInstruction extends Instruction {
	public BasicInstruction(int pc, Opcode opcode) {
		super(pc, opcode);
	}

	@Override
	public String toString() {
		return String.format("%04d: %s", getPC(), getOpcode());
	}
}
