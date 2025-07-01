package com.heartscopy.heartsocpyBeckEnd.dto.mate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MatchingUserDto {
    private String userId;
    private Long matchCount;
}