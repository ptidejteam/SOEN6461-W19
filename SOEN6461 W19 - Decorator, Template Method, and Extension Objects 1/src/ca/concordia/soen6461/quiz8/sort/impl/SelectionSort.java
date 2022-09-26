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

class SelectionSort<E extends Comparable<E>> extends InternalSort<E> implements
		ISort<E> {

	public List<E> sort(final List<E> aList) {
		final E[] array = this.convertListToArray(aList);
		final int arraySize = array.length;

		for (int i = 0; i < array.length; i++) {
			int min = i;
			for (int j = i + 1; j < arraySize; j++) {
				if (this.compareValuesInArray(array, j, min) < 0) {
					min = j;
				}
			}
			if (min != i) {
				this.swapValuesInArray(array, i, min);
			}
		}

		return Arrays.asList(array);
	}
	public String getName() {
		return "SelectionSort";
	}
}
