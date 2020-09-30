package com.volanty.projetodesafio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.volanty.projetodesafio.model.Car;

public interface CarRepository extends JpaRepository<Car, Integer> {

}
