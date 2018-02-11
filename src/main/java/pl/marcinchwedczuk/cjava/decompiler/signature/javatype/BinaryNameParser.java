package pl.marcinchwedczuk.cjava.decompiler.signature.javatype;

import java.util.List;
import java.util.Objects;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static pl.marcinchwedczuk.cjava.util.ListUtils.lastElement;
import static pl.marcinchwedczuk.cjava.util.ListUtils.withoutLastElement;

/**
 * Binary name has form
 * <c>foo/bar/Class$NestedClass</c>.
 */
public class BinaryNameParser {
	private final String binaryName;

	public BinaryNameParser(String binaryName) {
		this.binaryName = Objects.requireNonNull(binaryName);
	}

	public ClassType parse() {
	 	List<String> parts = asList(binaryName.split("/"));

		List<String> packageParts = withoutLastElement(parts);
		List<String> classParts = asList(lastElement(parts).split("\\$"));

		List<SimpleClassType> typedClassParts = classParts.stream()
				.map(SimpleClassType::fromClassName)
				.collect(toList());

		return new ClassType(packageParts, typedClassParts);
	}
}
