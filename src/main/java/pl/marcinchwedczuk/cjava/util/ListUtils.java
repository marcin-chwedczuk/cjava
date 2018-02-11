package pl.marcinchwedczuk.cjava.util;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

public class ListUtils {
	private ListUtils() { }

	public static <T> List<T> withoutLastElement(List<T> source) {
		if (source.size() <= 1) {
			return Collections.emptyList();
		}

		return source.subList(0, source.size()-1);
	}

	public static <T> T lastElement(List<T> source) {
		return source.get(source.size()-1);
	}

	public static <T> List<T> readOnlyCopy(List<T> source) {
		return Collections.unmodifiableList(Lists.newArrayList(source));
	}
}
