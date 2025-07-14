package com.heartscopy.heartsocpyBeckEnd.controller.lenzecontroller;
import com.heartscopy.heartsocpyBeckEnd.domain.lenze.Lenze;
import com.heartscopy.heartsocpyBeckEnd.dto.lenze.LenzeSearchResultDto;
import com.heartscopy.heartsocpyBeckEnd.service.lenzeservice.LenzeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lenzes")
public class LenzeQueryController {

    private final LenzeService lenzeService;

    @GetMapping
    public Page<Lenze> getRandomLenzes(@RequestParam(defaultValue = "0") int page) {
        return lenzeService.getRandomLenzesByPage(page, 30);
    }

    @GetMapping("/search")
    public ResponseEntity<List<LenzeSearchResultDto>> search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size
    ) {
        List<LenzeSearchResultDto> results = lenzeService.searchLenzes(keyword, page, size);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/recent")
    public List<Lenze> getRecentLenzes() {
        return lenzeService.getRecentLenzes();
    }
}