package com.nse.pms.standard.system.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nse.pms.standard.system.service.NseConfigService;
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

@Service("NseConfigService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class NseConfigServiceImpl implements NseConfigService {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExqueryService exqueryService;

	@Autowired
	private ConstraintService constraintService;
	
	/**
	* 리스트 조회 (다국어-화면용어)
	* @param
	* @return
	**/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> selectLangLabelList(HashMap<String, Object> param) throws Exception {

		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		// json ArrayList를 Java List로 변환
		List<String> searchList = new ArrayList<String>();
		ObjectMapper mapper = new ObjectMapper();
		searchList = mapper.readValue(param.get("searchMsgList").toString(), new TypeReference<ArrayList<String>>(){});

		param.put("searchList", searchList);

		resultMap = exqueryService.selectPagingList("nse.pms.system.sqlConfig.selectLangLabelList", param,
				Integer.parseInt(param.get("pageNo").toString()),
				Integer.parseInt(param.get("pageSize").toString()));

		return resultMap;

	}

	/**
	* 리스트 조회 (다국어-공통)
	* @param
	* @return
	**/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public List<HashMap<String, Object>> selectLangLabelListCommon(HashMap<String, Object> param) throws Exception {

		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();

		param.get("searchMsgList");
		// json ArrayList를 Java List로 변환
		List<String> searchList = new ArrayList<String>();
		ObjectMapper mapper = new ObjectMapper();
		searchList = mapper.readValue(param.get("searchMsgList").toString(), new TypeReference<ArrayList<String>>(){});

		param.put("searchList", searchList);

		resultList = exqueryService.selectList("nse.pms.system.sqlConfig.selectLangLabelList", param);

		return resultList;
	}


	/**
	 * 한건 조회 (다국어-화면용어)
	 * @param
	 * @return
	 **/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> selectLangLabelOne(HashMap<String, Object> param){

		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		resultMap = exqueryService.selectOne("nse.pms.system.sqlConfig.selectLangLabelOne", param);


		return resultMap;
	}


	/**
	 * 한건을 등록한다. (다국어-화면용어)
	 * @param
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> insertLangLabel(HashMap<String, Object> param) throws Exception {

		//constraint check
		String pk = "{'colName':'KEY','colValue':'"+param.get("key")+"'}";
		String con = "["
				+"{'tableName':'COM_MESSAGE_LABEL', 'pkList':["+pk+"], 'msgCode':'중복되었습니다.'}"
				+"]";
		String consResult = constraintService.checkExistsV2(con.replaceAll("'", "\""));
		HashMap<String, Object> resultMap = new HashMap<>();
		resultMap.put("resultMsg", consResult);

		boolean isExist = false;
		if(consResult.equals("OK")){
			exqueryService.insert("nse.pms.system.sqlConfig.insertLangLabelEn", param);
			exqueryService.insert("nse.pms.system.sqlConfig.insertLangLabelKo", param);
			exqueryService.insert("nse.pms.system.sqlConfig.insertLangLabelCn", param);
			isExist = true;
		}
		resultMap.put("isExist", isExist);
		return resultMap;
	}


	/**
	 * 한건을 수정한다. (다국어-화면용어)
	 * @param
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> updateLangLabel(HashMap<String, Object> param){

		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		exqueryService.update("nse.pms.system.sqlConfig.updateLangLabelEn", param);
		exqueryService.update("nse.pms.system.sqlConfig.updateLangLabelKo", param);
		exqueryService.update("nse.pms.system.sqlConfig.updateLangLabelCn", param);

		return resultMap;
	}

	/**
	 * 한건을 사용하지 않는다. (다국어-화면용어)
	 * @param
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> disableLangLabel(HashMap<String, Object> param){

		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		exqueryService.update("nse.pms.system.sqlConfig.disableLangLabel", param);

		return resultMap;
	}
	
	/**
	* 리스트 조회 (다국어-메시지)
	* @param
	* @return
	**/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> selectLangMsgList(HashMap<String, Object> param) throws Exception {

		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		// json ArrayList를 Java List로 변환
		List<String> searchList = new ArrayList<String>();
		ObjectMapper mapper = new ObjectMapper();
		searchList = mapper.readValue(param.get("searchMsgList").toString(), new TypeReference<ArrayList<String>>(){});

		param.put("searchList", searchList);

		resultMap = exqueryService.selectPagingList("nse.pms.system.sqlConfig.selectLangMsgList", param,
				Integer.parseInt(param.get("pageNo").toString()),
				Integer.parseInt(param.get("pageSize").toString()));

		return resultMap;

	}


	/**
	 * 리스트 조회 (다국어-메시지)
	 * @param
	 * @return
	 **/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public List<HashMap<String, Object>> selectLangMsgListCommon(HashMap<String, Object> param) throws Exception {


		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();

		param.get("searchMsgList");
		// json ArrayList를 Java List로 변환
		List<String> searchList = new ArrayList<String>();
		ObjectMapper mapper = new ObjectMapper();
		searchList = mapper.readValue(param.get("searchMsgList").toString(), new TypeReference<ArrayList<String>>(){});

		param.put("searchList", searchList);

		resultList = exqueryService.selectList("nse.pms.system.sqlConfig.selectLangMsgList", param);

		return resultList;

	}


	/**
	 * 한건 조회 (다국어-메시지)
	 * @param
	 * @return
	 **/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> selectLangMsgOne(HashMap<String, Object> param){

		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		resultMap = exqueryService.selectOne("nse.pms.system.sqlConfig.selectLangMsgOne", param);


		return resultMap;
	}


	/**
	 * 한건을 등록한다. (다국어-메시지)
	 * @param
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> insertLangMsg(HashMap<String, Object> param) throws Exception {

		//constraint check
		String pk = "{'colName':'KEY','colValue':'"+param.get("key")+"'}";
		String con = "["
				+"{'tableName':'COM_MESSAGE', 'pkList':["+pk+"], 'msgCode':'중복되었습니다.'}"
				+"]";
		String consResult = constraintService.checkExistsV2(con.replaceAll("'", "\""));
		HashMap<String, Object> resultMap = new HashMap<>();
		resultMap.put("resultMsg", consResult);

		boolean isExist = false;
		if(consResult.equals("OK")){
			exqueryService.insert("nse.pms.system.sqlConfig.insertLangMsgEn", param);
			exqueryService.insert("nse.pms.system.sqlConfig.insertLangMsgKo", param);
			exqueryService.insert("nse.pms.system.sqlConfig.insertLangMsgCn", param);
			isExist = true;
		}
		resultMap.put("isExist", isExist);
		return resultMap;
	}


	/**
	 * 한건을 수정한다. (다국어-메시지)
	 * @param
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> updateLangMsg(HashMap<String, Object> param){

		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		exqueryService.update("nse.pms.system.sqlConfig.updateLangMsgEn", param);
		exqueryService.update("nse.pms.system.sqlConfig.updateLangMsgKo", param);
		exqueryService.update("nse.pms.system.sqlConfig.updateLangMsgCn", param);

		return resultMap;
	}

	/**
	 * 한건을 사용하지 않는다. (다국어-메시지)
	 * @param
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> disableLangMsg(HashMap<String, Object> param){

		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		exqueryService.update("nse.pms.system.sqlConfig.disableLangMsg", param);

		return resultMap;
	}

	/**
	 * 일반코드 리스트 조회
	 * */
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> selectComCodeList(HashMap<String, Object> param) {

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = exqueryService.selectPagingList("nse.pms.system.sqlConfig.selectComCodeList", param,
				Integer.parseInt(param.get("pageNo").toString()),
				Integer.parseInt(param.get("pageSize").toString()));
		return resultMap;

	}

	/**
	 * 일안코드 한거 조회
	 * */
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> selectComCodeOne(HashMap<String, Object> param) {
		return exqueryService.selectOne("nse.pms.system.sqlConfig.selectComCodeOne", param);
	}

	/**
	 * 일반코드 insert
	 * */
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> insertComCode(HashMap<String, Object> param) throws Exception {
		//constraint check
		String pk = "{'colName':'CODE_CD','colValue':'"+param.get("codeCd")+"'}, {'colName':'CODE_VALUE','colValue':'"+param.get("codeValue")+"'}";
		String con = "["
				+"{'tableName':'COM_COMMONCODE', 'pkList':["+pk+"], 'msgCode':'중복되었습니다.'}"
				+"]";
		String consResult = constraintService.checkExistsV2(con.replaceAll("'", "\""));
		HashMap<String, Object> resultMap = new HashMap<>();
		resultMap.put("resultMsg", consResult);

		boolean isExist = false;
		if(consResult.equals("OK")){
			exqueryService.insert("nse.pms.system.sqlConfig.insertComCode", param);
			isExist = true;
		}
		resultMap.put("isExist", isExist);
		return resultMap;
	}

	/**
	 * 일반코드 업데이트
	 * */
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> updateComCode(HashMap<String, Object> param) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		boolean isExist = exqueryService.update("nse.pms.system.sqlConfig.updateComCode", param) > 0;
		resultMap.put("isExist", isExist);

		return resultMap;
	}

	/**
	 * 일반코드 사용여부 'N'
	 * */
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> disableComCode(HashMap<String, Object> param) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		boolean isExist = exqueryService.update("nse.pms.system.sqlConfig.disableComCode", param) > 0;
		resultMap.put("isExist", isExist);

		return resultMap;
	}

	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public List<HashMap<String, Object>> selectComCodeListByCd(HashMap<String, Object> param) {
		return exqueryService.selectList("nse.pms.system.sqlConfig.selectComCodeListByCd", param);
	}


	/**
	 * 한건 조회 (시스템설정)
	 * */
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> selectSysSetting(HashMap<String, Object> param) {
		return exqueryService.selectOne("nse.pms.system.sqlConfig.selectSysSetting", param);
	}


	/**
	 * 시스템설정 업데이트
	 * */
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> updateSysSetting(HashMap<String, Object> param) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		boolean isExist = exqueryService.update("nse.pms.system.sqlConfig.updateSysSetting", param) > 0;
		resultMap.put("isExist", isExist);

		return resultMap;
	}

}
