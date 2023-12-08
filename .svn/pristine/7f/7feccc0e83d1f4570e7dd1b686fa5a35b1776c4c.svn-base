package cronies.meeting.user.service.impl;

import cronies.meeting.user.service.CommonService;
import cronies.meeting.user.service.PushService;
import cronies.meeting.user.service.AskMeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import select.spring.exquery.service.ExqueryService;

import java.util.HashMap;

@Service("AskMeService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AskMeServiceImpl implements AskMeService {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExqueryService exqueryService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private PushService pushService;


    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getUserAskMe(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        HashMap<String, Object> askMeMap = new HashMap<String, Object>();
        HashMap<String, Object> askMeListMap = new HashMap<String, Object>();
        param.put("userId", param.get("ssUserId"));
        askMeMap = exqueryService.selectOne("cronies.app.askMe.getAskMeCode", param);
        askMeListMap = exqueryService.selectPagingList("cronies.app.askMe.getAskMeList", param,
                Integer.parseInt(param.get("pageNo").toString()),
                Integer.parseInt(param.get("pageSize").toString()));

        resultMap.put("askMeInfo", askMeMap);
        resultMap.put("askMeList", askMeListMap);

        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> setAskMeCode(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        param.put("userId", param.get("ssUserId"));
        resultMap.put("successYn", "Y");

        Boolean isExist = false;
        String nanoId = "";

        try {
            while(!isExist){
                // nanoId 생성
                nanoId = commonService.getNanoId(6);
                // nanoId 중복체크
                param.put("askMeCode", nanoId);
                if(isAskMeCode(param) == null){
                    exqueryService.update("cronies.app.askMe.mergeUserAskMeCode", param);
                    returnMap = exqueryService.selectOne("cronies.app.askMe.getAskMeCode", param);
                    resultMap.put("askMeInfo", returnMap);
                    isExist = true;
                } else {
                    resultMap.put("successYn", "N");
                    resultMap.put("message", "URL 코드가 중복되었습니다. 재생성 부탁드립니다.");
                }
            }
        } catch (Exception ex) {
            resultMap.put("successYn", "N");
            resultMap.put("message", "ASK ME 코드 생성 중 오류가 발생하였습니다. 문의 부탁드립니다.");
        }

        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> isAskMeCode(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        resultMap = exqueryService.selectOne("cronies.app.askMe.isAskMeCode", param);
        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> sendAskMe(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        param.put("userId", commonService.getDecoding(String.valueOf(param.get("userKey"))));
        resultMap.put("successYn", "Y");

        try {
            if(isAskMeCode(param) != null){
                returnMap = exqueryService.selectOne("cronies.app.askMe.getAskMeBeforeSend", param);
                if(returnMap == null){
                    exqueryService.insert("cronies.app.askMe.insertAskMe", param);
                    exqueryService.update("cronies.app.askMe.updateAskMeUserCurrentInfo", param);
                    resultMap.put("successYn", "Y");
                    resultMap.put("message", "메시지가 전송되었습니다.");
                } else {
                    resultMap.put("successYn", "N");
                    resultMap.put("message", "1시간 이내에 재전송 하실 수 없습니다.");
                }
            } else {
                resultMap.put("successYn", "N");
                resultMap.put("message", "유효하지 않은 URL입니다. 재접속 후 시도바랍니다.");
            }
        } catch (Exception ex) {
            resultMap.put("successYn", "N");
            resultMap.put("message", "ASK ME를 보내는 과정 중 오류가 발생하였습니다. 문의 부탁드립니다.");
        }

        return resultMap;
    }

}
