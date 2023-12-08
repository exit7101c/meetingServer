package cronies.meeting.user.service;

import org.springframework.scheduling.annotation.Async;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public interface FcmService {

    @Async
    public void sendMessageTo(String targetToken, String title, String body) throws IOException;
    @Async
    public void sendMessageTo(String pushType, String targetToken, String title, String body) throws IOException;
    @Async
    public void sendMessageTo(String pushType, String targetToken, String title, String body, HashMap<String, Object> data) throws IOException;
    @Async
    public void sendMessageToMulti(String pushType, List<String> tokenList, String title, String body, HashMap<String, Object> data) throws IOException;

}