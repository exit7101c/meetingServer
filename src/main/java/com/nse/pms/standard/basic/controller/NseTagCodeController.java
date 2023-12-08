package com.nse.pms.standard.basic.controller;

import com.nse.pms.standard.basic.service.NseTagCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping(value="tagCode")
public class NseTagCodeController {

	@Autowired
	private NseTagCodeService nseTagCodeService;

	/**
	* 리스트 조회
	**/
	@RequestMapping( value="selectTagCodeList", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public HashMap<String, Object> selectTagCodeList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseTagCodeService.selectTagCodeList(param);
	}

    /**
     * 한건 조회
     */
    @RequestMapping( value="selectTagCodeOne", method = {RequestMethod.GET, RequestMethod.POST})
    public HashMap<String, Object> selectTagCodeOne(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
        return nseTagCodeService.selectTagCodeOne(param);
    }


	/**
	 * 한건을 등록한다.
	 */
	@RequestMapping( value="insertTagCode", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> insertTagCode(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseTagCodeService.insertTagCode(param);
	}


	/**
	 * 한건을 등록한다. (수동 접점값 등록)
	 */
	@RequestMapping( value="insertEnterTagCode", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> insertEnterTagCode(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseTagCodeService.insertEnterTagCode(param);
	}

	/**
	 * 한건을 수정한다.
	 */
	@RequestMapping( value="updateTagCode", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> updateTagCode(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseTagCodeService.updateTagCode(param);
	}

	/**
	 * 한건을 사용하지 않는다.
	 */
	@RequestMapping( value="disableTagCode", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> disableTagCode(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseTagCodeService.disableTagCode(param);
	}

	/**
	 * 리스트 조회 (공통)
	 **/
	@RequestMapping( value="selectComTagCodeList", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<HashMap<String, Object>> selectComTagCodeList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseTagCodeService.selectComTagCodeList(param);
	}

	/**
	 * 라인-공정-설비-Tag List
	 * @param
	 * @return
	 **/
	@RequestMapping( value="selectLineProcEqpTagList", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<HashMap<String, Object>> selectLineProcEqpTagList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseTagCodeService.selectLineProcEqpTagList(param);
	}

	/**
	 * 에너지-계량기-Tag List
	 * @param
	 * @return
	 **/
	@RequestMapping( value="selectEngMeterTagList", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<HashMap<String, Object>> selectEngMeterTagList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseTagCodeService.selectEngMeterTagList(param);
	}


	/**
	 * 라인-공정 List
	 * @param
	 * @return
	 **/
	@RequestMapping( value="selectLineProcList", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<HashMap<String, Object>> selectLineProcList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseTagCodeService.selectLineProcList(param);
	}


	/**
	 * 설비-접점 List
	 * @param
	 * @return
	 **/
	@RequestMapping( value="selectTagPopupList", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<HashMap<String, Object>> selectTagPopupList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseTagCodeService.selectTagPopupList(param);
	}

	@RequestMapping( value="selectTagPopupList2", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public HashMap<String, Object> selectTagPopupList2(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseTagCodeService.selectTagPopupList2(param);
	}

	@RequestMapping( value="selectTagPopupList_moniCond", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<HashMap<String, Object>> selectTagPopupList_moniCond(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseTagCodeService.selectTagPopupList_moniCond(param);
	}

}