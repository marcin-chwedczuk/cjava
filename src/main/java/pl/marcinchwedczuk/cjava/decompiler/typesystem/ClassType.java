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

	public static ClassType fromPackageAndClassName(String packageNameString, String className) {
		PackageName packageName = PackageName.fromString(packageNameString);

		ImmutableList<SimpleClassType> classes =
				ImmutableList.of(SimpleClassType.fromClassName(className));

		return new AutoValue_ClassType(packageName, classes);
	}

	public static ClassType create(PackageName packageName, List<SimpleClassType> classes) {
		return new AutoValue_ClassType(packageName, copyOf(classes));
	}

	public static ClassType create(PackageName packageSpecifier, SimpleClassType class_) {
		return create(packageSpecifier, singletonList(class_));
	}

	public abstract PackageName getPackageName();
	public abstract ImmutableList<SimpleClassType> getClasses();

	public ClassType toRawType() {
		List<SimpleClassType> rawTypes = getClasses().stream()
				.map(SimpleClassType::toRawType)
				.collect(toList());

		return create(getPackageName(), rawTypes);
	}

	@Override
	public String asSourceCodeString() {
		String javaPackage = getPackageName().asJavaSouceCode();
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

	public boolean isPartOfPackage(PackageName package_) {
		return getPackageName().equals(package_);
	}
}
