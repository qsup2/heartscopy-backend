package com.heartscopy.heartsocpyBeckEnd.dto.choice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class ChoiceCountDto {
    private long countA;
    private long countB;
}