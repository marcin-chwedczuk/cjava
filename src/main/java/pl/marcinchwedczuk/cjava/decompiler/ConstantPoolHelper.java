package pl.marcinchwedczuk.cjava.decompiler;

import pl.marcinchwedczuk.cjava.bytecode.constantpool.*;
import pl.marcinchwedczuk.cjava.decompiler.descriptor.field.FieldDescriptorParser;
import pl.marcinchwedczuk.cjava.decompiler.descriptor.method.MethodDescriptorParser;
import pl.marcinchwedczuk.cjava.decompiler.signature.MethodSignature;
import pl.marcinchwedczuk.cjava.decompiler.signature.parser.BinaryNameParser;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.ClassType;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;
import pl.marcinchwedczuk.cjava.decompiler.signature.parser.TokenStream;

import java.util.Objects;

public class ConstantPoolHelper {
	private final ConstantPool constantPool;

	public ConstantPoolHelper(ConstantPool constantPool) {
		this.constantPool = Objects.requireNonNull(constantPool);
	}

	public ConstantPool getConstantPool() {
		return constantPool;
	}

	public ClassType getClassName(ConstantPoolIndex indexToClassConstant) {
		return getClassName(indexToClassConstant.asInteger());
	}

	public ClassType getClassName(int indexToClassConstant) {
		ClassConstant classConstant = constantPool.getClass(indexToClassConstant);
		String classBinaryName = getString(classConstant.getName());
		return new BinaryNameParser(classBinaryName).parse();
	}

	public JavaType getFieldDescriptor(ConstantPoolIndex indexToFieldDescriptor) {
		String fieldDescriptor = getString(indexToFieldDescriptor);
		return new FieldDescriptorParser(new TokenStream(fieldDescriptor)).parse();
	}

	public String getString(ConstantPoolIndex utf8Constant) {
		Utf8Constant constant = constantPool.getUtf8(utf8Constant);
		return constant.asString();
	}

	public int getInteger(ConstantPoolIndex index) {
		IntegerConstant integerConstant = constantPool.getInteger(index);
		return integerConstant.getValue();
	}

	public MethodSignature getMethodDescriptor(ConstantPoolIndex descriptorIndex) {
		String methodDescriptor = getString(descriptorIndex);

		return new MethodDescriptorParser(
				new TokenStream(methodDescriptor)).parse();
	}

	public FieldRefConstant getFieldRef(int index) {
		return constantPool.getFieldRef(index);
	}

	public NameAndTypeConstant getNameAndType(ConstantPoolIndex index) {
		return constantPool.getNameAndType(index);
	}

	public MethodRefConstant getMethodRef(int index) {
		return constantPool.getMethodRef(index);
	}

	public Constant getAny(int index) {
		return constantPool.getAny(index);
	}
}
