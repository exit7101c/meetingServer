package com.nse.pms.standard.system.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nse.config.Common;
import com.nse.config.CryptoUtil;
import com.nse.config.excel.download.ExcelCreator;
import com.nse.pms.standard.system.service.NseBoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import select.spring.exquery.service.ExqueryService;

import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "board")
public class NseBoardController {

	@Autowired
	private NseBoardService nseBoardService;

	@RequestMapping(value = "selectNoticeList", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<HashMap<String, Object>> selectNoticeList(@RequestParam HashMap<String, Object> param, ModelMap model)
			throws Exception {
		return nseBoardService.selectNoticeList(param);
	}
	
	@RequestMapping(value = "insertNotice", method = { RequestMethod.GET, RequestMethod.POST })
	public HashMap<String, Object> insertNotice(@RequestParam(value="files", required=false) List<MultipartFile> files, @RequestParam HashMap<String, Object> param, ModelMap model)throws Exception {
		return nseBoardService.insertNotice(param, files);
	}
	
	@RequestMapping(value = "selectNoticeOne", method = { RequestMethod.GET, RequestMethod.POST })
	public HashMap<String, Object> selectNoticeOne(@RequestParam HashMap<String, Object> param, ModelMap model)
			throws Exception {
		return nseBoardService.selectNoticeOne(param);
	}

	
	/**
	 * 파일 리스트
	 * TODO : Controller 변경
	 */
	@RequestMapping(value = "selectAttachFileList", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<HashMap<String, Object>> selectAttachFileList(@RequestParam HashMap<String, Object> param, ModelMap model)throws Exception {
		return nseBoardService.selectAttachFileList(param);
	}
	
	@RequestMapping(value = "selectNoticeMainPopup", method = { RequestMethod.GET, RequestMethod.POST })
	public HashMap<String, Object> selectNoticeMainPopup(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseBoardService.selectNoticeMainPopup(param);
	}
	
	@RequestMapping(value = "updateNotice", method = { RequestMethod.GET, RequestMethod.POST })
	public HashMap<String, Object> updateNotice(@RequestParam(value="files", required=false) List<MultipartFile> files, @RequestParam HashMap<String, Object> param, ModelMap model)throws Exception {
		return nseBoardService.updateNotice(param, files);
	}

	@RequestMapping(value = "deleteNotice", method = { RequestMethod.GET, RequestMethod.POST })
	public HashMap<String, Object> deleteNotice(@RequestParam HashMap<String, Object> param, ModelMap model)
			throws Exception {
		return nseBoardService.deleteNotice(param);
	}


	/**
	 * 업데이트 이력 관리
	 */
	@RequestMapping(value = "selectBoardList", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<HashMap<String, Object>> selectBoardList(@RequestParam HashMap<String, Object> param, ModelMap model)
			throws Exception {
		return nseBoardService.selectBoardList(param);
	}

	@RequestMapping(value = "selectBoardOne", method = { RequestMethod.GET, RequestMethod.POST })
	public HashMap<String, Object> selectBoardOne(@RequestParam HashMap<String, Object> param, ModelMap model)
			throws Exception {
		return nseBoardService.selectBoardOne(param);
	}

	@RequestMapping(value = "insertDistributionHis", method = { RequestMethod.GET, RequestMethod.POST })
	public HashMap<String, Object> insertDistributionHis(@RequestParam HashMap<String, Object> param, ModelMap model)
			throws Exception {
		return nseBoardService.insertDistributionHis(param);
	}

	@RequestMapping(value = "updateDistributionHis", method = { RequestMethod.GET, RequestMethod.POST })
	public HashMap<String, Object> updateDistributionHis(@RequestParam HashMap<String, Object> param, ModelMap model)
			throws Exception {
		return nseBoardService.updateDistributionHis(param);
	}

	@RequestMapping(value = "deleteDistributionHis", method = { RequestMethod.GET, RequestMethod.POST })
	public HashMap<String, Object> deleteDistributionHis(@RequestParam HashMap<String, Object> param, ModelMap model)
			throws Exception {
		return nseBoardService.deleteDistributionHis(param);
	}


	/**
	 * 자유게시판
	 */
	@RequestMapping(value = "selectFreeBoardList", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<HashMap<String, Object>> selectFreeBoardList(@RequestParam HashMap<String, Object> param, ModelMap model)
			throws Exception {
		return nseBoardService.selectFreeBoardList(param);
	}

	@RequestMapping(value = "selectFreeBoardOne", method = { RequestMethod.GET, RequestMethod.POST })
	public HashMap<String, Object> selectFreeBoardOne(@RequestParam HashMap<String, Object> param, ModelMap model)
			throws Exception {
		return nseBoardService.selectFreeBoardOne(param);
	}

	@RequestMapping(value = "insertFreeBoard", method = { RequestMethod.GET, RequestMethod.POST })
	public HashMap<String, Object> insertFreeBoard(@RequestParam(value="files", required=false) List<MultipartFile> files, @RequestParam HashMap<String, Object> param, ModelMap model)throws Exception {
		return nseBoardService.insertFreeBoard(param, files);
	}


	@RequestMapping(value = "updateFreeBoard", method = { RequestMethod.GET, RequestMethod.POST })
	public HashMap<String, Object> updateFreeBoard(@RequestParam(value="files", required=false) List<MultipartFile> files, @RequestParam HashMap<String, Object> param, ModelMap model)throws Exception {
		return nseBoardService.updateFreeBoard(param, files);
	}

	@RequestMapping(value = "deleteFreeBoard", method = { RequestMethod.GET, RequestMethod.POST })
	public HashMap<String, Object> deleteFreeBoard(@RequestParam HashMap<String, Object> param, ModelMap model)
			throws Exception {
		return nseBoardService.deleteFreeBoard(param);
	}


}