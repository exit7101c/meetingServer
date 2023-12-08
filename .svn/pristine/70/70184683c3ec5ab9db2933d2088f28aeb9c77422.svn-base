package cronies.app.service.impl;

import cronies.app.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import select.spring.exquery.service.ExqueryService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service("messageService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class MessageServiceImpl implements MessageService {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExqueryService exqueryService;


    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<HashMap<String, Object>> getMessageList(HashMap<String, Object> param) throws Exception {
        List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
        resultList = exqueryService.selectList("cronies.app.message.getMessageList", param);
        return resultList;
    }
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<HashMap<String, Object>> getChatList(HashMap<String, Object> param) throws Exception {
        List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
        resultList = exqueryService.selectList("cronies.app.message.getChatList", param);
        return resultList;
    }
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> sendMsg(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        exqueryService.insert("cronies.app.message.sendMsg", param);
        exqueryService.update("cronies.app.message.channelUpdate",param);
        return resultMap;
    }
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> getChannelId(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        resultMap = exqueryService.selectOne("cronies.app.message.getChannelId", param);
        return resultMap;
    }
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> openChannel(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        exqueryService.insert("cronies.app.message.openChannel", param);
        return resultMap;
    }

}
