package pl.marcinchwedczuk.cjava.bytecode.attribute;

import com.google.common.base.Preconditions;

public enum AttributeType {
	SIGNATURE("Signature"),
	SYNTHETIC("Synthetic"),
	RUNTIME_VISIBLE_ANNOTATIONS("RuntimeVisibleAnnotations"),
	CODE("Code"),

	UNKNOWN(null);

	private final String attributeName;

	AttributeType(String attributeName) {
		this.attributeName = attributeName;
	}

	private boolean hasName(String name) {
		if (attributeName == null)
			return false;

		return attributeName.equals(name);
	}

	public static AttributeType fromAttributeName(String attributeName) {
		Preconditions.checkNotNull(attributeName);

		for (AttributeType attributeType : values()) {
			if (attributeType.hasName(attributeName)) {
				return attributeType;
			}
		}

		return UNKNOWN;
	}
}
