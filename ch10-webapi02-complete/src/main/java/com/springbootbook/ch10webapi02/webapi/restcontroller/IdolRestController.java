package com.springbootbook.ch10webapi02.webapi.restcontroller;

import com.springbootbook.ch10webapi02.persistence.entity.Idol;
import com.springbootbook.ch10webapi02.service.IdolService;
import com.springbootbook.ch10webapi02.webapi.exception.IdolNotFoundException;
import com.springbootbook.ch10webapi02.webapi.request.IdolRequest;
import com.springbootbook.ch10webapi02.webapi.response.IdolResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
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

    @PostMapping
    public ResponseEntity<Object> join(@RequestBody @Validated IdolRequest idolRequest) {
        Idol idol = idolRequest.toEntity();
        Idol joinedIdol = idolService.join(idol);
        URI location = URI.create("/api/idols/" + joinedIdol.id());
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> fix(@PathVariable Integer id,
                                 @RequestBody @Validated IdolRequest idolRequest) {
        if (idolService.exists(id) == false) {
            throw new IdolNotFoundException(id);
        }
        Idol idol = idolRequest.toEntity(id);
        idolService.fix(idol);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> graduate(@PathVariable Integer id) {
        if (idolService.exists(id) == false) {
            throw new IdolNotFoundException(id);
        }
        idolService.graduate(id);
        return ResponseEntity.noContent().build();
    }
}
