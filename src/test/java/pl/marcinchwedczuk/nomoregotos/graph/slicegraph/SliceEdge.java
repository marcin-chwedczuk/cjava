package pl.marcinchwedczuk.nomoregotos.graph.slicegraph;

import pl.marcinchwedczuk.nomoregotos.CfgEdge;
import pl.marcinchwedczuk.nomoregotos.graph.Edge;

public class SliceEdge extends Edge<SliceNode> {
	SliceEdge(SliceNode from, SliceNode to) {
		super(from, to);
	}
}
