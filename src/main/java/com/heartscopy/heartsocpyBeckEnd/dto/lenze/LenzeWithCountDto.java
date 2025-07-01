package com.heartscopy.heartsocpyBeckEnd.dto.lenze;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LenzeWithCountDto {
    private Long lenzeId;
    private Long count;
    private String topic;

}