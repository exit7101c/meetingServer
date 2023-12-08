package com.nse.pms.standard.system.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nse.pms.standard.common.service.ConstraintService;
import com.nse.pms.standard.system.service.NseUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import select.spring.exquery.service.ExqueryService;
//import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("NseUserService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class NseUserServiceImpl implements NseUserService {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExqueryService exqueryService;

	@Autowired
	private ConstraintService constraintService;

	/**
	 * UserDept List 를 조회 한다.
	 * 
	 * @param
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String, Object>> selectUserDeptList(HashMap<String, Object> param) {
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		resultList = exqueryService.selectList("nse.pms.system.sqlUser.selectUserDeptList", param);
		return resultList;
	}

	/**
	 * @param String startDate, String endDate
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> selectUserDeptOne(HashMap<String, Object> param) {

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = exqueryService.selectOne("nse.pms.system.sqlUser.selectUserDeptOne", param);
		return resultMap;
	}

	/**
	 * * @param String startDate, String endDate
	 * 
	 * @returng
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> insertUserDept(HashMap<String, Object> param) throws Exception {

		// constraint check
		String pk = "{'colName':'DEPT_CD','colValue':'" + param.get("deptCd") + "'}";
		String con = "[" + "{'tableName':'COM_DEPARTMENT', 'pkList':[" + pk + "], 'msgCode':'중복되었습니다.'}" + "]";
		String consResult = constraintService.checkExistsV2(con.replaceAll("'", "\""));
		HashMap<String, Object> resultMap = new HashMap<>();
		resultMap.put("resultMsg", consResult);

		boolean isExist = false;
		if (consResult.equals("OK")) {
			exqueryService.insert("nse.pms.system.sqlUser.insertUserDept", param);
			isExist = true;
		}
		resultMap.put("isExist", isExist);
		return resultMap;
	}

	/**
	 * @param String startDate, String endDate
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> updateUserDept(HashMap<String, Object> param) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		exqueryService.selectList("nse.pms.system.sqlUser.updateUserDept", param);
		return resultMap;

	}

	/**
	 * @param String startDate, String endDate
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> deleteUserDept(HashMap<String, Object> param) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		exqueryService.selectList("nse.pms.system.sqlUser.deleteUserDept", param);
		return resultMap;
	}
	
	/**
	 * 사용자 정보 조회
	 * @param
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String, Object>> selectUserList(HashMap<String, Object> param) {
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		resultList = exqueryService.selectList("nse.pms.system.sqlUser.selectUserList", param);
		return resultList;
	}

	/**
	 * 사용자 정보 단건 조회
	 * @param String startDate, String endDate
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> selectUserOne(HashMap<String, Object> param) {

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = exqueryService.selectOne("nse.pms.system.sqlUser.selectUserOne", param);
		return resultMap;
	}

	/**
	 * 사용자 정보 입력
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> insertUser(HashMap<String, Object> param) throws Exception {

		// constraint check
		String pk = "{'colName':'DEPT_CD','colValue':'" + param.get("deptCd") + "'}";
		String con = "[" + "{'tableName':'COM_DEPARTMENT', 'pkList':[" + pk + "], 'msgCode':'중복되었습니다.'}" + "]";
		String consResult = constraintService.checkExistsV2(con.replaceAll("'", "\""));
		HashMap<String, Object> resultMap = new HashMap<>();
		resultMap.put("resultMsg", consResult);

		boolean isExist = false;
		if (consResult.equals("OK")) {
			exqueryService.insert("nse.pms.system.sqlUser.insertUser", param);
			isExist = true;
		}
		resultMap.put("isExist", isExist);
		return resultMap;
	}

	/**
	 * 사용자 정보 수정
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> updateUser(HashMap<String, Object> param) {
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		exqueryService.selectList("nse.pms.system.sqlUser.updateUser", param);
		return resultMap;
	}

	/**
	 * 사용자 정보 삭제
	 * @param String startDate, String endDate
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> deleteUser(HashMap<String, Object> param) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		exqueryService.selectList("nse.pms.system.sqlUser.deleteUser", param);
		return resultMap;
	}
	
	
	/**
	 * 사용자 등록시 사용자 정보 유무 확인
	 * @param String startDate, String endDate
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> selectCheckUser(HashMap<String, Object> param) {

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = exqueryService.selectOne("nse.pms.system.sqlUser.selectCheckUser", param);
		return resultMap;
	}


	/**
	 * [개인정보수정] 사용자 정보 조회
	 * @param String startDate, String endDate
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> selectUserInfo(HashMap<String, Object> param) {

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = exqueryService.selectOne("nse.pms.system.sqlUser.selectUserInfo", param);
		return resultMap;
	}

	/**
	 * [개인정보수정] 사용자 정보 조회
	 * @param String startDate, String endDate
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> selectCheckUserPwd(HashMap<String, Object> param) {

		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		boolean isExist = false;

		HashMap<String, Object> chkMap = exqueryService.selectOne("nse.pms.system.sqlUser.selectCheckUserPwd", param);

		if(chkMap != null){
			isExist = true;
		}

		resultMap.put("isExist", isExist);
		return resultMap;
	}


	/**
	 * [개인정보수정] 데이터 저장
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> updateUserInfo(HashMap<String, Object> param) {

		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		exqueryService.selectList("nse.pms.system.sqlUser.updateUserInfo", param);
		return resultMap;
	}



	/**
	 * [개인정보수정] 즐겨찾기 메뉴 조회
	 * @param
	 * @return
	 **/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public List<HashMap<String, Object>> selectFavMenuList(HashMap<String, Object> param) {

		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();

		resultList = exqueryService.selectList("nse.pms.system.sqlUser.selectFavMenuList", param);

		return resultList;
	}


	/**
	 * [개인정보수정] 즐겨찾기메뉴 저장
	 * @param HashMap<String, Object>
	 * @return HashMap<String, Object>
	 **/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String,Object> insertFavMenu(HashMap<String, Object> param) throws Exception {

		HashMap<String,Object> resultHashMap = new HashMap<String,Object>();
		List<HashMap<String,Object>> dataList = new ArrayList<HashMap<String,Object>>();

		ObjectMapper mapper = new ObjectMapper();
		dataList =  mapper.readValue(param.get("data").toString(), new TypeReference<ArrayList<HashMap<String, Object>>>() {});

		// 1. 삭체 처리
		exqueryService.delete("nse.pms.system.sqlUser.deleteFavMenuAll", param);

		// 2. Checked 데이터 INSERT
		if(dataList.size()>0) {
			for(int i=0; i<dataList.size(); i++) {
				dataList.get(i).put("ssUserId", param.get("ssUserId"));
				exqueryService.insert("nse.pms.system.sqlUser.insertFavMeu", dataList.get(i));
			}
		}
		resultHashMap.put("isUpdate", true);
		return resultHashMap;
	}

}