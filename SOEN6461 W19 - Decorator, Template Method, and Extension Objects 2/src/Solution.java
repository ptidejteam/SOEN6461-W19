
/*******************************************************************************
 * Copyright (c) 2014 Yann-Gaël Guéhéneuc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc - Initial API and implementation for CSE3009 W14
 ******************************************************************************/

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Solution {
	public static void main(final String[] args) {
		final List<String> l = Arrays.asList(
			new String[] { "Rick Deckard", "Roy Batty", "Harry Bryant",
					"Hannibal Chew", "Gaff", "Holden", "Leon Kowalski",
					"Taffey Lewis", "Pris", "Rachael", "J.F. Sebastian",
					"Dr. Eldon Tyrell", "Zhora", "Hodge", "Mary" });
		final CountingObserver<String> observer =
			new CountingObserver<String>();

		final ISort<String> t1 =
			Factory.getInstance().getInternalSortAlgorithms();
		t1.addObserver(observer);
		final ISort<String> d1 = new NamingDecorator(t1);
		d1.addObserver(observer);
		System.out.println(d1.sort(l));

		@SuppressWarnings({ "unchecked", "rawtypes" })
		final ITypeOfSort<String> t2 =
			(ITypeOfSort) Factory.getInstance().getInternalSortAlgorithms();
		t2.addExtension(
			"Statistics",
			(Class<? extends ISortExtension>) CountingExtension.class);
		t2.sort(l);
		final ISortIterator<String> iterator = t2.getSortAlgorithms();
		while (iterator.hasNext()) {
			final ISort<String> sort = iterator.getNext();
			final CountingExtension countingExtension =
				(CountingExtension) sort.getExtension("Statistics");
			System.out.println(countingExtension.getCounts());
		}
	}
}

abstract class AbstractSort<E extends Comparable<E>> {
	private final List<ISortObserver<E>> listOfObservers;
	private final Map<String, ISortExtension> mapOfExtensionInstances;

	public AbstractSort() {
		this.listOfObservers = new ArrayList<ISortObserver<E>>();
		this.mapOfExtensionInstances = new HashMap<String, ISortExtension>();
	}
	public void addExtension(
		final String anExtensionName,
		final Class<? extends ISortExtension> anExtensionClass) {

		try {
			final ISortExtension extension = anExtensionClass.getDeclaredConstructor().newInstance();
			final Method dependencyInjector =
				anExtensionClass.getMethod("setExtendedSort", ISort.class);
			dependencyInjector.invoke(extension, this);
			this.mapOfExtensionInstances.put(anExtensionName, extension);
		}
		catch (NoSuchMethodException | SecurityException
				| InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {

			e.printStackTrace();
		}
	}
	public void addObserver(final ISortObserver<E> anObserver) {
		// No need to add twice the same observer.
		if (!this.listOfObservers.contains(anObserver)) {
			this.listOfObservers.add(anObserver);
		}
	}
	protected final void assignValue(
		final E[] array,
		final int position,
		final E value) {

		this.notifyObserversOfASwap(array, array[position], value);
		array[position] = value;
	}
	protected final int compareValues(
		final E[] array,
		final E value1,
		final E value2) {
		this.notifyObserversOfAComparison(array, value1, value2);
		return value1.compareTo(value2);
	}
	protected final int compareValuesInArray(
		final E[] array,
		final int position1,
		final int position2) {

		return this.compareValues(array, array[position1], array[position2]);
	}
	protected final E[] convertListToArray(final List<E> aList) {
		// See http://stackoverflow.com/a/6522958
		// See http://stackoverflow.com/a/3617972
		@SuppressWarnings("unchecked")
		final E[] array = (E[]) java.lang.reflect.Array
			.newInstance(aList.get(0).getClass(), aList.size());
		for (int i = 0; i < aList.size(); i++) {
			array[i] = aList.get(i);
		}
		return array;
	}
	public final ISortExtension getExtension(final String anExtensionName) {
		return this.mapOfExtensionInstances.get(anExtensionName);
	}
	private final void notifyObserversOfAComparison(
		final E[] values,
		final E value1,
		final E value2) {

		final ComparisonEvent<E> event =
			new ComparisonEvent<E>(values, value1, value2);

		final Iterator<ISortObserver<E>> iterator =
			this.listOfObservers.iterator();
		while (iterator.hasNext()) {
			final ISortObserver<E> sortObserver =
				(ISortObserver<E>) iterator.next();
			sortObserver.valuesCompared(event);
		}
	}
	private final void notifyObserversOfASwap(
		final E[] values,
		final E value1,
		final E value2) {

		final SwapEvent<E> event = new SwapEvent<E>(values, value1, value2);

		final Iterator<ISortObserver<E>> iterator =
			this.listOfObservers.iterator();
		while (iterator.hasNext()) {
			final ISortObserver<E> sortObserver =
				(ISortObserver<E>) iterator.next();
			sortObserver.valuesSwapped(event);
		}
	}
	public final void removeExtension(final String anExtensionName) {
		this.mapOfExtensionInstances.remove(anExtensionName);
	}
	protected final void swapValuesInArray(
		final E[] array,
		final int i,
		final int j) {
		this.notifyObserversOfASwap(array, array[i], array[j]);

		final E temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
}

class BubbleSort<E extends Comparable<E>> extends InternalSort<E>
		implements ISort<E> {

	public List<E> sort(final List<E> aList) {
		final E[] array = this.convertListToArray(aList);
		final int arraySize = array.length;

		for (int i = 0; i < arraySize - 1; i++) {
			for (int j = 0; j < arraySize - 1; j++) {
				if (this.compareValuesInArray(array, j, j + 1) > 0) {
					this.swapValuesInArray(array, j, j + 1);
				}
			}
		}

		return Arrays.asList(array);
	}
	public String getName() {
		return "BubbleSort";
	}
}

final class ComparisonEvent<E extends Comparable<E>> {
	private final E[] values;
	private final E value1;
	private final E value2;

	public ComparisonEvent(final E[] values, final E value1, final E value2) {
		this.values = values;
		this.value1 = value1;
		this.value2 = value2;
	}
	public E[] getValues() {
		return this.values;
	}
	public E getValue1() {
		return this.value1;
	}
	public E getValue2() {
		return this.value2;
	}
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Comparison of ");
		builder.append(this.value1);
		builder.append(" with ");
		builder.append(this.value2);
		return builder.toString();
	}
}

class ConcreteSortIterator<E extends Comparable<E>>
		implements ISortIterator<E> {

	private final List<ISort<E>> privateCopyOfList;
	private int cursor;

	public ConcreteSortIterator(final List<ISort<E>> aListOfItems) {
		this.privateCopyOfList = aListOfItems;
	}

	public boolean hasNext() {
		return this.cursor <= this.privateCopyOfList.size() - 1;
	}

	public ISort<E> getNext() {
		final ISort<E> currentSortAlgorithm =
			this.privateCopyOfList.get(this.cursor);
		this.cursor++;
		return currentSortAlgorithm;
	}
}

class CountingExtension implements ISortExtension {
	// I cannot add type information here because
	// - only in method setExtendedSort() could we
	//   know the parameterised type of ISort but
	// - it is erased and therefore, even with a cast
	//   the compiler cannot relate the types.
	@SuppressWarnings("rawtypes")
	private ISort extendedSort;
	private CountingObserver<?> countingObserver;

	@SuppressWarnings("unchecked")
	@Override
	public <E extends Comparable<E>> void setExtendedSort(
		final ISort<E> anExtendedSort) {

		this.extendedSort = anExtendedSort;
		this.countingObserver = new CountingObserver<E>();
		this.extendedSort.addObserver(this.countingObserver);
	}
	public String getCounts() {
		final StringBuilder builder = new StringBuilder();
		builder.append(this.extendedSort.getClass().getSimpleName());
		builder.append("\tComparisons: ");
		builder.append(this.countingObserver.getNumberOfComparisons());
		builder.append("\n\t\tSwaps      : ");
		builder.append(this.countingObserver.getNumberOfSwaps());
		return builder.toString();
	}
}

class CountingObserver<E extends Comparable<E>> implements ISortObserver<E> {

	private int numberOfComparisons;
	private int numberOfSwaps;

	@Override
	public void valuesCompared(final ComparisonEvent<E> comparisonEvent) {
		this.numberOfComparisons++;
	}
	@Override
	public void valuesSwapped(final SwapEvent<E> swapEvent) {
		this.numberOfSwaps++;
	}

	public int getNumberOfComparisons() {
		return this.numberOfComparisons;
	}
	public int getNumberOfSwaps() {
		return this.numberOfSwaps;
	}
}

class EncryptAfterSortingDecorator extends SortDecorator<String> {
	public EncryptAfterSortingDecorator(final ISort<String> aSortAlgorithm) {
		super(aSortAlgorithm);
	}

	@Override
	protected List<String> postProcessOutput(final List<String> sortedList) {
		final List<String> newList = new ArrayList<String>();
		final Iterator<String> iterator = sortedList.iterator();
		while (iterator.hasNext()) {
			final String s = iterator.next();
			newList.add(String.valueOf(s.hashCode()));
		}
		return newList;
	}
}

abstract class ExternalSort<E extends Comparable<E>> extends AbstractSort<E> {
	protected void someOtherHelperMethod() {
		// Some implementation...
	}
}

class Factory {
	private static class FactoryUniqueInstanceHolder {
		private static final Factory THE_UNIQUE_FACTORY = new Factory();
	}
	public static Factory getInstance() {
		return FactoryUniqueInstanceHolder.THE_UNIQUE_FACTORY;
	}

	private Factory() {
		// Some implementation if needed...
		// This constructor could take in parameters
		// then getInstance() should have parameters too.
	}

	public <E extends Comparable<E>> ISort<E> getBubbleSortAlgorithm() {
		return new BubbleSort<E>();
	}
	public <E extends Comparable<E>> ISort<E> getInsertionSortAlgorithm() {
		return new BubbleSort<E>(); // Wrong implementation for the sake of simplicity
	}
	public <E extends Comparable<E>> ISort<E> getMergeSortAlgorithm() {
		return new BubbleSort<E>(); // Wrong implementation for the sake of simplicity
	}
	public <E extends Comparable<E>> ISort<E> getPSortAlgorithm() {
		return new BubbleSort<E>(); // Wrong implementation for the sake of simplicity
	}
	public <E extends Comparable<E>> ISort<E> getQuickSortAlgorithm() {
		return new BubbleSort<E>(); // Wrong implementation for the sake of simplicity
	}
	public <E extends Comparable<E>> ISort<E> getSelectionSortAlgorithm() {
		return new BubbleSort<E>(); // Wrong implementation for the sake of simplicity
	}
	public <E extends Comparable<E>> ISort<E> getTritonSortAlgorithm() {
		return new BubbleSort<E>(); // Wrong implementation for the sake of simplicity
	}

	public <E extends Comparable<E>> ISort<E> getExternalSortAlgorithms() {
		final TypeOfSort<E> externalSorts = new TypeOfSort<E>("External Sorts");

		final ISort<E> pSortAlgorithm = this.getPSortAlgorithm();
		externalSorts.addSortAlgorithm(pSortAlgorithm);

		final ISort<E> tritonSortAlgorithm = this.getTritonSortAlgorithm();
		externalSorts.addSortAlgorithm(tritonSortAlgorithm);

		return externalSorts;
	}
	public <E extends Comparable<E>> ISort<E> getInternalSortAlgorithms() {
		final TypeOfSort<E> internalSorts = new TypeOfSort<E>("Internal Sorts");

		final ISort<E> bubbleSortAlgorithm = this.getBubbleSortAlgorithm();
		internalSorts.addSortAlgorithm(bubbleSortAlgorithm);

		final ISort<E> insertionSortAlgorithm =
			this.getInsertionSortAlgorithm();
		internalSorts.addSortAlgorithm(insertionSortAlgorithm);

		final ISort<E> mergeSortAlgorithm = this.getMergeSortAlgorithm();
		internalSorts.addSortAlgorithm(mergeSortAlgorithm);

		final ISort<E> quickSortAlgorithm = this.getQuickSortAlgorithm();
		internalSorts.addSortAlgorithm(quickSortAlgorithm);

		final ISort<E> selectionSortAlgorithm =
			this.getSelectionSortAlgorithm();
		internalSorts.addSortAlgorithm(selectionSortAlgorithm);

		return internalSorts;
	}
}

abstract class InternalSort<E extends Comparable<E>> extends AbstractSort<E> {
	protected void someOtherHelperMethod() {
		// Some implementation...
	}
}

interface ISort<E extends Comparable<E>> {
	List<E> sort(final List<E> aList);
	void addObserver(final ISortObserver<E> anObserver);
	void addExtension(
		final String anExtensionName,
		final Class<? extends ISortExtension> anExtensionClass);
	ISortExtension getExtension(final String anExtensionName);
	void removeExtension(final String anExtensionName);
}

interface ISortExtension {
	<E extends Comparable<E>> void setExtendedSort(
		final ISort<E> anExtendedSort);
}

interface ISortIterator<E extends Comparable<E>> {
	boolean hasNext();
	ISort<E> getNext();
}

interface ISortObserver<E extends Comparable<E>> {
	void valuesCompared(final ComparisonEvent<E> comparisonEvent);
	void valuesSwapped(final SwapEvent<E> swapEvent);
}

interface ITypeOfSort<E extends Comparable<E>> extends ISort<E> {
	ISortIterator<E> getSortAlgorithms();
	String getTypeName();
	List<E> sort(final List<E> aList);
}

class NamingDecorator extends SortDecorator<String> {
	private final String name;

	public NamingDecorator(final ISort<String> aSortAlgorithm) {
		super(aSortAlgorithm);
		this.name = aSortAlgorithm.getClass().getSimpleName();
	}

	@Override
	protected List<String> preProcessInput(final List<String> aList) {
		System.out.println(this.name);
		return aList;
	}
}

abstract class SortDecorator<E extends Comparable<E>> extends AbstractSort<E>
		implements ISort<E> {

	private final ISort<E> decoratedSortAlgorithm;

	public SortDecorator(final ISort<E> aSortAlgorithm) {
		this.decoratedSortAlgorithm = aSortAlgorithm;
	}
	@Override
	public final void addExtension(
		final String anExtensionName,
		final Class<? extends ISortExtension> anExtensionClass) {

		this.decoratedSortAlgorithm
			.addExtension(anExtensionName, anExtensionClass);
	}
	@Override
	public final void addObserver(final ISortObserver<E> anObserver) {
		this.decoratedSortAlgorithm.addObserver(anObserver);
	}
	protected List<E> postProcessOutput(final List<E> sortedList) {
		return sortedList;
	}
	protected List<E> preProcessInput(final List<E> aList) {
		return aList;
	}
	@Override
	public final List<E> sort(final List<E> aList) {
		final List<E> preList = this.preProcessInput(aList);
		final List<E> sortedList = this.decoratedSortAlgorithm.sort(preList);
		final List<E> postList = this.postProcessOutput(sortedList);

		return postList;
	}
}

final class SwapEvent<E extends Comparable<E>> {
	private final E[] values;
	private final E value1;
	private final E value2;

	public SwapEvent(final E[] values, final E value1, final E value2) {
		this.values = values;
		this.value1 = value1;
		this.value2 = value2;
	}
	public E[] getValues() {
		return this.values;
	}
	public E getValue1() {
		return this.value1;
	}
	public E getValue2() {
		return this.value2;
	}
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Swap of ");
		builder.append(this.value1);
		builder.append(" with ");
		builder.append(this.value2);
		return builder.toString();
	}
}

class ToLowerCaseDecorator extends SortDecorator<String> {
	public ToLowerCaseDecorator(final ISort<String> aSortAlgorithm) {
		super(aSortAlgorithm);
	}

	@Override
	protected List<String> preProcessInput(final List<String> aList) {
		final List<String> newList = new ArrayList<String>();
		final Iterator<String> iterator = aList.iterator();
		while (iterator.hasNext()) {
			final String s = iterator.next();
			newList.add(s.toLowerCase());
		}
		return newList;
	}
}

class TypeOfSort<E extends Comparable<E>> extends AbstractSort<E>
		implements ITypeOfSort<E> {

	private final List<ISort<E>> listOfSortAlgorithms;
	private final String typeName;

	public TypeOfSort(final String aTypeName) {
		this.listOfSortAlgorithms = new ArrayList<ISort<E>>();
		this.typeName = aTypeName;
	}
	@Override
	public final void addExtension(
		final String anExtensionName,
		final Class<? extends ISortExtension> anExtensionClass) {

		final Iterator<ISort<E>> iterator =
			this.listOfSortAlgorithms.iterator();
		while (iterator.hasNext()) {
			final ISort<E> sortAlgorithm = (ISort<E>) iterator.next();
			sortAlgorithm.addExtension(anExtensionName, anExtensionClass);
		}
	}
	@Override
	public void addObserver(final ISortObserver<E> anObserver) {
		final Iterator<ISort<E>> iterator =
			this.listOfSortAlgorithms.iterator();
		while (iterator.hasNext()) {
			final ISort<E> sortAlgorithm = (ISort<E>) iterator.next();
			sortAlgorithm.addObserver(anObserver);
		}
	}
	public void addSortAlgorithm(final ISort<E> aSortAlgorithm) {
		this.listOfSortAlgorithms.add(aSortAlgorithm);
	}
	public ISortIterator<E> getSortAlgorithms() {
		return new ConcreteSortIterator<E>(this.listOfSortAlgorithms);
	}
	public String getTypeName() {
		return this.typeName;
	}
	public List<E> sort(final List<E> aList) {
		// Call each sort algorithm of this type one after the other...
		final Iterator<ISort<E>> iterator =
			this.listOfSortAlgorithms.iterator();
		List<E> sortedList = null;
		while (iterator.hasNext()) {
			final ISort<E> sortAlgorithm = (ISort<E>) iterator.next();
			sortedList = sortAlgorithm.sort(aList);
		}
		return sortedList;
	}
}
