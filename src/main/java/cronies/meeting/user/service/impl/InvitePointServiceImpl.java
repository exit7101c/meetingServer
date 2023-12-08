package cronies.meeting.user.service.impl;

import cronies.meeting.user.service.CommonService;
import cronies.meeting.user.service.InvitePointService;
import cronies.meeting.user.service.PushService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import select.spring.exquery.service.ExqueryService;

import java.util.HashMap;

@Service("InvitePointService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class InvitePointServiceImpl implements InvitePointService {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExqueryService exqueryService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private PushService pushService;


    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getUserAskMeA(HashMap<String, Object> param) throws Exception {
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

}
