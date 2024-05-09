package backend.backend.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Configuration
@Slf4j
public class FcmConfig {
    public static final String FIREBASE_ADMIN_KEY = "firebase/achieve-cc2aa-firebase-adminsdk-0ygsr-ff26214f49.json";
    @PostConstruct
    public void init() {
        try {
            FileInputStream resource = new FileInputStream(FIREBASE_ADMIN_KEY);
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.getApplicationDefault())
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (FileNotFoundException e) {
            log.error("FileNotFoundException {}", e.getMessage());
        } catch (IOException e) {
            log.error("FirebaseOptions IOException {}", e.getMessage());
        }
    }
}
