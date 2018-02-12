package pl.marcinchwedczuk.cjava.bytecode.attribute;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Attributes {
	private List<Attribute> attributes;

	public Attributes(List<Attribute> attributes) {
		this.attributes = Collections.unmodifiableList(Lists.newArrayList(attributes));
	}

	public int getCount() { return attributes.size(); }

	public Attribute get(int index) {
		return attributes.get(index);
	}

	public Optional<SignatureAttribute> findSignatureAttribute() {
		return findOne(SignatureAttribute.class);
	}

	public Optional<RuntimeVisibleAnnotationsAttribute> findRuntimeVisibleAnnotationsAttribute() {
		return findOne(RuntimeVisibleAnnotationsAttribute.class);
	}

	private <T extends Attribute> Optional<T> findOne(Class<T> attributeClass) {
		return attributes.stream()
				.filter(attributeClass::isInstance)
				.map(attributeClass::cast)
				.findFirst();
	}
}
