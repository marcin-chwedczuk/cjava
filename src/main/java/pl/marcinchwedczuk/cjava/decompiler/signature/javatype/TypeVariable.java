package pl.marcinchwedczuk.cjava.decompiler.signature.javatype;

public class TypeVariable implements JavaType {
	public static TypeVariable forTypeParameter(String typeParameterName) {
		return new TypeVariable(typeParameterName);
	}

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
