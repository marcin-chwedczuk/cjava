package pl.marcinchwedczuk.cjava.decompiler;

import pl.marcinchwedczuk.cjava.ast.ClassDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.CompilationUnitAst;
import pl.marcinchwedczuk.cjava.ast.MethodDeclarationAst;
import pl.marcinchwedczuk.cjava.bytecode.JavaClassFile;
import pl.marcinchwedczuk.cjava.bytecode.JavaClassFileLoader;

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.fail;
import static pl.marcinchwedczuk.cjava.bytecode.TestUtils.readClassBytes;

public abstract class BaseDecompilerTests {
	protected static ClassDeclarationAst decompileWithoutCode(Class<?> klassToDecompile) throws IOException {
		return decompile(klassToDecompile, DecompilationOptions.withoutCode());
	}

	protected static ClassDeclarationAst decompile(Class<?> klassToDecompile) throws IOException {
		return decompile(klassToDecompile, DecompilationOptions.defaultOptions());
	}

	private static ClassDeclarationAst decompile(
			Class<?> klassToDecompile, DecompilationOptions options) throws IOException {

		JavaClassFileLoader loader = new JavaClassFileLoader();
		JavaClassFile classFile = loader.load(readClassBytes(klassToDecompile));

		CompilationUnitAst compilationUnit =
				new BytecodeDecompiler(classFile, options).decompile();

		ClassDeclarationAst classDeclaration =
				(ClassDeclarationAst) compilationUnit.getDeclaredTypes().get(0);

		return classDeclaration;
	}

	protected static MethodDeclarationAst findMethodByName(ClassDeclarationAst classDeclaration, String methodName) {
		Optional<MethodDeclarationAst> methodDeclarationOpt = classDeclaration
				.getMethods()
				.stream()
				.filter(m -> m.getMethodName().equals(methodName))
				.findFirst();

		if (!methodDeclarationOpt.isPresent()) {
			fail("Cannot find method with name: '" + methodName + "'.");
		}

		return methodDeclarationOpt.get();
	}
}
