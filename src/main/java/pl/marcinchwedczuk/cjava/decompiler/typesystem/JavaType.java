package pl.marcinchwedczuk.cjava.decompiler.typesystem;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import static pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType.MetaType.PRIMITIVE_TYPE;
import static pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType.MetaType.REFERENCE_TYPE;
import static pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType.MetaType.TYPE_VARIABLE;

public interface JavaType {
	String asSourceCodeString();
	ImmutableList<JavaType> decomposeToRawTypes();

	MetaType getMetaType();

	default boolean isReferenceType() {
		return getMetaType() == REFERENCE_TYPE;
	}

	default boolean isPrimitiveType() {
		return getMetaType() == PRIMITIVE_TYPE;
	}

	default boolean isTypeVariable() {
		return getMetaType() == TYPE_VARIABLE;
	}

	enum MetaType {
		REFERENCE_TYPE,
		PRIMITIVE_TYPE,
		TYPE_VARIABLE
	}
}
