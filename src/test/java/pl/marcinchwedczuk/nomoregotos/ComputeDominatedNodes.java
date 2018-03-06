package pl.marcinchwedczuk.nomoregotos;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class ComputeDominatedNodes {
	private final ControlFlowGraph g;
	private final CfgNode head;

	private final IdentityHashMap<CfgNode, CfgNode> interval = new IdentityHashMap<>();

	public ComputeDominatedNodes(ControlFlowGraph g, CfgNode head) {
		this.g = g;
		this.head = head;
	}

	public List<CfgNode> getDominatedNodes() {
		return new ArrayList<>(interval.keySet());
	}

	public List<CfgNode> getRegionSuccessor() {
		return interval.keySet().stream()
				.flatMap(n -> n.getOutgoing().stream())
				.map(e -> e.to)
				.filter(n -> !interval.containsKey(n))
				.collect(toList());
	}

	public void compute() {
		interval.put(head, head);

		boolean changed = true;
		while (changed) {
			changed = false;

			// compute successors
			List<CfgNode> successors = interval.keySet().stream()
					.flatMap(n -> n.getOutgoing().stream())
					.map(e -> e.to)
					.filter(toNode -> !interval.containsKey(toNode))
					.collect(toList());

			for (CfgNode successor : successors) {
				if (successor == g.start || successor == g.stop)
					continue;

				boolean allPredecessorsAreInInterval =
						successor.getIncoming().stream()
							.map(e -> e.from)
							.allMatch(interval::containsKey);

				if (allPredecessorsAreInInterval) {
					interval.put(successor, successor);
					changed = true;
				}
			}
		}
	}

}
