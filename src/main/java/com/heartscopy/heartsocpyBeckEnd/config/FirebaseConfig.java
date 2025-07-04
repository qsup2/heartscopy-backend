package com.heartscopy.heartsocpyBeckEnd.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class FirebaseConfig {

    private static final Logger logger = LoggerFactory.getLogger(FirebaseConfig.class);

    @PostConstruct
    public void init() {
        try {
            InputStream serviceAccount = new ClassPathResource("firebase/heartscopy-c5dae-firebase-adminsdk-k5m4j-db35d89050.json").getInputStream();

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                logger.info("FirebaseApp이 성공적으로 초기화되었습니다.");
            } else {
                logger.info("이미 초기화된 FirebaseApp이 존재합니다.");
            }

        } catch (IOException e) {
            logger.error("Firebase 초기화 중 IOException 발생", e);
            throw new RuntimeException("Firebase 초기화 실패", e);
        } catch (Exception e) {
            logger.error("Firebase 초기화 중 예외 발생", e);
            throw new RuntimeException("Firebase 초기화 실패", e);
        }
    }
}