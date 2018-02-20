package pl.marcinchwedczuk.cjava.optimizer.imports;

import pl.marcinchwedczuk.cjava.decompiler.typesystem.ArrayType;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.ClassType;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.typeargs.BoundedWildcardTypeArgument;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.typeargs.ConcreteTypeTypeArgument;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.typeargs.TypeArgument;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JavaTypeHistogram {
	private final Map<ClassType, Integer> counts = new HashMap<>();

	public int getNumberOfUsages(ClassType type) {
		return counts.getOrDefault(type.toRawType(), 0);
	}

	public void addUsage(JavaType type) {
		if (type instanceof ClassType) {
			ClassType classType = (ClassType)type;

			incrementUsage(classType.toRawType());
			scanTypeArguments(classType);

		} else if (type instanceof ArrayType) {
			addUsage(((ArrayType)type).getElementType());
		} else {
			// Skip TypeVariable and PrimitiveTypes
		}
	}

	private void scanTypeArguments(ClassType classType) {
		classType.getClasses()
				.stream()
				.flatMap(c -> c.getTypeArguments().stream())
				.forEach(this::addUsagesWithinTypeArgument);
	}

	private void addUsagesWithinTypeArgument(TypeArgument typeArgument) {
		if (typeArgument instanceof ConcreteTypeTypeArgument) {
			addUsage(((ConcreteTypeTypeArgument) typeArgument).getType());
		} else if(typeArgument instanceof BoundedWildcardTypeArgument) {
			addUsage(((BoundedWildcardTypeArgument) typeArgument).getType());
		} else {
			// Wildcart type argument (?) has no types e.g. Class<?>
		}
	}

	private void incrementUsage(ClassType classType) {
		counts.compute(classType, (k,v) -> v == null ? 1 : v+1);
	}
}
