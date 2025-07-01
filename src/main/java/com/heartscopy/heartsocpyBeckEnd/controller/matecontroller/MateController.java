package com.heartscopy.heartsocpyBeckEnd.controller.matecontroller;

import com.heartscopy.heartsocpyBeckEnd.service.choiceservice.ChoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mate")
public class MateController {

    private final ChoiceService choiceService;

}