package pl.marcinchwedczuk.cjava;

import com.google.common.collect.ImmutableSet;
import pl.marcinchwedczuk.cjava.ast.ClassDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.CompilationUnitAst;
import pl.marcinchwedczuk.cjava.bytecode.JavaClassFile;
import pl.marcinchwedczuk.cjava.bytecode.JavaClassFileLoader;
import pl.marcinchwedczuk.cjava.decompiler.BytecodeDecompiler;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.ClassType;
import pl.marcinchwedczuk.cjava.optimizer.imports.*;
import pl.marcinchwedczuk.cjava.sourcecode.formatter.CompilationUnitSourceCodeFormatter;
import pl.marcinchwedczuk.cjava.sourcecode.formatter.JavaCodeWriter;

import java.io.IOException;

public class CJava {

	public static String decompile(byte[] klassBytes, DecompilationOptions options) throws IOException {
		JavaClassFile classFile =
				new JavaClassFileLoader().load(klassBytes);

		CompilationUnitAst compilationUnit =
				new BytecodeDecompiler(classFile, options).decompile();

		ClassDeclarationAst classDeclaration = getTopLevelType(compilationUnit);

		JavaCodeWriter codeWriter = new JavaCodeWriter();

		JavaTypeNameRenderer typeNameRenderer = options.optimizeImports()
				? createImportOptimizingTypeNameRenderer(compilationUnit)
				: new FullQualifiedNameJavaTypeNameRenderer();

		CompilationUnitSourceCodeFormatter formatter =
				new CompilationUnitSourceCodeFormatter(typeNameRenderer, codeWriter, compilationUnit);

		formatter.convertAstToJavaCode();

		String sourceCode = codeWriter.dumpSourceCode();
		return sourceCode;
	}

	private static ClassDeclarationAst getTopLevelType(CompilationUnitAst compilationUnit) {
		return (ClassDeclarationAst) compilationUnit.getDeclaredTypes().get(0);
	}

	private static JavaTypeNameRenderer createImportOptimizingTypeNameRenderer(CompilationUnitAst compilationUnit) {
		JavaTypeHistogram histogram = new JavaTypeHistogram();

		compilationUnit.astMap(
				new JavaTypeHistogramCollector(histogram));

		ClassType topLevelType = getTopLevelType(compilationUnit)
				.getClassName();

		ImportAlgorithm importAlgorithm = new ImportAlgorithm(
				topLevelType.getPackageName(),
				ImmutableSet.of(),
				ImmutableSet.of(topLevelType),
				histogram);

		importAlgorithm.selectTypesToImport();

		return new ImportOptimizingJavaTypeNameRenderer(
				importAlgorithm.findExplicitImport(),
				importAlgorithm.findImplicitImports());
	}
}
