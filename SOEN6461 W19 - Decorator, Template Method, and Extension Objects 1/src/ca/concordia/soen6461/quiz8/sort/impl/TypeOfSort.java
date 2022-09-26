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
package ca.concordia.soen6461.quiz8.sort.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import ca.concordia.soen6461.quiz8.sort.ISort;
import ca.concordia.soen6461.quiz8.sort.ISortExtension;
import ca.concordia.soen6461.quiz8.sort.ISortIterator;
import ca.concordia.soen6461.quiz8.sort.ITypeOfSort;
import ca.concordia.soen6461.quiz8.sort.iterator.ConcreteSortIterator;
import ca.concordia.soen6461.quiz8.sort.observer.ISortObserver;

class TypeOfSort<E extends Comparable<E>> extends AbstractSort<E> implements
		ITypeOfSort<E> {

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
