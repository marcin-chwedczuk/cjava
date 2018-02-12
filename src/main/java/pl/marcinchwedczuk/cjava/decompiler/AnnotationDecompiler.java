package pl.marcinchwedczuk.cjava.decompiler;

import pl.marcinchwedczuk.cjava.ast.AnnotationAst;
import pl.marcinchwedczuk.cjava.bytecode.attribute.RuntimeVisibleAnnotationsAttribute;
import pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPool;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

public class AnnotationDecompiler {

	private final ConstantPoolHelper cp;
	private final RuntimeVisibleAnnotationsAttribute annotationsAttribute;

	public AnnotationDecompiler(
			RuntimeVisibleAnnotationsAttribute annotationsAttribute,
			ConstantPool constantPool) {
		this.annotationsAttribute = requireNonNull(annotationsAttribute);
		this.cp = new ConstantPoolHelper(constantPool);
	}

	public List<AnnotationAst> decompile() {
		return annotationsAttribute.getAnnotations().stream()
				.map(this::decompileAnnotation)
				.collect(toList());
	}

	private AnnotationAst decompileAnnotation(RuntimeVisibleAnnotationsAttribute.Annotation bytecodeAnnotation) {
		return new AnnotationAst();
	}
}
