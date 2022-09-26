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

import ca.concordia.soen6461.quiz8.sort.ISort;
import ca.concordia.soen6461.quiz8.sort.ISortExtension;

public class CountingExtension implements ISortExtension {
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
