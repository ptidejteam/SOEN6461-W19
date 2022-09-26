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
package ca.concordia.soen6461.quiz8.sort.decorators;

import java.util.List;
import ca.concordia.soen6461.quiz8.sort.ISort;
import ca.concordia.soen6461.quiz8.sort.impl.SortDecorator;

public class NamingDecorator extends SortDecorator<String> {
	private final String name;

	public NamingDecorator(final ISort<String> aSortAlgorithm) {
		super(aSortAlgorithm);
		this.name = aSortAlgorithm.getClass().getSimpleName();
	}

	@Override
	protected List<String> preProcessInput(final List<String> aList) {
		System.out.println(this.name);
		return aList;
	}
}
