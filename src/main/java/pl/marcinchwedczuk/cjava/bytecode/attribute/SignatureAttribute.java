package pl.marcinchwedczuk.cjava.bytecode.attribute;

import pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPoolIndex;

public class SignatureAttribute extends Attribute {
	private final ConstantPoolIndex utf8SignatureString;

	public SignatureAttribute(ConstantPoolIndex utf8SignatureString) {
		super(AttributeType.SIGNATURE);

		this.utf8SignatureString = utf8SignatureString;
	}

	public ConstantPoolIndex getUtf8SignatureString() {
		return utf8SignatureString;
	}
}
