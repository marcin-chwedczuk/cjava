package pl.marcinchwedczuk.cjava.bytecode.constantpool;

import java.util.Arrays;
import java.util.Objects;

public class Utf8Constant extends Constant {
	private final byte[] bytes;
	private final String string;

	public Utf8Constant(byte[] bytes, String string) {
		super(ConstantTag.UTF8);

		this.bytes = Objects.requireNonNull(bytes);
		this.string = Objects.requireNonNull(string);
	}

	public byte[] getBytes() {
		return Arrays.copyOf(bytes, bytes.length);
	}

	public String getString() {
		return string;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Utf8Constant that = (Utf8Constant) o;

		if (!Arrays.equals(bytes, that.bytes)) return false;
		return string.equals(that.string);
	}

	@Override
	public int hashCode() {
		int result = Arrays.hashCode(bytes);
		result = 31 * result + string.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "Utf8Constant{" +
				"bytes=" + Arrays.toString(bytes) +
				", string='" + string + '\'' +
				"} " + super.toString();
	}
}
