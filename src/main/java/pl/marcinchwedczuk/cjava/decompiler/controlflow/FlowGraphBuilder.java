package pl.marcinchwedczuk.cjava.decompiler.controlflow;

import pl.marcinchwedczuk.cjava.bytecode.instruction.*;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static pl.marcinchwedczuk.cjava.bytecode.instruction.Opcode.nop;
import static pl.marcinchwedczuk.cjava.bytecode.instruction.Opcode.swap;
import static pl.marcinchwedczuk.cjava.decompiler.controlflow.TransitionCondition.*;
import static pl.marcinchwedczuk.cjava.util.ListUtils.firstElement;
import static pl.marcinchwedczuk.cjava.util.ListUtils.withoutFirstElement;

public class FlowGraphBuilder {
	private List<Instruction> instructions = new ArrayList<>();

	public FlowGraphBuilder(List<Instruction> instructions) {
		this.instructions.addAll(instructions);
	}

	public FlowGraph buildGraph() {
		FlowGraph graph = new FlowGraph(new FlowBlock(), new FlowBlock());

		List<FlowBlock> blocks = splitInstructionsIntoBlocks();
		Map<InstructionPC, FlowBlock> blockByPC = computeBlocksOffsets(blocks);

		for (int i = 0; i < blocks.size(); i++) {
			FlowBlock current = blocks.get(i);
			FlowBlock next = ((i+1) < blocks.size()) ? blocks.get(i+1) : graph.stop;

			graph.addBlock(current);

			Instruction lastInstruction = current.getLastInstruction();
			if (isReturnOrThrowInstruction(lastInstruction)) {
				current.addTransitionTo(graph.stop, ALWAYS);
				continue;
			}

			if (isJumpInstruction(lastInstruction)) {
				boolean isUnconditional = isUnconditionalJumpInstruction(lastInstruction);

				// when jump is performed
				InstructionPC target = getJumpTarget(lastInstruction);
				FlowBlock jumpTarget = blockByPC.get(target);
				current.addTransitionTo(jumpTarget, isUnconditional ? ALWAYS : WHEN_TRUE);

				if (isConditionalJumpInstruction(lastInstruction)) {
					// when jump is not performed
					current.addTransitionTo(next, WHEN_FALSE);
				}

				continue;
			}

			// block split because of a jump target.
			// control flow falls to the next block.
			current.addTransitionTo(next, ALWAYS);
		}

		graph.start.addTransitionTo(firstElement(blocks), ALWAYS);

		return graph;
	}

	private Map<InstructionPC, FlowBlock> computeBlocksOffsets(List<FlowBlock> blocks) {
		return blocks
				.stream()
				.collect(toMap(FlowBlock::getPC, identity()));
	}

	private List<FlowBlock> splitInstructionsIntoBlocks() {
		List<FlowBlock> blocks = new ArrayList<>();

		FlowBlock current = new FlowBlock();
		blocks.add(current);

		Set<InstructionPC> jumpTargets = findJumpInstructionsTargets();
		for (Instruction instr : instructions) {
			boolean isJumpTarget = jumpTargets.contains(instr.getPC());

			if (isJumpTarget) {
				// start new block
				current = new FlowBlock();
				blocks.add(current);
			}

			current.addInstruction(instr);

			if (isControlFlowInstruction(instr)) {
				// start new block
				current = new FlowBlock();
				blocks.add(current);
			}
		}

		blocks.removeIf(FlowBlock::isEmpty);
		return blocks;
	}

	private Set<InstructionPC> findJumpInstructionsTargets() {
		Set<InstructionPC> targets = new HashSet<>();

		for (Instruction instruction : instructions) {
			if (isJumpInstruction(instruction)) {
				InstructionPC target = getJumpTarget(instruction);
				targets.add(target);
			}
		}

		return targets;
	}

	private static InstructionPC getJumpTarget(Instruction instruction) {
		int offset = ((SingleOperandInstruction)instruction).getOperand();

		InstructionPC target = instruction
				.getPC()
				.addOffset(offset);

		return target;
	}

	private boolean isControlFlowInstruction(Instruction instruction) {
		return isJumpInstruction(instruction)
			|| isReturnOrThrowInstruction(instruction);
	}

	private boolean isReturnOrThrowInstruction(Instruction instruction) {
		switch (instruction.getOpcode()) {
			case return_:
			case athrow:
				return true;

			default:
				return false;
		}
	}

	private boolean isJumpInstruction(Instruction instruction) {
		return isUnconditionalJumpInstruction(instruction)
			|| isConditionalJumpInstruction(instruction);
	}

	private boolean isConditionalJumpInstruction(Instruction instruction) {
		switch (instruction.getOpcode()) {
			case ifeq: case ifne:
			case iflt: case ifgt:
			case ifle: case ifge:
				// TODO: Other jumps

			case if_icmpeq: case if_icmpne:
			case if_icmplt: case if_icmpgt:
			case if_icmple: case if_icmpge:
				return true;

			default:
				return false;
		}
	}

	private boolean isUnconditionalJumpInstruction(Instruction instruction) {
		switch (instruction.getOpcode()) {
			case goto_: case goto_w:
				return true;

			default:
				return false;
		}
	}
}
