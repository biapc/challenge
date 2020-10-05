package com.volanty.projetodesafio.model;

import javax.persistence.Entity;

@Entity
public class Cav extends BaseEntity<Cav>{
	
	private String name;
	
	private String open_hours;

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
	
	@Override
	public void merge(Cav another) {
		this.setName(another.getName() == null ? this.getName() : another.getName());
		this.setOpen_hours(another.getOpen_hours() == null ? this.getOpen_hours() : another.getOpen_hours());
	}	
}
