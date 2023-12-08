package cronies.meeting.user.service.impl;

import cronies.meeting.user.service.CommonService;
import cronies.meeting.user.service.PointService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import select.spring.exquery.service.ExqueryService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service("PointService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class PointServiceImpl implements PointService {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExqueryService exqueryService;

    @Autowired
    private CommonService commonService;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> setPointByBuyHeart(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        HashMap<String, Object> userPointMap = new HashMap<String, Object>();
        HashMap<String, Object> targetHeartMap = new HashMap<String, Object>();

        param.put("userId", param.get("ssUserId"));

        try {
            // 구매 할 하트 수량 조회
            targetHeartMap = exqueryService.selectOne("cronies.app.storeCommon.getStoreHeartCntByItemCd", param);
            param.put("targetHeart", targetHeartMap.get("payment").toString());
            // 현재 하트 조회
            userPointMap = exqueryService.selectOne("cronies.app.point.getUserLastPoint", param);
            param.put("currentHeart", userPointMap.get("lastPoint").toString());
            param.put("sumHeart", Integer.parseInt(param.get("currentHeart").toString())+Integer.parseInt(param.get("targetHeart").toString()));
            param.put("priceWon", targetHeartMap.get("priceWon").toString());
            // ITEM 구매 이력
            exqueryService.insert("cronies.app.storeProcess.insertStoreBuyHis", param);

            // 하트 소모 이력 저장
            HashMap<String, Object> returnMap = setPointHis(param);
            if(!returnMap.get("successYn").toString().equals("Y")){
                resultMap.put("successYn", returnMap.get("successYn"));
                resultMap.put("message", returnMap.get("message"));
            } else {
                resultMap.put("successYn", "Y");
                resultMap.put("message", "구매되었습니다.");
                resultMap.put("lastPoint", param.get("sumHeart"));
            }

        } catch (Exception ex) {
            resultMap.put("successYn", "N");
            resultMap.put("message", "하트 구매 중 오류가 발생하였습니다. 문의 부탁드립니다.");
        }
        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> setPointHis(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        param.put("userId", param.get("ssUserId"));

        try {
            // 하트 이력 INSERT
            exqueryService.insert("cronies.app.point.insertHeartHis", param);
            // 최종 하트 수 MERGE
            exqueryService.update("cronies.app.point.mergeHeartCurrent", param);
            resultMap.put("successYn", "Y");

        } catch (Exception ex) {
            resultMap.put("successYn", "N");
            resultMap.put("message", "하트 처리 중 오류가 발생하였습니다. 문의 부탁드립니다.");
        }
        return resultMap;
    }

}
