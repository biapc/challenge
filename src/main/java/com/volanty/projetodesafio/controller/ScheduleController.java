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
	public Map<String, SortedMap<Integer, String>> showCalendar(@PathVariable int cavId, @PathVariable String day) throws ParseException, JsonMappingException, JsonProcessingException, InvalidAttributeValueException {
		Date date = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(day).getTime());
		List<Schedule> calendars = calendarRepo.findByDayAndCavId(date, cavId);

		DayOfTheWeek weekDay = getWeekDay(date);

		Cav cav = calendars.get(0).getCav();

		HashMap<String, OpenHours> openHours = jsonfyOpenHours(cav.getOpen_hours());
		
		if (openHours.get(weekDay.name()) == null) {
			throw new InvalidAttributeValueException("Sem expediente para visita e inspe\u00E7\u00E3o neste dia");
		}

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
	public void schedule(@PathVariable int cavId, @PathVariable String day, @PathVariable int hour, @PathVariable int carId, @PathVariable String type) throws ParseException, JsonMappingException, JsonProcessingException, InvalidAttributeValueException {
		CalendarType calType = null;
		try {
			calType = CalendarType.valueOf(type);		
		} catch (Exception e) {
			new IllegalArgumentException("Tipo n\u00E3o existente. Valores permitidos: \"visit\" ou \"inspection\"");
		}
		
		Date date = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(day).getTime());
		DayOfTheWeek weekDay = getWeekDay(date);		
		
		Optional<Cav> cavOp = cavRepo.findById(cavId);
		if (!cavOp.isPresent()) {
			throw new NoResultException("Cav n\u00E3o encontrada");
		}		
		Cav cav = cavOp.get();
		HashMap<String, OpenHours> openHours = jsonfyOpenHours(cav.getOpen_hours());
		
		if (openHours.get(weekDay.name()) == null) {
			throw new InvalidAttributeValueException("Sem expediente para visita e inspe\u00E7\u00E3o neste dia");
		}		
		
		Optional<Car> car = carRepo.findById(carId);
		if (!car.isPresent()) {
			throw new NoResultException("Carro n\u00E3o encontrado");
		}
		
		Schedule cal = new Schedule();
		cal.setDay(date);
		cal.setHour(hour);
		cal.setType(calType.name());		
		cal.setCav(cav);
		cal.setCar(car.get());
		
		calendarRepo.save(cal);
	}
	
	// deleta um agendamento
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = {"/{cavId}/{day}/{hour}/{carId}/{type}"}, method = RequestMethod.DELETE)
	public void deleteSchedule(@PathVariable int cavId, @PathVariable String day, @PathVariable int hour, @PathVariable int carId, @PathVariable String type) throws ParseException {
		CalendarType calType = null;
		try {
			calType = CalendarType.valueOf(type);		
		} catch (Exception e) {
			new IllegalArgumentException("Tipo n\u00E3o existente. Valores permitidos: \"visit\" ou \"inspection\"");
		}
		
		Date date = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(day).getTime());
		
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
}
