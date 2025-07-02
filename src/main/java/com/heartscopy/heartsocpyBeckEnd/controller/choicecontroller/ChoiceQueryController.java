package com.heartscopy.heartsocpyBeckEnd.controller.choicecontroller;

import com.heartscopy.heartsocpyBeckEnd.dto.choice.MyChoiceDto;
import com.heartscopy.heartsocpyBeckEnd.dto.lenze.LenzeWithCountDto;
import com.heartscopy.heartsocpyBeckEnd.service.choiceservice.ChoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/choices")
public class ChoiceQueryController {

    private final ChoiceService choiceService;

    @GetMapping("/popular")
    public ResponseEntity<List<LenzeWithCountDto>> getPopularLenzes() {
        List<LenzeWithCountDto> popularLenzes = choiceService.findTop10LenzesByChoiceCount();
        return ResponseEntity.ok(popularLenzes);
    }

    @GetMapping("/match-count")
    public ResponseEntity<Map<String, Integer>> getMatchingUsers(
            @RequestParam String userId, // path는 그대로 두지만 검증 추가
            Authentication authentication) {

        String uid = authentication.getName();

        // userId와 인증된 uid가 일치하는지 검사
        if (!uid.equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Map<String, Integer> result = choiceService.findMatchingUsers(uid);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MyChoiceDto>> getChoicesByUserId(
            @PathVariable("userId") String userId,
            Authentication authentication) {

        String uid = authentication.getName();

        if (!uid.equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        List<MyChoiceDto> choices = choiceService.getChoicesByUserId(uid);
        return ResponseEntity.ok(choices);
    }
}