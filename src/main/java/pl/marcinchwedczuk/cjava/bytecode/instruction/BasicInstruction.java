package pl.marcinchwedczuk.cjava.bytecode.instruction;

public class BasicInstruction extends Instruction {
	public BasicInstruction(Opcode opcode) {
		super(opcode);
	}

	@Override
	public String toString() {
		return getOpcode().toString();
	}
}
