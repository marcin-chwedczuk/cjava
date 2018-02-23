package pl.marcinchwedczuk.cjava.decompiler.controlflow;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class FlowTransition {
	private final FlowBlock fromBlock;
	private final FlowBlock toBlock;
	private final TransitionCondition condition;

	public FlowTransition(FlowBlock fromBlock, FlowBlock toBlock, TransitionCondition condition) {
		this.fromBlock = requireNonNull(fromBlock);
		this.toBlock = requireNonNull(toBlock);
		this.condition = condition;
	}

	public FlowBlock getFromBlock() {
		return fromBlock;
	}

	public FlowBlock getToBlock() {
		return toBlock;
	}

	public TransitionCondition getCondition() {
		return condition;
	}
}
