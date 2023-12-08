package cronies.app.service;

import java.util.HashMap;
import java.util.List;

public interface MessageService {

    public List<HashMap<String, Object>> getMessageList(HashMap<String, Object> param) throws Exception;
    public List<HashMap<String, Object>> getChatList(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> sendMsg(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> getChannelId(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> openChannel(HashMap<String, Object> param) throws Exception;

}
