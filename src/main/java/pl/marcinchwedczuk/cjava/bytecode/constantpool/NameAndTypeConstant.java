package pl.marcinchwedczuk.cjava.bytecode.constantpool;

import java.util.Objects;

public class NameAndTypeConstant extends Constant {
	private final ConstantPoolIndex name;
	private final ConstantPoolIndex descriptor;

	public NameAndTypeConstant(ConstantPoolIndex name, ConstantPoolIndex descriptor) {
		super(ConstantTag.NAME_AND_TYPE);

		this.name = Objects.requireNonNull(name);
		this.descriptor = Objects.requireNonNull(descriptor);
	}

	public ConstantPoolIndex getName() {
		return name;
	}
	public ConstantPoolIndex getDescriptor() {
		return descriptor;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		NameAndTypeConstant that = (NameAndTypeConstant) o;

		if (!name.equals(that.name)) return false;
		return descriptor.equals(that.descriptor);
	}

	@Override
	public int hashCode() {
		int result = name.hashCode();
		result = 31 * result + descriptor.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "NameAndTypeConstant{" +
				"name=" + name +
				", descriptor=" + descriptor +
				"} " + super.toString();
	}
}
