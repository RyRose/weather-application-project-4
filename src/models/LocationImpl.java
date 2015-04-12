package models;

import interfaces.Location;

public class LocationImpl implements Location {

	private String zipCode;
	private String cityName;
	
	public LocationImpl() {}
	
	public LocationImpl( String zip_code, String city_name ) {
		zipCode = zip_code;
		cityName = city_name;
	}
	
	@Override
	public int hashCode() {
		return zipCode.hashCode() + cityName.hashCode();
	}
	
	@Override
	public boolean equals( Object obj ) {
		if ( obj instanceof LocationImpl ) {
			LocationImpl other = (LocationImpl) obj;
			return other.zipCode.equals(zipCode) &&
					other.cityName.equals(cityName);
					
		} else {
			return false;
		}
	}
	
	@Override
	public String getZipCode() {
		return zipCode;
	}

	@Override
	public void setZipCode(String zip_code) {
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
