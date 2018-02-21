package pl.marcinchwedczuk.cjava.decompiler.typesystem;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

import java.util.Arrays;
import java.util.List;

import static com.google.common.collect.ImmutableList.copyOf;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;

@AutoValue
public abstract class PackageName {
	public static PackageName fromString(String packageName) {
		return builder()
				.setPackageSpecifier(asList(packageName.split("\\.")))
				.build();
	}

	public static PackageName from(List<String> packagePath) {
		return builder()
				.setPackageSpecifier(packagePath)
				.build();
	}

	public abstract ImmutableList<String> getPackageSpecifier();

	public String asJavaSouceCode() {
		return getPackageSpecifier()
				.stream()
				.collect(joining("."));
	}

	public static Builder builder() {
		return new AutoValue_PackageName.Builder();
	}

	@AutoValue.Builder
	public abstract static class Builder {
		public abstract Builder setPackageSpecifier(List<String> newPackageSpecifier);
		public abstract Builder setPackageSpecifier(String... newPackageSpecifier);

		public abstract PackageName build();
	}
}
