package pl.marcinchwedczuk.cjava.bytecode.annotation;

import pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPoolIndex;

public class Attribute {
	private final ConstantPoolIndex attributeName;
	private final byte[] attributeData;

	public Attribute(ConstantPoolIndex attributeName, byte[] attributeData) {
		this.attributeName = attributeName;
		this.attributeData = attributeData;
	}

	public ConstantPoolIndex getAttributeName() {
		return attributeName;
	}

	public byte[] getAttributeData() {
		return attributeData;
	}
}
