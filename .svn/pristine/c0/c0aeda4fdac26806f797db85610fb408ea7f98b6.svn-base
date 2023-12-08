package com.nse.pms.standard.basic.service.impl;

import com.nse.pms.standard.basic.service.NseTagCodeService;
import com.nse.pms.standard.basic.service.NseProcessService;
import com.nse.pms.standard.common.service.ConstraintService;
import com.nse.pms.standard.basic.service.NseLineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import select.spring.exquery.service.ExqueryService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service("NseTagCodeService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class NseTagCodeServiceImpl implements NseTagCodeService {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExqueryService exqueryService;

	@Autowired
	private ConstraintService constraintService;
	
	@Autowired
	private NseProcessService nseProcessService;
	
	@Autowired
	private NseLineService nseLineService;
	
	
	/**
	* 리스트 조회
	* @param
	* @return
	**/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> selectTagCodeList(HashMap<String, Object> param){
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = exqueryService.selectPagingList("nse.pms.basic.sqlTagCode.selectTagCodeList",param
				, Integer.parseInt(param.get("pageNo").toString())
				, Integer.parseInt(param.get("pageSize").toString()));

		return resultMap;
	}


	/**
	 * 한건 조회
	 * @param
	 * @return
	 **/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> selectTagCodeOne(HashMap<String, Object> param){

		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		resultMap = exqueryService.selectOne("nse.pms.basic.sqlTagCode.selectTagCodeOne", param);


		return resultMap;
	}


	/**
	 * 한건을 등록한다.
	 * @param
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> insertTagCode(HashMap<String, Object> param) throws Exception {

		//constraint check
		String pk = "{'colName':'TAG_CD','colValue':'"+param.get("tagCd")+"'}";
		String con = "["
				+"{'tableName':'COM_TAG', 'pkList':["+pk+"], 'msgCode':'중복되었습니다.'}"
				+"]";
		String consResult = constraintService.checkExistsV2(con.replaceAll("'", "\""));
		HashMap<String, Object> resultMap = new HashMap<>();
		resultMap.put("resultMsg", consResult);
		boolean isExist = false;

		if(param.containsKey("ctqUdateYn")){
			if(param.get("ctqUdateYn") == "Y"){
				exqueryService.update("nse.pms.basic.sqlCtqCode.updateCtqByTagCd", param);
			}

		}

		if(consResult.equals("OK")){
			exqueryService.insert("nse.pms.basic.sqlTagCode.insertTagCode", param);
			isExist = true;
		}
		resultMap.put("isExist", isExist);
		return resultMap;
	}


	/**
	 * 한건을 등록한다.
	 * @param
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> insertEnterTagCode(HashMap<String, Object> param) throws Exception {

		/*//constraint check
		String pk = "{'colName':'TAG_CD','colValue':'"+param.get("tagCd")+"'}";
		String con = "["
				+"{'tableName':'COM_TAG', 'pkList':["+pk+"], 'msgCode':'중복되었습니다.'}"
				+"]";
		String consResult = constraintService.checkExistsV2(con.replaceAll("'", "\""));
		HashMap<String, Object> resultMap = new HashMap<>();
		resultMap.put("resultMsg", consResult);
		boolean isExist = false;
		if(consResult.equals("OK")){
			exqueryService.insert("nse.pms.basic.sqlTagCode.insertTagCode", param);
			isExist = true;
		}*/
		boolean isExist = false;
		HashMap<String, Object> resultMap = new HashMap<>();
		exqueryService.insert("nse.pms.basic.sqlTagCode.insertEnterTagCode", param);
		exqueryService.update("nse.pms.basic.sqlTagCode.mergeEnterTagCode", param);
		isExist = true;


		resultMap.put("isExist", isExist);
		return resultMap;
	}


	/**
	 * 한건을 수정한다.
	 * @param
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> updateTagCode(HashMap<String, Object> param){

		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		exqueryService.update("nse.pms.basic.sqlTagCode.updateTagCode", param);
		exqueryService.update("nse.pms.basic.sqlTagCode.updateTagCode2", param);

		return resultMap;
	}

	/**
	 * 한건을 사용하지 않는다.
	 * @param
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> disableTagCode(HashMap<String, Object> param){

		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		exqueryService.update("nse.pms.basic.sqlTagCode.disableTagCode", param);

		return resultMap;
	}

	/**
	 * 리스트 조회 (공통)
	 * @param
	 * @return
	 **/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public List<HashMap<String, Object>> selectComTagCodeList(HashMap<String, Object> param){

		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();

		resultList = exqueryService.selectList("nse.pms.basic.sqlTagCode.selectComTagCodeList", param);

		return resultList;
	}
	
	/**
	 * 라인-공정-설비-Tag List
	 * @param
	 * @return
	 **/
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String, Object>>selectLineProcEqpTagList(HashMap<String, Object> param){
		
		// 1. 구역에 따른 라인 리스트 
		List<HashMap<String,Object>> LineList =  nseLineService.selectApiLineList(param);
		
		if(LineList.size() > 0) {
			
			for (HashMap<String, Object> LineMap : LineList) {
				List<HashMap<String,Object>> ProcessList = new ArrayList<HashMap<String, Object>>();		
				LineMap.put("ssUserLang", param.get("ssUserLang"));
				
				ProcessList = nseProcessService.selectApiLineProcList(LineMap);	 	   										// 해당 라인의 공정 리스트
				
				if(ProcessList.size() > 0) {
					LineMap.put("children", ProcessList);
					for(HashMap<String, Object> procMap : ProcessList) {			
						procMap.put("ssUserLang", param.get("ssUserLang"));
						List<HashMap<String,Object>> EquipmentList = nseProcessService.selectApiLineProcEqpList(procMap);	// 해당 공정의 설비 리스트
						if(EquipmentList.size() > 0) {
							procMap.put("children", EquipmentList);															//조회된 설비를 공정에 담는다.
							
							for(HashMap<String, Object> eqpMap : EquipmentList) {
								eqpMap.put("ssUserLang", param.get("ssUserLang"));
								eqpMap.put("collectType", param.containsKey("collectType") ? param.get("collectType") : "");

								List<HashMap<String,Object>> tagList = exqueryService.selectList("nse.pms.basic.sqlTagCode.selectEqpTagList", eqpMap);
								if(tagList.size() > 0) {
									eqpMap.put("children", tagList);
								}
							}
						}
					}
				}
			}
		}

		return LineList;
	}

	/**
	 * 에너지-계량기-Tag List
	 * @param
	 * @return
	 **/
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String, Object>>selectEngMeterTagList(HashMap<String, Object> param){

		// 1. 구역에 따른 라인 리스트
		List<HashMap<String,Object>> engList =  exqueryService.selectList("nse.pms.eng.sqlEng.selectEnergyList", param);

		if(engList.size() > 0) {

			for (HashMap<String, Object> engMap : engList) {
				List<HashMap<String,Object>> meterList = new ArrayList<HashMap<String, Object>>();
				engMap.put("ssUserLang", param.get("ssUserLang"));

				meterList = exqueryService.selectList("nse.pms.eng.sqlEng.selectMeterList", engMap);   										// 해당 라인의 공정 리스트

				if(meterList.size() > 0) {
					engMap.put("children", meterList);
					for(HashMap<String, Object> meterMap : meterList) {
						meterMap.put("ssUserLang", param.get("ssUserLang"));

						List<HashMap<String,Object>> tagList = exqueryService.selectList("nse.pms.eng.sqlEng.selectMeterTagList", meterMap);
						if(tagList.size() > 0) {
							meterMap.put("children", tagList);
						}

					}
				}
			}

		}


		return engList;
	}


	/**
	 * 라인-공정 List
	 * @param
	 * @return
	 **/
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String, Object>>selectLineProcList(HashMap<String, Object> param){

		// 1. 구역에 따른 라인 리스트
		List<HashMap<String,Object>> LineList =  nseLineService.selectApiLineList(param);

		if(LineList.size() > 0) {

			for (HashMap<String, Object> LineMap : LineList) {
				List<HashMap<String,Object>> ProcessList = new ArrayList<HashMap<String, Object>>();
				LineMap.put("ssUserLang", param.get("ssUserLang"));

				ProcessList = nseProcessService.selectApiLineProcList(LineMap);	 	   										// 해당 라인의 공정 리스트

				if(ProcessList.size() > 0) {

					LineMap.put("children", ProcessList);

//					for(HashMap<String, Object> procMap : ProcessList) {
//						procMap.put("ssUserLang", param.get("ssUserLang"));
//						List<HashMap<String,Object>> EquipmentList = nseProcessService.selectApiLineProcEqpList(procMap);	// 해당 공정의 설비 리스트
//						if(EquipmentList.size() > 0) {
//							procMap.put("children", EquipmentList);															//조회된 설비를 공정에 담는다.
//
//							for(HashMap<String, Object> eqpMap : EquipmentList) {
//								eqpMap.put("ssUserLang", param.get("ssUserLang"));
//								eqpMap.put("collectType", param.containsKey("collectType") ? param.get("collectType") : "");
//
//								List<HashMap<String,Object>> tagList = exqueryService.selectList("nse.pms.basic.sqlTagCode.selectEqpTagList", eqpMap);
//								if(tagList.size() > 0) {
//									eqpMap.put("children", tagList);
//								}
//							}
//						}
//					}

				}
			}
		}

		return LineList;
	}


	/**
	 * 설비-접점 List
	 * @param
	 * @return
	 **/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public List<HashMap<String, Object>> selectTagPopupList(HashMap<String, Object> param){

		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();

		resultList = exqueryService.selectList("nse.pms.basic.sqlTagCode.selectEqpTagList", param);

		return resultList;
	}

	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> selectTagPopupList2(HashMap<String, Object> param){

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = exqueryService.selectPagingList("nse.pms.basic.sqlTagCode.selectTagPopupList2",param
				, Integer.parseInt(param.get("pageNo").toString())
				, Integer.parseInt(param.get("pageSize").toString()));

		return resultMap;
	}


	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public List<HashMap<String, Object>> selectTagPopupList_moniCond(HashMap<String, Object> param){

		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();

		resultList = exqueryService.selectList("nse.pms.basic.sqlTagCode.selectTagPopupList_moniCond", param);

		return resultList;
	}



}
