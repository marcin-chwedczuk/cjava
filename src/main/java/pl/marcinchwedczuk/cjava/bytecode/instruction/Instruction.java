package pl.marcinchwedczuk.cjava.bytecode.instruction;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public abstract class Instruction {
	private final Opcode opcode;

	protected Instruction(Opcode opcode) {
		this.opcode = requireNonNull(opcode);
	}

	public Opcode getOpcode() {
		return opcode;
	}
}
