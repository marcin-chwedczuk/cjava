package pl.marcinchwedczuk.nomoregotos;

public class ConditionNode extends CfgNode {
	public final String conditionVariable;

	public ConditionNode(String conditionVariable) {
		this.conditionVariable = conditionVariable;
	}

	@Override
	public String toString() {
		return conditionVariable;
	}
}
