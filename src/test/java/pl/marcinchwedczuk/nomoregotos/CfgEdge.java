package pl.marcinchwedczuk.nomoregotos;

public class CfgEdge {
	public static CfgEdge connect(CfgNode from, CfgNode to, CfgEdgeCondition condition) {
		CfgEdge e = new CfgEdge(from, to, condition);

		from.addOutgoingEdge(e);
		to.addIncomingEdge(e);

		return e;
	}

	public final CfgNode from;
	public final CfgNode to;
	public final CfgEdgeCondition condition;

	public boolean backedge = false;

	public CfgEdge(CfgNode from, CfgNode to, CfgEdgeCondition condition) {
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
