package com.nse.pms.standard.basic.service.impl;

import com.nse.pms.standard.common.service.ConstraintService;
import com.nse.pms.standard.basic.service.NseProdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import select.spring.exquery.service.ExqueryService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@Service("prodService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class NseProdServiceImpl implements NseProdService {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExqueryService exqueryService;

	@Autowired
	private ConstraintService constraintService;

	/**
	 * Prod List 를 조회 한다.
	 *
	 * @param
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> selectProdList(HashMap<String, Object> param) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = exqueryService.selectPagingList("nse.pms.basic.sqlProd.selectProdList", param,
				Integer.parseInt(param.get("pageNo").toString()),
				Integer.parseInt(param.get("pageSize").toString()));
		return resultMap;
	}


	/**
	 * @param
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> selectProdOne(HashMap<String, Object> param) {

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = exqueryService.selectOne("nse.pms.basic.sqlProd.selectProdOne", param);
		return resultMap;
	}

	/**
	 * 제품별관리상하한 Prod List 를 조회 한다.
	 *
	 * @param
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> selectSpecProdList(HashMap<String, Object> param) {
		//List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		resultMap = exqueryService.selectPagingList("nse.pms.basic.sqlProd.selectSpecProdList", param,
				Integer.parseInt(param.get("pageNo").toString()),
				Integer.parseInt(param.get("pageSize").toString()));

		return resultMap;
	}

	/**
	 * 제품별관리상하한 tag List 를 조회 한다.
	 *
	 * @param
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String, Object>> selectSpecTagList(HashMap<String, Object> param) {
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		resultList = exqueryService.selectList("nse.pms.basic.sqlProd.selectSpecTagList", param);
		return resultList;
	}

	/**
	 * Prod List 를 조회 한다.
	 *
	 * @param
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> selectProdModalList(HashMap<String, Object> param) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = exqueryService.selectPagingList("nse.pms.basic.sqlProd.selectProdModalList", param,
				Integer.parseInt(param.get("pageNo").toString()),
				Integer.parseInt(param.get("pageSize").toString()));
		return resultMap;
	}


	/**
	 * 관리상하한 이력조회 (모달)
	 *
	 * @param
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> selectTagClModalList(HashMap<String, Object> param) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();

		resultMap = exqueryService.selectOne("nse.pms.basic.sqlProd.selectTagProdInfo", param);
		resultList = exqueryService.selectList("nse.pms.basic.sqlProd.selectTagClModalList", param);

		resultMap.put("resultList", resultList);

		return resultMap;
	}


	/**
	 * 공정안정성 이력조회 (모달)
	 *
	 * @param
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> selectProcStabilityList(HashMap<String, Object> param) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();

		resultMap = exqueryService.selectOne("nse.pms.basic.sqlProd.selectTagProdInfo", param);
		resultList = exqueryService.selectList("nse.pms.basic.sqlProd.selectProcStabilityList", param);

		resultMap.put("resultList", resultList);

		return resultMap;
	}


	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String, Object>> selectAutoProdSpecSetting(HashMap<String, Object> param) {
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		resultList = exqueryService.selectList("nse.pms.basic.sqlProd.selectAutoProdSpecSetting", param);
		return resultList;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> updateAutoProdSpecSetting(HashMap<String, Object> param) throws Exception {
		HashMap<String, Object> resultHashMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();

		ObjectMapper mapper = new ObjectMapper();
		dataList = mapper.readValue(param.get("data").toString(), new TypeReference<ArrayList<HashMap<String, Object>>>() {
		});

		// 1. 삭제(COM_CONTROL_LIMIT_CALC_OPT) 처리
		exqueryService.update("nse.pms.basic.sqlProd.deleteAutoProdSpecSetting", param);

		// 2. Checked 데이터 INSERT
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				dataList.get(i).put("prodCd", param.get("prodCd"));
				dataList.get(i).put("ssUserId", param.get("ssUserId"));

				// 1. COM_TAG - AUTO_LIMIT_YN 변경
				exqueryService.update("nse.pms.basic.sqlTagCode.updateComTagAutoLimitYn", dataList.get(i));

				// 2. 자동 설정 데이터만 INSERT
				if (dataList.get(i).get("autoLimitYn").equals("Y")) {
					exqueryService.insert("nse.pms.basic.sqlProd.insertAutoProdSpecSetting", dataList.get(i));
				}
			}
		}

		resultHashMap.put("isUpdate", true);
		return resultHashMap;
	}

	/**
	 * 제품-Area Mapping
	 * @param
	 * @return
	 **/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String,Object> mappingAreaProd(HashMap<String, Object> param) throws Exception {
		HashMap<String,Object> resultHashMap = new HashMap<String,Object>();
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		List<HashMap<String,Object>> dataList = new ArrayList<HashMap<String,Object>>();

		ObjectMapper mapper = new ObjectMapper();
		dataList =  mapper.readValue(param.get("data").toString(), new TypeReference<ArrayList<HashMap<String, Object>>>() {});

		String state = param.get("state").toString();

		//Checked 데이터 INSERT
		if(dataList.size() > 0) {
			for(int i = 0; i < dataList.size(); i++) {
                dataList.get(i).put("ssUserAreaCd", param.get("ssUserAreaCd"));
                dataList.get(i).put("ssUserId", param.get("ssUserId"));

				resultMap = exqueryService.selectOne("nse.pms.basic.sqlProd.selectAreaProdRel", dataList.get(i));

				if (resultMap !=  null) {
					if (state.equals("add"))
						continue;
					else
						exqueryService.insert("nse.pms.basic.sqlProd.deleteAreaProdMapping", dataList.get(i));
				}
				else {
					if (state.equals("add"))
						exqueryService.insert("nse.pms.basic.sqlProd.insertAreaProdMapping", dataList.get(i));
					else
						continue;
				}
			}
		}

		resultHashMap.put("isUpdate", true);

		return resultHashMap;
	}
}