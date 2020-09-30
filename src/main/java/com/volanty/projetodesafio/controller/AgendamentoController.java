package com.volanty.projetodesafio.controller;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.volanty.projetodesafio.model.Calendar;
import com.volanty.projetodesafio.model.Cav;
import com.volanty.projetodesafio.repository.CalendarRepository;
import com.volanty.projetodesafio.repository.CarRepository;
import com.volanty.projetodesafio.repository.CavRepository;

@Controller
@RequestMapping("/cav")
public class AgendamentoController {
	
	@Autowired
	private CavRepository cavRepo;
	
	@Autowired
	private CalendarRepository calendarRepo;
	
	@Autowired
	private CarRepository carRepo;	
	
	//lista os CAVs
	@ResponseBody
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public List<Cav> listCavs() throws Exception {
		List<Cav> list = cavRepo.findAll();
        return list;
    }
	
	//insere um CAV
    @PostMapping
    public HttpStatus insertCav(@RequestBody Cav cav) throws Exception {
		if (cavRepo.findByName(cav.getName()) == null) {
			cavRepo.save(cav);
			return HttpStatus.CREATED;
		}
        return HttpStatus.NO_CONTENT;
    }
	
	//deleta um CAV
	
	//atualiza um CAV
	
	//retorna horários disponíveis de um cav para cada procedimento (inspeção e visita)
    @ResponseBody
    @RequestMapping(value = {"/{cavId}/date/{day}"}, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public List<Calendar> showCalendar(@PathVariable int cavId, @PathVariable String day) throws ParseException {
    	Date date = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(day).getTime());
    	List<Calendar> c = calendarRepo.findByDayAndCavId(date, cavId);
    	//montar retorno    	
    	
		return c;
    }
    
    //agenda uma inspeção
//    @PostMapping
//    
//    public void scheduleVisit( ) {
//    	
//    }
    
	
  	//agenda uma visita para um dado veículo
    
	/*
	 * private HashMap<String, Object> buildResponse( object){ HashMap<String,
	 * Object> map = new HashMap<String, Object>(); map.put("10",
	 * object.getCar_id_10() == null ? "" : "X"); map.put("11",
	 * object.getCar_id_11() == null ? "" : "X"); map.put("12",
	 * object.getCar_id_12() == null ? "" : "X"); map.put("13",
	 * object.getCar_id_13() == null ? "" : "X"); map.put("14",
	 * object.getCar_id_14() == null ? "" : "X"); map.put("15",
	 * object.getCar_id_15() == null ? "" : "X"); map.put("16",
	 * object.getCar_id_16() == null ? "" : "X"); map.put("17",
	 * object.getCar_id_17() == null ? "" : "X");
	 * 
	 * return map; }
	 */
	
	

}
