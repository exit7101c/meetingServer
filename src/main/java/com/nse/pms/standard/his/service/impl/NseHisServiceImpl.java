package com.nse.pms.standard.his.service.impl;

import com.nse.pms.standard.common.service.ConstraintService;
import com.nse.pms.standard.his.service.NseHisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import select.spring.exquery.service.ExqueryService;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("hisService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class NseHisServiceImpl implements NseHisService {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExqueryService exqueryService;

	@Autowired
	private ConstraintService constraintService;

	/**
	 * 날씨 이력 차트조회
	 * @throws Exception
	 **/
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> selectHisWeatherChart(HashMap<String, Object> param) throws Exception {

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		boolean isExist = false;

		// 조회기간 String 조회
		HashMap<String, Object> dateMap = exqueryService.selectOne("nse.pms.ctq.sqlCtq.selectSearchRangeStr", param);

		String checkStr = param.get("searchTypeStr").toString();

		String[] checkList = checkStr.split(",");

		if(!param.get("searchTypeStr").toString().equals("")){

			for(int idx=0, len=checkList.length; idx<len; idx++){

				HashMap<String, Object> resMap = new HashMap<String, Object>();

				/** checkList : T=온도, H=습도, P=기압 */
				String mapper = "";
				String unit = "";
				String tagName = "";

				if(checkList[idx].equals("T")){
					//온도
					mapper = "nse.pms.his.sqlHis.selectHisWeatherChartListT";
					unit = "˚C";
					tagName = "온도";
				} else if(checkList[idx].equals("H")){
					//습도
					mapper = "nse.pms.his.sqlHis.selectHisWeatherChartListH";
					unit = "%";
					tagName = "습도";
				} else if(checkList[idx].equals("P")){
					//기압
					mapper = "nse.pms.his.sqlHis.selectHisWeatherChartListP";
					unit = "hPa";
					tagName = "기압";
				}

				//차트데이터조회
				List<HashMap<String, Object>> chartList = exqueryService.selectList(mapper, param);

				resMap.put("chart", chartList); //차트데이터
//				resMap.put("tagCd", checkList[idx]);
				resMap.put("tagUnit", unit);
				resMap.put("tagName", tagName);
				resMap.put("min", null);
				resMap.put("max", null);
				resMap.put("startTimeStr", dateMap.get("startTimeStr"));
				resMap.put("endTimeStr", dateMap.get("endTimeStr"));

				resultList.add(resMap);

			}
		}

		isExist =  resultList.size() > 0 ;
		resultMap.put("chartList", resultList);
		resultMap.put("isExist", isExist);
		return resultMap;

	}
}
