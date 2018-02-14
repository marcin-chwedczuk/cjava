package pl.marcinchwedczuk.cjava.decompiler;

import pl.marcinchwedczuk.cjava.bytecode.constantpool.*;
import pl.marcinchwedczuk.cjava.decompiler.descriptor.field.FieldDescriptorParser;
import pl.marcinchwedczuk.cjava.decompiler.descriptor.method.MethodDescriptorParser;
import pl.marcinchwedczuk.cjava.decompiler.descriptor.method.MethodSignature;
import pl.marcinchwedczuk.cjava.decompiler.signature.javatype.BinaryNameParser;
import pl.marcinchwedczuk.cjava.decompiler.signature.javatype.ClassType;
import pl.marcinchwedczuk.cjava.decompiler.signature.javatype.JavaType;
import pl.marcinchwedczuk.cjava.decompiler.signature.parser.TokenStream;

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
}
