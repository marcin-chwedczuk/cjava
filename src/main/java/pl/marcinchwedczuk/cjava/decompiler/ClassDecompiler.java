package pl.marcinchwedczuk.cjava.decompiler;

import pl.marcinchwedczuk.cjava.DecompilationOptions;
import pl.marcinchwedczuk.cjava.ast.ClassDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.FieldDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.MethodDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.annotation.AnnotationAst;
import pl.marcinchwedczuk.cjava.bytecode.JavaClassFile;
import pl.marcinchwedczuk.cjava.bytecode.attribute.RuntimeVisibleAnnotationsAttribute;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class ClassDecompiler {
	private final JavaClassFile classFile;
	private final ConstantPoolHelper cp;
	private final DecompilationOptions decompilationOptions;

	public ClassDecompiler(JavaClassFile classFile, DecompilationOptions decompilationOptions) {
		this.classFile = Objects.requireNonNull(classFile);
		this.cp = new ConstantPoolHelper(classFile.getConstantPool());
		this.decompilationOptions = decompilationOptions;
	}

	public ClassDeclarationAst decompile() {
		ClassDeclarationAst.Builder declaration =
				new ClassDeclarationDecompiler(classFile).decompile();

		addAnnotations(declaration);
		addFields(declaration);
		addMethods(declaration);

		return declaration.build();
	}

	private void addAnnotations(ClassDeclarationAst.Builder declaration) {
		Optional<RuntimeVisibleAnnotationsAttribute> annotationsAttribute = classFile
				.getAttributes()
				.findRuntimeVisibleAnnotationsAttribute();

		if (!annotationsAttribute.isPresent()) {
			return;
		}

		AnnotationDecompiler annotationDecompiler =
				new AnnotationDecompiler(annotationsAttribute.get(), classFile.getConstantPool());
		List<AnnotationAst> annotations = annotationDecompiler.decompile();

		declaration.setAnnotations(annotations);
	}

	private void addFields(ClassDeclarationAst.Builder declaration) {
		List<FieldDeclarationAst> fieldDeclarations =
				new FieldDecompiler(classFile.getClassFields(), cp)
					.decompile();

		declaration.setFields(fieldDeclarations);
	}

	private void addMethods(ClassDeclarationAst.Builder declaration) {
		List<MethodDeclarationAst> methodDeclarations =
				new MethodDecompiler(classFile.getClassMethods(), cp, decompilationOptions)
					.decompile();

		// TODO: This should be executed as a separate AST pass
		//HACK:
		String className = declaration.build()
				.getClassName()
				.computeSimpleClassName();

		methodDeclarations = methodDeclarations.stream()
				.map(m -> m.isConstructor() ? m.withMethodName(className) : m)
				.collect(toList());

		declaration.setMethods(methodDeclarations);
	}
}
