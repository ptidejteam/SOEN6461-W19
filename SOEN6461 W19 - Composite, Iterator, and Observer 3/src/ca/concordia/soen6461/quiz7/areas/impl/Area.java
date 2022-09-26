package ca.concordia.soen6461.quiz7.areas.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import ca.concordia.soen6461.quiz7.areas.IArea;
import ca.concordia.soen6461.quiz7.things.IThing;

class Area {
	private String name;
	private List listOfThings = new ArrayList();
	private List listOfSubAreas = new ArrayList();

	public Area(final String aName) {
		this.name = aName;
	}
	public String getName() {
		return this.name;
	}

	public void addThing(final IThing aThing) {
		this.listOfThings.add(aThing);
	}
	public boolean doesContain(final IThing aThing) {
		return this.listOfThings.contains(aThing);
	}

	public void addSubArea(final IArea aArea) {
		this.listOfSubAreas.add(aArea);
	}
	public Iterator iterateOnSubArea() {
		return this.listOfSubAreas.iterator();
	}
}
