package com.springbootbook.ch06transaction.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springbootbook.ch06transaction.persistence.entity.Idol;
import com.springbootbook.ch06transaction.persistence.repository.IdolRepository;

@Service
public class IdolService {
	private final IdolRepository idolRepository;
	
	public IdolService(IdolRepository idolRepository) {
		this.idolRepository = idolRepository;
	}
	
	@Transactional(readOnly = true)
	public Optional<Idol> findById(Integer id){
		Optional<Idol> idolOptional = idolRepository.selectById(id);
		return idolOptional;
	}
	
	@Transactional(readOnly = true)
	public List<Idol> findByNameOrderById(String nameKeyword){
		List<Idol> idolList = idolRepository.selectByNameOrderById(nameKeyword);
		return idolList;
	}
	
	@Transactional(readOnly = true)
	public boolean exists(Integer id) {
		int count = idolRepository.countById(id);
		return count ==1;
	}
	
	@Transactional(readOnly = false)
	public int fix(Idol idol) {
		int rows = idolRepository.update(idol);
		return rows;
	}
	
	@Transactional(readOnly = false)
	public int graduate(Integer id) {
		int rows = idolRepository.delete(id);
		return rows;
	}
	
	@Transactional(readOnly = false)
	public Idol join(Idol idol) {
		Idol newIdol = idolRepository.insert(idol);
		return newIdol;
	}
}
