package pl.marcinchwedczuk.cjava.decompiler.controlflow;

import pl.marcinchwedczuk.cjava.bytecode.instruction.InstructionPC;

public interface FlowElement {
	InstructionPC getPC();
}
