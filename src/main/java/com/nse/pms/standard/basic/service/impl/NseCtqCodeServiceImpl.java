package com.nse.pms.standard.basic.service.impl;

import com.nse.pms.standard.basic.service.NseCtqCodeService;
import com.nse.pms.standard.common.service.ConstraintService;
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

@Service("NseCtqCodeService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class NseCtqCodeServiceImpl implements NseCtqCodeService {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExqueryService exqueryService;

	@Autowired
	private ConstraintService constraintService;
	
	/**
	* 리스트 조회
	* @param
	* @return
	**/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public List<HashMap<String, Object>> selectCtqCodeList(HashMap<String, Object> param){

		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();

		resultList = exqueryService.selectList("nse.pms.basic.sqlCtqCode.selectCtqCodeList", param);

		return resultList;
	}


	/**
	 * 한건 조회
	 * @param
	 * @return
	 **/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> selectCtqCodeOne(HashMap<String, Object> param){

		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		resultMap = exqueryService.selectOne("nse.pms.basic.sqlCtqCode.selectCtqCodeOne", param);


		return resultMap;
	}


	/**
	 * 한건을 등록한다.
	 * @param
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> insertCtqCode(HashMap<String, Object> param) throws Exception {

		//constraint check
		String pk = "{'colName':'CTQ_CD','colValue':'"+param.get("ctqCd")+"'}";
		String con = "["
				+"{'tableName':'COM_CTQ_CODE', 'pkList':["+pk+"], 'msgCode':'중복되었습니다.'}"
				+"]";
		String consResult = constraintService.checkExistsV2(con.replaceAll("'", "\""));
		HashMap<String, Object> resultMap = new HashMap<>();
		resultMap.put("resultMsg", consResult);
		boolean isExist = false;
		if(consResult.equals("OK")){
			exqueryService.insert("nse.pms.basic.sqlCtqCode.insertCtqCode", param);

			if(param.get("tagCd") != ""){
				exqueryService.insert("nse.pms.basic.sqlCtqCode.insertCtqTag", param);
			}

			isExist = true;
		}
		resultMap.put("isExist", isExist);
		return resultMap;
	}


	/**
	 * 한건을 수정한다.
	 * @param
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> updateCtqCode(HashMap<String, Object> param){

		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		exqueryService.update("nse.pms.basic.sqlCtqCode.updateCtqCode", param);
		if(param.get("tagCd") != ""){
			exqueryService.insert("nse.pms.basic.sqlCtqCode.insertCtqTag", param);
		}

		return resultMap;
	}

	/**
	 * 한건을 사용하지 않는다.
	 * @param
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> disableCtqCode(HashMap<String, Object> param){

		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		exqueryService.update("nse.pms.basic.sqlCtqCode.disableCtqCode", param);
		exqueryService.delete("nse.pms.basic.sqlCtqCode.disableCtqTag", param);

		return resultMap;
	}


}
