package pl.marcinchwedczuk.cjava.bytecode;

import pl.marcinchwedczuk.cjava.bytecode.attribute.Attribute;
import pl.marcinchwedczuk.cjava.bytecode.attribute.Attributes;
import pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPool;
import pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPoolIndex;
import pl.marcinchwedczuk.cjava.bytecode.fields.Fields;
import pl.marcinchwedczuk.cjava.bytecode.interfaces.Interfaces;
import pl.marcinchwedczuk.cjava.bytecode.method.Methods;

import java.util.EnumSet;

public class JavaClassFile {
	private int magicNumber;

	private short minorVersion;
	private short majorVersion;

	private ConstantPool constantPool;

	private EnumSet<AccessFlag> accessFlags;

	private ConstantPoolIndex thisClass;
	private ConstantPoolIndex superClass;

	private Interfaces interfaces;
	private Fields classFields;
	private Methods classMethods;
	private Attributes attributes;

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

	public ConstantPoolIndex getThisClass() {
		return thisClass;
	}

	public void setThisClass(ConstantPoolIndex thisClass) {
		this.thisClass = thisClass;
	}

	public ConstantPoolIndex getSuperClass() {
		return superClass;
	}

	public void setSuperClass(ConstantPoolIndex superClass) {
		this.superClass = superClass;
	}

	public Interfaces getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(Interfaces interfaces) {
		this.interfaces = interfaces;
	}

	public Fields getClassFields() {
		return classFields;
	}

	public void setClassFields(Fields classFields) {
		this.classFields = classFields;
	}

	public Methods getClassMethods() {
		return classMethods;
	}

	public void setClassMethods(Methods classMethods) {
		this.classMethods = classMethods;
	}

	public Attributes getAttributes() {
		return attributes;
	}

	public void setAttributes(Attributes attributes) {
		this.attributes = attributes;
	}
}
