package pl.marcinchwedczuk.cjava.decompiler;

import pl.marcinchwedczuk.cjava.ast.ClassDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.CompilationUnitAst;
import pl.marcinchwedczuk.cjava.bytecode.JavaClassFile;

import java.util.Arrays;

public class BytecodeDecompiler {
	private ClassDeclarationDecompiler classDeclarationDecompiler;

	public BytecodeDecompiler() {
		this.classDeclarationDecompiler = new ClassDeclarationDecompiler();
	}

	public CompilationUnitAst decompile(JavaClassFile classFile) {
		ClassDeclarationAst classDeclarationAst =
				classDeclarationDecompiler.decompile(classFile);

		return new CompilationUnitAst(Arrays.asList(classDeclarationAst));
	}
}
