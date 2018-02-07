package pl.marcinchwedczuk.cjava.bytecode.constantpool;

public class NameAndTypeConstant extends Constant {
	private final short nameIndex;
	private final short descriptorIndex;

	public NameAndTypeConstant(int nameIndex, int descriptorIndex) {
		super(ConstantTag.NAME_AND_TYPE);

		this.nameIndex = (short) nameIndex;
		this.descriptorIndex = (short) descriptorIndex;
	}

	public short getNameIndex() {
		return nameIndex;
	}
	public short getDescriptorIndex() {
		return descriptorIndex;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		NameAndTypeConstant that = (NameAndTypeConstant) o;

		if (nameIndex != that.nameIndex) return false;
		return descriptorIndex == that.descriptorIndex;
	}

	@Override
	public int hashCode() {
		int result = (int) nameIndex;
		result = 31 * result + (int) descriptorIndex;
		return result;
	}

	@Override
	public String toString() {
		return "NameAndTypeConstant{" +
				"nameIndex=" + nameIndex +
				", descriptorIndex=" + descriptorIndex +
				"} " + super.toString();
	}
}
