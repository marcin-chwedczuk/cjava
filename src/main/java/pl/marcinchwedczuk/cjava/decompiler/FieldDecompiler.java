package pl.marcinchwedczuk.cjava.decompiler;

import pl.marcinchwedczuk.cjava.ast.FieldDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.Visibility;
import pl.marcinchwedczuk.cjava.ast.annotation.AnnotationAst;
import pl.marcinchwedczuk.cjava.bytecode.attribute.RuntimeVisibleAnnotationsAttribute;
import pl.marcinchwedczuk.cjava.bytecode.fields.FieldAccessFlag;
import pl.marcinchwedczuk.cjava.bytecode.fields.FieldInfo;
import pl.marcinchwedczuk.cjava.bytecode.fields.Fields;
import pl.marcinchwedczuk.cjava.decompiler.signature.javatype.JavaType;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toList;

public class FieldDecompiler {
	private final Fields classFields;
	private final ConstantPoolHelper cp;

	public FieldDecompiler(Fields classFields, ConstantPoolHelper cp) {
		this.classFields = classFields;
		this.cp = cp;
	}

	public List<FieldDeclarationAst> decompile() {
		return classFields.getFields().stream()
				.map(this::decompileField)
				.collect(toList());
	}

	private FieldDeclarationAst decompileField(FieldInfo fieldInfo) {
		JavaType fieldType = cp.getFieldDescriptor(fieldInfo.getDescriptor());
		String fieldName = cp.getString(fieldInfo.getName());

		FieldDeclarationAst declarationAst = new FieldDeclarationAst(fieldType, fieldName);

		Visibility visibility = computeVisibility(fieldInfo.getAccessFlags());
		declarationAst.setVisibility(visibility);

		addModifiers(declarationAst, fieldInfo.getAccessFlags());
		addAnnotations(declarationAst, fieldInfo);

		return declarationAst;
	}

	private Visibility computeVisibility(Set<FieldAccessFlag> accessFlags) {
		if (accessFlags.contains(FieldAccessFlag.ACC_PRIVATE)) {
			return Visibility.PRIVATE;
		} else if (accessFlags.contains(FieldAccessFlag.ACC_PROTECTED)) {
			return Visibility.PROTECTED;
		} else if (accessFlags.contains(FieldAccessFlag.ACC_PUBLIC)) {
			return Visibility.PUBLIC;
		} else {
			return Visibility.PACKAGE;
		}
	}

	private void addModifiers(FieldDeclarationAst declarationAst, Set<FieldAccessFlag> accessFlags) {
		if (accessFlags.contains(FieldAccessFlag.ACC_FINAL)) {
			declarationAst.setFinal(true);
		}

		if (accessFlags.contains(FieldAccessFlag.ACC_STATIC)) {
			declarationAst.setStatic(true);
		}

		if (accessFlags.contains(FieldAccessFlag.ACC_TRANSIENT)) {
			declarationAst.setTransient(true);
		}

		if (accessFlags.contains(FieldAccessFlag.ACC_VOLATILE)) {
			declarationAst.setVolatile(true);
		}
	}

	private void addAnnotations(FieldDeclarationAst declarationAst, FieldInfo fieldInfo) {
		Optional<RuntimeVisibleAnnotationsAttribute> annotationsAttribute =
				fieldInfo
					.getAttributes()
					.findRuntimeVisibleAnnotationsAttribute();

		if (annotationsAttribute.isPresent()) {
			List<AnnotationAst> annotationAsts =
					new AnnotationDecompiler(annotationsAttribute.get(), cp)
						.decompile();

			declarationAst.setAnnotations(annotationAsts);
		}
	}
}
