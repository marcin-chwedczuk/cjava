package pl.marcinchwedczuk.cjava.decompiler.typesystem;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class TypeVariable implements JavaType {
	public static TypeVariable fromTypeParameterName(String typeParameterName) {
		return new AutoValue_TypeVariable(typeParameterName);
	}

	public abstract String getIdentifier();

	@Override
	public String asSourceCodeString() {
		return getIdentifier();
	}
}
