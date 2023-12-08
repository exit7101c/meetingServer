package com.nse.pms.standard.system.controller;

import com.nse.config.Common;
import com.nse.config.CryptoUtil;
import com.nse.config.excel.download.ExcelCreator;
import com.nse.pms.standard.system.service.NseUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import select.spring.exquery.service.ExqueryService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "user")
public class NseUserController {

	@Autowired
	private NseUserService nseUserService;

	@RequestMapping(value = "selectUserDeptList", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<HashMap<String, Object>> selectUserDeptList(@RequestParam HashMap<String, Object> param, ModelMap model)
			throws Exception {
		return nseUserService.selectUserDeptList(param);
	}

	@RequestMapping(value = "selectUserDeptOne", method = { RequestMethod.GET, RequestMethod.POST })
	public HashMap<String, Object> selectUserDeptOne(@RequestParam HashMap<String, Object> param, ModelMap model)
			throws Exception {
		return nseUserService.selectUserDeptOne(param);
	}

	@RequestMapping(value = "insertUserDept", method = { RequestMethod.GET, RequestMethod.POST })
	public HashMap<String, Object> insertUserDept(@RequestParam HashMap<String, Object> param, ModelMap model)
			throws Exception {
		return nseUserService.insertUserDept(param);
	}

	@RequestMapping(value = "updateUserDept", method = { RequestMethod.GET, RequestMethod.POST })
	public HashMap<String, Object> updateUserDept(@RequestParam HashMap<String, Object> param, ModelMap model)
			throws Exception {
		return nseUserService.updateUserDept(param);
	}

	@RequestMapping(value = "deleteUserDept", method = { RequestMethod.GET, RequestMethod.POST })
	public HashMap<String, Object> deleteUserDept(@RequestParam HashMap<String, Object> param, ModelMap model)
			throws Exception {
		return nseUserService.deleteUserDept(param);
	}
	
	/**
	* Plant 정보 조회
	* @param 
	* @param 
	* @return 
	**/
	@RequestMapping(value = "selectUserList", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<HashMap<String, Object>> selectUserList(@RequestParam HashMap<String, Object> param, ModelMap model)
			throws Exception {
		return nseUserService.selectUserList(param);
	}
	
	/**
	* 사용자 정보 단건 조회
	* @param 
	* @param 
	* @return 
	**/
	@RequestMapping(value = "selectUserOne", method = { RequestMethod.GET, RequestMethod.POST })
	public HashMap<String, Object> selectUserOne(@RequestParam HashMap<String, Object> param, ModelMap model)
			throws Exception {
		return nseUserService.selectUserOne(param);
	}
	
	/**
	* 사용자 정보 등록 
	* @param HashMap<String, Object> param
	* @param ModelMap model
	* @return HashMap<String, Object>
	**/
	@RequestMapping(value = "insertUser", method = { RequestMethod.GET, RequestMethod.POST })
	public HashMap<String, Object> insertUser(@RequestParam HashMap<String, Object> param, ModelMap model)
			throws Exception {
		if(param.get("userPwd").toString().length() > 0) {
			CryptoUtil pwEncyript = new CryptoUtil();	
			param.put("userPwd", pwEncyript.encrypt(param.get("userPwd").toString()));
		}
		
		return nseUserService.insertUser(param);
	}
	
	/**
	* 사용자 정보 변경 
	* @param HashMap<String, Object> param
	* @param ModelMap model
	* @return HashMap<String, Object>
	**/
	@RequestMapping(value = "updateUser", method = { RequestMethod.GET, RequestMethod.POST })
	public HashMap<String, Object> updateUser(@RequestParam HashMap<String, Object> param, ModelMap model)
			throws Exception {
		
		if(param.get("userPwd").toString().length() > 0) {
			CryptoUtil enc = new CryptoUtil();	
			param.put("userPwd", enc.encrypt(param.get("userPwd").toString()));
		}
		//param.put("userPwd", "$2a$12$7vV7sd6fdyNHHNaU11HTEOJgCB.n25G7/LZ9xK25ubJjhRNC3Uxn.");//enc.hash(param.get("userPwd").toString()));
		return nseUserService.updateUser(param);
	}
	
	/**
	* 사용자 정보 삭제 
	* @param HashMap<String, Object> param
	* @param ModelMap model
	* @return HashMap<String, Object>
	**/
	@RequestMapping(value = "deleteUser", method = { RequestMethod.GET, RequestMethod.POST })
	public HashMap<String, Object> deleteUser(@RequestParam HashMap<String, Object> param, ModelMap model)
			throws Exception {
		return nseUserService.deleteUser(param);
	}
	
	/**
	* 사용자 정보 단건 조회
	* @param 
	* @param 
	* @return 
	**/
	@RequestMapping(value = "selectCheckUser", method = { RequestMethod.GET, RequestMethod.POST })
	public HashMap<String, Object> selectCheckUser(@RequestParam HashMap<String, Object> param, ModelMap model)
			throws Exception {
		return nseUserService.selectCheckUser(param);
	}


	/**
	 * [개인정보수정] 사용자 정보 조회
	 * @param
	 * @param
	 * @return
	 **/
	@RequestMapping(value = "selectUserInfo", method = { RequestMethod.GET, RequestMethod.POST })
	public HashMap<String, Object> selectUserInfo(@RequestParam HashMap<String, Object> param, ModelMap model)
			throws Exception {
		return nseUserService.selectUserInfo(param);
	}

	/**
	 * [개인정보수정] 데이터 저장시 현재비밀번호 확인
	 * @param
	 * @param
	 * @return
	 **/
	@RequestMapping(value = "selectCheckUserPwd", method = { RequestMethod.GET, RequestMethod.POST })
	public HashMap<String, Object> selectCheckUserPwd(@RequestParam HashMap<String, Object> param, ModelMap model)
			throws Exception {
		if(param.get("userPwd").toString().length() > 0) {
			CryptoUtil pwEncyript = new CryptoUtil();
			param.put("userPwd", pwEncyript.encrypt(param.get("userPwd").toString()));
		}

		return nseUserService.selectCheckUserPwd(param);
	}

	/**
	 * [개인정보수정] 데이터 저장
	 * @param HashMap<String, Object> param
	 * @param ModelMap model
	 * @return HashMap<String, Object>
	 **/
	@RequestMapping(value = "updateUserInfo", method = { RequestMethod.GET, RequestMethod.POST })
	public HashMap<String, Object> updateUserInfo(@RequestParam HashMap<String, Object> param, ModelMap model)
			throws Exception {

		if(param.get("userNewPwd").toString().length() > 0) {
			CryptoUtil enc = new CryptoUtil();
			param.put("userNewPwd", enc.encrypt(param.get("userNewPwd").toString()));
		}
		return nseUserService.updateUserInfo(param);
	}


	/**
	 * [개인정보수정] 즐겨찾기 메뉴 조회
	 **/
	@RequestMapping( value="selectFavMenuList", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<HashMap<String, Object>> selectFavMenuList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseUserService.selectFavMenuList(param);
	}


	/**
	 * [개인정보수정] 즐겨찾기메뉴 저장
	 * @param
	 * @param
	 * @return
	 **/
	@RequestMapping(value = "insertFavMenu", method = { RequestMethod.GET, RequestMethod.POST })
	public HashMap<String, Object> insertFavMenu(@RequestParam HashMap<String, Object> param, ModelMap model)
			throws Exception {
		return nseUserService.insertFavMenu(param);
	}

}