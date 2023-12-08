package cronies.app.service.impl;

import cronies.app.service.MessageTalkService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import select.spring.exquery.service.ExqueryService;

@Service("messageTalkService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class MessageTalkServiceImpl implements MessageTalkService {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExqueryService exqueryService;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String, Object>> getMessageList(HashMap<String, Object> param) throws Exception {
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		List<HashMap<String, Object>> messageList = new ArrayList<HashMap<String, Object>>();
		//메시지 날짜별 그룹
		resultList = exqueryService.selectList("cronies.app.messageTalk.getMessageListDateGroup", param);
		for(int i = 0; i < resultList.size(); i++){
			param.put("chatDate", resultList.get(i).get("chatDate2"));
			messageList = exqueryService.selectList("cronies.app.messageTalk.getMessageList", param);
			resultList.get(i).put("messageList", messageList);
		}

		return resultList;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> updateMessageRecept(HashMap<String, Object> param) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		exqueryService.update("cronies.app.messageTalk.updateMessageRecept", param);
		return resultMap;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> sendMessageSingle(HashMap<String, Object> param) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		HashMap<String, Object> selectMaxMap = new HashMap<String, Object>();
		HashMap<String, Object> insertMessageInfo = new HashMap<String, Object>();

		selectMaxMap = exqueryService.selectOne("cronies.app.messageTalk.selectMessageMasterMaxId", param);
		param.put("maxMessageId", selectMaxMap.get("maxMessageId"));
		exqueryService.insert("cronies.app.messageTalk.sendMessageMasterSingleLoginSeq", param);
		exqueryService.insert("cronies.app.messageTalk.sendMessageDetailSingleUserSeq", param);
		exqueryService.update("cronies.app.messageTalk.updateMessageDetailLastChat", param);

		insertMessageInfo = exqueryService.selectOne("cronies.app.messageTalk.getMessageOneByMessageId", param);

		resultMap.put("insertMessageInfo", insertMessageInfo);

		return resultMap;
	}


}