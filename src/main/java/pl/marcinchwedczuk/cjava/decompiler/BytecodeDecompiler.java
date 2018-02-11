package pl.marcinchwedczuk.cjava.decompiler;

import pl.marcinchwedczuk.cjava.ast.ClassDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.CompilationUnitAst;
import pl.marcinchwedczuk.cjava.bytecode.JavaClassFile;

import java.util.Arrays;

public class BytecodeDecompiler {
	private final ClassDeclarationDecompiler classDeclarationDecompiler;

	public BytecodeDecompiler(JavaClassFile classFile) {
		this.classDeclarationDecompiler = new ClassDeclarationDecompiler(classFile);
	}

	public CompilationUnitAst decompile() {
		ClassDeclarationAst classDeclarationAst =
				classDeclarationDecompiler.decompile();

		return new CompilationUnitAst(Arrays.asList(classDeclarationAst));
	}
}
