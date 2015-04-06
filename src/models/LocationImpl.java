package models;

import interfaces.Location;

public class LocationImpl implements Location {

	private Integer zipCode;
	private String cityName;
	
	public LocationImpl( Integer zip_code, String city_name ) {
		zipCode = zip_code;
		cityName = city_name;
	}
	@Override
	public Integer getZipCode() {
		return zipCode;
	}

	@Override
	public void setZipCode(Integer zip_code) {
		zipCode = zip_code;
	}

	@Override
	public String getCityName() {
		return cityName;
	}

	@Override
	public void setCityName(String city_name) {
		cityName = city_name;
	}

}
