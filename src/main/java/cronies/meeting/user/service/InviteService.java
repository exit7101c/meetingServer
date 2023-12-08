package cronies.meeting.user.service;

import java.util.HashMap;

public interface InviteService {

    public HashMap<String, Object> getUserInviteCode(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> saveTargetInviteCode(HashMap<String, Object> param) throws Exception;

    public HashMap<String, Object> checkNanoId(HashMap<String, Object> param) throws Exception;

}