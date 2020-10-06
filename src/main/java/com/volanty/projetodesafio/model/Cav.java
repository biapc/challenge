package com.volanty.projetodesafio.model;

import java.util.HashMap;

import javax.persistence.Entity;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.volanty.projetodesafio.vo.OpenHours;

@Entity
public class Cav extends BaseEntity<Cav>{
	
	private String name;
	
	@JsonIgnore
	private String open_hours;
	
	@Transient
	@JsonAlias({"open_hours", "openHours"})
	private HashMap<String, OpenHours> open_hoursJson;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOpen_hours() {
		return open_hours;
	}

	public void setOpen_hours(String open_hours) {
		this.open_hours = open_hours;
	}
	
	public HashMap<String, OpenHours> getOpen_hoursJson() {
		return open_hoursJson;
	}

	public void setOpen_hoursJson(HashMap<String, OpenHours> open_hoursJson) {
		this.open_hoursJson = open_hoursJson;
	}
	
	@Override
	public void merge(Cav another) {
		this.setName(another.getName() == null ? this.getName() : another.getName());
		this.setOpen_hours(another.getOpen_hours() == null ? this.getOpen_hours() : another.getOpen_hours());
	}	
}
