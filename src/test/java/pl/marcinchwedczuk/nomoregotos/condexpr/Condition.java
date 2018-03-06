package pl.marcinchwedczuk.nomoregotos.condexpr;

public class Condition {
	public static Condition variable(String variableName) {
		Variable variable = new Variable(variableName);
		TermsAnd conjunction = new TermsAnd(variable);
		DnfBooleanExpression booleanExpression = new DnfBooleanExpression(conjunction);
		return new Condition(booleanExpression);
	}

	public static Condition negatedVariable(String variableName) {
		Condition variableCondition = variable(variableName);
		return variableCondition.negate();
	}

	public static Condition alwaysTrue() {
		return new Condition(new DnfBooleanExpression(new TermsAnd(True.instance)));
	}

	public static Condition alwaysFalse() {
		return new Condition(new DnfBooleanExpression(new TermsAnd(False.instance)));
	}

	private final DnfBooleanExpression booleanExpression;

	private Condition(DnfBooleanExpression booleanExpression) {
		this.booleanExpression = booleanExpression;
	}

	public Condition negate() {
		return new Condition(booleanExpression.not());
	}

	public Condition and(Condition other) {
		DnfBooleanExpression conjunction = booleanExpression.and(other.booleanExpression);
		return new Condition(conjunction);
	}

	public Condition or(Condition other) {
		DnfBooleanExpression disjunction = booleanExpression.or(other.booleanExpression);
		return new Condition(disjunction);
	}

	@Override
	public String toString() {
		return booleanExpression.toString();
	}
}
