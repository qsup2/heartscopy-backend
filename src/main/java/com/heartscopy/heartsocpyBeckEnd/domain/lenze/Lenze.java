package com.heartscopy.heartsocpyBeckEnd.domain.lenze;

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
public class Lenze {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lenzeId;

    private String writerId;

    private String topic;

    @Column(name = "a")
    private String a;

    @Column(name = "b")
    private String b;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}