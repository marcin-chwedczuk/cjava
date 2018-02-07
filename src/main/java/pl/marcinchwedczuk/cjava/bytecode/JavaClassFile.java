package pl.marcinchwedczuk.cjava.bytecode;

import pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPool;

public class JavaClassFile {
	private int magicNumber;

	private short minorVersion;
	private short majorVersion;

	private short constantPoolCount;
	private ConstantPool constantPool;

	public int getMagicNumber() {
		return magicNumber;
	}

	public void setMagicNumber(int magicNumber) {
		this.magicNumber = magicNumber;
	}

	public short getMinorVersion() {
		return minorVersion;
	}

	public void setMinorVersion(short minorVersion) {
		this.minorVersion = minorVersion;
	}

	public short getMajorVersion() {
		return majorVersion;
	}

	public void setMajorVersion(short majorVersion) {
		this.majorVersion = majorVersion;
	}

	public short getConstantPoolCount() {
		return constantPoolCount;
	}

	public void setConstantPoolCount(short constantPoolCount) {
		this.constantPoolCount = constantPoolCount;
	}

	public ConstantPool getConstantPool() {
		return constantPool;
	}

	public void setConstantPool(ConstantPool constantPool) {
		this.constantPool = constantPool;
	}
}
