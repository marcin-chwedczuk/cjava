package pl.marcinchwedczuk.cjava.decompiler.signature.javatype;

import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;

public class ArrayType implements JavaType {
	private final int dimensions;
	private final JavaType type;

	public ArrayType(int dimensions, JavaType type) {
		this.dimensions = dimensions;
		this.type = type;
	}

	@Override
	public String asSourceCodeString() {
		String suffix = IntStream.rangeClosed(1, dimensions)
				.mapToObj(i -> "[]")
				.collect(joining(""));

		return String.format("%s%s", type.asSourceCodeString(), suffix);
	}

	@Override
	public String toString() {
		return asSourceCodeString();
	}

}
