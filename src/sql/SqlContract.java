package sql;

public class SqlContract {
	
	public static final String COLUMN_ZIP = "zipcode";

	public class Area {
		public static final String TABLE_NAME = "area";
		public static final String COLUMN_CITY = "cityname";
	}
	
	public class Weather {
		public static final String TABLE_NAME = "weather";
		public static final String COLUMN_DATE = "date";
		public static final String COLUMN_HIGH_TEMP = "hightemp";
		public static final String COLUMN_LOW_TEMP = "lowtemp";
		public static final String COLUMN_WIND_SPEED = "wind";
		public static final String COLUMN_HUMIDITY = "humidity";
	}
	
}
