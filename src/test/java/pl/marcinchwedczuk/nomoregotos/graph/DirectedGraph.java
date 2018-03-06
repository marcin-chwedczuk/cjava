package pl.marcinchwedczuk.nomoregotos.graph;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class DirectedGraph<N extends Node<N,E>, E extends Edge<N>> {
	protected final Set<N> nodes = new HashSet<>();
	protected final Set<E> edges = new HashSet<>();

	public List<N> peformTopologicalSort(N start) {
		TopologicalSortAlgorithm<N, E> sortAlgorithm = new TopologicalSortAlgorithm<>(start, this);
		sortAlgorithm.start();
		return sortAlgorithm.getTopologicalOrder();
	}

	public Set<N> getNodes() {
		return nodes;
	}

	public Set<E> getEdges() {
		return edges;
	}
}
