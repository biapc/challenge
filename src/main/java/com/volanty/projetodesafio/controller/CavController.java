package com.volanty.projetodesafio.controller;

import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.volanty.projetodesafio.model.Cav;
import com.volanty.projetodesafio.repository.CavRepository;

@Controller
@RequestMapping("/cav")
public class CavController {
	
	@Autowired
	private CavRepository cavRepo;

	// lista os CAVs
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public List<Cav> listCavs() throws Exception {
		List<Cav> list = cavRepo.findAll();
		return list;
	}

	// insere um CAV
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public void insertCav(@RequestBody Cav cav) throws Exception {
		if (cavRepo.findByName(cav.getName()) != null) {
			throw new IllegalArgumentException("CAV existente");
		} else if (cav.getOpen_hoursJson() == null || cav.getName() == null) {
			throw new IllegalArgumentException("Todos os dados devem estar preenchidos");
		}
		
		cav.setOpen_hours(returnJson(cav.getOpen_hoursJson()));
		
		cavRepo.save(cav);
	}
	
	// atualiza CAV
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = {"/{cavId}"}, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public void updateCav(@PathVariable Integer cavId, @RequestBody Cav cav) throws Exception {
		if (cav.getOpen_hoursJson() != null) {
			cav.setOpen_hours(returnJson(cav.getOpen_hoursJson()));
		}
		
		Cav cavToUpdate = getCav(cavId);

		cavToUpdate.merge(cav);
		
		cavRepo.save(cavToUpdate);
	}
	
	private String returnJson(Object obj) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(obj);
	}
	
	private Cav getCav(Integer id) {
		Optional<Cav> cavOp = cavRepo.findById(id);
		if (!cavOp.isPresent()) {
			throw new NoResultException("CAV n\u00E3o encontrada");
		}
		return cavOp.get();
	}
}
