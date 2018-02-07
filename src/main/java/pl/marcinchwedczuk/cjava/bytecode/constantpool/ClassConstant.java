package pl.marcinchwedczuk.cjava.bytecode.constantpool;

public class ClassConstant extends Constant {
	private final short nameIndex;

	public ClassConstant(int nameIndex) {
		super(ConstantTag.CLASS);
		this.nameIndex = (short) nameIndex;
	}

	public short getNameIndex() {
		return nameIndex;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ClassConstant that = (ClassConstant) o;

		return nameIndex == that.nameIndex;
	}

	@Override
	public int hashCode() {
		return (int) nameIndex;
	}

	@Override
	public String toString() {
		return "ClassConstant{" +
				"nameIndex=" + nameIndex +
				"} " + super.toString();
	}
}
