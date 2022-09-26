package ca.concordia.soen6461.quiz7.areas;
import java.util.Iterator;
import ca.concordia.soen6461.quiz7.things.IThing;

public interface IArea {
	public String getName();
	
	public void addThing(final IThing aThing);
	public boolean doesContain(final IThing aThing);
	
	public void addSubArea(final IArea aArea);
	public Iterator iterateOnSubArea();
}
