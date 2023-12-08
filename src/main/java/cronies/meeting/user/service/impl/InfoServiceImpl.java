package cronies.meeting.user.service.impl;

import cronies.meeting.user.service.CommonService;
import cronies.meeting.user.service.InfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import select.spring.exquery.service.ExqueryService;

import java.util.HashMap;

@Service("InfoService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class InfoServiceImpl implements InfoService {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExqueryService exqueryService;

    @Autowired
    private CommonService commonService;

    @Override
    public HashMap<String, Object> getTerm(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        String term = "";

        if(param.get("termCd") != null) {
            HashMap<String, Object> termMap = exqueryService.selectOne("cronies.app.join.selectGetTerm", param);

            term = termMap.get("contents").toString();
        }
        resultMap.put("term", term);

        return resultMap;
    }

}
