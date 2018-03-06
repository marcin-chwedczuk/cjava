package pl.marcinchwedczuk.nomoregotos;

import pl.marcinchwedczuk.nomoregotos.graph.slicegraph.SliceGraph;
import pl.marcinchwedczuk.nomoregotos.graph.slicegraph.SliceNode;

import java.util.Stack;

public class BuildSliceGraphAlgorithm {
	private final SliceGraph sliceGraph = new SliceGraph();

	private final Stack<CfgNode> dfsStack = new Stack<>();
	private final Stack<CfgEdge> edgesToVisit = new Stack<>();

	private final ObjectTracker<CfgEdge> visitedEdges = new ObjectTracker<>();
	private final ObjectTracker<CfgNode> visitedNodes = new ObjectTracker<>();

	private final ControlFlowGraph graph;
	private final CfgNode start;
	private final CfgNode stop;

	public BuildSliceGraphAlgorithm(ControlFlowGraph graph, CfgNode start, CfgNode stop) {
		this.graph = graph;
		this.start = start;
		this.stop = stop;
	}

	public SliceGraph createSliceGraph() {
		dfsStack.push(start);
		visit(start);

		return sliceGraph;
	}

	private void visit(CfgNode node) {
		markAsVisited(node);

		for (CfgEdge edge : node.getOutgoing()) {
			CfgNode target = edge.to;

			if (target == stop) {
				dfsStack.push(stop);
				addDfsPathToSliceGraph();
				dfsStack.pop();
			} else if (sliceGraph.contains(target) && !dfsStack.contains(target)) {
				addDfsPathToSliceGraph();
			}

			if (!isVisited(target)) {
				dfsStack.push(target);
				visit(target);
				dfsStack.pop();
			}

		}
	}

	private void addDfsPathToSliceGraph() {
		SliceNode prev = null;

		for (CfgNode node : dfsStack) {
			 SliceNode curr = sliceGraph.findOrCreateNode(node);
			 if (prev != null) {
				 sliceGraph.addEdge(prev, curr);
			 }
			 prev = curr;
		}
	}

	private boolean isVisited(CfgEdge node) {
		return visitedEdges.isTracked(node);
	}

	private void markAsVisited(CfgEdge node) {
		visitedEdges.track(node);
	}

	private boolean isVisited(CfgNode node) {
		return visitedNodes.isTracked(node);
	}

	private void markAsVisited(CfgNode node) {
		visitedNodes.track(node);
	}
}
