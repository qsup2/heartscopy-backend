package com.heartscopy.heartsocpyBeckEnd.dto.choice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MyChoiceDto {
    private Long lenzeId;
    private String topic;
    private String selectedValue;
    private LocalDateTime chooseAt;
    private String a;
    private String b;
}