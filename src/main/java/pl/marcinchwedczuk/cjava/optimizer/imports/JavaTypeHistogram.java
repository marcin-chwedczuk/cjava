package pl.marcinchwedczuk.cjava.optimizer.imports;

import pl.marcinchwedczuk.cjava.decompiler.typesystem.ClassType;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;
import pl.marcinchwedczuk.cjava.util.Histogram;

import java.util.List;

public class JavaTypeHistogram {
	public static JavaTypeHistogram fromUsages(JavaType... types) {
		JavaTypeHistogram histogram =
				new JavaTypeHistogram();

		for (JavaType type : types) {
			histogram.addUsage(type);
		}

		return histogram;
	}

	private final Histogram<ClassType> histogram = new Histogram<>();

	public int getNumberOfUsages(ClassType type) {
		return histogram.getNumberOfOccurrences(type.toRawType());
	}

	public JavaTypeHistogram addUsage(JavaType type) {
		type.decomposeToRawTypes().stream()
				.filter(ClassType.class::isInstance)
				.map(ClassType.class::cast)
				.forEach(histogram::addOccurrence);

		return this;
	}

	public List<ClassType> getTypesSortedByFrequency() {
		return histogram.getValuesSortedByFrequency();
	}
}
