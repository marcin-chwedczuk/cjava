package pl.marcinchwedczuk.nomoregotos.graph.slicegraph;

import pl.marcinchwedczuk.nomoregotos.CfgEdge;
import pl.marcinchwedczuk.nomoregotos.CfgNode;
import pl.marcinchwedczuk.nomoregotos.graph.DirectedGraph;

public class SliceGraph extends DirectedGraph<SliceNode, SliceEdge> {
	public SliceNode addNode(CfgNode cfgNode) {
		SliceNode node = new SliceNode(cfgNode);
		nodes.add(node);
		return node;
	}

	public SliceEdge addEdge(SliceNode from, SliceNode to) {
		SliceEdge edge = new SliceEdge(from, to);
		edges.add(edge);

		from.addOutgoing(edge);
		to.addIncoming(edge);

		return edge;
	}

	public SliceNode findNode(CfgNode cfgNode) {
		return nodes.stream()
				.filter(n -> n.cfgNode == cfgNode)
				.findFirst()
				.orElse(null);
	}

	public SliceNode findOrCreateNode(CfgNode cfgNode) {
		SliceNode holdingNode = findNode(cfgNode);

		if (holdingNode != null) {
			return holdingNode;
		}

		return addNode(cfgNode);
	}

	public boolean contains(CfgNode cfgNode) {
		return nodes
				.stream()
				.anyMatch(n -> n.cfgNode == cfgNode);
	}
}
