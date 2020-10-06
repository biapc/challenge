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

import com.volanty.projetodesafio.model.Car;
import com.volanty.projetodesafio.repository.CarRepository;

@Controller
@RequestMapping("/car")
public class CarController {
	
	@Autowired
	private CarRepository carRepo;
	
	// lista os carros
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public List<Car> listCars() throws Exception {
		List<Car> list = carRepo.findAll();
		return list;
	}
	
	// insere um carro
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public void insertCar(@RequestBody Car car) throws Exception {
		if (car.getBrand() == null || car.getModel() == null) {
			throw new IllegalArgumentException("Dados precisam estar preenchidos");
		}
		carRepo.save(car);
	}

	// deleta carro
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = {"/{carId}"}, method = RequestMethod.DELETE)
	public void deleteCar(@PathVariable Integer carId) throws Exception {
		if (!carRepo.findById(carId).isPresent()) {
			throw new NoResultException("Carro n\u00E3o encontrado");
		}
		carRepo.deleteById(carId);
	}
	
	// atualiza carro
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = {"/{carId}"}, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public void updateCar(@PathVariable Integer carId, @RequestBody Car car) throws Exception {
		Optional<Car> carOp = carRepo.findById(carId);
		if (!carOp.isPresent()) {
			throw new NoResultException("Carro n\u00E3o encontrado");
		}
		
		Car carToUpdate = carOp.get();
		
		carToUpdate.merge(car);
		
		carRepo.save(carToUpdate);
	}
}
