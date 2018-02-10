package pl.marcinchwedczuk.cjava.decompiler.signature.reftype;

public class TypeVariable implements JavaTypeSignature {
	private final String identifier;

	public TypeVariable(String identifier) {
		this.identifier = identifier;
	}

	@Override
	public String toString() {
		return identifier;
	}

	@Override
	public String toJavaType() {
		return identifier;
	}
}
