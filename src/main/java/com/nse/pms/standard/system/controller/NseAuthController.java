package com.nse.pms.standard.system.controller;

import com.nse.pms.standard.system.service.NseAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping(value="auth")
public class NseAuthController {

	@Autowired
	private NseAuthService nseAuthService;

	/**
	* 리스트 조회 (메뉴등록)
	**/
	@RequestMapping( value="selectMenuList", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<HashMap<String, Object>> selectMenuList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseAuthService.selectMenuList(param);
	}

    /**
     * 한건 조회 (메뉴등록)
     */
    @RequestMapping( value="selectMenuOne", method = {RequestMethod.GET, RequestMethod.POST})
    public HashMap<String, Object> selectMenuOne(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
        return nseAuthService.selectMenuOne(param);
    }


	/**
	 * 한건을 등록한다. (메뉴등록)
	 */
	@RequestMapping( value="insertMenu", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> insertMenu(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseAuthService.insertMenu(param);
	}

	/**
	 * 한건을 수정한다. (메뉴등록)
	 */
	@RequestMapping( value="updateMenu", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> updateMenu(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseAuthService.updateMenu(param);
	}

	/**
	 * 한건을 사용하지 않는다. (메뉴등록)
	 */
	@RequestMapping( value="disableMenu", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> disableMenu(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseAuthService.disableMenu(param);
	}
	
	/**
	* 사용자 권한 조회
	* @param 
	* @param 
	* @return 
	**/
	@RequestMapping( value="selectUserAuthList", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<HashMap<String, Object>> selectUserAuthList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseAuthService.selectUserAuthList(param);
	}
	/**
	* 사용자 권한 삭제
	* @param 
	* @param 
	* @return 
	**/
	@RequestMapping( value="deleteUserAuth", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public HashMap<String, Object> deleteUserAuth(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseAuthService.deleteUserAuth(param);
	}
	/**
	* 사용자 권한 조회
	* @param 
	* @param 
	* @return 
	**/
	@RequestMapping( value="insertUserAuth", method =  {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public HashMap<String, Object> insertUserAuth(@RequestParam HashMap<String, Object> param,  ModelMap model) throws Exception {
		return nseAuthService.insertUserAuth(param);
	}
	/**
	 * 권한별 메뉴 관리 화면
	 * ===================================
	 * */

	/**
	 * 사용자 권한 조회
	 **/
	@RequestMapping( value="selectAuthList", method = {RequestMethod.GET, RequestMethod.POST})
	public List<HashMap<String, Object>> selectAuthList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseAuthService.selectAuthList(param);
	}

	/**
	 * 역할별 권한 조회
	 **/
	@RequestMapping( value="selectMenuAuthList", method = {RequestMethod.GET, RequestMethod.POST})
	public List<HashMap<String, Object>> selectMenuAuthList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseAuthService.selectMenuAuthList(param);
	}

	/**
	 * 역할별 권한 조회
	 **/
	@RequestMapping( value="updateMenuAuth", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> updateMenuAuth(@RequestParam HashMap<String, Object> params) throws Exception {
		return nseAuthService.updateMenuAuth(params);
	}

	
	/**
	* 권한 조회
	**/
	@RequestMapping( value="selectAuthorityList", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<HashMap<String, Object>> selectAuthorityList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseAuthService.selectAuthorityList(param);
	}

    /**
     * 권한 단건조회
     */
    @RequestMapping( value="selectAuthorityOne", method = {RequestMethod.GET, RequestMethod.POST})
    public HashMap<String, Object> selectAuthorityOne(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
        return nseAuthService.selectAuthorityOne(param);
    }


	/**
	 * 권한 등록
	 */
	@RequestMapping( value="insertAuthority", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> insertAuthority(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseAuthService.insertAuthority(param);
	}

	/**
	 * 권한 수정
	 */
	@RequestMapping( value="updateAuthority", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> updateAuthority(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseAuthService.updateAuthority(param);
	}
	
	/**
	 * 권한 삭제
	 */
	@RequestMapping( value="deleteAuthority", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> deleteAuthority(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseAuthService.deleteAuthority(param);
	}
	
	/**
	* 구역별 메뉴 리스트 
	**/
	@RequestMapping( value="selectAreaMenuRelList", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<HashMap<String, Object>> selectAreaMenuRelList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseAuthService.selectAreaMenuRelList(param);
	}
	
	/**
	* 구역별 메뉴 매핑
	* @param HashMap<String, Object>
	* @return 
	**/
	@RequestMapping( value="updateAreaMenuMapping", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String,Object> updateAreaMenuMapping(@RequestParam HashMap<String,Object> param, ModelMap model) throws Exception {
		return nseAuthService.updateAreaMenuMapping(param);
	}
	
}