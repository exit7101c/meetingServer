package com.nse.pms.standard.basic.service.impl;

import com.nse.pms.standard.basic.service.NseUnitService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nse.pms.standard.basic.service.NseLineService;
import com.nse.pms.standard.common.service.ConstraintService;
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



@Service("unitService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class NseUnitServiceImpl implements NseUnitService {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExqueryService exqueryService;
	
	@Autowired
	private ConstraintService constraintService;
	
	/**
	* Unit 정보 조회
	* @param
	* @return HashMap<String, Object>
	**/	
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public List<HashMap<String, Object>> selectUnitList(HashMap<String, Object> param){
		
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		resultList = exqueryService.selectList("nse.pms.basic.sqlUnit.selectUnitList", param);
		return resultList;		
	}
}
