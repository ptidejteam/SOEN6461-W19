/* (c) Copyright 2020 and following years, Yann-Gaël Guéhéneuc,
 * Concordia University.
 * 
 * Use and copying of this software and preparation of derivative works
 * based upon this software are permitted. Any copy of this software or
 * of any derivative work must include the above copyright notice of
 * the author, this paragraph and the one after it.
 * 
 * This software is made available AS IS, and THE AUTHOR DISCLAIMS
 * ALL WARRANTIES, EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE, AND NOT WITHSTANDING ANY OTHER PROVISION CONTAINED HEREIN,
 * ANY LIABILITY FOR DAMAGES RESULTING FROM THE SOFTWARE OR ITS USE IS
 * EXPRESSLY DISCLAIMED, WHETHER ARISING IN CONTRACT, TORT (INCLUDING
 * NEGLIGENCE) OR STRICT LIABILITY, EVEN IF THE AUTHOR IS ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 * 
 * All Rights Reserved.
 */
package ca.concordia.soen6461.sort.command;

import java.util.List;
import ca.concordia.soen6461.sort.ISort;
import ca.concordia.soen6461.sort.decorators.ToLowerCaseDecorator;
import ca.concordia.soen6461.sort.observer.ISortObserver;

public class ToLowerCaseCommand implements ICommand<String> {
	private ISortObserver<String> observer;
	public ToLowerCaseCommand(final ISortObserver<String> observer) {
		this.observer = observer;
	}
	@Override
	public ISort<String> execute(
		final ISort<String> sort,
		final List<String> list) {

		final ISort<String> newSort = new ToLowerCaseDecorator(sort);
		newSort.addObserver(this.observer);
		System.out.println(newSort.sort(list));
		return newSort;
	}
}
