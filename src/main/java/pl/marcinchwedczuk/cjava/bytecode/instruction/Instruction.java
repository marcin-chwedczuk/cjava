package pl.marcinchwedczuk.cjava.bytecode.instruction;

import com.google.common.base.Preconditions;

import static java.util.Objects.requireNonNull;

public abstract class Instruction {
	private final int pc;
	private final Opcode opcode;

	protected Instruction(int pc, Opcode opcode) {
		Preconditions.checkArgument(pc >= 0);

		this.opcode = requireNonNull(opcode);
		this.pc = pc;
	}

	public Opcode getOpcode() {
		return opcode;
	}

	public int getPC() {
		return pc;
	}
}
