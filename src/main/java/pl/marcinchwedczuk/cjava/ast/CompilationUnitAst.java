package pl.marcinchwedczuk.cjava.ast;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import pl.marcinchwedczuk.cjava.ast.visitor.AstMapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;

@AutoValue
public abstract class CompilationUnitAst extends Ast {
	public static CompilationUnitAst create(List<TypeDeclarationAst> declaredTypes) {
		return new AutoValue_CompilationUnitAst.Builder()
				.setDeclaredTypes(declaredTypes)
				.build();
	}

	public abstract ImmutableList<TypeDeclarationAst> getDeclaredTypes();

	public abstract Builder toBuilder();

	@Override
	public Ast astMap(AstMapper mapper) {
		List<TypeDeclarationAst> mappedTypes = getDeclaredTypes().stream()
				.map(t -> t.astMap(mapper))
				.collect(toList());

		Builder mapped = this
				.toBuilder()
				.setDeclaredTypes(mappedTypes);

		return mapper.map(this, mapped);
	}

	@AutoValue.Builder
	public static abstract class Builder {
		public abstract Builder setDeclaredTypes(List<TypeDeclarationAst> declaredTypes);

		public abstract CompilationUnitAst build();
	}
}
