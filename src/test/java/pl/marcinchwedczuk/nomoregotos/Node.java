package pl.marcinchwedczuk.nomoregotos;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static pl.marcinchwedczuk.nomoregotos.Condition.ALWAYS;

public abstract class Node {
	public String color;

	private final List<Edge> incoming = newArrayList();
	private final List<Edge> outgoing = newArrayList();

	public void addIncomingEdge(Edge e) {
		incoming.add(e);
	}

	public void addOutgoingEdge(Edge e) {
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

	public List<Edge> getIncoming() {
		return incoming;
	}

	public List<Edge> getOutgoing() {
		return outgoing;
	}
}
