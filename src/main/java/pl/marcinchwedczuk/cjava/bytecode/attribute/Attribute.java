package pl.marcinchwedczuk.cjava.bytecode.attribute;

public abstract class Attribute {
	private final AttributeType type;

	public Attribute(AttributeType type) {
		this.type = type;
	}

	public AttributeType getType() {
		return type;
	}
}
