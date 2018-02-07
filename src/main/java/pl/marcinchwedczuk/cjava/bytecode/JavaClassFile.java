package pl.marcinchwedczuk.cjava.bytecode;

import pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPool;
import pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPoolIndex;

import java.util.EnumSet;

public class JavaClassFile {
	private int magicNumber;

	private short minorVersion;
	private short majorVersion;

	private ConstantPool constantPool;

	private EnumSet<AccessFlag> accessFlags;

	private ConstantPoolIndex thisClass;
	private ConstantPoolIndex superClass;

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

	public ConstantPool getConstantPool() {
		return constantPool;
	}

	public void setConstantPool(ConstantPool constantPool) {
		this.constantPool = constantPool;
	}

	public EnumSet<AccessFlag> getAccessFlags() {
		return accessFlags;
	}

	public void setAccessFlags(EnumSet<AccessFlag> accessFlags) {
		this.accessFlags = accessFlags;
	}
}
