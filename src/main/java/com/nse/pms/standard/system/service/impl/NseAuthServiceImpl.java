package com.nse.pms.standard.system.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nse.pms.standard.common.service.ConstraintService;
import com.nse.pms.standard.system.service.NseAuthService;
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
import java.util.Map;

@Service("NseAuthService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class NseAuthServiceImpl implements NseAuthService {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExqueryService exqueryService;

	@Autowired
	private ConstraintService constraintService;
	
	/**
	* 리스트 조회 (메뉴등록)
	* @param
	* @return
	**/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public List<HashMap<String, Object>> selectMenuList(HashMap<String, Object> param) throws Exception {

		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();

		resultList = exqueryService.selectList("nse.pms.system.sqlAuth.selectMenuList", param);

		return resultList;
	}


	/**
	 * 한건 조회 (메뉴등록)
	 * @param
	 * @return
	 **/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> selectMenuOne(HashMap<String, Object> param){

		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		resultMap = exqueryService.selectOne("nse.pms.system.sqlAuth.selectMenuOne", param);

		return resultMap;
	}


	/**
	 * 한건을 등록한다. (메뉴등록)
	 * @param
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> insertMenu(HashMap<String, Object> param) throws Exception {

		//constraint check
		String pk = "{'colName':'MENU_CD','colValue':'"+param.get("menuCd")+"'}";
		String con = "["
				+"{'tableName':'COM_MENU', 'pkList':["+pk+"], 'msgCode':'중복되었습니다.'}"
				+"]";
		String consResult = constraintService.checkExistsV2(con.replaceAll("'", "\""));
		HashMap<String, Object> resultMap = new HashMap<>();
		resultMap.put("resultMsg", consResult);

		boolean isExist = false;
		if(consResult.equals("OK")){
			exqueryService.insert("nse.pms.system.sqlAuth.insertMenu", param);
			isExist = true;
		}
		resultMap.put("isExist", isExist);
		return resultMap;
	}


	/**
	 * 한건을 수정한다. (메뉴등록)
	 * @param
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> updateMenu(HashMap<String, Object> param){

		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		exqueryService.update("nse.pms.system.sqlAuth.updateMenu", param);

		return resultMap;
	}

	/**
	 * 한건을 사용하지 않는다. (메뉴등록)
	 * @param
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> disableMenu(HashMap<String, Object> param){

		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		exqueryService.update("nse.pms.system.sqlAuth.disableMenu", param);

		return resultMap;
	}

	/**
	* 사용자 권한 조회
	* @param
	* @return
	**/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public List<HashMap<String, Object>> selectUserAuthList(HashMap<String, Object> param) throws Exception {

		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();

		resultList = exqueryService.selectList("nse.pms.system.sqlAuth.selectUserAuthList", param);

		return resultList;
	}

	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> deleteUserAuth(HashMap<String, Object> param) throws Exception {

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean isExist = false;

		int resultCnt = exqueryService.delete("nse.ftsWorker.deleteWorker", param);

		isExist =  resultCnt > 0 ;

		resultMap.put("isExist", isExist);

		return resultMap;

	}
	
	
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> insertUserAuth(HashMap<String, Object> params) throws Exception {


		boolean isExist = true;		
		HashMap<String, Object> chkMap = new HashMap<String, Object>();
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		try {
			List<Map<String, Object>> selectdAuth = new ObjectMapper().readValue(params.get("selectedItem").toString(), new TypeReference<List<Map<String, Object>>>(){});
			
			params.put("authList", selectdAuth);
			
			int deleteCount = exqueryService.delete("nse.pms.system.sqlAuth.deleteUserAuth", params);
			int insertCount = exqueryService.insert("nse.pms.system.sqlAuth.insertUserAuth", params);
			
			resultMap.put("insertCount", insertCount);
			resultMap.put("deleteCount", deleteCount);
			
		} catch(Exception ex){
			isExist = false;
			// 로깅 처리 ..
		}
		
		return resultMap;
		
//		boolean isExist = true;
//		HashMap<String, Object> resultMap = new HashMap<>();
//		try {
//			List<Map<String, Object>> menuList = new ObjectMapper().readValue(params.get("menuList").toString(), new TypeReference<List<Map<String, Object>>>(){});
//
//			params.put("authList", menuList);
//			int deleteCount = exqueryService.delete("nse.pms.system.sqlAuth.deleteAuthMenu", params);
//			int insertCount = exqueryService.insert("nse.pms.system.sqlAuth.insertAuth", params);
//
//			resultMap.put("insertCount", insertCount);
//			resultMap.put("deleteCount", deleteCount);
//
//		} catch (Exception ex) {
//			isExist = false;
//			// 로깅 처리 ..
//		}
//		resultMap.put("isExist", isExist);
//		return resultMap;
		

//		if(chkMap == null){
//			exqueryService.insert("nse.ftsWorker.insertWorker", param);
//
//			resultMap.put("result", true);
//			resultMap.put("message", "저장 되었습니다.");
//		} else {
//
//			resultMap.put("result", false);
//			resultMap.put("message", "작업자가 이미 존재합니다.");
//		}
//		return resultMap;
	}
	
	/**
	 * 권한 정보 리스트 조회
	 *
	 * @param
	 * @return
	 **/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public List<HashMap<String, Object>> selectAuthList(HashMap<String, Object> param) throws Exception {
		return exqueryService.selectList("nse.pms.system.sqlAuth.selectAuthList", param);
	}


	/**
	 * 한건 조회 (메뉴등록)
	 * @param
	 * @return
	 **/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> selectAuthOne(HashMap<String, Object> param){
		return exqueryService.selectOne("nse.pms.system.sqlAuth.selectAuthOne", param);
	}

	/**
	 * 역할별 권한 조회 (권한별 메뉴 등록)
	 **/
	@Override
	public List<HashMap<String, Object>> selectMenuAuthList(HashMap<String, Object> param) {
		return exqueryService.selectList("nse.pms.system.sqlAuth.selectMenuAuthList", param);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> updateMenuAuth(HashMap<String, Object> params) {
		boolean isExist = true;
		HashMap<String, Object> resultMap = new HashMap<>();
		try {
			List<Map<String, Object>> menuList = new ObjectMapper().readValue(params.get("menuList").toString(), new TypeReference<List<Map<String, Object>>>(){});

			params.put("authList", menuList);
			int deleteCount = exqueryService.delete("nse.pms.system.sqlAuth.deleteAuthMenu", params);
			int insertCount = exqueryService.insert("nse.pms.system.sqlAuth.insertAuth", params);

			resultMap.put("insertCount", insertCount);
			resultMap.put("deleteCount", deleteCount);

		} catch (Exception ex) {
			isExist = false;
			// 로깅 처리 ..
		}
		resultMap.put("isExist", isExist);
		return resultMap;
	}
	
	
	/**
	* 권한 리스트 조회
	* @param
	* @return
	**/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public List<HashMap<String, Object>> selectAuthorityList(HashMap<String, Object> param) throws Exception {

		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();

		resultList = exqueryService.selectList("nse.pms.system.sqlAuth.selectAuthorityList", param);

		return resultList;
	}


	/**
	 * 권한 리스트 단건 조회
	 * @param
	 * @return
	 **/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> selectAuthorityOne(HashMap<String, Object> param){

		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		resultMap = exqueryService.selectOne("nse.pms.system.sqlAuth.selectAuthorityOne", param);

		return resultMap;
	}


	/**
	 * 권한 리스트 등록
	 * @param
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> insertAuthority(HashMap<String, Object> param) throws Exception {

		//constraint check
		String pk = "{'colName':'AUTH_CD','colValue':'"+param.get("authCd")+"'}";
		String con = "["+"{'tableName':'COM_AUTHORITY', 'pkList':["+pk+"], 'msgCode':'중복되었습니다.'}"+"]";
		String consResult = constraintService.checkExistsV2(con.replaceAll("'", "\""));
		HashMap<String, Object> resultMap = new HashMap<>();
		resultMap.put("resultMsg", consResult);

		boolean isExist = false;
		if(consResult.equals("OK")){
			exqueryService.insert("nse.pms.system.sqlAuth.insertAuthority", param);
			isExist = true;
		}
		resultMap.put("isExist", isExist);
		return resultMap;
	}


	/**
	 * 권한 리스트 수정
	 * @param
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> updateAuthority(HashMap<String, Object> param){

		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		exqueryService.update("nse.pms.system.sqlAuth.updateAuthority", param);

		return resultMap;
	}
	
	/**
	* 권한 정보 삭제
	* @param HashMap<String, Object>
	* @return HashMap<String, Object>
	**/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> deleteAuthority(HashMap<String, Object> param){

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		exqueryService.update("nse.pms.system.sqlAuth.deleteAuthority", param);
		return resultMap;
	}
	
	/**
	 * 권한 정보 리스트 조회
	 *
	 * @param
	 * @return
	 **/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public List<HashMap<String, Object>> selectAreaMenuRelList(HashMap<String, Object> param) {
		return exqueryService.selectList("nse.pms.system.sqlAuth.selectAreaMenuRelList", param);
	}
	
	/**
	* 구역별 메뉴 매핑
	* @param HashMap<String, Object>
	* @return 
	**/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> updateAreaMenuMapping(HashMap<String, Object> param) throws Exception{

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> mappingList = new ArrayList<HashMap<String, Object>>();
		
		ObjectMapper objMap = new ObjectMapper();
		mappingList = objMap.readValue(param.get("data").toString(), new TypeReference<ArrayList<HashMap<String,Object>>>(){});
		
		// 1. 매핑 삭제
		exqueryService.delete("nse.pms.system.sqlAuth.deleteAreaMenuMapping", param);
		
		// 2. 매핑 처리
		for(int i=0; i< mappingList.size(); i++) {
			mappingList.get(i).put("areaCd", param.get("areaCd"));
			mappingList.get(i).put("ssUserId", param.get("ssUserId"));
			exqueryService.update("nse.pms.system.sqlAuth.InsertAreaMenuMapping", mappingList.get(i));
		}
		
		resultMap.put("isUpdate", true);
		return resultMap;
	}
	
}
