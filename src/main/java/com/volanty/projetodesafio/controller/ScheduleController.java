package com.volanty.projetodesafio.controller;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.management.InvalidAttributeValueException;
import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.volanty.projetodesafio.model.Car;
import com.volanty.projetodesafio.model.Cav;
import com.volanty.projetodesafio.model.Schedule;
import com.volanty.projetodesafio.repository.CalendarRepository;
import com.volanty.projetodesafio.repository.CarRepository;
import com.volanty.projetodesafio.repository.CavRepository;
import com.volanty.projetodesafio.util.CalendarType;
import com.volanty.projetodesafio.util.DayOfTheWeek;
import com.volanty.projetodesafio.vo.OpenHours;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {
	
	@Autowired
	private CarRepository carRepo;

	@Autowired
	private CavRepository cavRepo;

	@Autowired
	private CalendarRepository calendarRepo;


	// retorna horarios disponiveis de um cav para cada procedimento (inspecao e visita)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = {"/{cavId}/{day}" }, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Map<String, SortedMap<Integer, String>> showCalendar(@PathVariable Integer cavId, @PathVariable String day) throws ParseException, JsonMappingException, JsonProcessingException, InvalidAttributeValueException {
		Date date = parseToFormatDate(day);
		List<Schedule> calendars = calendarRepo.findByDayAndCavId(date, cavId);

		Cav cav = null;
		
		if (calendars.isEmpty()) {
			cav = getCav(cavId);
		} else {
			cav = calendars.get(0).getCav();
		}

		HashMap<String, OpenHours> openHours = jsonfyOpenHours(cav.getOpen_hours());
		
		DayOfTheWeek weekDay = getWeekDay(date);
		
		verifyWeekDayAndOpenHours(openHours, weekDay);

		SortedMap<Integer, String> resultVisit = new TreeMap<Integer, String>();
		SortedMap<Integer, String> resultInspection = new TreeMap<Integer, String>();

		for (Schedule c : calendars) {
			if (c.getType().equalsIgnoreCase("inspection")) {
				resultInspection.put(c.getHour(), String.valueOf(c.getCar().getId()));
			} else if (c.getType().equalsIgnoreCase("visit")) {
				resultVisit.put(c.getHour(), String.valueOf(c.getCar().getId()));
			}
		}

		// preenche espacos vazios
		for (int i = openHours.get(weekDay.name()).getBegin(); i <= openHours.get(weekDay.name()).getEnd(); i++) {
			if (!resultVisit.containsKey(i)) {
				resultVisit.put(i, "");
			}

			if (!resultInspection.containsKey(i)) {
				resultInspection.put(i, "");
			}
		}

		Map<String, SortedMap<Integer, String>> ret = new HashMap<String, SortedMap<Integer, String>>();

		ret.put("Visit", resultVisit);
		ret.put("Inspection", resultInspection);

		return ret;
	}

	// insere um agendamento
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(value = {"/{cavId}/{day}/{hour}/{carId}/{type}"}, method = RequestMethod.POST)
	public void schedule(@PathVariable Integer cavId, @PathVariable String day, @PathVariable int hour, @PathVariable Integer carId, @PathVariable String type) throws ParseException, JsonMappingException, JsonProcessingException, InvalidAttributeValueException {
		CalendarType calType = getType(type);
		
		Date date = parseToFormatDate(day);
		DayOfTheWeek weekDay = getWeekDay(date);		
		
		Cav cav = getCav(cavId);
		HashMap<String, OpenHours> openHours = jsonfyOpenHours(cav.getOpen_hours());
		
		verifyWeekDayAndOpenHours(openHours, weekDay);
		
		validateHour(openHours, weekDay, hour);
		
		Schedule cal = new Schedule();
		cal.setDay(date);
		cal.setHour(hour);
		cal.setType(calType.name());		
		cal.setCav(cav);
		cal.setCar(getCar(carId));
		
		calendarRepo.save(cal);
	}
	
	// deleta um agendamento
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = {"/{cavId}/{day}/{hour}/{carId}/{type}"}, method = RequestMethod.DELETE)
	public void deleteSchedule(@PathVariable Integer cavId, @PathVariable String day, @PathVariable int hour, @PathVariable Integer carId, @PathVariable String type) throws ParseException {
		CalendarType calType = getType(type);
		
		Date date = parseToFormatDate(day);
		
		Schedule cal = calendarRepo.findByDayAndCavIdAndHourAndCarIdAndType(date, cavId, hour, carId, calType.name());
		
		if (cal == null) {
			throw new NoResultException("Agendamento n\u00E3o encontrado");
		}
		
		calendarRepo.deleteById(cal.getId());
	}
	
	private DayOfTheWeek getWeekDay(Date day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(day);
		int dow = cal.get(Calendar.DAY_OF_WEEK);
		return DayOfTheWeek.of(dow);
	}
	
	private HashMap<String, OpenHours> jsonfyOpenHours(String openHours) throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		TypeReference<HashMap<String, OpenHours>> typeRef = new TypeReference<HashMap<String, OpenHours>>() {};
		return mapper.readValue(openHours, typeRef);
	}
	
	private Date parseToFormatDate(String day) throws ParseException  {
		Date date;
		try {
			date = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(day).getTime());
		} catch (ParseException e) {
			throw new IllegalArgumentException("Erro de formata\u00E7\u00E3o na data");
		}
				
		if (!day.equals(date.toString())) {
	        throw new IllegalArgumentException("Data incorreta");
	    }
		return date;
	}
	
	private <T> T validate(Optional<T> op) {
		if (!op.isPresent()) {
			throw new NoResultException("Cav ou carro n\u00E3o encontrado");
		}
		return op.get();
	}
	
	private Cav getCav(Integer id) {
		return validate(cavRepo.findById(id));
	}
	
	private Car getCar(Integer id) {
		return validate(carRepo.findById(id));
	}
	
	private CalendarType getType(String type) {
		try {
			return CalendarType.valueOf(type);		
		} catch (Exception e) {
			throw new IllegalArgumentException("Tipo n\u00E3o existente. Valores permitidos: \"visit\" ou \"inspection\"");
		}
	}
	
	private void verifyWeekDayAndOpenHours(HashMap<String, OpenHours> openHours, DayOfTheWeek weekDay) throws InvalidAttributeValueException {
		if (openHours.get(weekDay.name()) == null || openHours.get(weekDay.name()).getBegin() == null || openHours.get(weekDay.name()).getEnd() == null) {
			throw new InvalidAttributeValueException();
		}
	}
	
	private void validateHour(HashMap<String, OpenHours> openHours, DayOfTheWeek weekDay, int hour) {
		int begin = openHours.get(weekDay.name()).getBegin();
		int end = openHours.get(weekDay.name()).getEnd();
		if (hour < begin || hour > end) {
			throw new IllegalArgumentException("Hor\u00E1rio inv\u00E1lido");
		}
	}
}
