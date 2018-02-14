package pl.marcinchwedczuk.cjava.decompiler;

import pl.marcinchwedczuk.cjava.ast.MethodDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.Visibility;
import pl.marcinchwedczuk.cjava.bytecode.attribute.SignatureAttribute;
import pl.marcinchwedczuk.cjava.bytecode.fields.FieldAccessFlag;
import pl.marcinchwedczuk.cjava.bytecode.method.MethodAccessFlag;
import pl.marcinchwedczuk.cjava.bytecode.method.MethodInfo;
import pl.marcinchwedczuk.cjava.bytecode.method.Methods;
import pl.marcinchwedczuk.cjava.decompiler.descriptor.method.MethodSignature;
import pl.marcinchwedczuk.cjava.decompiler.signature.MethodSignatureParser;
import pl.marcinchwedczuk.cjava.decompiler.signature.parser.TokenStream;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class MethodDecompiler {
	private final Methods classMethods;
	private final ConstantPoolHelper cp;

	public MethodDecompiler(Methods classMethods, ConstantPoolHelper cp) {
		this.classMethods = classMethods;
		this.cp = cp;
	}

	public List<MethodDeclarationAst> decompile() {
		return classMethods.getMethods().stream()
				.map(this::decompileMethod)
				.collect(toList());
	}

	private MethodDeclarationAst decompileMethod(MethodInfo methodInfo) {
		String methodName = cp.getString(methodInfo.getName());
		MethodSignature methodSignature = decompileMethodSignature(methodInfo);

		MethodDeclarationAst methodDeclaration =
				new MethodDeclarationAst(methodName, methodSignature);

		Visibility visibility = computeVisibility(methodInfo.getAccessFlags());
		methodDeclaration.setVisibility(visibility);

		addModifiers(methodDeclaration, methodInfo.getAccessFlags());

		return methodDeclaration;
	}

	private MethodSignature decompileMethodSignature(MethodInfo methodInfo) {
		Optional<SignatureAttribute> signatureAttribute =
				methodInfo.getAttributes().findSignatureAttribute();

		if (signatureAttribute.isPresent()) {
			String methodSignatureText =
					cp.getString(signatureAttribute.get().getSignatureText());

			MethodSignature methodSignature =
					new MethodSignatureParser(new TokenStream(methodSignatureText))
						.parseMethodSignature();

			return methodSignature;
		}

		return cp.getMethodDescriptor(methodInfo.getDescriptor());
	}

	private Visibility computeVisibility(Set<MethodAccessFlag> accessFlags) {
		if (accessFlags.contains(MethodAccessFlag.ACC_PRIVATE)) {
			return Visibility.PRIVATE;
		} else if (accessFlags.contains(MethodAccessFlag.ACC_PROTECTED)) {
			return Visibility.PROTECTED;
		} else if (accessFlags.contains(MethodAccessFlag.ACC_PUBLIC)) {
			return Visibility.PUBLIC;
		} else {
			return Visibility.PACKAGE;
		}
	}

	private void addModifiers(MethodDeclarationAst methodDeclarationAst,
							  Set<MethodAccessFlag> accessFlags) {
		if (accessFlags.contains(MethodAccessFlag.ACC_STATIC)) {
			methodDeclarationAst.setStatic(true);
		}

		if (accessFlags.contains(MethodAccessFlag.ACC_ABSTRACT)) {
			methodDeclarationAst.setAbstract(true);
		}

		if (accessFlags.contains(MethodAccessFlag.ACC_FINAL)) {
			methodDeclarationAst.setFinal(true);
		}

		if (accessFlags.contains(MethodAccessFlag.ACC_NATIVE)) {
			methodDeclarationAst.setNative(true);
		}

		if (accessFlags.contains(MethodAccessFlag.ACC_SYNCHRONIZED)) {
			methodDeclarationAst.setSynchronized(true);
		}

		if (accessFlags.contains(MethodAccessFlag.ACC_VARARGS)) {
			methodDeclarationAst.setVarargs(true);
		}

		if (accessFlags.contains(MethodAccessFlag.ACC_STRICT)) {
			methodDeclarationAst.setStrictFP(true);
		}
	}

}
