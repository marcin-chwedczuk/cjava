package pl.marcinchwedczuk.cjava.bytecode.attribute;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

public class Attributes {
	private List<Attribute> attributes;

	public Attributes(List<Attribute> attributes) {
		this.attributes = Collections.unmodifiableList(Lists.newArrayList(attributes));
	}

	public int getCount() { return attributes.size(); }

	public Attribute get(int index) {
		return attributes.get(index);
	}
}
