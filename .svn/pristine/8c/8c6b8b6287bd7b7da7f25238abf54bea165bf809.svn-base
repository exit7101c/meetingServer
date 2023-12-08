package com.nse.pms.standard.common.service.impl;

import java.util.HashMap;

import javax.inject.Inject;
import javax.inject.Named;

import com.nse.pms.standard.common.service.ExqueryLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import select.spring.exquery.dao.ExqueryDao;
import select.spring.exquery.service.ExqueryService;

@Service("exqueryLogService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ExqueryLogServiceImpl implements ExqueryLogService {

	@Autowired
	private ExqueryService exqueryService;
	
	@Inject
	@Named("exqueryDao")
	private ExqueryDao exqueryDao;

	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public int logIn(String clientIp, HashMap<String, Object> param) {

		/*if(logUse()){*/
			HashMap<String, Object> zMap = new HashMap<String, Object>();
			if(param.get("userId") == null){
				zMap.put("userId", 		param.get("customerId").toString());
				zMap.put("customerToken", 		param.get("customerToken").toString());
			} else {
				zMap.put("userId", 		param.get("userId").toString());
				zMap.put("userToken", 		param.get("userToken").toString());
			}
//			zMap.put("userId", 		param.get("userId").toString());
//			zMap.put("userToken", 		param.get("userToken").toString());
			zMap.put("userIp", 		clientIp);

			// 임시 mes로그인 로그 저장 X
			if(param.get("customerId") == null){
				//로그저장
				exqueryService.insert("nse.pms.common.sqlLogin.insertLoginHis", zMap);
				//사용자정보 업데이트
				exqueryService.update("nse.pms.common.sqlLogin.updateUserLogin", zMap);
			}
//			//로그저장
//			exqueryService.insert("nse.pms.common.sqlLogin.insertLoginHis", zMap);
			int logId = Integer.parseInt(zMap.get("newId").toString());
//			//사용자정보 업데이트
//			exqueryService.update("nse.pms.common.sqlLogin.updateUserLogin", zMap);

			return logId;

		/*}*/
	}
	
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public void logOut(HashMap<String, Object> param) {
		
		if(logUse()){
			HashMap<String, Object> zMap = new HashMap<String, Object>();
			zMap.put("logUserId", 		param.get("ssUserId").toString());
			zMap.put("logProgramCd", 	"로그인");
			zMap.put("logAction", 		"LOGOUT");
			zMap.put("logUserIp", 		"");
			exqueryService.insert("nse.pmsUser.insertUserLog", zMap);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public void insert(String id, HashMap<String, Object> sqlParam, HashMap<String, Object> param) {
		
		if(logUse()){
			String sqlText = extractString(id, sqlParam);
			
			HashMap<String, Object> zMap = new HashMap<String, Object>();
			zMap.put("logUserId", 		param.get("ssUserId").toString());
			zMap.put("logProgramCd", 	param.get("logProgramCd").toString());
			zMap.put("logAction", 		"NEW");
			zMap.put("logDetails", 		sqlText);
			exqueryService.insert("nse.pmsUser.insertUserLog", zMap);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public void update(String id, HashMap<String, Object> sqlParam, HashMap<String, Object> param) {

		if(logUse()){
			String sqlText = extractString(id, sqlParam);
			
			HashMap<String, Object> zMap = new HashMap<String, Object>();
			zMap.put("logUserId", 		param.get("ssUserId").toString());
			zMap.put("logProgramCd", 	param.get("logProgramCd").toString());
			zMap.put("logAction", 		"UPDATE");
			zMap.put("logDetails", 		sqlText);
			exqueryService.insert("nse.pmsUser.insertUserLog", zMap);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public void delete(String id, HashMap<String, Object> sqlParam, HashMap<String, Object> param) {

		if(logUse()){
			String sqlText = extractString(id, sqlParam);
			
			HashMap<String, Object> zMap = new HashMap<String, Object>();
			zMap.put("logUserId", 		((HashMap<String,Object>)param).get("ssUserId").toString());
			zMap.put("logProgramCd", 	((HashMap<String,Object>)param).get("logProgramCd").toString());
			zMap.put("logAction", 		"DELETE");
			zMap.put("logDetails", 		sqlText);
			exqueryService.insert("nse.pmsUser.insertUserLog", zMap);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public void download(HashMap<String, Object> fileInfo, HashMap<String, Object> param) {

		if(logUse()){
			String fileName = (String)fileInfo.get("orgFileName");
			String sqlText = "downloaded the file [ "+fileName+" ]";
			
			HashMap<String, Object> zMap = new HashMap<String, Object>();
			zMap.put("logUserId", 		((HashMap<String,Object>)param).get("ssUserId").toString());
			zMap.put("logProgramCd", 	((HashMap<String,Object>)param).get("logProgramCd").toString());
			zMap.put("logAction", 		"DOWNLOAD");
			zMap.put("logDetails", 		sqlText);
			exqueryService.insert("nse.pmsUser.insertUserLog", zMap);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	private String extractString(String sqlId, HashMap<String, Object> param){
		
		String sqlText	= exqueryDao.getSelectString(sqlId, param);
		sqlText = sqlText.replaceAll("\t", "");
		sqlText = sqlText.replaceAll(System.getProperty("line.separator"), "");
		
		return sqlText;
	}
	
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	private Boolean logUse(){
		
		
		HashMap<String, Object> aMap = new HashMap<String, Object>();
		aMap.put("code", "LOG_USE");
		
		HashMap<String, Object> bMap = exqueryService.selectOne("nse.pmsConf.selectConfOne", aMap);
		Boolean bool = (bMap.get("val").equals("Y")) ? true : false; 
		return bool;
	}
	
}