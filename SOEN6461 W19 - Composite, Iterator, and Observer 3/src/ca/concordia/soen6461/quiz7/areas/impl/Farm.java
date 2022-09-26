package ca.concordia.soen6461.quiz7.areas.impl;

import ca.concordia.soen6461.quiz7.areas.IArea;
import ca.concordia.soen6461.quiz7.areas.IFarm;

public class Farm extends Area implements IFarm {
	public Farm(final String aName) {
		super(aName);
	}
	@Override
	public void addSubArea(final IArea aArea) {
		System.out.println("Cannot add a sub-area to a farm!");
	}
}
