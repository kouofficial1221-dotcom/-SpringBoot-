package com.springbootbook.ch07web.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springbootbook.ch07web.persistence.entity.Idol;
import com.springbootbook.ch07web.service.IdolService;

@Controller
public class IdolController {
	private final IdolService idolService;

	public IdolController(IdolService idolService) {
		this.idolService = idolService;
	}

	@GetMapping("/")
	public String index(Model model) {
		List<Idol> idolList = idolService.findByNameOrderById("");
		model.addAttribute("idolList",idolList);
		return "idol/index";
	}

	@GetMapping("/idol")
	public String searchByName(@RequestParam(defaultValue = "") String Keyword, Model model) {
		List<Idol> idolList = idolService.findByNameOrderById(Keyword);
		model.addAttribute("idolList", idolList);
		return "idol/index";
	}

	@PostMapping("/idol/graduate/{id}")
	public String graduate(@PathVariable Integer id) {
		idolService.graduate(id);
		return "redirect:/";
	}

}
