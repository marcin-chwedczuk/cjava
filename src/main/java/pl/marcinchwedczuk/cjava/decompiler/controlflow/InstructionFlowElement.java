package pl.marcinchwedczuk.cjava.decompiler.controlflow;

import pl.marcinchwedczuk.cjava.bytecode.instruction.Instruction;
import pl.marcinchwedczuk.cjava.bytecode.instruction.InstructionPC;
import pl.marcinchwedczuk.cjava.bytecode.instruction.Opcode;

public class InstructionFlowElement implements FlowElement {
	private final Instruction instruction;

	public InstructionFlowElement(Instruction instruction) {
		this.instruction = instruction;
	}

	public Opcode getOpcode() {
		return instruction.getOpcode();
	}

	public Instruction getInstruction() {
		return instruction;
	}

	@Override
	public InstructionPC getPC() {
		return instruction.getPC();
	}

	@Override
	public String toString() {
		return instruction.toString();
	}
}
