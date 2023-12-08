package com.cronies.smartmes.service.impl;

import com.cronies.smartmes.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import select.spring.exquery.service.ExqueryService;

import java.util.*;

@Service("productService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ProductServiceImpl implements ProductService {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExqueryService exqueryService;

	/*@Autowired
	private ConstraintService constraintService;

	@Autowired
	private SmsService smsService;*/

	// 제품 리스트를 출력한다
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String, Object>> getProdList(HashMap<String, Object> param) throws Exception {
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		resultList = exqueryService.selectList("nse.pms.smartmes.mesProduct.selectProdList", param);
		return resultList;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> insertProduct(HashMap<String, Object> param) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean isExist = false;
		if(exqueryService.selectOne("nse.pms.smartmes.mesProduct.selectProdOneByProdCd", param) == null){
			exqueryService.insert("nse.pms.smartmes.mesProduct.insertProduct", param);
			isExist = true;
		}
		resultMap.put("isExist", isExist);
		return resultMap;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> updateProduct(HashMap<String, Object> param) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean isExist = false;
		if(exqueryService.selectOne("nse.pms.smartmes.mesProduct.selectProdOneByProdCd", param) == null){
			exqueryService.update("nse.pms.smartmes.mesProduct.updateProduct", param);
			isExist = true;
		}
		resultMap.put("isExist", isExist);
		return resultMap;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> deleteProduct(HashMap<String, Object> param) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		exqueryService.update("nse.pms.smartmes.mesProduct.deleteProduct", param);
		return resultMap;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> selectProdOne(HashMap<String, Object> param) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = exqueryService.selectOne("nse.pms.smartmes.mesProduct.selectProdOne", param);
		return resultMap;
	}







}