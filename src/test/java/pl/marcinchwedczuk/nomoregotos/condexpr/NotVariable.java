package pl.marcinchwedczuk.nomoregotos.condexpr;

public class NotVariable extends Term {
	public final Variable variable;

	public NotVariable(Variable variable) {
		this.variable = variable;
	}

	@Override
	public Variable not() {
		return variable;
	}

	@Override
	public String toString() {
		return "!" + variable.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		NotVariable notVariable = (NotVariable) o;

		return variable.equals(notVariable.variable);
	}

	@Override
	public int hashCode() {
		return variable.hashCode();
	}
}
