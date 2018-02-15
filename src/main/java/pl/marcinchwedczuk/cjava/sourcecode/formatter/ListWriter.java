package pl.marcinchwedczuk.cjava.sourcecode.formatter;

import java.util.List;
import java.util.function.BiConsumer;

import static java.util.Objects.requireNonNull;

public class ListWriter<T> {
	private static final Runnable NO_ACTION = () -> { };

	public static <T> ListWriter<T> writeList(List<T> list) {
		return new ListWriter<>(list);
	}

	private Runnable beforeAction = NO_ACTION;
	private Runnable beforeNonEmpty = NO_ACTION;
	private Runnable afterAction = NO_ACTION;
	private BiConsumer<T, ElementPosition> elementAction = this::throwExceptionIfNotSet;
	private Runnable betweenAction = NO_ACTION;

	private final List<T> list;

	private ListWriter(List<T> list) {
		this.list = list;
	}

	private void throwExceptionIfNotSet(T element, ElementPosition position) {
		throw new IllegalStateException("You must set elementAction write method.");
	}

	public ListWriter<T> before(Runnable action) {
		beforeAction = requireNonNull(action);
		return this;
	}

	public ListWriter<T> beforeNonEmpty(Runnable action) {
		beforeNonEmpty = requireNonNull(action);
		return this;
	}

	public ListWriter<T> after(Runnable action) {
		afterAction = requireNonNull(action);
		return this;
	}

	public ListWriter<T> between(Runnable action) {
		betweenAction = requireNonNull(action);
		return this;
	}

	public ListWriter<T> element(BiConsumer<T, ElementPosition> action) {
		elementAction = requireNonNull(action);
		return this;
	}

	public final void write() {
		beforeAction.run();

		if (!list.isEmpty()) {
			beforeNonEmpty.run();
		}

		final int LAST_INDEX = list.size()-1;

		for (int i = 0; i < list.size(); i++) {
			//@formatter:off
			ElementPosition position =
					(i == LAST_INDEX) ? ElementPosition.LAST :
					(i == 0) ? ElementPosition.FIRST :
					ElementPosition.MIDDLE;
			//@formatter:on

			elementAction.accept(list.get(i), position);

			if (position != ElementPosition.LAST) {
				betweenAction.run();
			}
		}

		afterAction.run();
	}

	public enum ElementPosition { FIRST, LAST, MIDDLE }
}
