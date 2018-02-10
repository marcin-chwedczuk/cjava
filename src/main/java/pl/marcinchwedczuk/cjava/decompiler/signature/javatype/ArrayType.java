package pl.marcinchwedczuk.cjava.decompiler.signature.javatype;

import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;

public class ArrayType implements JavaTypeSignature {
	private final int dimensions;
	private final JavaTypeSignature type;

	public ArrayType(int dimensions, JavaTypeSignature type) {
		this.dimensions = dimensions;
		this.type = type;
	}

	@Override
	public String toJavaType() {
		String suffix = IntStream.rangeClosed(1, dimensions)
				.mapToObj(i -> "[]")
				.collect(joining(""));

		return String.format("%s%s", type.toJavaType(), suffix);
	}

	@Override
	public String toString() {
		return toJavaType();
	}

}
