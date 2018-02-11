package pl.marcinchwedczuk.cjava.decompiler;

import pl.marcinchwedczuk.cjava.bytecode.constantpool.ClassConstant;
import pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPool;
import pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPoolIndex;
import pl.marcinchwedczuk.cjava.bytecode.constantpool.Utf8Constant;
import pl.marcinchwedczuk.cjava.decompiler.signature.javatype.BinaryNameParser;
import pl.marcinchwedczuk.cjava.decompiler.signature.javatype.ClassType;
import pl.marcinchwedczuk.cjava.decompiler.signature.javatype.JavaType;

import java.util.Objects;

public class ConstantPoolHelper {
	private final ConstantPool constantPool;

	public ConstantPoolHelper(ConstantPool constantPool) {
		this.constantPool = Objects.requireNonNull(constantPool);
	}

	public ClassType getClassName(ConstantPoolIndex indexToClassConstant) {
		ClassConstant classConstant = constantPool.getClass(indexToClassConstant);
		String classBinaryName = getString(classConstant.getName());
		return new BinaryNameParser(classBinaryName).parse();
	}

	public String getString(ConstantPoolIndex utf8Constant) {
		Utf8Constant constant = constantPool.getUtf8(utf8Constant);
		return constant.asString();
	}
}
