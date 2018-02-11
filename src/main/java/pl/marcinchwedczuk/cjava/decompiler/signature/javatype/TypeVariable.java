package pl.marcinchwedczuk.cjava.decompiler.signature.javatype;

public class TypeVariable implements JavaType {
	private final String identifier;

	public TypeVariable(String identifier) {
		this.identifier = identifier;
	}

	@Override
	public String toString() {
		return identifier;
	}

	@Override
	public String asSourceCodeString() {
		return identifier;
	}
}
