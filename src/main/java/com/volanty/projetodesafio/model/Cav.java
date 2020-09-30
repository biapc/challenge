package com.volanty.projetodesafio.model;

import javax.persistence.Entity;

@Entity
public class Cav extends BaseEntity{
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	
}
