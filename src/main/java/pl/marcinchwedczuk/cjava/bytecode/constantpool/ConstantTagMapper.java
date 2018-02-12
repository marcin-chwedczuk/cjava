package pl.marcinchwedczuk.cjava.bytecode.constantpool;

import pl.marcinchwedczuk.cjava.bytecode.InvalidJavaClassFileException;

import java.io.InvalidClassException;
import java.util.Arrays;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public class ConstantTagMapper {
	private final Map<Byte, ConstantTag> constantTagByByteCodeConstant;

	public ConstantTagMapper() {
		this.constantTagByByteCodeConstant = createMapping();
	}

	public ConstantTag mapByteCodeTagToConstantTag(byte byteCodeConstant) {
		ConstantTag constantTag =
				constantTagByByteCodeConstant.get(byteCodeConstant);

		if (constantTag == null)
			throw new InvalidJavaClassFileException(
					"Unrecognized constant_pool_tag: " + byteCodeConstant + ".");

		return constantTag;
	}

	private static Map<Byte, ConstantTag> createMapping() {
		return Arrays.stream(ConstantTag.values())
				.collect(toMap(ConstantTag::asByteCodeConstant, identity()));
	}
}
