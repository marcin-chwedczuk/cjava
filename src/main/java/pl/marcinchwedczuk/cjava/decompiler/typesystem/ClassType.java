package pl.marcinchwedczuk.cjava.decompiler.typesystem;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import pl.marcinchwedczuk.cjava.util.ListUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.collect.ImmutableList.copyOf;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

@AutoValue
public abstract class ClassType implements JavaType {
	public static ClassType of(Class<?> klass) {
		return fromPackageAndClassName(
				klass.getPackage().getName(),
				klass.getSimpleName());
	}

	public static ClassType fromPackageAndClassName(String package_, String className) {
		ImmutableList<String> packageSpecifier = copyOf(package_.split("\\."));

		ImmutableList<SimpleClassType> classes =
				ImmutableList.of(SimpleClassType.fromClassName(className));

		return new AutoValue_ClassType(packageSpecifier, classes);
	}

	public static ClassType create(List<String> packageSpecifier, List<SimpleClassType> classes) {
		return new AutoValue_ClassType(copyOf(packageSpecifier), copyOf(classes));
	}

	public static ClassType create(List<String> packageSpecifier, SimpleClassType class_) {
		return create(packageSpecifier, singletonList(class_));
	}

	public abstract ImmutableList<String> getPackageSpecifier();
	public abstract ImmutableList<SimpleClassType> getClasses();

	public ClassType toRawType() {
		List<SimpleClassType> rawTypes = getClasses().stream()
				.map(SimpleClassType::toRawType)
				.collect(toList());

		return create(getPackageSpecifier(), rawTypes);
	}

	@Override
	public String asSourceCodeString() {
		String javaPackage = getPackageSpecifier().stream()
			.collect(joining("."));

		if (!javaPackage.isEmpty()) {
			javaPackage += ".";
		}

		String javaClasses = getClasses().stream()
				.map(SimpleClassType::asSourceCodeString)
				.collect(joining("."));

		return javaPackage.concat(javaClasses);
	}

	public String computeSimpleClassName() {
		SimpleClassType currentClass = ListUtils.lastElement(getClasses());
		return currentClass.getClassName();
	}
}
