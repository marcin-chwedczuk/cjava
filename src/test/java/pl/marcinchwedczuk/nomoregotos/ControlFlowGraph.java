package pl.marcinchwedczuk.nomoregotos;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import pl.marcinchwedczuk.cjava.sourcecode.formatter.ListWriter;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static pl.marcinchwedczuk.nomoregotos.Condition.ALWAYS;

public class ControlFlowGraph {
	private final List<Node> vertices = newArrayList();
	private final List<Edge> edges = newArrayList();

	public final StartNode start = new StartNode();
	public final StopNode stop = new StopNode();

	public CodeNode addCodeNode(String instruction) {
		CodeNode v = new CodeNode(instruction);
		vertices.add(v);
		return v;
	}

	public ConditionNode addConditionNode(String conditionVariable) {
		ConditionNode v = new ConditionNode(conditionVariable);
		vertices.add(v);
		return v;
	}

	public Edge addEdge(Node from, Node to, Condition condition) {
		if (condition == ALWAYS) {
			Preconditions.checkState(!(from instanceof ConditionNode));
		}

		if (condition != ALWAYS) {
			Preconditions.checkState(from instanceof ConditionNode);
		}

		Edge e = Edge.connect(from, to, condition);
		edges.add(e);
		return e;
	}

	public List<Node> getVertices() {
		return vertices;
	}

	public List<Node> getVeriticesWithStartStopMarks() {
		ArrayList<Node> copy = Lists.newArrayList(vertices);
		copy.add(stop); copy.add(start);
		return copy;
	}

	public List<Edge> getEdges() {
		return edges;
	}

	public Node findNode(String name) {
		return vertices.stream()
				.filter(n -> n.toString().contains(name))
				.findFirst()
				.orElse(null);
	}
}
