package com.nse.pms.standard.system.service;

import java.util.HashMap;
import java.util.List;

public interface NseLogService {

	/**
	 * 리스트 조회 (다국어-화면용어)
	 **/
	public List<HashMap<String, Object>> selectLogMenuList(HashMap<String, Object> param) throws Exception;

	/**
	 * 한건 조회 (다국어-화면용어)
	 **/
	// public HashMap<String, Object> selectLogMenuOne(HashMap<String, Object>
	// param);
	//
	// /**
	// * 한건을 등록한다. (다국어-화면용어)
	// */
	// public HashMap<String, Object> insertLogMenu(HashMap<String, Object> param)
	// throws Exception;
	//
	// /**
	// * 한건을 수정한다. (다국어-화면용어)
	// */
	// public HashMap<String, Object> updateLogMenu(HashMap<String, Object> param);
	//
	// /**
	// * 한건을 사용하지 않는다. (다국어-화면용어)
	// */
	// public HashMap<String, Object> disableLogMenu(HashMap<String, Object> param);

	/**
	 * 리스트 조회 (다국어-화면용어)
	 **/
	public HashMap<String, Object> selectLogUserAccess(HashMap<String, Object> param) throws Exception;

	/**
	 * 이벤트 로그 조회
	 **/
	public List<HashMap<String, Object>> selectLogErrorHis(HashMap<String, Object> param) throws Exception;

}
