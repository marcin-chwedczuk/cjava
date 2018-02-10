package pl.marcinchwedczuk.cjava.bytecode.constantpool;

import java.util.Objects;

public class StringConstant extends Constant {
	private final ConstantPoolIndex utf8;

	public StringConstant(ConstantPoolIndex utf8) {
		super(ConstantTag.STRING);

		this.utf8 = Objects.requireNonNull(utf8);
	}

	public ConstantPoolIndex getUtf8() {
		return utf8;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		StringConstant that = (StringConstant) o;

		return utf8.equals(that.utf8);
	}

	@Override
	public int hashCode() {
		return utf8.hashCode();
	}

	@Override
	public String toString() {
		return "StringConstant{" +
				"utf8=" + utf8 +
				"} " + super.toString();
	}
}
