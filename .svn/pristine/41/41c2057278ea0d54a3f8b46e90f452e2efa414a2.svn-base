package cronies.meeting.user.service.impl;

import cronies.meeting.user.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import select.spring.exquery.service.ExqueryService;

import java.util.ArrayList;
import java.util.HashMap;

@Service("UsePointService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UsePointServiceImpl implements UsePointService {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExqueryService exqueryService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private PushService pushService;


    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getUserQrcode(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        param.put("userId", param.get("ssUserId"));
        resultMap = exqueryService.selectOne("cronies.app.usePoint.getUserQrcode", param);
        if(resultMap == null){
            resultMap = new HashMap<String, Object>();
        }
        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> setUserQrcode(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        param.put("userId", param.get("ssUserId"));

        try {
            exqueryService.update("cronies.app.usePoint.mergeUserQrcode", param);
            returnMap = exqueryService.selectOne("cronies.app.usePoint.getUserQrcode", param);
            resultMap.put("successYn", "Y");
            resultMap.put("qrcode", returnMap.get("qrcode"));
            resultMap.put("diffSecond", returnMap.get("diffSecond"));
        } catch (Exception ex) {
            resultMap.put("successYn", "N");
            resultMap.put("message", "QR코드 생성 중 오류가 발생하였습니다. 문의 부탁드립니다.");
        }
        return resultMap;
    }

}
