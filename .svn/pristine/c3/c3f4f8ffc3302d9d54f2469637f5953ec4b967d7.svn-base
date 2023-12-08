package com.nse.pms.standard.system.controller;

import com.nse.pms.standard.system.service.NseConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping(value="config")
public class NseConfigController {

	@Autowired
	private NseConfigService nseConfigService;

	/**
	* 리스트 조회 (다국어-화면용어)
	**/
	@RequestMapping( value="selectLangLabelList", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> selectLangLabelList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseConfigService.selectLangLabelList(param);
	}

	/**
	* 리스트 조회 (다국어-공통)
	**/
	@RequestMapping( value="selectLangLabelListCommon", method = {RequestMethod.GET, RequestMethod.POST})
	public List<HashMap<String, Object>> selectLangLabelListCommon(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseConfigService.selectLangLabelListCommon(param);
	}

    /**
     * 한건 조회 (다국어-화면용어)
     */
    @RequestMapping( value="selectLangLabelOne", method = {RequestMethod.GET, RequestMethod.POST})
    public HashMap<String, Object> selectLangLabelOne(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
        return nseConfigService.selectLangLabelOne(param);
    }


	/**
	 * 한건을 등록한다. (다국어-화면용어)
	 */
	@RequestMapping( value="insertLangLabel", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> insertLangLabel(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseConfigService.insertLangLabel(param);
	}

	/**
	 * 한건을 수정한다. (다국어-화면용어)
	 */
	@RequestMapping( value="updateLangLabel", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> updateLangLabel(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseConfigService.updateLangLabel(param);
	}

	/**
	 * 한건을 사용하지 않는다. (다국어-화면용어)
	 */
	@RequestMapping( value="disableLangLabel", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> disableLangLabel(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseConfigService.disableLangLabel(param);
	}

	/**
	* 리스트 조회 (다국어-메시지)
	**/
	@RequestMapping( value="selectLangMsgList", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public HashMap<String, Object> selectLangMsgList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseConfigService.selectLangMsgList(param);
	}

	/**
	 * 리스트 조회 (다국어-메시지)
	 **/
	@RequestMapping( value="selectLangMsgListCommon", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<HashMap<String, Object>> selectLangMsgListCommon(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseConfigService.selectLangMsgListCommon(param);
	}

    /**
     * 한건 조회 (다국어-메시지)
     */
    @RequestMapping( value="selectLangMsgOne", method = {RequestMethod.GET, RequestMethod.POST})
    public HashMap<String, Object> selectLangMsgOne(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
        return nseConfigService.selectLangMsgOne(param);
    }


	/**
	 * 한건을 등록한다. (다국어-메시지)
	 */
	@RequestMapping( value="insertLangMsg", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> insertLangMsg(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseConfigService.insertLangMsg(param);
	}

	/**
	 * 한건을 수정한다. (다국어-메시지)
	 */
	@RequestMapping( value="updateLangMsg", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> updateLangMsg(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseConfigService.updateLangMsg(param);
	}

	/**
	 * 한건을 사용하지 않는다. (다국어-메시지)
	 */
	@RequestMapping( value="disableLangMsg", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> disableLangMsg(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseConfigService.disableLangMsg(param);
	}

	/**
	 * 일반코드 리스트 조회
	 * */
	@RequestMapping( value="selectComCodeList", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> selectComCodeList(@RequestParam HashMap<String, Object> param) throws Exception {
		return nseConfigService.selectComCodeList(param);
	}

	/**
	 * 일반코드 리스트 조회 (codeCd 별로)
	 * */
	@RequestMapping( value="selectComCodeListByCd", method = {RequestMethod.GET, RequestMethod.POST})
	public List<HashMap<String, Object>> selectComCodeListByCd(@RequestParam HashMap<String, Object> param) throws Exception {
		return nseConfigService.selectComCodeListByCd(param);
	}

	/**
	 * 한건 조회 (일반코드)
	 */
	@RequestMapping( value="selectComCodeOne", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> selectComCodeOne(@RequestParam HashMap<String, Object> param) throws Exception {
		return nseConfigService.selectComCodeOne(param);
	}


	/**
	 * 한건을 등록한다. (일반코드)
	 */
	@RequestMapping( value="insertComCode", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> insertComCode(@RequestParam HashMap<String, Object> param) throws Exception {
		return nseConfigService.insertComCode(param);
	}

	/**
	 * 한건을 수정한다. (일반코드)
	 */
	@RequestMapping( value="updateComCode", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> updateComCode(@RequestParam HashMap<String, Object> param) throws Exception {
		return nseConfigService.updateComCode(param);
	}

	/**
	 * 한건을 사용하지 않는다. (일반코드)
	 */
	@RequestMapping( value="disableComCode", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> disableComCode(@RequestParam HashMap<String, Object> param) throws Exception {
		return nseConfigService.disableComCode(param);
	}


	/**
	 * 한건 조회 (시스템설정)
	 */
	@RequestMapping( value="selectSysSetting", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> selectSysSetting(@RequestParam HashMap<String, Object> param) throws Exception {
		return nseConfigService.selectSysSetting(param);
	}

	/**
	 * 한건을 수정한다. (시스템설정)
	 */
	@RequestMapping( value="updateSysSetting", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> updateSysSetting(@RequestParam HashMap<String, Object> param) throws Exception {
		return nseConfigService.updateSysSetting(param);
	}


}