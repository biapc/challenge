package com.volanty.projetodesafio.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.volanty.projetodesafio.model.Schedule;

public interface CalendarRepository extends JpaRepository<Schedule, Integer>{
	public List<Schedule> findByDayAndCavId(Date day, int cavId);
	
	public Schedule findByDayAndCavIdAndHourAndCarIdAndType(Date day, int cavId, int hour, int carId, String type);
}
