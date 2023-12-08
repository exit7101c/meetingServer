package cronies.app.service.impl;

import cronies.app.service.UserListService;
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

@Service("userListService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserListServiceImpl implements UserListService {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExqueryService exqueryService;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String, Object>> getUserList(HashMap<String, Object> param) throws Exception {
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		resultList = exqueryService.selectList("cronies.app.userList.getUserList", param);
		return resultList;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> checkMsgChannel(HashMap<String, Object> param) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = exqueryService.selectOne("cronies.app.userList.checkMsgChannel", param);
		return resultMap;
	}

}