package pl.marcinchwedczuk.nomoregotos.graph;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public abstract class Edge<N extends Node<N, ? extends Edge<N>>> {
	private final N from;
	private final N to;

	public Edge(N from, N to) {
		this.from = requireNonNull(from);
		this.to = requireNonNull(to);
	}

	public N getFrom() {
		return from;
	}

	public N getTo() {
		return to;
	}
}
