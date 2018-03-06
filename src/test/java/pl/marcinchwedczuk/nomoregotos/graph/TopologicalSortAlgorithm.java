package pl.marcinchwedczuk.nomoregotos.graph;

import pl.marcinchwedczuk.nomoregotos.CfgEdge;
import pl.marcinchwedczuk.nomoregotos.CfgNode;
import pl.marcinchwedczuk.nomoregotos.ObjectTracker;

import java.util.ArrayList;
import java.util.List;

public class TopologicalSortAlgorithm<N extends Node<N,E>, E extends Edge<N>> {
	private final N start;
	private final DirectedGraph<N,E> g;

	private final List<N> topologicalOrder = new ArrayList<>();
	private final ObjectTracker<N> visitedNodes = new ObjectTracker<>();

	public TopologicalSortAlgorithm(N start, DirectedGraph<N, E> g) {
		this.start = start;
		this.g = g;
	}

	public List<N> getTopologicalOrder() {
		return topologicalOrder;
	}

	public void start() {
		visit(start);
	}

	private void visit(N node) {
		if (visitedNodes.isTracked(node)) {
			return;
		}

		visitedNodes.track(node);

		for (E outgoingEdge : node.getOutgoing()) {
			visit(outgoingEdge.getTo());
		}

		topologicalOrder.add(node);
	}
}
