package cronies.meeting.user.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FCMConfig {

    //private static final Logger LOGGER = LoggerFactory.getLogger(FCMConfig.class);



    @PostConstruct
    private void initialFCM() throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = "fcmConfig/navy-69d5c-firebase-adminsdk-ifnll-6cf4099089.json";
        try(InputStream input = classLoader.getResourceAsStream(path)){
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(input))
                    .build();
            FirebaseApp.initializeApp(options);
            System.out.println("[N]Firebase init successfully.");
        }catch(IOException | IllegalStateException e){
            System.out.println("[N]Filebase no init. already exists");
        }
    }
}