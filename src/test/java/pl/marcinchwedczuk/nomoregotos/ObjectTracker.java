package pl.marcinchwedczuk.nomoregotos;

import java.util.IdentityHashMap;
import java.util.Set;

public class ObjectTracker<T> {
	private final IdentityHashMap<T,Object> identityHashMap = new IdentityHashMap<>();

	public boolean isTracked(T instance) {
		return identityHashMap.containsKey(instance);
	}

	public void track(T instance) {
		identityHashMap.put(instance, null);
	}
}
