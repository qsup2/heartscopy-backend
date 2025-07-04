package com.heartscopy.heartsocpyBeckEnd.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Component
public class FirebaseConfig {

    private static final Logger logger = LoggerFactory.getLogger(FirebaseConfig.class);

    @PostConstruct
    public void init() {
        try {
            String firebaseJson = System.getenv("FIREBASE_CONFIG_JSON");

            if (firebaseJson == null || firebaseJson.isBlank()) {
                logger.error("FIREBASE_CONFIG_JSON 환경변수가 설정되지 않았습니다.");
                throw new IllegalStateException("FIREBASE_CONFIG_JSON 환경변수가 설정되지 않았습니다.");
            }

            logger.info("FIREBASE_CONFIG_JSON 환경변수가 정상적으로 로드되었습니다.");

            InputStream serviceAccount = new ByteArrayInputStream(firebaseJson.getBytes(StandardCharsets.UTF_8));

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                logger.info("FirebaseApp이 성공적으로 초기화되었습니다.");
            } else {
                logger.info("이미 초기화된 FirebaseApp이 존재합니다.");
            }

        } catch (Exception e) {
            logger.error("Firebase 초기화 중 예외 발생", e);
            throw new RuntimeException("Firebase 초기화 실패", e);
        }
    }
}