package com.nse.pms.standard.common.service.impl;

import com.nse.pms.standard.common.service.CommonAuthService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import select.spring.exquery.dao.ExqueryDao;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;

@Service("commonAuthService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class CommonAuthServiceImpl implements CommonAuthService {

	@Inject
	@Named("exqueryDao")
	private ExqueryDao exqueryDao;

	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public boolean isValidAccess(String userId, String menuUrl) {
		
		HashMap<String, Object> aMap = new HashMap<String, Object>();
		aMap.put("userId", 		userId);
		aMap.put("menuUrl", 	menuUrl);
		HashMap<String, Object> checkResult = exqueryDao.selectOne("nse.pmsProgram.selectAuthUrl", aMap);
		if(checkResult == null){ 
			// 개인정보수정은 예외
			if(menuUrl.equals("muMainPerInfo")){
				return true;
			}
			return false;
		}
		return true;
	}	
	
}
