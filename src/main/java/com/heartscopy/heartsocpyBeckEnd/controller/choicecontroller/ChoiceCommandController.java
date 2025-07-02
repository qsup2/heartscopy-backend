package com.heartscopy.heartsocpyBeckEnd.controller.choicecontroller;

import com.heartscopy.heartsocpyBeckEnd.domain.choice.Choice;
import com.heartscopy.heartsocpyBeckEnd.service.choiceservice.ChoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/choices")
public class ChoiceCommandController {

    private final ChoiceService choiceService;

    @PostMapping("/make")
    public ResponseEntity<Choice> makeChoice(@RequestBody Choice choice, Authentication authentication) {
        String uid = authentication.getName();
        // 클라이언트가 보내는 userId 덮어쓰기 (무조건 서버 인증 UID 사용)
        choice.setUserId(uid);
        return ResponseEntity.ok(choiceService.makeChoice(choice));
    }

    @GetMapping("/selected/{userId}/{lenzeId}")
    public ResponseEntity<?> getSelectedValue(
            @PathVariable String userId,
            @PathVariable Long lenzeId,
            Authentication authentication) {

        String uid = authentication.getName();
        if (!uid.equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("권한이 없습니다.");
        }

        return choiceService.getSelectedValue(uid, lenzeId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{userId}/{lenzeId}")
    public ResponseEntity<String> cancelChoice(
            @PathVariable String userId,
            @PathVariable Long lenzeId,
            Authentication authentication
    ) {
        String uid = authentication.getName();
        if (!uid.equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("권한이 없습니다.");
        }

        choiceService.cancelChoice(uid, lenzeId);
        return ResponseEntity.ok("선택이 성공적으로 취소되었습니다.");
    }

    @DeleteMapping("/lenze/{lenzeId}")
    public ResponseEntity<String> deleteChoicesByLenzeId(@PathVariable Long lenzeId) {
        choiceService.deleteAllByLenzeId(lenzeId);
        return ResponseEntity.ok("해당 렌즈 ID의 모든 선택이 삭제되었습니다.");
    }
}