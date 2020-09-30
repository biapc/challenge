package com.volanty.projetodesafio.model;

import javax.persistence.Entity;

@Entity
public class Car extends BaseEntity{
	
	private String brand;
	
	private String model;

	public String getBrand() {
		return brand;
	}

	public String getModel() {
		return model;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public void setModel(String model) {
		this.model = model;
	}
}
