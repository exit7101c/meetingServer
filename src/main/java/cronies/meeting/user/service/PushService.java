package cronies.meeting.user.service;

import java.util.HashMap;
import java.util.List;

public interface PushService {

    public HashMap<String, Object> sendPushMessage(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> sendPushMessageMulti(HashMap<String, Object> param, List<String> tokenList) throws Exception;

}