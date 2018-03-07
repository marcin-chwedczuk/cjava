package pl.marcinchwedczuk.nomoregotos.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import pl.marcinchwedczuk.nomoregotos.CfgEdge;
import pl.marcinchwedczuk.nomoregotos.graph.slicegraph.SliceEdge;
import pl.marcinchwedczuk.nomoregotos.graph.slicegraph.SliceNode;

import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;

public abstract class Node<N extends Node<N,E>, E extends Edge<N>> {
	private final Set<E> incoming = newHashSet();
	private final Set<E> outgoing = newHashSet();

	public void addIncoming(E edge) {
		checkArgument(edge.getTo() == this);

		incoming.add(edge);
	}

	public void addOutgoing(E edge) {
		checkArgument(edge.getFrom() == this);

		outgoing.add(edge);
	}

	public E tryFindEdge(N to) {
		return outgoing.stream()
				.filter(e -> e.getTo() == to)
				.findFirst()
				.orElse(null);
	}

	public Set<E> getIncoming() {
		return incoming;
	}

	public Set<E> getOutgoing() {
		return outgoing;
	}
}
