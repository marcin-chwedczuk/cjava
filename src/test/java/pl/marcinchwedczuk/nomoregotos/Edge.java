package pl.marcinchwedczuk.nomoregotos;

public class Edge {
	public static Edge connect(Node from, Node to, Condition condition) {
		Edge e = new Edge(from, to, condition);

		from.addOutgoingEdge(e);
		to.addIncomingEdge(e);

		return e;
	}

	public final Node from;
	public final Node to;
	public final Condition condition;

	public boolean backedge = false;

	public Edge(Node from, Node to, Condition condition) {
		this.from = from;
		this.to = to;
		this.condition = condition;
	}

	@Override
	public String toString() {
		switch (condition) {
			case WHEN_TRUE:
				return ((ConditionNode)from).conditionVariable;

			case WHEN_FALSE:
				return "NOT " + ((ConditionNode)from).conditionVariable;

			default: return "";
		}
	}
}
