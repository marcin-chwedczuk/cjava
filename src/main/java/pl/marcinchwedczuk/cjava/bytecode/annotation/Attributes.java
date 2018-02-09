package pl.marcinchwedczuk.cjava.bytecode.annotation;

import java.util.List;

public class Attributes {
	private List<Attribute> attributes;

	public Attributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

	public int getCount() { return attributes.size(); }

	public List<Attribute> getAttributes() {
		return attributes;
	}
}
