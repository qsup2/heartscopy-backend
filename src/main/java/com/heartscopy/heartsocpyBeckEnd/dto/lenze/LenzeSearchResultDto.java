package com.heartscopy.heartsocpyBeckEnd.dto.lenze;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class LenzeSearchResultDto {
    private Long lenzeId;
    private String topic;
    private String writerId;
}