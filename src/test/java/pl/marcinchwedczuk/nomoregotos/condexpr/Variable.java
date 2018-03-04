package pl.marcinchwedczuk.nomoregotos.condexpr;

public class Variable extends Term {
	public final String varName;

	public Variable(String varName) {
		this.varName = varName;
	}

	@Override
	public NotVariable not() {
		return new NotVariable(this);
	}

	@Override
	public String toString() {
		return varName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Variable variable = (Variable) o;

		return varName.equals(variable.varName);
	}

	@Override
	public int hashCode() {
		return varName.hashCode();
	}
}
