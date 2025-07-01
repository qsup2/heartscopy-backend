package com.heartscopy.heartsocpyBeckEnd.dto.comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {
    private Long lenzeId;
    private String writerId;
    private String comment;
}