package pl.marcinchwedczuk.cjava.util;

import pl.marcinchwedczuk.cjava.decompiler.typesystem.ArrayType;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.ClassType;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.typeargs.BoundedWildcardTypeArgument;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.typeargs.ConcreteTypeTypeArgument;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.typeargs.TypeArgument;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Comparator.comparing;
import static java.util.Comparator.reverseOrder;
import static java.util.stream.Collectors.toList;

public class Histogram<T> {
	private final Map<T,Integer> counts = new HashMap<>();

	public int getNumberOfOccurrences(T value) {
		return counts.getOrDefault(value, 0);
	}

	public void addOccurrence(T value) {
		counts.compute(value, (value_,count_) -> count_ == null ? 1 : count_+1);
	}

	public List<T> getValuesSortedByFrequency() {
		return counts.entrySet().stream()
				.sorted(comparing(Map.Entry::getValue, reverseOrder()))
				.map(Map.Entry::getKey)
				.collect(toList());
	}
}
