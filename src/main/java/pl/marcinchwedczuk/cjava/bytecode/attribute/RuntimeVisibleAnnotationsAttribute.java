package pl.marcinchwedczuk.cjava.bytecode.attribute;

import java.util.List;

import static pl.marcinchwedczuk.cjava.util.ListUtils.readOnlyCopy;

public class RuntimeVisibleAnnotationsAttribute extends Attribute {
	private final List<Annotation> annotations;

	public RuntimeVisibleAnnotationsAttribute(List<Annotation> annotations) {
		super(AttributeType.RUNTIME_VISIBLE_ANNOTATIONS);

		this.annotations = readOnlyCopy(annotations);
	}

	public List<Annotation> getAnnotations() {
		return annotations;
	}

	public static class Annotation {

	}
}
