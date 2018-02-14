package pl.marcinchwedczuk.cjava.decompiler;

import pl.marcinchwedczuk.cjava.ast.ClassDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.CompilationUnitAst;
import pl.marcinchwedczuk.cjava.bytecode.JavaClassFile;

import java.util.Arrays;
import java.util.Collections;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

public class BytecodeDecompiler {
	private final ClassDecompiler classDecompiler;

	public BytecodeDecompiler(JavaClassFile classFile) {
		this.classDecompiler = new ClassDecompiler(classFile);
	}

	public CompilationUnitAst decompile() {
		ClassDeclarationAst declarationAst = classDecompiler.decompile();

		return new CompilationUnitAst(singletonList(declarationAst));
	}
}
