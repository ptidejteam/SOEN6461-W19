
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

class BubbleSort<E extends Comparable<E>> extends AbstractSort<E>
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

interface ISort<E> {
	List<E> sort(final List<E> aList);
}

interface ISortObserver<E extends Comparable<E>> {
	void valuesCompared(final ComparisonEvent<E> comparisonEvent);
	void valuesSwapped(final SwapEvent<E> swapEvent);
}

class SimpleObserver<E extends Comparable<E>> implements ISortObserver<E> {

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
