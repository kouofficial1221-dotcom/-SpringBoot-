package com.springbootbook.ch09webapi.webapi.restcontroller;

import com.springbootbook.ch09webapi.persistence.entity.Idol;
import com.springbootbook.ch09webapi.service.IdolService;
import com.springbootbook.ch09webapi.webapi.exception.IdolNotFoundException;
import com.springbootbook.ch09webapi.webapi.response.IdolResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/idols")
public class IdolRestController {
    private final IdolService idolService;

    public IdolRestController(IdolService idolService) {
        this.idolService = idolService;
    }

    @GetMapping("/{id}")
    public IdolResponse searchById(@PathVariable Integer id) {
        Optional<Idol> idolOptional = idolService.findById(id);
        IdolResponse idolResponse = idolOptional.map(idol -> IdolResponse.fromEntity(idol))
                .orElseThrow(() -> new IdolNotFoundException(id));
        return idolResponse;
    }

    @GetMapping
    public List<IdolResponse> searchByName(@RequestParam(defaultValue = "") String keyword) {
        List<Idol> idolList = idolService.findByNameOrderById(keyword);
        List<IdolResponse> idolResponseList = idolList.stream()
                .map(idol -> IdolResponse.fromEntity(idol))
                .toList();
        return idolResponseList;
    }
}
