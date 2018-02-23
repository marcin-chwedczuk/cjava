package pl.marcinchwedczuk.cjava.optimizer.imports;

import com.google.common.collect.ImmutableList;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;

import java.util.List;

public interface JavaTypeNameRenderer {
	String renderTypeName(JavaType type);

	default ImmutableList<ImportStatement> getImports() {
		return ImmutableList.of();
	}
}
