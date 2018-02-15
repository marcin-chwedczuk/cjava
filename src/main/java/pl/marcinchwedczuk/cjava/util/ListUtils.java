package pl.marcinchwedczuk.cjava.util;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;

public class ListUtils {
	private ListUtils() { }


	public static <T> List<T> withoutLastElement(List<T> source) {
		if (source.size() <= 1) {
			return Collections.emptyList();
		}

		return source.subList(0, source.size()-1);
	}

	public static <T> List<T> withoutLastElement(T[] source) {
		return withoutLastElement(asList(source));
	}

	public static <T> List<T> withoutFirstElement(List<T> source) {
		if (source.size() <= 1) {
			return Collections.emptyList();
		}

		return source.subList(1, source.size());
	}

	public static <T> List<T> withoutFirstElement(T[] source) {
		return withoutFirstElement(asList(source));
	}

	public static <T> T lastElement(List<T> source) {
		return source.get(source.size()-1);
	}

	public static <T> T lastElement(T[] source) {
		return lastElement(asList(source));
	}

	public static <T> T firstElement(List<T> source) {
		return source.get(0);
	}

	public static <T> T firstElement(T[] source) {
		return firstElement(asList(source));
	}

	public static <T> List<T> readOnlyCopy(List<T> source) {
		return Collections.unmodifiableList(Lists.newArrayList(source));
	}
}
