package pl.marcinchwedczuk.cjava.bytecode.attribute;

import pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPoolIndex;

import java.util.Objects;

public class UnknownAttribute extends Attribute {
	private final ConstantPoolIndex name;
	private final byte[] data;

	public UnknownAttribute(ConstantPoolIndex name, byte[] data) {
		super(AttributeType.UNKNOWN);

		this.name = Objects.requireNonNull(name);
		this.data = Objects.requireNonNull(data);
	}

	public ConstantPoolIndex getName() {
		return name;
	}

	public byte[] getData() {
		return data;
	}
}
