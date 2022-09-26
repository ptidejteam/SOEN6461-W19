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
package ca.concordia.soen6461.sort;

import java.util.List;

public interface ITypeOfSort<E extends Comparable<E>> extends ISort<E> {
	ISortIterator<E> getSortAlgorithms();
	String getTypeName();
	List<E> sort(final List<E> aList);
}
