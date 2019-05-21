package com.temperatura.gerenciador.dtos;

import java.io.Serializable;
import java.util.List;

public class Clima implements Serializable{

	private static final long serialVersionUID = -8548460803142658980L;
		
	private  List<ClimaConsolidado> consolidated_weather;
	
	private String time;
	private String sun_rise;
	private String sun_set;
	private String timezone_name;
	private TipoLocalidade parent;
	
	private List<Fonte> sources;
	
	private String title;
    private String location_type;
    private String woeid;
    private String latt_long;
    private String timezone;
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getSun_rise() {
		return sun_rise;
	}
	public void setSun_rise(String sun_rise) {
		this.sun_rise = sun_rise;
	}
	public String getSun_set() {
		return sun_set;
	}
	public void setSun_set(String sun_set) {
		this.sun_set = sun_set;
	}
	public String getTimezone_name() {
		return timezone_name;
	}
	public void setTimezone_name(String timezone_name) {
		this.timezone_name = timezone_name;
	}
	public TipoLocalidade getParent() {
		return parent;
	}
	public void setParent(TipoLocalidade parent) {
		this.parent = parent;
	}
	public List<ClimaConsolidado> getConsolidated_weather() {
		return consolidated_weather;
	}
	public void setConsolidated_weather(List<ClimaConsolidado> consolidated_weather) {
		this.consolidated_weather = consolidated_weather;
	}
	public List<Fonte> getSources() {
		return sources;
	}
	public void setSources(List<Fonte> sources) {
		this.sources = sources;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLocation_type() {
		return location_type;
	}
	public void setLocation_type(String location_type) {
		this.location_type = location_type;
	}
	public String getWoeid() {
		return woeid;
	}
	public void setWoeid(String woeid) {
		this.woeid = woeid;
	}
	public String getLatt_long() {
		return latt_long;
	}
	public void setLatt_long(String latt_long) {
		this.latt_long = latt_long;
	}
	public String getTimezone() {
		return timezone;
	}
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
	
}
