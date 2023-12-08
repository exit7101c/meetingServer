package com.nse.pms.standard.system.service;

import java.util.HashMap;
import java.util.List;

public interface NseAuthService {

	/**
	 * 리스트 조회 (메뉴등록)
	 **/
	public List<HashMap<String, Object>> selectMenuList(HashMap<String, Object> param) throws Exception;

    /**
     * 한건 조회 (메뉴등록)
     **/
    public HashMap<String, Object> selectMenuOne(HashMap<String, Object> param);

    /**
     * 한건을 등록한다. (메뉴등록)
     */
    public HashMap<String, Object> insertMenu(HashMap<String, Object> param) throws Exception;

    /**
     * 한건을 수정한다. (메뉴등록)
     */
    public HashMap<String, Object> updateMenu(HashMap<String, Object> param);

    /**
     * 한건을 사용하지 않는다. (메뉴등록)
     */
    public HashMap<String, Object> disableMenu(HashMap<String, Object> param);

	/**
	* 사용자 권한 조회
	* @param List<HashMap<String, Object>>
	* @return 
	**/
	public List<HashMap<String, Object>> selectUserAuthList(HashMap<String, Object> param) throws Exception;
	
	/**
	* 사용자 정보 등록
	* @param HashMap<String, Object>
	* @return 
	**/
	public HashMap<String, Object> insertUserAuth(HashMap<String, Object> param) throws Exception;
	
	/**
	* 사용자 권한 삭제
	* @param HashMap<String, Object>
	* @return 
	**/
	public HashMap<String, Object> deleteUserAuth(HashMap<String, Object> param) throws Exception;
	
	/**
     * 권한 조회 (권한별 메뉴 등록)
     **/
    List<HashMap<String, Object>> selectAuthList(HashMap<String, Object> param) throws Exception;

    /**
     * 한건 조회 (권한별 메뉴 등록)
     */
    public HashMap<String, Object> selectAuthOne(HashMap<String, Object> param);


    /**
     * 역할별 권한 조회 (권한별 메뉴 등록)
     **/
    List<HashMap<String, Object>> selectMenuAuthList(HashMap<String, Object> param);

    HashMap<String, Object> updateMenuAuth(HashMap<String, Object> params);
    
	/**
	 * 리스트 조회 (메뉴등록)
	 **/
	public List<HashMap<String, Object>> selectAuthorityList(HashMap<String, Object> param) throws Exception;

    /**
     * 한건 조회 (메뉴등록)
     **/
    public HashMap<String, Object> selectAuthorityOne(HashMap<String, Object> param);

    /**
     * 한건을 등록한다. (메뉴등록)
     */
    public HashMap<String, Object> insertAuthority(HashMap<String, Object> param) throws Exception;

    /**
     * 한건을 수정한다. (메뉴등록)
     */
    public HashMap<String, Object> updateAuthority(HashMap<String, Object> param);
    
	/**
	* 권한 삭제
	* @param HashMap<String, Object>
	* @return 
	**/
    public HashMap<String, Object> deleteAuthority(HashMap<String, Object> param);
    
    /**
	* 구역별 메뉴 리스트
	* @param HashMap<String, Object>
	* @return 
	**/
    public List<HashMap<String, Object>> selectAreaMenuRelList(HashMap<String, Object> param);
    
    /**
	* 구역별 메뉴 매핑
	* @param HashMap<String, Object>
	* @return 
	**/
    public HashMap<String, Object> updateAreaMenuMapping(HashMap<String, Object> param) throws Exception;
}
