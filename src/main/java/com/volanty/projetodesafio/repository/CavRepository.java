package com.volanty.projetodesafio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.volanty.projetodesafio.model.Cav;

public interface CavRepository extends JpaRepository<Cav, Integer>{
	public Cav findByName(String name);
}
