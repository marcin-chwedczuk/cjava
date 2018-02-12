package pl.marcinchwedczuk.cjava.bytecode.constantpool;

public class FloatConstant extends Constant {
	private final float value;

	protected FloatConstant(float value) {
		super(ConstantTag.FLOAT);

		this.value = value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		FloatConstant that = (FloatConstant) o;

		return Float.compare(that.value, value) == 0;
	}

	@Override
	public int hashCode() {
		return (value != +0.0f ? Float.floatToIntBits(value) : 0);
	}

	@Override
	public String toString() {
		return "FloatConstant{" +
				"value=" + value +
				"} " + super.toString();
	}
}
