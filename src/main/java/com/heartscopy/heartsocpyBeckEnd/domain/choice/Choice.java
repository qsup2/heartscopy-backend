package com.heartscopy.heartsocpyBeckEnd.domain.choice;


import com.fasterxml.jackson.annotation.JsonProperty;
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
public class Choice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long choiceId;

    private String userId;

    private Long lenzeId;

    private String selectedValue;
    private String topic;

    private String a;

    private String b;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime chooseAt;

}