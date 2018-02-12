package pl.marcinchwedczuk.cjava.decompiler.signature.javatype;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.joining;

public class ClassType implements JavaType {
	private final List<String> packageSpecifier;
	private final List<SimpleClassType> classes;

	public ClassType(List<String> packageSpecifier, List<SimpleClassType> classes) {
		this.packageSpecifier = packageSpecifier;
		this.classes = classes;
	}

	public ClassType(List<String> packagePart, SimpleClassType klass) {
		this(packagePart, singletonList(klass));
	}

	@Override
	public String asSourceCodeString() {
		String javaPackage = packageSpecifier.stream()
			.collect(joining("."));

		if (!javaPackage.isEmpty()) {
			javaPackage += ".";
		}

		String javaClasses = classes.stream()
				.map(SimpleClassType::asSourceCodeString)
				.collect(joining("."));

		return javaPackage.concat(javaClasses);
	}

	public List<String> getPackageSpecifier() {
		return packageSpecifier;
	}

	public List<SimpleClassType> getClasses() {
		return classes;
	}
}
