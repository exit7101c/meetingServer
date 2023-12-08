package cronies.meeting.user.service.impl;

import cronies.meeting.user.service.InviteService;
import cronies.meeting.user.service.CommonService;
import cronies.meeting.user.service.PushService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import select.spring.exquery.service.ExqueryService;

import java.util.HashMap;
import java.util.UUID;

@Service("InviteService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class InviteServiceImpl implements InviteService {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExqueryService exqueryService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private PushService pushService;


    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getUserInviteCode(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        param.put("userId", param.get("ssUserId"));

        resultMap = exqueryService.selectOne("cronies.app.invite.getUserInviteCode", param);
        String nanoId = "";
        Boolean isExist = false;
        if(resultMap != null){
            isExist = true;
            resultMap.put("successYn", "Y");
        }

        while(!isExist){
            // nanoId 생성
            nanoId = commonService.getNanoId(6);
            // nanoId 중복체크
            param.put("nanoId", nanoId);
            if(checkNanoId(param) == null){
                try {
                    exqueryService.insert("cronies.app.invite.insertInviteInfo", param);
                    resultMap = exqueryService.selectOne("cronies.app.invite.getUserInviteCode", param);
                    resultMap.put("successYn", "Y");
                } catch (Exception e) {
                    resultMap.put("successYn", "N");
                    resultMap.put("message", "초대코드 확인 중 오류가 발생하였습니다. 문의 부탁드립니다.");
                }
                isExist = true;
            }
        }

        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> saveTargetInviteCode(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        param.put("userId", param.get("ssUserId"));

        // 초대코드 등록 여부 확인
        if(exqueryService.selectOne("cronies.app.invite.checkInviteHis", param) == null){
            // 초대코드가 유효한지 확인
            resultMap = exqueryService.selectOne("cronies.app.invite.checkTargetUserInviteCode", param);
            if(resultMap == null){
                resultMap = new HashMap<String, Object>();
                resultMap.put("successYn", "N");
                resultMap.put("message", "유효하지 않은 상대방 코드입니다.");
            } else {
                try {
                    param.put("targetUserId", resultMap.get("userId"));
                    param.put("inviteUserId", param.get("userId"));

                    // 친구코드 등록
                    exqueryService.insert("cronies.app.invite.insertInviteCode", param);
                    exqueryService.insert("cronies.app.invite.insertInvitePointHis", param);
                    exqueryService.update("cronies.app.invite.updateInvitePoint", param);
//                    exqueryService.update("cronies.app.invite.updateInvitePoint2", param);
                    resultMap = exqueryService.selectOne("cronies.app.invite.getUserInviteCode", param);
                    resultMap.put("successYn", "Y");
                    resultMap.put("message", "초대코드 등록에 성공하였습니다!");
                } catch (Exception e) {
                    resultMap.put("successYn", "N");
                    resultMap.put("message", "초대코드 등록 중 오류가 발생하였습니다. 문의 부탁드립니다.");
                }
            }
        } else {
            resultMap.put("successYn", "N");
            resultMap.put("message", "이미 초대코드를 등록하셨습니다.");
        }

        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> checkNanoId(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        resultMap = exqueryService.selectOne("cronies.app.invite.checkNanoId", param);
        return resultMap;
    }

}
