package pl.marcinchwedczuk.cjava.decompiler;

import pl.marcinchwedczuk.cjava.ast.ClassDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.CompilationUnitAst;
import pl.marcinchwedczuk.cjava.bytecode.JavaClassFile;

import static java.util.Collections.singletonList;

public class BytecodeDecompiler {
	private final ClassDecompiler classDecompiler;

	public BytecodeDecompiler(JavaClassFile classFile, DecompilationOptions decompilationOptions) {
		this.classDecompiler = new ClassDecompiler(classFile, decompilationOptions);
	}

	public CompilationUnitAst decompile() {
		ClassDeclarationAst declarationAst = classDecompiler.decompile();

		return CompilationUnitAst.create(singletonList(declarationAst));
	}
}
