package pl.marcinchwedczuk.cjava.bytecode.constantpool;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class DoubleConstant extends Constant {
	protected DoubleConstant() {
		super(ConstantTag.DOUBLE);
	}

	public static DoubleConstant create(double value) {
		return new AutoValue_DoubleConstant(value);
	}

	public abstract double getValue();
}
