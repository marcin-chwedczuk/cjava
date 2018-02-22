package pl.marcinchwedczuk.cjava.decompiler.typesystem;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import static pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType.MetaType.TYPE_VARIABLE;

@AutoValue
public abstract class TypeVariable implements JavaType {
	public static TypeVariable fromTypeParameterName(String typeParameterName) {
		return new AutoValue_TypeVariable(typeParameterName);
	}

	public abstract String getIdentifier();

	@Override
	public ImmutableList<JavaType> decomposeToRawTypes() {
		return ImmutableList.of();
	}

	@Override
	public MetaType getMetaType() {
		return TYPE_VARIABLE;
	}

	@Override
	public String asSourceCodeString() {
		return getIdentifier();
	}
}
