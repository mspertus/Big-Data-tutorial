namespace java edu.uchicago.mpcs53013.weatherSummary

struct WeatherSummary {
	1: required string station;
	2: required i16 year;
	3: required byte month;
	4: required byte day;
	5: required double meanTemperature;
	6: required double meanVisibility;
	7: required double meanWindSpeed;
	8: required bool fog;
	9: required bool rain;
	10: required bool snow;
	11: required bool hail;
	12: required bool thunder;
	13: required bool tornado;
}

