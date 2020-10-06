package com.volanty.projetodesafio.vo;

import com.fasterxml.jackson.annotation.JsonAlias;

public class OpenHours {
	
	@JsonAlias({"inicio", "Inicio", "begin"})
	private Integer begin;
	
	@JsonAlias({"fim", "Fim", "end"})
	private Integer end;
	
	public Integer getBegin() {
		return begin;
	}
	public void setBegin(Integer begin) {
		this.begin = begin;
	}
	public Integer getEnd() {
		return end;
	}
	public void setEnd(Integer end) {
		this.end = end;
	}
}
