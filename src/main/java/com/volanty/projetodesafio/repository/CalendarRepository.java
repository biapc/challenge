package com.volanty.projetodesafio.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.volanty.projetodesafio.model.Calendar;

public interface CalendarRepository extends JpaRepository<Calendar, Integer>{
	public List<Calendar> findByDayAndCavId(Date day, int cavId);

}
