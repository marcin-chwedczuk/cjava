package pl.marcinchwedczuk.nomoregotos;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static pl.marcinchwedczuk.nomoregotos.CfgEdgeCondition.ALWAYS;

public class ControlFlowGraph {
	private final List<CfgNode> vertices = newArrayList();
	private final List<CfgEdge> edges = newArrayList();

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

	public CfgEdge addEdge(CfgNode from, CfgNode to, CfgEdgeCondition condition) {
		if (condition == ALWAYS) {
			Preconditions.checkState(!(from instanceof ConditionNode));
		}

		if (condition != ALWAYS) {
			Preconditions.checkState(from instanceof ConditionNode);
		}

		CfgEdge e = CfgEdge.connect(from, to, condition);
		edges.add(e);
		return e;
	}

	public List<CfgNode> getVertices() {
		return vertices;
	}

	public List<CfgNode> getVeriticesWithStartStopMarks() {
		ArrayList<CfgNode> copy = Lists.newArrayList(vertices);
		copy.add(stop); copy.add(start);
		return copy;
	}

	public List<CfgEdge> getEdges() {
		return edges;
	}

	public CfgNode findNode(String name) {
		return vertices.stream()
				.filter(n -> n.toString().contains(name))
				.findFirst()
				.orElse(null);
	}
}
