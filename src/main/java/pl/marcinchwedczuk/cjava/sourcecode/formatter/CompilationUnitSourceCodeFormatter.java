package pl.marcinchwedczuk.cjava.sourcecode.formatter;

import com.google.common.collect.ImmutableList;
import pl.marcinchwedczuk.cjava.ast.ClassDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.CompilationUnitAst;
import pl.marcinchwedczuk.cjava.ast.TypeDeclarationAst;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.PackageName;
import pl.marcinchwedczuk.cjava.optimizer.imports.ImportStatement;
import pl.marcinchwedczuk.cjava.optimizer.imports.JavaTypeNameRenderer;

import static pl.marcinchwedczuk.cjava.util.ListUtils.firstElement;

public class CompilationUnitSourceCodeFormatter extends BaseSourceCodeFormatter {
	private final CompilationUnitAst compilationUnit;

	public CompilationUnitSourceCodeFormatter(JavaTypeNameRenderer typeNameRenderer, JavaCodeWriter codeWriter, CompilationUnitAst compilationUnit) {
		super(typeNameRenderer, codeWriter);
		this.compilationUnit = compilationUnit;
	}

	public void convertAstToJavaCode() {
		printPackageDeclaration();
		printImports();

		printTopLevelTypes();
	}

	private void printPackageDeclaration() {
		TypeDeclarationAst firstTypeDeclaration =
				firstElement(compilationUnit.getDeclaredTypes());

		PackageName currentPackage = ((ClassDeclarationAst)firstTypeDeclaration)
				.getClassName()
				.getPackageName();

		if (!currentPackage.isUnnamed()) {
			codeWriter
					.print("package ")
					.print(currentPackage.asJavaSouceCode())
					.print(";")
					.printNewLine();
		}
	}

	private void printImports() {
		codeWriter.printNewLine();

		ImmutableList<ImportStatement> imports = typeNameRenderer.getImports();
		for (ImportStatement import_ : imports) {
			codeWriter
					.print("import ")
					// TODO: Move import rendering to typeNameRenderer
					.print(import_.getTypeToImport().asSourceCodeString())
					.print(";")
					.printNewLine();
		}

		codeWriter.printNewLine();
	}

	private void printTopLevelTypes() {
		for (TypeDeclarationAst declaredType : compilationUnit.getDeclaredTypes()) {
			new ClassFormatter(typeNameRenderer, codeWriter, (ClassDeclarationAst)declaredType)
					.convertAstToJavaCode();
		}
	}
}
