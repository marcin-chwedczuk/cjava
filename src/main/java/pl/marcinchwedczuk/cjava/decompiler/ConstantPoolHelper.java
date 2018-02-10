package pl.marcinchwedczuk.cjava.decompiler;

import pl.marcinchwedczuk.cjava.ast.TypeName;
import pl.marcinchwedczuk.cjava.bytecode.constantpool.ClassConstant;
import pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPool;
import pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPoolIndex;
import pl.marcinchwedczuk.cjava.bytecode.constantpool.Utf8Constant;

import java.util.Objects;

public class ConstantPoolHelper {
	private final ConstantPool constantPool;

	public ConstantPoolHelper(ConstantPool constantPool) {
		this.constantPool = Objects.requireNonNull(constantPool);
	}

	public TypeName getClassName(ConstantPoolIndex indexToClassConstant) {
		ClassConstant classConstant = constantPool.getClass(indexToClassConstant);
		String byteCodeClassName = getString(classConstant.getName());
		return TypeName.fromBytecodeClassName(byteCodeClassName);
	}

	public String getString(ConstantPoolIndex utf8Constant) {
		Utf8Constant constant = constantPool.getUtf8(utf8Constant);
		return constant.asString();
	}
}
