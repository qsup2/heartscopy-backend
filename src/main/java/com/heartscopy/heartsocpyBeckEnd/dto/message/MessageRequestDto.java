package com.heartscopy.heartsocpyBeckEnd.dto.message;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageRequestDto {
    private Long chatRoomId;
    private String senderId;
    private String content;
}