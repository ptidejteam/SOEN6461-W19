
/*******************************************************************************
 * Copyright (c) 2014-2019 Yann-Gaël Guéhéneuc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc - Initial API and implementation for SOEN6461
 ******************************************************************************/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

class AbstractSort<E extends Comparable<E>> {
	private final List<ISortObserver<E>> listOfObservers;

	public AbstractSort() {
		this.listOfObservers = new ArrayList<ISortObserver<E>>();
	}
	public void addObserver(final ISortObserver<E> anObserver) {
		// No need to add twice the same observer.
		if (!this.listOfObservers.contains(anObserver)) {
			this.listOfObservers.add(anObserver);
		}
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

		final E value1 = array[position1];
		final E value2 = array[position2];
		this.notifyObserversOfAComparison(array, value1, value2);
		return value1.compareTo(value2);
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

final class BubbleSort<E extends Comparable<E>> extends AbstractSort<E>
		implements ISort<E> {

	public BubbleSort() {
		super();
	}

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
}

final class ComparisonEvent<E extends Comparable<E>> {
	private final E value1;
	private final E value2;
	private final E[] values;

	public ComparisonEvent(final E[] values, final E value1, final E value2) {
		this.values = values;
		this.value1 = value1;
		this.value2 = value2;
	}
	public E getValue1() {
		return this.value1;
	}
	public E getValue2() {
		return this.value2;
	}
	public E[] getValues() {
		return this.values;
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

	private int cursor;
	private final List<ISort<E>> privateCopyOfList;

	public ConcreteSortIterator(final List<ISort<E>> aListOfItems) {
		this.privateCopyOfList = new ArrayList<>(aListOfItems);
	}

	public ISort<E> getNext() {
		final ISort<E> currentSortAlgorithm =
			this.privateCopyOfList.get(this.cursor);
		this.cursor++;
		return currentSortAlgorithm;
	}

	public boolean hasNext() {
		return this.cursor <= this.privateCopyOfList.size() - 1;
	}
}

final class InsertionSort<E extends Comparable<E>> extends AbstractSort<E>
		implements ISort<E> {

	public InsertionSort() {
		super();
	}

	public List<E> sort(final List<E> aList) {
		final E[] array = this.convertListToArray(aList);
		final int arraySize = array.length;

		int j;
		for (int i = 1; i < arraySize; i++) {
			j = i;
			while (j > 0 && this.compareValuesInArray(array, j - 1, j) > 0) {
				this.swapValuesInArray(array, j - 1, j);
				j--;
			}
		}

		return Arrays.asList(array);
	}
}

class InternalSorts<E extends Comparable<E>> extends TypeOfSort<E>
		implements ITypeOfSort<E> {

	public InternalSorts() {
		super("Internal Sorts");

		this.addSortAlgorithm(new InsertionSort<E>());
		this.addSortAlgorithm(new QuickSort<E>());
		this.addSortAlgorithm(new BubbleSort<E>());
	}
}

interface ISort<E extends Comparable<E>> {
	void addObserver(final ISortObserver<E> anObserver);
	List<E> sort(final List<E> aList);
}

interface ISortIterator<E extends Comparable<E>> {
	ISort<E> getNext();
	boolean hasNext();
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

final class QuickSort<E extends Comparable<E>> extends AbstractSort<E>
		implements ISort<E> {

	public QuickSort() {
		super();
	}

	private int partition(final E[] array, int p, int r) {
		final E x = array[p];
		int i = p - 1;
		int j = r + 1;

		while (true) {
			i++;
			while (i < r && this.compareValues(array, array[i], x) < 0) {
				i++;
			}
			j--;
			while (j > p && this.compareValues(array, array[j], x) > 0) {
				j--;
			}

			if (i < j) {
				this.swapValuesInArray(array, i, j);
			}
			else {
				return j;
			}
		}
	}
	private void quickSort(final E[] a, final int p, final int r) {
		if (p < r) {
			final int q = this.partition(a, p, r);
			this.quickSort(a, p, q);
			this.quickSort(a, q + 1, r);
		}
	}
	public List<E> sort(final List<E> aList) {
		final E[] array = this.convertListToArray(aList);
		this.quickSort(array, 0, array.length - 1);
		return Arrays.asList(array);
	}
}

final class SimpleObserver<E extends Comparable<E>>
		implements ISortObserver<E> {
	@Override
	public void valuesCompared(final ComparisonEvent<E> comparisonEvent) {
		System.out.println(comparisonEvent);
	}
	@Override
	public void valuesSwapped(final SwapEvent<E> swapEvent) {
		System.out.println(swapEvent);
	}
}

public class Solution {
	public static void main(final String args[]) throws Exception {
		final BubbleSort<String> sort = new BubbleSort<String>();
		sort.addObserver(new SimpleObserver<String>());
		System.out.println(
			sort.sort(
				Arrays.asList(new String[] { "Venus", "Earth", "Mars" })));

		final InternalSorts<String> internalSorts = new InternalSorts<String>();
		internalSorts.addObserver(new SimpleObserver<String>());
		System.out.println(
			internalSorts.sort(
				Arrays.asList(new String[] { "Venus", "Earth", "Mars" })));
	}
}

final class SwapEvent<E extends Comparable<E>> {
	private final E value1;
	private final E value2;
	private final E[] values;

	public SwapEvent(final E[] values, final E value1, final E value2) {
		this.values = values;
		this.value1 = value1;
		this.value2 = value2;
	}
	public E getValue1() {
		return this.value1;
	}
	public E getValue2() {
		return this.value2;
	}
	public E[] getValues() {
		return this.values;
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

class TypeOfSort<E extends Comparable<E>> extends AbstractSort<E> {
	private final List<ISort<E>> listOfSortAlgorithms;
	private final String typeName;

	public TypeOfSort(final String aTypeName) {
		this.listOfSortAlgorithms = new ArrayList<ISort<E>>();
		this.typeName = aTypeName;
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