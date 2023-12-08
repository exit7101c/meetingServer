package com.cronies.smartmes.service.impl;

import com.cronies.smartmes.service.ProdRecordService;
import com.nse.pms.standard.common.service.ConstraintService;
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

@Service("prodRecordService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ProdRecordServiceImpl implements ProdRecordService {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExqueryService exqueryService;

	@Autowired
	private ConstraintService constraintService;

	/**
	 *  해당 법인의 생산일지를 조회한다.
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String, Object>> selectDailyProdRecordList(HashMap<String, Object> param) throws Exception {
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();

		// 해당 법인의 생산일지를 조회한다.
		resultList = exqueryService.selectList("nse.pms.smartmes.mesProdRecord.selectDailyProdRecordList", param);

		return resultList;
	}


}