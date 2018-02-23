package pl.marcinchwedczuk.cjava.bytecode.instruction;

public class DoubleOperandInstruction extends Instruction {
	private int operand1;
	private int operand2;

	public DoubleOperandInstruction(int pc, Opcode opcode, int operand1, int operand2) {
		super(pc, opcode);
		this.operand1 = operand1;
		this.operand2 = operand2;
	}

	public int getOperand1() {
		return operand1;
	}

	public int getOperand2() {
		return operand2;
	}

	@Override
	public String toString() {
		return String.format("%4s: %s %02d %02d", getPC(), getOpcode(), operand1, operand2);
	}
}
