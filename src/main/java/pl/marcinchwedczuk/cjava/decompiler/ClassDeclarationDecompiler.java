package pl.marcinchwedczuk.cjava.decompiler;

import pl.marcinchwedczuk.cjava.ast.ClassDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.TypeName;
import pl.marcinchwedczuk.cjava.bytecode.JavaClassFile;
import pl.marcinchwedczuk.cjava.bytecode.constantpool.ClassConstant;
import pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPool;
import pl.marcinchwedczuk.cjava.bytecode.constantpool.Utf8Constant;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class ClassDeclarationDecompiler {
	public ClassDeclarationAst decompile(JavaClassFile classFile) {

		ConstantPoolHelper cp = new ConstantPoolHelper(classFile.getConstantPool());

		TypeName className = cp.getClassName(classFile.getThisClass());
		TypeName superClassName = cp.getClassName(classFile.getSuperClass());

		List<TypeName> implementedInterfaces = classFile.getInterfaces()
				.getClasses()
				.stream()
				.map(cp::getClassName)
				.collect(toList());

		return new ClassDeclarationAst(className, superClassName, implementedInterfaces);
	}
}
