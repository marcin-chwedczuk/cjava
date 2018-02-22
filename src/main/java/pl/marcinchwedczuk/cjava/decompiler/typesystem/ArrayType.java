package pl.marcinchwedczuk.cjava.decompiler.typesystem;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;
import static pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType.MetaType.REFERENCE_TYPE;

@AutoValue
public abstract class ArrayType implements JavaType {
	public static ArrayType create(int numberOfDimensions, JavaType elementType) {
		return new AutoValue_ArrayType(numberOfDimensions, elementType);
	}

	public abstract int getDimensions();
	public abstract JavaType getElementType();

	@Override
	public ImmutableList<JavaType> decomposeToRawTypes() {
		return getElementType().decomposeToRawTypes();
	}

	@Override
	public MetaType getMetaType() {
		return REFERENCE_TYPE;
	}

	@Override
	public String asSourceCodeString() {
		String suffix = IntStream.rangeClosed(1, getDimensions())
				.mapToObj(i -> "[]")
				.collect(joining(""));

		return String.format("%s%s", getElementType().asSourceCodeString(), suffix);
	}
}
