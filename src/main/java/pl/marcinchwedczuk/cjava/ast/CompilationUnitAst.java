package pl.marcinchwedczuk.cjava.ast;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import pl.marcinchwedczuk.cjava.ast.visitor.AstMapper;

import java.util.Collections;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.unmodifiableList;

@AutoValue
public abstract class CompilationUnitAst extends Ast {
	public static CompilationUnitAst create(List<TypeDeclarationAst> declaredTypes) {
		return new AutoValue_CompilationUnitAst.Builder()
				.setDeclaredTypes(declaredTypes)
				.build();
	}

	public abstract ImmutableList<TypeDeclarationAst> getDeclaredTypes();

	@Override
	public Ast astMap(AstMapper mapper) {
		return null;
	}

	@AutoValue.Builder
	public static abstract class Builder {
		public abstract Builder setDeclaredTypes(List<TypeDeclarationAst> declaredTypes);

		public abstract CompilationUnitAst build();
	}
}
