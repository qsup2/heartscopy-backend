package com.heartscopy.heartsocpyBeckEnd.controller.lenzecontroller;

import com.heartscopy.heartsocpyBeckEnd.domain.lenze.Lenze;
import com.heartscopy.heartsocpyBeckEnd.service.lenzeservice.LenzeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lenzes")
public class LenzeCommandController {

    private final LenzeService lenzeService;

    @PostMapping
    public ResponseEntity<Lenze> createLenze(@RequestBody Lenze lenze, Authentication authentication) {
        String uid = authentication.getName(); // Firebase UID는 문자열임
        Lenze savedLenze = lenzeService.createLenze(lenze, uid); // ✅ uid를 같이 전달
        return ResponseEntity.ok(savedLenze);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lenze> getLenzeById(@PathVariable Long id) {
        Lenze lenze = lenzeService.findById(id);
        return ResponseEntity.ok(lenze);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLenze(@PathVariable Long id, Authentication authentication) {
        String uid = authentication.getName(); // 인증된 사용자 UID
        lenzeService.deleteById(id, uid); // ✅ UID로 삭제 권한 체크
        return ResponseEntity.noContent().build();
    }
}