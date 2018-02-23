package pl.marcinchwedczuk.cjava.bytecode.instruction;

public class TripleOperandInstruction extends Instruction {
	private final int operand1;
	private final int operand2;
	private final int operand3;

	protected TripleOperandInstruction(int pc, Opcode opcode,
									   int operand1, int operand2, int operand3) {
		super(pc, opcode);
		this.operand1 = operand1;
		this.operand2 = operand2;
		this.operand3 = operand3;
	}

	public int getOperand1() {
		return operand1;
	}

	public int getOperand2() {
		return operand2;
	}

	public int getOperand3() {
		return operand3;
	}

	@Override
	public String toString() {
		return String.format("%4s: %s 0x%02X 0x%02X 0x%02X",
				getPC(), getOpcode(),
				operand1, operand2, operand3);
	}
}
