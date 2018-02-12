package pl.marcinchwedczuk.cjava.bytecode.constantpool;

public class IntegerConstant extends Constant {
	private final int value;

	protected IntegerConstant(int value) {
		super(ConstantTag.INTEGER);

		this.value = value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		IntegerConstant that = (IntegerConstant) o;

		return value == that.value;
	}

	@Override
	public int hashCode() {
		return value;
	}

	@Override
	public String toString() {
		return "IntegerConstant{" +
				"value=" + value +
				"} " + super.toString();
	}
}
