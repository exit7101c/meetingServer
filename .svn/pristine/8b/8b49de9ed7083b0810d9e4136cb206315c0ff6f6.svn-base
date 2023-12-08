package com.nse.pms.standard.system.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.ui.ModelMap;

public interface NseUserService {

	public List<HashMap<String, Object>> selectUserDeptList(HashMap<String, Object> param);

	public HashMap<String, Object> selectUserDeptOne(HashMap<String, Object> param);

	public HashMap<String, Object> insertUserDept(HashMap<String, Object> param) throws Exception;

	public HashMap<String, Object> updateUserDept(HashMap<String, Object> param);

	public HashMap<String, Object> deleteUserDept(HashMap<String, Object> param);
	
	/**
	* 사용자 정보 조회
	* @param HashMap<String, Object>
	* @return HashMap<String, Object>
	**/
	public List<HashMap<String, Object>> selectUserList(HashMap<String, Object> param);
	
	/**
	* 사용자 정보 조회
	* @param HashMap<String, Object>
	* @return HashMap<String, Object>
	**/
	public HashMap<String, Object> selectUserOne(HashMap<String, Object> param);

	/**
	* 사용자 정보 조회
	* @param HashMap<String, Object>
	* @return HashMap<String, Object>
	**/
	public HashMap<String, Object> insertUser(HashMap<String, Object> param) throws Exception;

	/**
	* 사용자 정보 조회
	* @param HashMap<String, Object>
	* @return HashMap<String, Object>
	**/
	public HashMap<String, Object> updateUser(HashMap<String, Object> param);

	/**
	* 사용자 정보 삭제
	* @param HashMap<String, Object>
	* @return HashMap<String, Object>
	**/
	public HashMap<String, Object> deleteUser(HashMap<String, Object> param);

	/**
	* 사용자 등록시 사용자 정보 유무 확인
	* @param HashMap<String, Object>
	* @return HashMap<String, Object>
	**/
	public HashMap<String, Object> selectCheckUser(HashMap<String, Object> param);

	/**
	 * [개인정보수정] 사용자 정보 조회
	 * @param HashMap<String, Object>
	 * @return HashMap<String, Object>
	 **/
	public HashMap<String, Object> selectUserInfo(HashMap<String, Object> param);

	/**
	 * [개인정보수정] 데이터 저장시 현재비밀번호 확인
	 * @param HashMap<String, Object>
	 * @return HashMap<String, Object>
	 **/
	public HashMap<String, Object> selectCheckUserPwd(HashMap<String, Object> param);

	/**
	 * [개인정보수정] 데이터 저장
	 * @param HashMap<String, Object>
	 * @return HashMap<String, Object>
	 **/
	public HashMap<String, Object> updateUserInfo(HashMap<String, Object> param);

	/**
	 * [개인정보수정] 즐겨찾기 메뉴 조회
	 * @param HashMap<String, Object>
	 * @return HashMap<String, Object>
	 **/
	public List<HashMap<String, Object>> selectFavMenuList(HashMap<String, Object> param);


	/**
	 * [개인정보수정] 즐겨찾기메뉴 저장
	 * @param HashMap<String, Object>
	 * @return HashMap<String, Object>
	 **/
	public HashMap<String, Object> insertFavMenu(HashMap<String, Object> param) throws Exception;
}