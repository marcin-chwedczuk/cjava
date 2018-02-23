package pl.marcinchwedczuk.cjava.decompiler.controlflow;

import com.sun.org.apache.bcel.internal.generic.InstructionTargeter;
import pl.marcinchwedczuk.cjava.bytecode.instruction.Instruction;
import pl.marcinchwedczuk.cjava.bytecode.instruction.InstructionPC;

import java.util.ArrayList;
import java.util.List;

import static pl.marcinchwedczuk.cjava.util.ListUtils.firstElement;
import static pl.marcinchwedczuk.cjava.util.ListUtils.lastElement;

public class FlowBlock {
	// edges
	private final List<FlowTransition> outgoing = new ArrayList<>();
	private final List<FlowTransition> incoming = new ArrayList<>();

	private final List<FlowElement> elements = new ArrayList<>();

	public boolean isEmpty() {
		return elements.isEmpty();
	}

	public void addTransitionTo(FlowBlock targetBlock, TransitionCondition condition) {
		FlowTransition transition = new FlowTransition(this, targetBlock, condition);

		outgoing.add(transition);
		targetBlock.incoming.add(transition);
	}

	public void addInstruction(Instruction instruction) {
		elements.add(new InstructionFlowElement(instruction));
	}

	public InstructionPC getPC() {
		return firstElement(elements).getPC();
	}

	public Instruction getLastInstruction() {
		return ((InstructionFlowElement) lastElement(elements)).getInstruction();
	}

	public Instruction getFirstInstruction() {
		return ((InstructionFlowElement) firstElement(elements)).getInstruction();
	}

	public List<FlowTransition> getOutgoing() {
		return outgoing;
	}

	public List<FlowTransition> getIncoming() {
		return incoming;
	}

	public List<FlowElement> getElements() {
		return elements;
	}

	@Override
	public String toString() {
		if (isEmpty()) return "EmptyBlock";

		return "Block(from=" + firstElement(elements).getPC() +
				",to=" + lastElement(elements).getPC() + ")";
	}

}
