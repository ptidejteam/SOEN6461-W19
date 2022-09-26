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

import java.util.List;
import ca.concordia.soen6461.sort.ISort;

class pSort<E extends Comparable<E>> extends ExternalSort<E> implements
		ISort<E> {

	public List<E> sort(final List<E> aList) {
		return null;
	}
	public String getName() {
		return "pSort";
	}
}
