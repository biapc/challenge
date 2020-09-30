package com.volanty.projetodesafio.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Calendar extends BaseEntity{
	
	private Date day;
	
	private int hour;
	
	@ManyToOne
	private Cav cav;
	
	private String type;
	
	@ManyToOne
	private Car car;

	public Date getDay() {
		return day;
	}

	public void setDay(Date day) {
		this.day = day;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public Cav getCav() {
		return cav;
	}

	public void setCav(Cav cav) {
		this.cav = cav;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}
}
