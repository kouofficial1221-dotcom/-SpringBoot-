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
    public String searchByName(@RequestParam(defaultValue = "") String keyword, Model model) {
        List<Idol> idolList = idolService.findByNameOrderById(keyword);
        model.addAttribute("idolList", idolList);
        return "idol/index";
    }

    @GetMapping("/idol/join")
    public String joinMain(Model model) {
        model.addAttribute("idolForm", IdolForm.empty());
        return "idol/join";
    }

    @PostMapping("/idol/join")
    public String join(@Validated IdolForm idolForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "idol/join";
        }
        Idol idol = idolForm.toEntity();
        idolService.join(idol);
        return "redirect:/";
    }

    @GetMapping("/idol/{id}")
    public String fixMain(@PathVariable Integer id, Model model) {
        Idol idol = idolService.findById(id)
                .orElseThrow(() -> new IdolNotFoundException(id));
        IdolForm idolForm = IdolForm.fromEntity(idol);
        model.addAttribute("idolForm", idolForm);
        model.addAttribute("id", id);
        return "idol/fix";
    }

    @PostMapping("/idol/{id}")
    public String fix(@PathVariable Integer id, @Validated IdolForm idolForm, BindingResult bindingResult) {
        if (idolService.exists(id) == false) {
            throw new IdolNotFoundException(id);
        }
        if (bindingResult.hasErrors()) {
            return "idol/fix";
        }
        Idol idol = idolForm.toEntity(id);
        idolService.fix(idol);
        return "redirect:/";
    }

    @PostMapping("/idol/graduate/{id}")
    public String graduate(@PathVariable Integer id) {
        if (idolService.exists(id) == false) {
            throw new IdolNotFoundException(id);
        }
        idolService.graduate(id);
        return "redirect:/";
    }
}
