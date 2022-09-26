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
package ca.concordia.soen6461.quiz8;

import ca.concordia.soen6461.quiz8.sort.observer.ComparisonEvent;
import ca.concordia.soen6461.quiz8.sort.observer.ISortObserver;
import ca.concordia.soen6461.quiz8.sort.observer.SwapEvent;

public class CountingObserver<E extends Comparable<E>> implements
		ISortObserver<E> {

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
