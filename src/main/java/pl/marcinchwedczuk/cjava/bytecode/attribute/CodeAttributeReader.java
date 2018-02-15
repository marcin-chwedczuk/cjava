package pl.marcinchwedczuk.cjava.bytecode.attribute;

import pl.marcinchwedczuk.cjava.bytecode.attribute.CodeAttribute.Code;
import pl.marcinchwedczuk.cjava.bytecode.attribute.CodeAttribute.ExceptionTableEntry;
import pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPool;
import pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPoolIndex;
import pl.marcinchwedczuk.cjava.bytecode.utils.ClassFileReader;
import pl.marcinchwedczuk.cjava.decompiler.ConstantPoolHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPoolIndex.readFrom;

public class CodeAttributeReader {
	private final ClassFileReader classFileReader;
	private final ConstantPoolHelper cp;

	public CodeAttributeReader(ClassFileReader classFileReader, ConstantPool constantPool) {
		this.classFileReader = requireNonNull(classFileReader);
		this.cp = new ConstantPoolHelper(constantPool);
	}

	public CodeAttribute read() throws IOException {
		// Read attribute length (u4 field) - we ignore this value in the decompiler.
		classFileReader.readInt();

		Code code = readCode();
		List<ExceptionTableEntry> exceptionTable = readExceptionTable();
		Attributes attributes = readAttributes();

		return new CodeAttribute(code, exceptionTable, attributes);
	}

	private Code readCode() throws IOException {
		int maxStack = classFileReader.readUnsignedShort();
		int maxLocals = classFileReader.readUnsignedShort();

		int codeLength = classFileReader.readInt();
		byte[] instructions = classFileReader.readBytes(codeLength);

		return new Code(maxStack, maxLocals, instructions);
	}

	private List<ExceptionTableEntry> readExceptionTable() throws IOException {
		int tableLength = classFileReader.readUnsignedShort();

		List<ExceptionTableEntry> exceptionTable = new ArrayList<>();

		for (int i = 0; i < tableLength; i++) {
			ExceptionTableEntry entry = readExceptionTableEntry();
			exceptionTable.add(entry);
		}

		return exceptionTable;
	}

	private ExceptionTableEntry readExceptionTableEntry() throws IOException {
		int startPC = classFileReader.readUnsignedShort();
		int endPC = classFileReader.readUnsignedShort();
		int handlerPC = classFileReader.readUnsignedShort();
		ConstantPoolIndex catchType = readFrom(classFileReader);

		return new ExceptionTableEntry(startPC, endPC, handlerPC, catchType);
	}

	private Attributes readAttributes() throws IOException {
		AttributesReader attributesReader =
				new AttributesReader(classFileReader, cp.getConstantPool());
		return attributesReader.readAttributes();
	}

}
