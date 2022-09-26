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

import ca.concordia.soen6461.quiz8.sort.ISort;

public class Factory {
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
		return new InsertionSort<E>();
	}
	public <E extends Comparable<E>> ISort<E> getMergeSortAlgorithm() {
		return new MergeSort<E>();
	}
	public <E extends Comparable<E>> ISort<E> getPSortAlgorithm() {
		return new pSort<E>();
	}
	public <E extends Comparable<E>> ISort<E> getQuickSortAlgorithm() {
		return new QuickSort<E>();
	}
	public <E extends Comparable<E>> ISort<E> getSelectionSortAlgorithm() {
		return new SelectionSort<E>();
	}
	public <E extends Comparable<E>> ISort<E> getTritonSortAlgorithm() {
		return new TritonSort<E>();
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
