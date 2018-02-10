package pl.marcinchwedczuk.cjava.ast;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.unmodifiableList;

public class CompilationUnitAst {
	private final List<TypeDeclarationAst> declaredTypes;

	public CompilationUnitAst(List<TypeDeclarationAst> declaredTypes) {
		this.declaredTypes = unmodifiableList(newArrayList(declaredTypes));
	}

	public List<TypeDeclarationAst> getDeclaredTypes() {
		return declaredTypes;
	}
}
