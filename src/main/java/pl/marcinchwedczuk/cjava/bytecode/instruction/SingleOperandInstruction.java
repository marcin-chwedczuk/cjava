package pl.marcinchwedczuk.cjava.bytecode.instruction;

public class SingleOperandInstruction extends Instruction {
	private int operand;

	public SingleOperandInstruction(int pc, Opcode opcode, int operand) {
		super(pc, opcode);

		this.operand = operand;
	}

	public int getOperand() {
		return operand;
	}

	@Override
	public String toString() {
		return String.format("%04d: %s 0x%02X", getPC(), getOpcode(), operand);
	}
}
