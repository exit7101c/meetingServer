package com.nse.pms.standard.basic.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface NseTagCodeService {

	/**
	 * 리스트 조회
	 **/
	public HashMap<String, Object> selectTagCodeList(HashMap<String, Object> param);

    /**
     * 한건 조회
     **/
    public HashMap<String, Object> selectTagCodeOne(HashMap<String, Object> param);

    /**
     * 한건을 등록한다.
     */
    public HashMap<String, Object> insertTagCode(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> insertEnterTagCode(HashMap<String, Object> param) throws Exception;

    /**
     * 한건을 수정한다.
     */
    public HashMap<String, Object> updateTagCode(HashMap<String, Object> param);

    /**
     * 한건을 사용하지 않는다.
     */
    public HashMap<String, Object> disableTagCode(HashMap<String, Object> param);

    /**
     * 리스트 조회 (공통)
     **/
    public List<HashMap<String, Object>> selectComTagCodeList(HashMap<String, Object> param);
    
	/**
	 * 라인-공정-설비-Tag List
	 * @param
	 * @return
	 **/
    public List<HashMap<String, Object>> selectLineProcEqpTagList(HashMap<String, Object> param);
    

	/**
	 * 에너지-계량기-Tag List
	 * @param
	 * @return
	 **/
    public List<HashMap<String, Object>> selectEngMeterTagList(HashMap<String, Object> param);

	/**
	 * 라인-공정 List
	 * @param
	 * @return
	 **/
	public List<HashMap<String, Object>> selectLineProcList(HashMap<String, Object> param);


	/**
	 * 설비-접점 List
	 * @param
	 * @return
	 **/
	public List<HashMap<String, Object>> selectTagPopupList(HashMap<String, Object> param);
	public HashMap<String, Object> selectTagPopupList2(HashMap<String, Object> param);
	public List<HashMap<String, Object>> selectTagPopupList_moniCond(HashMap<String, Object> param);


}
