package cronies.meeting.user.service;

import java.util.HashMap;

public interface AskMeService {

    public HashMap<String, Object> getUserAskMe(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> setAskMeCode(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> sendAskMe(HashMap<String, Object> param) throws Exception;

}