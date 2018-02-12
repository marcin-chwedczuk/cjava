package pl.marcinchwedczuk.cjava.decompiler;

import pl.marcinchwedczuk.cjava.ast.ClassDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.CompilationUnitAst;
import pl.marcinchwedczuk.cjava.bytecode.JavaClassFile;
import pl.marcinchwedczuk.cjava.bytecode.JavaClassFileLoader;

import java.io.IOException;

import static pl.marcinchwedczuk.cjava.bytecode.TestUtils.readClassBytes;

public abstract class BaseDecompilerTests {
	protected static ClassDeclarationAst decompile(Class<?> klassToDecompile) throws IOException {
		JavaClassFileLoader loader = new JavaClassFileLoader();
		JavaClassFile classFile = loader.load(readClassBytes(klassToDecompile));

		CompilationUnitAst compilationUnit =
				new BytecodeDecompiler(classFile).decompile();

		ClassDeclarationAst classDeclaration =
				(ClassDeclarationAst) compilationUnit.getDeclaredTypes().get(0);

		return classDeclaration;
	}
}
