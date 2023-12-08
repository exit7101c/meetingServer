package cronies.meeting.user.service;

import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;

public interface JoinService {

    HashMap<String, Object> getCerti(HashMap<String, Object> param) throws Exception;
    HashMap<String, Object> getCheckEmail(HashMap<String, Object> param) throws Exception;
    HashMap<String, Object> getCheckUser(HashMap<String, Object> param) throws Exception;
    HashMap<String, Object> getCheckNick(HashMap<String, Object> param) throws Exception;
    HashMap<String, Object> setUserStep1(HashMap<String, Object> param) throws Exception;
    HashMap<String, Object> setUserStep2(HashMap<String, Object> param) throws Exception;
    HashMap<String, Object> setUserStep3(HashMap<String, Object> param) throws Exception;
    HashMap<String, Object> setUserStep4(HashMap<String, Object> param) throws Exception;
    HashMap<String, Object> setUserStep5(HashMap<String, Object> param) throws Exception;
    HashMap<String, Object> setUserStep6(HashMap<String, Object> param) throws Exception;
    HashMap<String, Object> setUserStep8(HashMap<String, Object> param) throws Exception;
    List<HashMap<String, Object>> getIconList(HashMap<String, Object> param) throws Exception;
    List<HashMap<String, Object>> getJobList(HashMap<String, Object> param) throws Exception;
    List<HashMap<String, Object>> getSchoolList(HashMap<String, Object> param) throws Exception;

    HashMap<String, Object> setUserPicJoin(HashMap<String, Object> param) throws Exception;

    HashMap<String, Object> getKeywordList(HashMap<String, Object> param) throws Exception;
    HashMap<String, Object> getInfoList(HashMap<String, Object> param) throws Exception;

    HashMap<String, Object> getInsertUser(HashMap<String, Object> param) throws Exception;
    HashMap<String, Object> getUpdateUser(HashMap<String, Object> param) throws Exception;
}