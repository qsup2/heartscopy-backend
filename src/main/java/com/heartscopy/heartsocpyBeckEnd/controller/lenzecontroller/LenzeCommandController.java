package com.heartscopy.heartsocpyBeckEnd.controller.lenzecontroller;

import com.heartscopy.heartsocpyBeckEnd.domain.lenze.Lenze;
import com.heartscopy.heartsocpyBeckEnd.service.lenzeservice.LenzeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lenzes")
public class LenzeCommandController {

    private final LenzeService lenzeService;

    @PostMapping
    public ResponseEntity<Lenze> createLenze(@RequestBody Lenze lenze) {
        Lenze savedLenze = lenzeService.createLenze(lenze);
        return ResponseEntity.ok(savedLenze);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Lenze> getLenzeById(@PathVariable Long id) {
        Lenze lenze = lenzeService.findById(id);
        return ResponseEntity.ok(lenze);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLenze(@PathVariable Long id) {
        lenzeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}