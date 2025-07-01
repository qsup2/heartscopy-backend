package com.heartscopy.heartsocpyBeckEnd.domain.comment;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    private Long lenzeId;     // 연결된 Lenze

    private String writerId;    // 댓글 작성자

    @Column(columnDefinition = "TEXT")
    private String comment;   // 댓글 내용

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private int agree;        // 공감 수

    private int disagree;     // 비공감 수
}