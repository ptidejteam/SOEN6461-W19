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
package ca.concordia.soen6461.quiz8.sort;

import java.util.List;
import ca.concordia.soen6461.quiz8.sort.observer.ISortObserver;

public interface ISort<E extends Comparable<E>> {
	List<E> sort(final List<E> aList);
	void addObserver(final ISortObserver<E> anObserver);
	void addExtension(
		final String anExtensionName,
		final Class<? extends ISortExtension> anExtensionClass);
	ISortExtension getExtension(final String anExtensionName);
	void removeExtension(final String anExtensionName);
}
