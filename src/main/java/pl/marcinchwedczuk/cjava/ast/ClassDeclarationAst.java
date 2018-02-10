package pl.marcinchwedczuk.cjava.ast;

import java.util.List;

public class ClassDeclarationAst extends TypeDeclarationAst {

	private final TypeName className;
	private final TypeName superClassName;
	private final List<TypeName> implementedInterfaces;

	public ClassDeclarationAst(TypeName className, TypeName superClassName, List<TypeName> implementedInterfaces) {
		this.className = className;
		this.superClassName = superClassName;
		this.implementedInterfaces = implementedInterfaces;
	}

	public TypeName getClassName() {
		return className;
	}

	public TypeName getSuperClassName() {
		return superClassName;
	}

	public List<TypeName> getImplementedInterfaces() {
		return implementedInterfaces;
	}
}
