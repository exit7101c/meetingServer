package com.nse.pms.standard.sample.controller;

import com.nse.pms.standard.sample.service.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value="sample")
public class SampleController {

	@Autowired
	private SampleService sampleService;


	/**
	 * 목록을 조회한다.
	 */
	@RequestMapping( value="selectUserList", method = {RequestMethod.GET, RequestMethod.POST})
	public List<HashMap<String, Object>> selectUserList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return sampleService.selectUserList(param);
	}


	/**
	 * 한건을 조회한다.
	 */
	@RequestMapping( value="selectUserOne", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> selectUserOne(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return sampleService.selectUserOne(param);
	}

	/**
	 * 한건을 등록한다.
	 */
	@RequestMapping( value="insertUser", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> insertUser(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return sampleService.insertUser(param);
	}

	/**
	 * 한건을 수정한다.
	 */
	@RequestMapping( value="updateUser", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> updateUser(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return sampleService.updateUser(param);
	}

	/**
	 * Enterprise 조회한다
	 * */

	/**
	 * 목록을 조회한다.
	 */
	@RequestMapping( value="selectEnterpriseList", method = {RequestMethod.GET, RequestMethod.POST})
	public List<HashMap<String, Object>> selectEnterpriseList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return sampleService.selectEnterpriseList(param);
	}

	/**
	 * 한건을 조회한다.
	 */
	@RequestMapping( value="selectEnterpriseOne", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> selectEnterpriseOne(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return sampleService.selectEnterpriseOne(param);
	}

	/**
	 * 한건을 등록한다.
	 */
	@RequestMapping( value="insertEnterprise", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> insertEnterprise(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return sampleService.insertEnterprise(param);
	}

	/**
	 * 한건을 수정한다.
	 */
	@RequestMapping( value="updateEnterprise", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> updateEnterprise(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return sampleService.updateEnterprise(param);
	}

	/**
	 * 한건을 사용하지 않는다.
	 */
	@RequestMapping( value="disableEnterprise", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> disableEnterprise(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return sampleService.disableEnterprise(param);
	}
}