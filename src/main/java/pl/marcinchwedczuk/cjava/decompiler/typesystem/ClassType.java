package pl.marcinchwedczuk.cjava.decompiler.typesystem;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import pl.marcinchwedczuk.cjava.util.ListUtils;

import java.util.List;

import static com.google.common.collect.ImmutableList.copyOf;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType.MetaType.REFERENCE_TYPE;

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
	public ImmutableList<JavaType> decomposeToRawTypes() {
		ImmutableList.Builder<JavaType> builder =
				ImmutableList.builder();

		builder.add(this.toRawType());

		getClasses().stream()
				.flatMap(c -> c.getTypeArguments().stream())
				.flatMap(t -> t.decomposeToRawTypes().stream())
				.forEach(builder::add);

		return builder.build();
	}

	@Override
	public MetaType getMetaType() {
		return REFERENCE_TYPE;
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

	@Override
	public String toString() {
		return "ClassType(" + asSourceCodeString() + ")";
	}

	public String computeSimpleClassName() {
		SimpleClassType currentClass = ListUtils.lastElement(getClasses());
		return currentClass.getClassName();
	}

	public boolean hasSimpleClassName(String simpleClassName) {
		return computeSimpleClassName().equals(simpleClassName);
	}

	public boolean isPartOfPackage(PackageName package_) {
		return getPackageName().equals(package_);
	}
}
