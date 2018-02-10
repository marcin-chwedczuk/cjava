package pl.marcinchwedczuk.cjava.ast;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import java.util.Arrays;
import java.util.Objects;

public class TypeName {
	public static TypeName fromBytecodeClassName(String className) {
		if (Strings.isNullOrEmpty(className))
			throw new IllegalArgumentException("className");

		String[] parts = className.split("/");

		if (parts.length == 1) {
			return new TypeName(null, parts[0]);
		}
		else {
			String[] packageName = Arrays.copyOf(parts, parts.length-1);

			return new TypeName(
					String.join(".", packageName),
					parts[parts.length-1]);
		}
	}

	private final String packageName;
	private final String typeName;

	public TypeName(String packageName, String typeName) {
		this.packageName = Strings.isNullOrEmpty(packageName) ? null : packageName;
		this.typeName = Objects.requireNonNull(typeName);
	}

	public String getPackageName() {
		return packageName;
	}

	public String getTypeName() {
		return typeName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		TypeName typeName1 = (TypeName) o;

		if (packageName != null ? !packageName.equals(typeName1.packageName) : typeName1.packageName != null)
			return false;
		return typeName.equals(typeName1.typeName);
	}

	@Override
	public int hashCode() {
		int result = packageName != null ? packageName.hashCode() : 0;
		result = 31 * result + typeName.hashCode();
		return result;
	}

	@Override
	public String toString() {
		if (packageName != null) {
			return packageName + "." + typeName;
		}
		else {
			return typeName;
		}
	}
}
