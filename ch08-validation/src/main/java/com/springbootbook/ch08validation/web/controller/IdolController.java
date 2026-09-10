package com.springbootbook.ch08validation.web.controller;

import com.springbootbook.ch08validation.persistence.entity.Idol;
import com.springbootbook.ch08validation.service.IdolService;
import com.springbootbook.ch08validation.web.exception.IdolNotFoundException;
import com.springbootbook.ch08validation.web.form.IdolForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class IdolController {
    private final IdolService idolService;

    public IdolController(IdolService idolService) {
        this.idolService = idolService;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Idol> idolList = idolService.findByNameOrderById("");
        model.addAttribute("idolList", idolList);
        return "idol/index";
    }

    @GetMapping("/idol")
    public String searchByName(@RequestParam(defaultValue = "") String keyword,  Model model) {
        List<Idol> idolList = idolService.findByNameOrderById(keyword);
        model.addAttribute("idolList", idolList);
        return "idol/index";
    }

    @PostMapping("/idol/graduate/{id}")
    public String graduate(@PathVariable Integer id) {
        idolService.graduate(id);
        return "redirect:/";
    }
}
