package pl.marcinchwedczuk.cjava.bytecode.constantpool;

import java.util.Objects;

public class InterfaceMethodRefConstant extends Constant {
	private final ConstantPoolIndex klass;
	private final ConstantPoolIndex nameAndType;

	public InterfaceMethodRefConstant(ConstantPoolIndex klass, ConstantPoolIndex nameAndType) {
		super(ConstantTag.METHOD_REF);

		this.klass = Objects.requireNonNull(klass);
		this.nameAndType = Objects.requireNonNull(nameAndType);
	}

	public ConstantPoolIndex getKlass() {
		return klass;
	}

	public ConstantPoolIndex getNameAndType() {
		return nameAndType;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		InterfaceMethodRefConstant that = (InterfaceMethodRefConstant) o;

		if (!klass.equals(that.klass)) return false;
		return nameAndType.equals(that.nameAndType);
	}

	@Override
	public int hashCode() {
		int result = klass.hashCode();
		result = 31 * result + nameAndType.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "InterfaceMethodRefConstant{" +
				"klass=" + klass +
				", nameAndType=" + nameAndType +
				"} " + super.toString();
	}
}
