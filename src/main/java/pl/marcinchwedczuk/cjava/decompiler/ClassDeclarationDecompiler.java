package pl.marcinchwedczuk.cjava.decompiler;

import pl.marcinchwedczuk.cjava.ast.ClassDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.Visibility;
import pl.marcinchwedczuk.cjava.bytecode.AccessFlag;
import pl.marcinchwedczuk.cjava.bytecode.JavaClassFile;
import pl.marcinchwedczuk.cjava.bytecode.attribute.SignatureAttribute;
import pl.marcinchwedczuk.cjava.decompiler.signature.ClassSignature;
import pl.marcinchwedczuk.cjava.decompiler.signature.ClassSignatureParser;
import pl.marcinchwedczuk.cjava.decompiler.signature.TypeParameter;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.ClassType;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;
import pl.marcinchwedczuk.cjava.decompiler.signature.parser.TokenStream;

import java.util.*;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

public class ClassDeclarationDecompiler {
	private static final List<TypeParameter> EMPTY_TYPE_PARAMETERS = emptyList();

	private final JavaClassFile classFile;
	private final ConstantPoolHelper cp;

	public ClassDeclarationDecompiler(JavaClassFile classFile) {
		this.classFile = Objects.requireNonNull(classFile);
		this.cp = new ConstantPoolHelper(classFile.getConstantPool());
	}

	public ClassDeclarationAst.Builder decompile() {
		// If class has Signature attribute attached use that
		// to obtain exact class declaration
		Optional<SignatureAttribute> signatureAttribute = classFile
				.getAttributes()
				.findSignatureAttribute();

		ClassDeclarationAst.Builder declaration = signatureAttribute
				.map(this::createDeclarationFromSignatureAttribute)
				.orElseGet(this::createDeclarationFromRawTypes);

		addClassModifiers(declaration, classFile.getAccessFlags());

		return declaration;
	}

	private ClassDeclarationAst.Builder createDeclarationFromSignatureAttribute(SignatureAttribute signatureAttribute) {
		ClassType className = cp.getClassName(classFile.getThisClass());

		String signatureText = cp.getString(signatureAttribute.getSignatureText());
		ClassSignature classSignature =
				new ClassSignatureParser(new TokenStream(signatureText)).parse();

		return ClassDeclarationAst.builder()
				.setClassName(className)
				.setTypeParameters(classSignature.getTypeParameters())
				.setSuperClass(classSignature.getSuperClass())
				.setImplementedInterfaces(classSignature.getImplementedInterfaces());
	}

	private ClassDeclarationAst.Builder createDeclarationFromRawTypes() {
		// Use bytecode raw class names to construct class declaration.
		ClassType className = cp.getClassName(classFile.getThisClass());

		JavaType superClass = cp.getClassName(classFile.getSuperClass());

		List<JavaType> implementedInterfaces = classFile.getInterfaces()
				.getClasses()
				.stream()
				.map(cp::getClassName)
				.collect(toList());

		return ClassDeclarationAst.builder()
				.setClassName(className)
				.setTypeParameters(EMPTY_TYPE_PARAMETERS)
				.setSuperClass(superClass)
				.setImplementedInterfaces(implementedInterfaces);
	}

	private void addClassModifiers(ClassDeclarationAst.Builder declaration, EnumSet<AccessFlag> accessFlags) {
		if (accessFlags.contains(AccessFlag.ACC_PUBLIC)) {
			declaration.setVisibility(Visibility.PUBLIC);
		}
		else {
			declaration.setVisibility(Visibility.PACKAGE);
		}

		declaration.setAbstract(accessFlags.contains(AccessFlag.ACC_ABSTRACT));
		declaration.setFinal(accessFlags.contains(AccessFlag.ACC_FINAL));
	}
}
