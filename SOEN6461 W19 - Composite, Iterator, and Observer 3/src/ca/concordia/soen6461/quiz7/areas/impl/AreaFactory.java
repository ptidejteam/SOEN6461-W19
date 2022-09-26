package ca.concordia.soen6461.quiz7.areas.impl;

import ca.concordia.soen6461.quiz7.areas.IArea;
import ca.concordia.soen6461.quiz7.areas.ICountry;
import ca.concordia.soen6461.quiz7.areas.IFarm;
import ca.concordia.soen6461.quiz7.areas.IProvince;
import ca.concordia.soen6461.quiz7.areas.IRegion;

public class AreaFactory {
	private static class FactoryUniqueInstanceHolder {
		private static final AreaFactory THE_UNIQUE_FACTORY = new AreaFactory();
	}
	public static AreaFactory getInstance() {
		return FactoryUniqueInstanceHolder.THE_UNIQUE_FACTORY;
	}

	public IArea getWorld() {
		final ICountry country = new Country("Canada");

		final IProvince province = new Province("Québec");
		country.addSubArea(province);

		final IRegion region = new Region("Gaspésie");
		province.addSubArea(region);

		final IFarm farm = new Farm("Little House on the Prairie");
		region.addSubArea(farm);

		return country;
	}
}
