package com.nse.pms.standard.common.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import select.spring.exquery.service.ExqueryService;
import select.spring.util.ApplicationProperty;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class PopupController {

	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ExqueryService exqueryService;

	@Autowired
	   HttpSession session;

	
	//*==================팝업======================*//
	
	/**
	 * 공통코드 팝업
	 * @param param
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "testPop1", method = RequestMethod.POST)
	public String testPop1(@RequestParam Map<String, Object> param,  ModelMap model) {
		log.info("=====> testPop1 started!");
		
		return "p:testPop1";
	}	
	
	/**
	 * 일괄입력 팝업
	 * @param param
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "popExcelUpload", method = RequestMethod.POST)
	public String popExcelUpload(@RequestParam Map<String, Object> param,  ModelMap model) {
		log.info("=====> popExcelUpload started!");
		return "p:popExcelUpload";
	}

	/**
	 * 조 편성 팝업
	 * @param param
	 * @param model
	 * @return String
	 * */
	@RequestMapping(value = "popProdWorkgroup", method = RequestMethod.POST)
	public String popProdWorkgroup(@RequestParam Map<String, Object> param,  ModelMap model) {
		log.info("=====> popProdWorkgroup started!");
		return "p:popProdWorkgroup";
	}

	/**
	 * 메뉴위치선택 팝업
	 * @param param
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "popAuthMenuPositionCd", method = RequestMethod.POST)
	public String popAuthMenuPositionCd(@RequestParam Map<String, Object> param,  ModelMap model) {
		log.info("=====> popAuthMenuPositionCd started!");
		
		return "p:popAuthMenuPositionCd";
	}	
	/**
	 * 메뉴위치선택 팝업(메뉴관리에서만 사용)
	 * @param param
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "popAuthMenuPositionAll", method = RequestMethod.POST)
	public String popAuthMenuPositionAll(@RequestParam Map<String, Object> param,  ModelMap model) {
		log.info("=====> popAuthMenuPositionAll started!");
		
		return "p:popAuthMenuPositionAll";
	}	
	
	/**
	 * 메뉴선택 팝업
	 * @param param
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "popAuthMenuCd", method = RequestMethod.POST)
	public String popAuthMenuCd(@RequestParam Map<String, Object> param,  ModelMap model) {
		log.info("=====> popAuthMenuCd started!");
		return "p:popAuthMenuCd";
	}	
	
	/**
	 * CTQ위치선택 팝업
	 * @param param
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "popCtqPositionCd", method = RequestMethod.POST)
	public String popCtqPositionCd(@RequestParam Map<String, Object> param,  ModelMap model) {
		log.info("=====> popCtqPositionCd started!");
		
		return "p:popCtqPositionCd";
	}	
	
	/**
	 * FTA위치선택 팝업
	 * @param param
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "popFtaPositionCd", method = RequestMethod.POST)
	public String popFtaPositionCd(@RequestParam Map<String, Object> param,  ModelMap model) {
		log.info("=====> popFtaPositionCd started!");
		
		return "p:popFtaPositionCd";
	}

	/**
	 * 역할메뉴 팝업
	 * @param param
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "popAuthRoleCd", method = RequestMethod.POST)
	public String popAuthRoleCd(@RequestParam Map<String, Object> param,  ModelMap model) {
		log.info("=====> popAuthRoleCd started!");
		
		return "p:popAuthRoleCd";
	}	
	
	/**
	 * 사용자 팝업
	 * @param param
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "popAuthUserCd", method = RequestMethod.POST)
	public String popAuthUserCd(@RequestParam Map<String, Object> param,  ModelMap model) {
		log.info("=====> popAuthUserCd started!");
		
		return "p:popAuthUserCd";
	}

	/**
	 * CTQ Manager 선택 팝업
	 * @param param
	 * @param model
	 * @return String
	 */
	@RequestMapping(value = "popCtqManager", method = RequestMethod.POST)
	public String popCtqManager(@RequestParam Map<String, Object> param,  ModelMap model) {
		log.info("=====> popCtqManager started!");
		return "p:popCtqManager";
	}

	/**
	 * CTP선택 팝업
	 * @param param
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "popCtp", method = RequestMethod.POST)
	public String popCtp(@RequestParam Map<String, Object> param,  ModelMap model) {
		log.info("=====> popCtp started!");
		
		return "p:popCtp";
	}

	/**
	 * 라인정지사유 입력 팝업
	 * @param param
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "popLineStopReason", method = RequestMethod.POST)
	public String popLineStopReason(@RequestParam Map<String, Object> param,  ModelMap model) {
		log.info("=====> popLineStopReason started!");
		model.addAttribute("stopCodeList", exqueryService.selectList("nse.pmsLine.selectStopCodes", param));
		return "p:popLineStopReason";
	}

	/**
	 * 생산공정코드 팝업
	 * @param param
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "popTreeCd", method = RequestMethod.POST)
	public String popTreeCd(@RequestParam Map<String, Object> param,  ModelMap model) {
		log.info("=====> popTreeCd started!");
		
		return "p:popTreeCd";
	}	
	
	/**
	 * 생산공정코드(위치선택) 팝업
	 * @param param
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "popTreePositionCd", method = RequestMethod.POST)
	public String popTreePositionCd(@RequestParam Map<String, Object> param,  ModelMap model) {
		log.info("=====> popTreePositionCd started!");
		
		return "p:popTreePositionCd";
	}
	
	/**
	 * 도면공정코드(위치선택) 팝업
	 * @param param
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "popBluePrintPositionCd", method = RequestMethod.POST)
	public String popBluePrintPositionCd(@RequestParam Map<String, Object> param,  ModelMap model) {
		log.info("=====> popBluePrintPositionCd started!");
		
		return "p:popBluePrintPositionCd";
	}
	
	/**
	 * 설비코드 선택 팝업
	 * @param param
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "popProcEquipCd", method = RequestMethod.POST)
	public String popProcEquipCd(@RequestParam Map<String, Object> param,  ModelMap model) {
		log.info("=====> popProcEquipCd started!");
		
		return "p:popProcEquipCd";
	}	
	
	/**
	 * 접점코드 한건 선택 팝업
	 * @param param
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "popCtpOne", method = RequestMethod.POST)
	public String popCtpOne(@RequestParam Map<String, Object> param,  ModelMap model) {
		log.info("=====> popCtpOne started!");
		
		return "p:popCtpOne";
	}	
	
	/**
	 * 크게보기
	 * @param param
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "popImageView", method = RequestMethod.POST)
	public String popImageView(@RequestParam Map<String, Object> param,  ModelMap model) {
		log.info("=====> popImageView started!");
		model.put("filePath", ApplicationProperty.get("upload.viewPath"));
		return "p:popImageView";
	}	
	
	/**
	 * 크게보기 (리스트형)
	 * @param param
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "popImageListView", method = RequestMethod.POST)
	public String popImageListView(@RequestParam Map<String, Object> param,  ModelMap model) {
		log.info("=====> popImageListView started!");
		model.put("filePath", ApplicationProperty.get("upload.viewPath"));
		return "p:popImageListView";
	}	
	
	/**
	 * 바코드출력 미리보기 
	 * @param param
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "popBacodView", method = RequestMethod.POST)
	public String popBacodView(@RequestParam Map<String, Object> param,  ModelMap model) {
		log.info("=====> popBacodView started!");
		return "p:popBacodView";
	}
	
	/**
	 * 제품코드 한건 선택 
	 * @param param
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "popGoodsOne", method = RequestMethod.POST)
	public String popGoodsOne(@RequestParam Map<String, Object> param,  ModelMap model) {
		log.info("=====> popGoodsOne started!");
		return "p:popGoodsOne";
	}
	
	/**
	 * 생산제품변경 
	 * @param param
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "popGoodsProdSel", method = RequestMethod.POST)
	public String popGoodsProdSel(@RequestParam Map<String, Object> param,  ModelMap model) {
		model.addAttribute("procGbList", exqueryService.selectList("nse.pmsGoodsProd.selectProcGbList", param));
//		log.info("=====> popGoodsProdSel started!");
		return "p:popGoodsProdSel";
	}

	/**
	 * 휴식시간 관리 팝업
	 * @param param
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "popRestPlanExcelUpload", method = RequestMethod.POST)
	public String popRestPlanExcelUpload(@RequestParam Map<String, Object> param,  ModelMap model) {
		log.info("=====> popRestPlanExcelUpload started!");
		return "p:popRestPlanExcelUpload";
	}

	/**
	 * 생산계획관리 팝업
	 * @param param
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "popProdPlanExcelUpload", method = RequestMethod.POST)
	public String popProdPlanExcelUpload(@RequestParam Map<String, Object> param,  ModelMap model) {
		log.info("=====> popProdPlanExcelUpload started!");
		return "p:popProdPlanExcelUpload";
	}

	/**
	 * 제품정보복사
	 * @param param
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "popGoodsCopy", method = RequestMethod.POST)
	public String popGoodsCopy(@RequestParam Map<String, Object> param,  ModelMap model) {
		model.addAttribute("goodsList", exqueryService.selectList("nse.pmsGoods.selectGoodsSelCopy", param));
		log.info("=====> popGoodsCopy started!");
		return "p:popGoodsCopy";
	}

	/**
	 * 정비일지 - 설비고장입력 팝업
	 * @param param
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "popEquipFailure", method = RequestMethod.POST)
	public String popEquipFailure(@RequestParam Map<String, Object> param,  ModelMap model) {
		log.info("=====> popEquipFailure started!");
		return "p:popEquipFailure";
	}


	/**
	 * 접점 상하한관리
	 * @param param
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "popBasicTag", method = RequestMethod.POST)
	public String popBasicTag(@RequestParam Map<String, Object> param,  ModelMap model) {
		log.info("=====> popBasicTag started!");
		return "p:popBasicTag";
	}


	/**
	 * 공정진행표 조회 팝업
	 * @param param
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "popProcTimeData", method = RequestMethod.POST)
	public String popProcTimeData(@RequestParam Map<String, Object> param,  ModelMap model) {

		return "p:popProcTimeData";
	}

	/**
	 * 메인화면 - 공지사항 팝업
	 * @param param
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "popNotice", method = RequestMethod.POST)
	public String popNotice(@RequestParam Map<String, Object> param,  ModelMap model) {

		return "p:popNotice";
	}

	/**
	 * 일자별LOT개수 팝업
	 * @param param
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "popDocStats", method = RequestMethod.POST)
	public String popDocStats(@RequestParam Map<String, Object> param,  ModelMap model) {

		return "p:popDocStats";
	}
}
