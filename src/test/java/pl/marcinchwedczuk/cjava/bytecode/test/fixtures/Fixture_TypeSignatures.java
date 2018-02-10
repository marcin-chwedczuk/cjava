package pl.marcinchwedczuk.cjava.bytecode.test.fixtures;

import java.util.List;
import java.util.Map;

public class Fixture_TypeSignatures<T, B extends Enum<B>> {
	private int justInt;
	private boolean justBool;
	private Boolean primitiveWrapper;

	private short[] array;
	private Short[] array2;

	private List<Short[]> listOfArrays;

	private Map<Integer,List<String>> mapOfLists;

	private Map<?, ? extends String> wildcards;
	private List<? extends Class<?>> wildcards2;

	private List<T> listOfT;
	private List<List<T>> listOfListOfT;

	private List<B> listOfB;

	public List<B> getListOfB() {
		return listOfB;
	}

	public void setListOfB(List<B> listOfB) {
		this.listOfB = listOfB;
	}

	public int getJustInt() {
		return justInt;
	}

	public void setJustInt(int justInt) {
		this.justInt = justInt;
	}

	public boolean isJustBool() {
		return justBool;
	}

	public void setJustBool(boolean justBool) {
		this.justBool = justBool;
	}

	public Boolean getPrimitiveWrapper() {
		return primitiveWrapper;
	}

	public void setPrimitiveWrapper(Boolean primitiveWrapper) {
		this.primitiveWrapper = primitiveWrapper;
	}

	public short[] getArray() {
		return array;
	}

	public void setArray(short[] array) {
		this.array = array;
	}

	public Short[] getArray2() {
		return array2;
	}

	public void setArray2(Short[] array2) {
		this.array2 = array2;
	}

	public List<Short[]> getListOfArrays() {
		return listOfArrays;
	}

	public void setListOfArrays(List<Short[]> listOfArrays) {
		this.listOfArrays = listOfArrays;
	}

	public Map<Integer, List<String>> getMapOfLists() {
		return mapOfLists;
	}

	public void setMapOfLists(Map<Integer, List<String>> mapOfLists) {
		this.mapOfLists = mapOfLists;
	}

	public Map<?, ? extends String> getWildcards() {
		return wildcards;
	}

	public void setWildcards(Map<?, ? extends String> wildcards) {
		this.wildcards = wildcards;
	}

	public List<? extends Class<?>> getWildcards2() {
		return wildcards2;
	}

	public void setWildcards2(List<? extends Class<?>> wildcards2) {
		this.wildcards2 = wildcards2;
	}

	public List<T> getListOfT() {
		return listOfT;
	}

	public void setListOfT(List<T> listOfT) {
		this.listOfT = listOfT;
	}

	public List<List<T>> getListOfListOfT() {
		return listOfListOfT;
	}

	public void setListOfListOfT(List<List<T>> listOfListOfT) {
		this.listOfListOfT = listOfListOfT;
	}
}
