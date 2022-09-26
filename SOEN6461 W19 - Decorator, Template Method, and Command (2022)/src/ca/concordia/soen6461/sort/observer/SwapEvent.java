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
package ca.concordia.soen6461.sort.observer;

public final class SwapEvent<E extends Comparable<E>> {
	private final E[] values;
	private final E value1;
	private final E value2;

	public SwapEvent(final E[] values, final E value1, final E value2) {
		this.values = values;
		this.value1 = value1;
		this.value2 = value2;
	}
	public E[] getValues() {
		return this.values;
	}
	public E getValue1() {
		return this.value1;
	}
	public E getValue2() {
		return this.value2;
	}
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Swap of ");
		builder.append(this.value1);
		builder.append(" with ");
		builder.append(this.value2);
		return builder.toString();
	}
}
