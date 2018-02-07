package pl.marcinchwedczuk.cjava.bytecode.constantpool;

public class MethodRefConstant extends Constant {
	private final short classIndex;
	private final short nameAndTypeIndex;

	public MethodRefConstant(int classIndex, int nameAndTypeIndex) {
		super(ConstantTag.METHOD_REF);

		this.classIndex = (short) classIndex;
		this.nameAndTypeIndex = (short) nameAndTypeIndex;
	}

	public short getClassIndex() {
		return classIndex;
	}

	public short getNameAndTypeIndex() {
		return nameAndTypeIndex;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		MethodRefConstant that = (MethodRefConstant) o;

		if (classIndex != that.classIndex) return false;
		return nameAndTypeIndex == that.nameAndTypeIndex;
	}

	@Override
	public int hashCode() {
		int result = (int) classIndex;
		result = 31 * result + (int) nameAndTypeIndex;
		return result;
	}

	@Override
	public String toString() {
		return "MethodRefConstant{" +
				"classIndex=" + classIndex +
				", nameAndTypeIndex=" + nameAndTypeIndex +
				"} " + super.toString();
	}
}
