package pl.marcinchwedczuk.cjava.bytecode.instruction;

public class SingleOperandInstruction extends Instruction {
	private int operand;

	public SingleOperandInstruction(Opcode opcode, int operand) {
		super(opcode);

		this.operand = operand;
	}

	public int getOperand() {
		return operand;
	}
}
