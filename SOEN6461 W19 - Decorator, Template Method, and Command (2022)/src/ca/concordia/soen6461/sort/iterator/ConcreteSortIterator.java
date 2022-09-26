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
package ca.concordia.soen6461.sort.iterator;

import java.util.List;
import ca.concordia.soen6461.sort.ISort;
import ca.concordia.soen6461.sort.ISortIterator;

public class ConcreteSortIterator<E extends Comparable<E>> implements
		ISortIterator<E> {

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
