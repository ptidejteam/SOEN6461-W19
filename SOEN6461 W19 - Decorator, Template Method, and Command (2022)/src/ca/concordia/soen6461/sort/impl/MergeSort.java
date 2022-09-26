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

import java.util.Arrays;
import java.util.List;
import ca.concordia.soen6461.sort.ISort;

class MergeSort<E extends Comparable<E>> extends InternalSort<E> implements
		ISort<E> {

	private void mergeSort(final E[] array) {
		final int arraySize = array.length;
		if (arraySize > 1) {
			final int q = arraySize / 2;

			final E[] leftArray = Arrays.copyOfRange(array, 0, q);
			final E[] rightArray = Arrays.copyOfRange(array, q, array.length);

			this.mergeSort(leftArray);
			this.mergeSort(rightArray);

			this.merge(array, leftArray, rightArray);
		}
	}
	private void merge(
		final E[] array,
		final E[] leftArray,
		final E[] rightArray) {

		final int size = leftArray.length + rightArray.length;
		int i = 0;
		int li = 0;
		int ri = 0;

		while (i < size) {
			if ((li < leftArray.length) && (ri < rightArray.length)) {
				if (this.compareValues(array, leftArray[li], rightArray[ri]) < 0) {
					this.assignValue(array,i, leftArray[li]);
					i++;
					li++;
				}
				else {
					this.assignValue(array,i, rightArray[ri]);
					i++;
					ri++;
				}
			}
			else {
				if (li >= leftArray.length) {
					while (ri < rightArray.length) {
						this.assignValue(array,i, rightArray[ri]);
						i++;
						ri++;
					}
				}
				if (ri >= rightArray.length) {
					while (li < leftArray.length) {
						this.assignValue(array,i, leftArray[li]);
						li++;
						i++;
					}
				}
			}
		}
	}
	public String getName() {
		return "MergeSort";
	}
	public List<E> sort(final List<E> aList) {
		final E[] array = this.convertListToArray(aList);
		this.mergeSort(array);
		return Arrays.asList(array);
	}
}
