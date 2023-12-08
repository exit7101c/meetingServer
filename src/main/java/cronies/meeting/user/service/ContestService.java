package cronies.meeting.user.service;

import java.util.HashMap;
import java.util.List;

public interface ContestService {

    HashMap<String, Object> getContestCheck(HashMap<String, Object> param) throws Exception;
    HashMap<String, Object> getContestSupport(HashMap<String, Object> param) throws Exception;
    List<HashMap<String, Object>> getContestUserList(HashMap<String, Object> param) throws Exception;
    HashMap<String, Object> getContestDetail(HashMap<String, Object> param) throws Exception;
    HashMap<String, Object> getVoteHis(HashMap<String, Object> param) throws Exception;
    List<HashMap<String, Object>> getContestReplyInfinite(HashMap<String, Object> param) throws Exception;
}
