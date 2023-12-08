package com.nse.pms.standard.monitoring.service.impl;

import com.nse.pms.standard.common.service.ConstraintService;
import com.nse.pms.standard.monitoring.service.NseEnergyService;
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

@Service("nseEnergyService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class NseEnergyServiceImpl implements NseEnergyService {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExqueryService exqueryService;

	@Autowired
	private ConstraintService constraintService;

	/**
	 * 에너지모니터링 데이터조회
	 * @throws Exception
	 **/
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String, Object>> selectPowerUsageList(HashMap<String, Object> param) throws Exception {

		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();

		resultList = exqueryService.selectList("nse.pms.main.sqlEnergy.selectPowerUsageList", param);

		return resultList;

	}


	/**
	 * 에너지모니터링 데이터조회 (상단 일단위, 월단위 통계)
	 * @throws Exception
	 **/
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String, Object>> selectPowerUsageStateList(HashMap<String, Object> param) throws Exception {

		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();

		resultList = exqueryService.selectList("nse.pms.main.sqlEnergy.selectPowerUsageStateList", param);

		return resultList;

	}


	/**
	 * 에너지모니터링 데이터조회 (상세보기 팝업)
	 * @throws Exception
	 **/
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String, Object>> selectEngTagCurrentList(HashMap<String, Object> param) throws Exception {

		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();

		resultList = exqueryService.selectList("nse.pms.main.sqlEnergy.selectEngTagCurrentList", param);

		return resultList;

	}
}
