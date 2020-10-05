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
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public HttpStatus insertCav(@RequestBody Cav cav) throws Exception {
		if (cavRepo.findByName(cav.getName()) == null) {
			cavRepo.save(cav);
			return HttpStatus.CREATED;
		}
		return HttpStatus.NOT_MODIFIED;
	}
	
	// atualiza CAV
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = {"/{cavId}"}, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public void updateCav(@PathVariable int cavId, @RequestBody Cav cav) throws Exception {
		Optional<Cav> cavOp = cavRepo.findById(cavId);
		if (!cavOp.isPresent()) {
			throw new NoResultException("Cav n\u00E3o encontrada");
		}
		
		Cav cavToUpdate = cavOp.get();

		cavToUpdate.merge(cav);
		
		cavRepo.save(cavToUpdate);
	}
}
