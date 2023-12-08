package cronies.app.service;

import java.util.HashMap;
import java.util.List;

public interface MessageTalkService {

    public List<HashMap<String, Object>> getMessageList(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> updateMessageRecept(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> sendMessageSingle(HashMap<String, Object> param) throws Exception;

}