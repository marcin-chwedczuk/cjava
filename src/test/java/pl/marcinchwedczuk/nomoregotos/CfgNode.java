package pl.marcinchwedczuk.nomoregotos;

import com.google.common.base.Preconditions;
import pl.marcinchwedczuk.nomoregotos.condexpr.Condition;
import pl.marcinchwedczuk.nomoregotos.graph.slicegraph.SliceNode;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static pl.marcinchwedczuk.nomoregotos.CfgEdgeCondition.ALWAYS;

public abstract class CfgNode {
	public String color;
	public String extraLabel;
	public Condition condition;

	private final List<CfgEdge> incoming = newArrayList();
	private final List<CfgEdge> outgoing = newArrayList();

	public void addIncomingEdge(CfgEdge e) {
		incoming.add(e);
	}

	public void addOutgoingEdge(CfgEdge e) {
		Preconditions.checkState(outgoing.size() <= 1);

		boolean containsAlwaysEdge = outgoing
				.stream()
				.anyMatch(it -> it.condition == ALWAYS);

		Preconditions.checkState(!containsAlwaysEdge);

		boolean containsRepetition = outgoing
				.stream()
				.anyMatch(it -> it.condition == e.condition);

		Preconditions.checkState(!containsRepetition);

		outgoing.add(e);
	}

	public List<CfgEdge> getIncoming() {
		return incoming;
	}

	public List<CfgEdge> getOutgoing() {
		return outgoing;
	}

	public CfgEdge findEdgeTo(CfgNode to) {
		return getOutgoing().stream()
				.filter(e -> e.to == to)
				.findFirst().orElse(null);
	}
}
