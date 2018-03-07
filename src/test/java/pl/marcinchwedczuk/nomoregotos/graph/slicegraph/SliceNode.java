package pl.marcinchwedczuk.nomoregotos.graph.slicegraph;

import pl.marcinchwedczuk.nomoregotos.CfgNode;
import pl.marcinchwedczuk.nomoregotos.graph.Node;

public class SliceNode extends Node<SliceNode, SliceEdge> {
	public final CfgNode cfgNode;

	SliceNode(CfgNode cfgNode) {
		this.cfgNode = cfgNode;
	}

	@Override
	public String toString() {
		return cfgNode.toString();
	}
}
