package pl.marcinchwedczuk.cjava.bytecode.attribute;

import pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPoolIndex;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.requireNonNull;
import static pl.marcinchwedczuk.cjava.util.ListUtils.readOnlyCopy;

public class CodeAttribute extends Attribute {
	private final Code code;
	private final List<ExceptionTableEntry> exceptionTable;
	private final Attributes attributes;

	public CodeAttribute(Code code,
						 List<ExceptionTableEntry> exceptionTable,
						 Attributes attributes) {
		super(AttributeType.CODE);

		this.code = requireNonNull(code);
		this.exceptionTable = readOnlyCopy(exceptionTable);
		this.attributes = requireNonNull(attributes);
	}

	public Code getCode() {
		return code;
	}

	public List<ExceptionTableEntry> getExceptionTable() {
		return exceptionTable;
	}

	public Attributes getAttributes() {
		return attributes;
	}

	public static class Code {
		private final int maxStack;
		private final int maxLocals;
		private final byte[] bytes;

		public Code(int maxStack, int maxLocals, byte[] bytes) {
			this.maxStack = maxStack;
			this.maxLocals = maxLocals;
			this.bytes = Arrays.copyOf(bytes, bytes.length);
		}

		public int getMaxStack() {
			return maxStack;
		}

		public int getMaxLocals() {
			return maxLocals;
		}

		public byte[] getBytes() {
			return bytes;
		}
	}

	public static class ExceptionTableEntry {
		private final int startPC;
		private final int endPC;
		private final int handlerPC;

		// If this index == 0 then we catch all exceptions.
		private final ConstantPoolIndex exceptionClassIndexOptional;

		public ExceptionTableEntry(int startPC, int endPC, int handlerPC, ConstantPoolIndex exceptionClassIndexOptional) {
			this.startPC = startPC;
			this.endPC = endPC;
			this.handlerPC = handlerPC;
			this.exceptionClassIndexOptional = exceptionClassIndexOptional;
		}

		public int getStartPC() {
			return startPC;
		}

		public int getEndPC() {
			return endPC;
		}

		public int getHandlerPC() {
			return handlerPC;
		}

		public ConstantPoolIndex getExceptionClassIndexOptional() {
			return exceptionClassIndexOptional;
		}
	}
}
