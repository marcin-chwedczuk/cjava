package pl.marcinchwedczuk.cjava.util;

import com.google.common.collect.ImmutableSet;

import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public class ImmutableUtils {
	public static <E> ImmutableSet<E> replaceElement(ImmutableSet<E> source, E original, E replacement) {
		return ImmutableSet.<E>builder()
				.addAll(
					source.stream()
						.filter(element -> !element.equals(original))
						.collect(toSet()))
				.add(replacement)
				.build();
	}

	public static <E> ImmutableSet<E> remove(ImmutableSet<E> source, E elementToRemove) {
		return ImmutableSet.<E>builder()
				.addAll(
					source.stream()
							.filter(element -> !element.equals(elementToRemove))
							.collect(toSet()))
				.build();
	}
}
