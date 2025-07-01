package com.heartscopy.heartsocpyBeckEnd.controller.choicecontroller;

import com.heartscopy.heartsocpyBeckEnd.dto.choice.MyChoiceDto;
import com.heartscopy.heartsocpyBeckEnd.dto.lenze.LenzeWithCountDto;
import com.heartscopy.heartsocpyBeckEnd.dto.mate.MatchingUserDto;
import com.heartscopy.heartsocpyBeckEnd.service.choiceservice.ChoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Map<String, Integer>> getMatchingUsers(@RequestParam String userId) {
        Map<String, Integer> result = choiceService.findMatchingUsers(userId);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/user/{userId}")
    public List<MyChoiceDto> getChoicesByUserId(@PathVariable("userId") String userId) {
        return choiceService.getChoicesByUserId(userId);
    }
}