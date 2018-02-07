package pl.marcinchwedczuk.cjava.bytecode.constantpool;

import java.util.Objects;

public class ClassConstant extends Constant {
	private final ConstantPoolIndex name;

	public ClassConstant(ConstantPoolIndex name) {
		super(ConstantTag.CLASS);
		this.name = Objects.requireNonNull(name);
	}

	public ConstantPoolIndex getName() {
		return name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ClassConstant that = (ClassConstant) o;

		return name.equals(that.name);
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public String toString() {
		return "ClassConstant{" +
				"name=" + name +
				"} " + super.toString();
	}
}
