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
import ca.concordia.soen6461.sort.decorators.EncryptAfterSortingDecorator;
import ca.concordia.soen6461.sort.observer.ISortObserver;

public class EncryptAfterSortingCommand implements ICommand<String> {
	private ISortObserver<String> observer;
	public EncryptAfterSortingCommand(
		final ISortObserver<String> observer) {

		this.observer = observer;
	}
	@Override
	public ISort<String> execute(
		final ISort<String> sort,
		final List<String> list) {

		final ISort<String> d2 = new EncryptAfterSortingDecorator(sort);
		d2.addObserver(this.observer);
		System.out.println(d2.sort(list));
		return d2;
	}
}
