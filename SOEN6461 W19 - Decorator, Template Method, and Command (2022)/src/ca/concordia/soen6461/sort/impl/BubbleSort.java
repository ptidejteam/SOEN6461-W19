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

class BubbleSort<E extends Comparable<E>> extends InternalSort<E> implements
		ISort<E> {

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
	public String getName() {
		return "BubbleSort";
	}
}
