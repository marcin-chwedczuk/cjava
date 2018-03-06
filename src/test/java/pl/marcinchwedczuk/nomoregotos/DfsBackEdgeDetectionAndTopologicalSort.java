package pl.marcinchwedczuk.nomoregotos;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;

public class DfsBackEdgeDetectionAndTopologicalSort {
	private enum Status { IN_PROGRESS, VISITED }

	private final ControlFlowGraph g;

	private final List<CfgNode> topologicalOrder = new ArrayList<>();
	private final IdentityHashMap<CfgNode, Status> visitedStatus = new IdentityHashMap<>();

	public DfsBackEdgeDetectionAndTopologicalSort(ControlFlowGraph g) {
		this.g = g;
	}

	public List<CfgNode> getTopologicalOrder() {
		return topologicalOrder;
	}

	public void start() {
		visit(g.start, null);
	}

	private void visit(CfgNode node, CfgEdge e) {
		if (visitedStatus.containsKey(node)) {
			Status status = visitedStatus.get(node);
			if (status == Status.IN_PROGRESS) {
				e.backedge = true;
			}
			return;
		}

		visitedStatus.put(node, Status.IN_PROGRESS);

		for (CfgEdge outgoingEdge : node.getOutgoing()) {
			visit(outgoingEdge.to, outgoingEdge);
		}

		visitedStatus.put(node, Status.VISITED);

		topologicalOrder.add(node);
	}
}
