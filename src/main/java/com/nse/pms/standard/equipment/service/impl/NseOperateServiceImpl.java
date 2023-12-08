package com.nse.pms.standard.equipment.service.impl;

import com.nse.pms.standard.common.service.ConstraintService;
import com.nse.pms.standard.equipment.service.NseOperateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import select.spring.exquery.service.ExqueryService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("operateService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class NseOperateServiceImpl implements NseOperateService {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExqueryService exqueryService;

	@Autowired
	private ConstraintService constraintService;


	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String,Object>> selectEquipmentList(HashMap<String,Object> param){
		List<HashMap<String,Object>> resultList = new ArrayList<HashMap<String,Object>>();
		resultList = exqueryService.selectList("nse.pms.equipment.sqlAlarm.selectEquipmentList", param);
		return resultList;		
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String,Object>> selectEquipmentRunHis(HashMap<String,Object> param){
		List<HashMap<String,Object>> resultList = new ArrayList<HashMap<String,Object>>();
		resultList = exqueryService.selectList("nse.pms.equipment.sqlAlarm.selectEquipmentRunHis", param);
		return resultList;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String,Object>> selectEquipmentAlarmHis(HashMap<String,Object> param){
		List<HashMap<String,Object>> resultList = new ArrayList<HashMap<String,Object>>();
		resultList = exqueryService.selectList("nse.pms.equipment.sqlAlarm.selectEquipmentAlarmHis", param);
		return resultList;		
	}
	
}
