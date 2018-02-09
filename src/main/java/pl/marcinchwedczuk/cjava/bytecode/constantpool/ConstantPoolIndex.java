package pl.marcinchwedczuk.cjava.bytecode.constantpool;

import com.google.common.base.Preconditions;
import pl.marcinchwedczuk.cjava.bytecode.utils.ClassFileReader;

import java.io.IOException;

public class ConstantPoolIndex {
	private static final int MAX_USHORT_VALUE = 65_535;

	public static ConstantPoolIndex readFrom(ClassFileReader reader) throws IOException {
		return new ConstantPoolIndex(reader.readUnsignedShort());
	}

	public static ConstantPoolIndex fromInteger(int index) {
		Preconditions.checkArgument(
				(0 <= index) && (index <= MAX_USHORT_VALUE),
				"index out of range");

		return new ConstantPoolIndex(index);
	}

	private final int index;

	private ConstantPoolIndex(int index) {
		this.index = index;
	}

	public int asInteger() {
		return index;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ConstantPoolIndex that = (ConstantPoolIndex) o;
		return index == that.index;
	}

	@Override
	public int hashCode() {
		return index;
	}

	@Override
	public String toString() {
		return "[cp_index: " + index + "]";
	}
}
