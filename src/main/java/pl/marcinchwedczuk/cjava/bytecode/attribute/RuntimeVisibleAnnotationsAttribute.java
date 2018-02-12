package pl.marcinchwedczuk.cjava.bytecode.attribute;

import pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPoolIndex;

import java.util.List;
import java.util.Objects;

import static java.util.Objects.requireNonNull;
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
		private final ConstantPoolIndex type;

		public Annotation(ConstantPoolIndex type) {
			this.type = requireNonNull(type);
		}

		public ConstantPoolIndex getType() {
			return type;
		}
	}
}
