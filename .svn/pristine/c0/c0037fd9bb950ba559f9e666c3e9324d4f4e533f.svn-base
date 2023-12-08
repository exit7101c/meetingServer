package com.nse.pms.standard.basic.controller;

import com.nse.pms.standard.basic.service.NseCtqCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping(value="ctqCode")
public class NseCtqCodeController {

	@Autowired
	private NseCtqCodeService nseCtqCodeService;

	/**
	* 리스트 조회
	**/
	@RequestMapping( value="selectCtqCodeList", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<HashMap<String, Object>> selectCtqCodeList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseCtqCodeService.selectCtqCodeList(param);
	}

    /**
     * 한건 조회
     */
    @RequestMapping( value="selectCtqCodeOne", method = {RequestMethod.GET, RequestMethod.POST})
    public HashMap<String, Object> selectCtqCodeOne(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
        return nseCtqCodeService.selectCtqCodeOne(param);
    }


	/**
	 * 한건을 등록한다.
	 */
	@RequestMapping( value="insertCtqCode", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> insertCtqCode(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseCtqCodeService.insertCtqCode(param);
	}

	/**
	 * 한건을 수정한다.
	 */
	@RequestMapping( value="updateCtqCode", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> updateCtqCode(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseCtqCodeService.updateCtqCode(param);
	}

	/**
	 * 한건을 사용하지 않는다.
	 */
	@RequestMapping( value="disableCtqCode", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> disableCtqCode(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseCtqCodeService.disableCtqCode(param);
	}


}