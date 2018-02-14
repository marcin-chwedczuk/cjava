package pl.marcinchwedczuk.cjava.decompiler;

import pl.marcinchwedczuk.cjava.ast.ClassDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.FieldDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.annotation.AnnotationAst;
import pl.marcinchwedczuk.cjava.bytecode.JavaClassFile;
import pl.marcinchwedczuk.cjava.bytecode.attribute.RuntimeVisibleAnnotationsAttribute;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ClassDecompiler {
	private final JavaClassFile classFile;
	private final ConstantPoolHelper cp;

	public ClassDecompiler(JavaClassFile classFile) {
		this.classFile = Objects.requireNonNull(classFile);
		this.cp = new ConstantPoolHelper(classFile.getConstantPool());
	}

	public ClassDeclarationAst decompile() {
		ClassDeclarationAst declaration =
				new ClassDeclarationDecompiler(classFile).decompile();

		addAnnotations(declaration);
		addFields(declaration);

		return declaration;
	}

	private void addAnnotations(ClassDeclarationAst declaration) {
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

	private void addFields(ClassDeclarationAst declaration) {
		List<FieldDeclarationAst> fieldDeclarations =
				new FieldDecompiler(classFile.getClassFields(), cp)
					.decompile();

		declaration.setFields(fieldDeclarations);
	}
}
