package com.nse.pms.standard.report.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nse.config.HttpSessionCollector;
import com.nse.pms.standard.common.service.ConstraintService;
import com.nse.pms.standard.report.service.NseReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import select.spring.exquery.service.ExqueryService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Service("nseReportService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class NseReportServiceImpl implements NseReportService {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExqueryService exqueryService;

	@Autowired
	private ConstraintService constraintService;

	@Autowired
	private HttpSession session;

	/**
	 * 일지 Document Tree List
	 *
	 * @param
	 * @return List<HashMap<String, Object>>
	 **/
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String, Object>> selectDocTreeList(HashMap<String, Object> param) {

		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		resultList = exqueryService.selectList("nse.pms.report.sqlReport.selectDocTreeList", param);

		return resultList;
	}

	/**
	 * 일지 Document Tree List
	 *
	 * @param
	 * @return List<HashMap<String, Object>>
	 **/
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String, Object>> selectPtsDocumentList(HashMap<String, Object> param) {

		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		resultList = exqueryService.selectList("nse.pms.report.sqlReport.selectPtsDocumentList", param);

		return resultList;
	}

	/**
	 * 일지 데이터 등록
	 * @param
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> insertReport(HashMap<String, Object> param) throws Exception {

		//constraint check
		String pk = "{'colName':'DOC_CD','colValue':'"+param.get("docCd")+"'}, " +
					"{'colName':'STD_DATE','colValue':'"+param.get("stdDate")+"'}, " +
					"{'colName':'WORK_TEAM','colValue':'"+param.get("workTeam")+"'}, " +
					"{'colName':'SHIFT','colValue':'"+param.get("shift")+"'}, " +
					"{'colName':'DEL_YN','colValue':'N'}";
		String con = "["
				+"{'tableName':'PTS_DOCUMENT', 'pkList':["+pk+"], 'msgCode':'중복되었습니다.'}"
				+"]";
		String consResult = constraintService.checkExistsV2(con.replaceAll("'", "\""));
		HashMap<String, Object> resultMap = new HashMap<>();
		resultMap.put("resultMsg", consResult);

		boolean isExist = false;
		if(consResult.equals("OK")){

			// 선택한 일지의 VER_ID 조회
			HashMap<String,Object> verIdMap = exqueryService.selectOne("nse.pms.report.sqlReport.selectVerId", param);
			param.put("verId", verIdMap.get("verId"));

			// 'PTS_DOCUMENT'테이블에 INSERT
			exqueryService.insert("nse.pms.report.sqlReport.insertPtsDoc", param);

			// json ArrayList => java List로 변환
			List<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();
			List<HashMap<String, Object>> etcDataList = new ArrayList<HashMap<String, Object>>();
			ObjectMapper mapper = new ObjectMapper();
			dataList = mapper.readValue(param.get("pts-tag").toString(), new TypeReference<ArrayList<HashMap<String, Object>>>() {});
			etcDataList = mapper.readValue(param.get("pts-etc-tag").toString(), new TypeReference<ArrayList<HashMap<String, Object>>>() {});

			// 'PTS_DOC_DATA'테이블에 INSERT
			for(int idx=0, len=dataList.size(); idx<len; idx++){
				dataList.get(idx).put("docId", param.get("newId"));
				dataList.get(idx).put("ssUserId", param.get("ssUserId"));
				exqueryService.insert("nse.pms.report.sqlReport.insertPtsDocData", dataList.get(idx));
			}

			// 'PTS_ETC_DATA'테이블에 INSERT
			for(int idx=0, len=etcDataList.size(); idx<len; idx++){
				etcDataList.get(idx).put("docId", param.get("newId"));
				etcDataList.get(idx).put("ssUserId", param.get("ssUserId"));
				exqueryService.insert("nse.pms.report.sqlReport.insertPtsEtcData", etcDataList.get(idx));
			}

			// 'PTS_DOC_LOG'테이블에 Log INSERT
			/*HttpSession nSession = HttpSessionCollector.find(param.get("userToken").toString());
			String ssUserIp = nSession.getAttribute("ssUserIp").toString();
			param.put("ssUserIp", ssUserIp);
			param.put("docId", param.get("newId"));
			param.put("procType", "INSERT");
			param.put("logDetail", param.toString());
			exqueryService.insert("nse.pms.report.sqlReport.insertPtsDocLog", param);*/

			isExist = true;
		}
		resultMap.put("isExist", isExist);
		return resultMap;
	}

	/**
	 * 일지 데이터 조회
	 * @param
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> selectPtsDocData(HashMap<String, Object> param) {

		HashMap<String, Object> resultMap = new HashMap<>();

		HashMap<String, Object> dataMap = exqueryService.selectOne("nse.pms.report.sqlReport.selectPtsDocument", param);
		List<HashMap<String, Object>> dataList = exqueryService.selectList("nse.pms.report.sqlReport.selectPtsDocData", param);
		List<HashMap<String, Object>> etcDataList = exqueryService.selectList("nse.pms.report.sqlReport.selectPtsEtcData", param);

		if(etcDataList.size() > 0){
			dataList.addAll(etcDataList);
		}

		resultMap.put("dataList", dataList);
		//resultMap.put("etcDataList", etcDataList);
		resultMap.put("stdDateStr", dataMap.get("stdDateStr"));
		resultMap.put("workTeam", dataMap.get("workTeam"));
		resultMap.put("shift", dataMap.get("shift"));
		resultMap.put("docNum", dataMap.get("docNum"));

		return resultMap;
	}

	/**
	 * 일지 데이터 수정
	 * @param
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> updatePtsDoc(HashMap<String, Object> param) throws Exception {

		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		// json ArrayList => java List로 변환
		List<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();
		List<HashMap<String, Object>> etcDataList = new ArrayList<HashMap<String, Object>>();

		ObjectMapper mapper = new ObjectMapper();

		dataList = mapper.readValue(param.get("pts-tag").toString(), new TypeReference<ArrayList<HashMap<String, Object>>>() {});
		etcDataList = mapper.readValue(param.get("pts-etc-tag").toString(), new TypeReference<ArrayList<HashMap<String, Object>>>() {});

		// 'PTS_DOC_DATA'테이블에 UPDATE
		for(int idx=0, len=dataList.size(); idx<len; idx++){
			dataList.get(idx).put("docId", param.get("docId"));
			dataList.get(idx).put("ssUserId", param.get("ssUserId"));
			exqueryService.update("nse.pms.report.sqlReport.updatePtsDocData", dataList.get(idx));
		}

		// 'PTS_ETC_DATA'테이블에 UPDATE
		for(int idx=0, len=etcDataList.size(); idx<len; idx++){
			etcDataList.get(idx).put("docId", param.get("docId"));
			etcDataList.get(idx).put("ssUserId", param.get("ssUserId"));
			exqueryService.update("nse.pms.report.sqlReport.updateEtcData", etcDataList.get(idx));
		}

		// 'PTS_DOCUMENT' 테이블에 UPDATE
		exqueryService.update("nse.pms.report.sqlReport.updatePtsDocument", param);

		// 'PTS_DOC_LOG'테이블에 Log INSERT
		/*HttpSession nSession = HttpSessionCollector.find(param.get("userToken").toString());
		String ssUserIp = nSession.getAttribute("ssUserIp").toString();
		param.put("ssUserIp", ssUserIp);
//		param.put("docId", param.get("newId"));
		param.put("procType", "UPDATE");
		param.put("logDetail", param.toString());
		exqueryService.insert("nse.pms.report.sqlReport.insertPtsDocLog", param);*/

		return resultMap;
	}

	/**
	 * 일지삭제 (삭제여부 'Y'으로 변경)
	 * @param
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> disablePtsDoc(HashMap<String, Object> param){

		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		exqueryService.update("nse.pms.report.sqlReport.disablePtsDoc", param);

		// 'PTS_DOC_LOG'테이블에 Log INSERT
		HttpSession nSession = HttpSessionCollector.find(param.get("userToken").toString());
		String ssUserIp = nSession.getAttribute("ssUserIp").toString();
		param.put("ssUserIp", ssUserIp);
//		param.put("docId", param.get("newId"));
		param.put("procType", "DELETE");
		param.put("logDetail", param.toString());
		exqueryService.insert("nse.pms.report.sqlReport.insertPtsDocLog", param);

		return resultMap;
	}

}
