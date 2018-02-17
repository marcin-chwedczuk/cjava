package pl.marcinchwedczuk.cjava.decompiler.typesystem;

import com.google.auto.value.AutoValue;

import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;

@AutoValue
public abstract class ArrayType implements JavaType {
	public static ArrayType create(int numberOfDimensions, JavaType elementType) {
		return new AutoValue_ArrayType(numberOfDimensions, elementType);
	}

	public abstract int getDimensions();
	public abstract JavaType getElementType();

	@Override
	public String asSourceCodeString() {
		String suffix = IntStream.rangeClosed(1, getDimensions())
				.mapToObj(i -> "[]")
				.collect(joining(""));

		return String.format("%s%s", getElementType().asSourceCodeString(), suffix);
	}
}
