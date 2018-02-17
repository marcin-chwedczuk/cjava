package pl.marcinchwedczuk.cjava.decompiler;

import pl.marcinchwedczuk.cjava.ast.FieldDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.Visibility;
import pl.marcinchwedczuk.cjava.ast.annotation.AnnotationAst;
import pl.marcinchwedczuk.cjava.bytecode.attribute.RuntimeVisibleAnnotationsAttribute;
import pl.marcinchwedczuk.cjava.bytecode.attribute.SignatureAttribute;
import pl.marcinchwedczuk.cjava.bytecode.fields.FieldAccessFlag;
import pl.marcinchwedczuk.cjava.bytecode.fields.FieldInfo;
import pl.marcinchwedczuk.cjava.bytecode.fields.Fields;
import pl.marcinchwedczuk.cjava.decompiler.signature.FieldSignatureParser;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;
import pl.marcinchwedczuk.cjava.decompiler.signature.parser.TokenStream;

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
		JavaType fieldType = decompileFieldType(fieldInfo);
		String fieldName = cp.getString(fieldInfo.getName());

		FieldDeclarationAst.Builder declarationAst =
				FieldDeclarationAst.builder(fieldType, fieldName);

		Visibility visibility = computeVisibility(fieldInfo.getAccessFlags());
		declarationAst.setVisibility(visibility);

		addModifiers(declarationAst, fieldInfo.getAccessFlags());
		addAnnotations(declarationAst, fieldInfo);

		return declarationAst.build();
	}

	private JavaType decompileFieldType(FieldInfo fieldInfo) {
		Optional<SignatureAttribute> signatureAttribute =
				fieldInfo
						.getAttributes()
						.findSignatureAttribute();

		if (signatureAttribute.isPresent()) {
			String fieldSignature =
					cp.getString(signatureAttribute.get().getSignatureText());

			return new FieldSignatureParser(new TokenStream(fieldSignature))
					.parse();
		}

		return cp.getFieldDescriptor(fieldInfo.getDescriptor());
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

	private void addModifiers(FieldDeclarationAst.Builder declarationAst, Set<FieldAccessFlag> accessFlags) {
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

	private void addAnnotations(FieldDeclarationAst.Builder declarationAst, FieldInfo fieldInfo) {
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
