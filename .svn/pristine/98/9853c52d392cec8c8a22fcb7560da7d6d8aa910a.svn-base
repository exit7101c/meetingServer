package com.nse.pms.standard.basic.service.impl;

import com.nse.pms.standard.basic.service.NseMtrlService;
import com.nse.pms.standard.common.service.ConstraintService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import select.spring.exquery.service.ExqueryService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Service("mtrlService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class NseMtrlServiceImpl implements NseMtrlService {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExqueryService exqueryService;
	
	@Autowired
	private ConstraintService constraintService;
	
	/**
	* 자재그룹 (Mtrl Group) 정보 조회
	* @param List<HashMap<String, Object>>
	* @return HashMap<String, Object>
	**/	
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> selectMtrlGroupList(HashMap<String, Object> param){
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = exqueryService.selectPagingList("nse.pms.basic.sqlMtrl.selectMtrlGroupList", param,
				Integer.parseInt(param.get("pageNo").toString()),
				Integer.parseInt(param.get("pageSize").toString()));
		return resultMap;		
	}

	/**
	 * 자재그룹 (Mtrl Group) 정보 조회
	 * @param List<HashMap<String, Object>>
	 * @return HashMap<String, Object>
	 **/
	public List<HashMap<String, Object>> selectNonPageMtrlGroupList(HashMap<String, Object> param){
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		resultList = exqueryService.selectList("nse.pms.basic.sqlMtrl.selectMtrlGroupList", param);
		return resultList;
	}
	
	/**
	* 자재그룹 (Mtrl Group) 정보 단건 조회
	* @param HashMap<String, Object>
	* @return HashMap<String, Object>
	**/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> selectMtrlGroupOne(HashMap<String, Object> param){

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = exqueryService.selectOne("nse.pms.basic.sqlMtrl.selectMtrlGroupOne", param);
		return resultMap;
	}
	
	/**
	* 자재그룹 (Mtrl Group) 정보 등록
	* @param HashMap<String, Object>
	* @return HashMap<String, Object>
	**/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> insertMtrlGroup(HashMap<String, Object> param) throws Exception {
			
		//constraint check
		// 수정해야됨
		String pk = "{'colName':'MTRL_GROUP_CD','colValue':'"+param.get("mtrlGroupCd")+"'}";
		String con = "["+"{'tableName':'COM_MATERIAL_GROUP', 'pkList':["+pk+"], 'msgCode':'중복되었습니다.'}"+"]";
		String consResult = constraintService.checkExistsV2(con.replaceAll("'", "\""));
		HashMap<String, Object> resultMap = new HashMap<>();
		resultMap.put("resultMsg", consResult);
		
		boolean isExist = false;
		
		if(consResult.equals("OK")){
			exqueryService.insert("nse.pms.basic.sqlMtrl.insertMtrlGroup", param);
			isExist = true;
		}
		resultMap.put("isExist", isExist);
		return resultMap;
	}

	/**
	* 자재그룹 (Mtrl Group) 정보 수정
	* @param HashMap<String, Object>
	* @return HashMap<String, Object>
	**/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> updateMtrlGroup(HashMap<String, Object> param){
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		exqueryService.update("nse.pms.basic.sqlMtrl.updateMtrlGroup", param);
		return resultMap;
		
	}
	
	/**
	* 자재그룹 (Mtrl Group) 정보 삭제
	* @param HashMap<String, Object>
	* @return HashMap<String, Object>
	**/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> deleteMtrlGroup(HashMap<String, Object> param){
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		exqueryService.update("nse.pms.basic.sqlMtrl.deleteMtrlGroup", param);
		return resultMap;
		
	}
	
	/**
	* 자재유형 (Mtrl Type) 정보 조회
	* @param List<HashMap<String, Object>>
	* @return HashMap<String, Object>
	**/	
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public List<HashMap<String, Object>> selectMtrlTypeList(HashMap<String, Object> param){
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		resultList = exqueryService.selectList("nse.pms.basic.sqlMtrl.selectMtrlTypeList", param);
		return resultList;		
	}
	
	/**
	* 자재유형 (Mtrl Type) 정보 단건 조회
	* @param HashMap<String, Object>
	* @return HashMap<String, Object>
	**/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> selectMtrlTypeOne(HashMap<String, Object> param){
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = exqueryService.selectOne("nse.pms.basic.sqlMtrl.selectMtrlTypeOne", param);
		return resultMap;
	}
	
	/**
	* 자재유형 (Mtrl Type) 정보 등록
	* @param HashMap<String, Object>
	* @return HashMap<String, Object>
	**/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> insertMtrlType(HashMap<String, Object> param) throws Exception {
			
		//constraint check
		// 수정해야됨
		String pk = "{'colName':'MTRL_GROUP_CD','colValue':'"+param.get("mtrlGroupCd")+"'}";
		String con = "["+"{'tableName':'COM_MATERIAL_GROUP', 'pkList':["+pk+"], 'msgCode':'중복되었습니다.'}"+"]";
		String consResult = constraintService.checkExistsV2(con.replaceAll("'", "\""));
		HashMap<String, Object> resultMap = new HashMap<>();
		resultMap.put("resultMsg", consResult);
		
		boolean isExist = false;
		
		if(consResult.equals("OK")){
			exqueryService.insert("nse.pms.basic.sqlMtrl.insertMtrlType", param);
			isExist = true;
		}
		resultMap.put("isExist", isExist);
		return resultMap;
	}

	/**
	* 자재유형 (Mtrl Type) 정보 수정
	* @param HashMap<String, Object>
	* @return HashMap<String, Object>
	**/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> updateMtrlType(HashMap<String, Object> param){
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		exqueryService.update("nse.pms.basic.sqlMtrl.updateMtrlType", param);
		return resultMap;
		
	}
	
	/**
	* 자재유형 (Mtrl Type) 정보 삭제
	* @param HashMap<String, Object>
	* @return HashMap<String, Object>
	**/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> deleteMtrlType(HashMap<String, Object> param){
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		exqueryService.update("nse.pms.basic.sqlMtrl.deleteMtrlType", param);
		return resultMap;
		
	}
	
}
