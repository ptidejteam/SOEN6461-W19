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
package ca.concordia.soen6461;

import java.util.Arrays;
import java.util.List;

import ca.concordia.soen6461.sort.ISort;
import ca.concordia.soen6461.sort.command.EncryptAfterSortingCommand;
import ca.concordia.soen6461.sort.command.ToLowerCaseCommand;
import ca.concordia.soen6461.sort.decorators.ToLowerCaseDecorator;
import ca.concordia.soen6461.sort.impl.Factory;

public class Client {
	public static void main(final String[] args) {
		final List<String> l = Arrays.asList(new String[] { "Rick Deckard", "Roy Batty", "Harry Bryant",
				"Hannibal Chew", "Gaff", "Holden", "Leon Kowalski", "Taffey Lewis", "Pris", "Rachael", "J.F. Sebastian",
				"Dr. Eldon Tyrell", "Zhora", "Hodge", "Mary" });
		final SimpleObserver<String> observer = new SimpleObserver<String>();

		final ISort<String> s = Factory.getInstance().getBubbleSortAlgorithm();
		s.addObserver(observer);
		System.out.println(s.sort(l));

		final ToLowerCaseCommand toLowerCaseCommand = new ToLowerCaseCommand(observer);
		final ISort<String> d1 = toLowerCaseCommand.execute(s, l);

		final EncryptAfterSortingCommand encryptAfterSortingCommand = new EncryptAfterSortingCommand(observer);
		final ISort<String> d2 = encryptAfterSortingCommand.execute(d1, l);

		System.out.println(d2);

		final ISort<String> t = Factory.getInstance().getInternalSortAlgorithms();
		t.addObserver(observer);
		final ISort<String> d3 = new ToLowerCaseDecorator(t);
		d3.addObserver(observer);
		System.out.println(d3.sort(l));
	}
}
