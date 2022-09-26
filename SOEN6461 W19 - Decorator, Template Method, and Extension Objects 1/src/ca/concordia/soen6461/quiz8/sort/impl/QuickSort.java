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

import java.util.Arrays;
import java.util.List;
import ca.concordia.soen6461.quiz8.sort.ISort;

class QuickSort<E extends Comparable<E>> extends InternalSort<E> implements
		ISort<E> {

	public String getName() {
		return "QuickSort";
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
	public void quickSort(final E[] a, final int p, final int r) {
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
