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
package ca.concordia.soen6461.sort.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import ca.concordia.soen6461.sort.observer.ComparisonEvent;
import ca.concordia.soen6461.sort.observer.ISortObserver;
import ca.concordia.soen6461.sort.observer.SwapEvent;

abstract class AbstractSort<E extends Comparable<E>> {
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
		final E[] array =
			(E[]) java.lang.reflect.Array.newInstance(
				aList.get(0).getClass(),
				aList.size());
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
