package com.nse.pms.standard.system.service;

import java.util.HashMap;
import java.util.List;

public interface NseConfigService {

	/**
	 * 리스트 조회 (다국어-화면용어)
	 **/
	public HashMap<String, Object> selectLangLabelList(HashMap<String, Object> param) throws Exception;

	/**
	 * 리스트 조회 (다국어-공통)
	 **/
	public List<HashMap<String, Object>> selectLangLabelListCommon(HashMap<String, Object> param) throws Exception;

    /**
     * 한건 조회 (다국어-화면용어)
     **/
    public HashMap<String, Object> selectLangLabelOne(HashMap<String, Object> param);

    /**
     * 한건을 등록한다. (다국어-화면용어)
     */
    public HashMap<String, Object> insertLangLabel(HashMap<String, Object> param) throws Exception;

    /**
     * 한건을 수정한다. (다국어-화면용어)
     */
    public HashMap<String, Object> updateLangLabel(HashMap<String, Object> param);

    /**
     * 한건을 사용하지 않는다. (다국어-화면용어)
     */
    public HashMap<String, Object> disableLangLabel(HashMap<String, Object> param);

	/**
	 * 리스트 조회 (다국어-메시지)
	 **/
	public HashMap<String, Object> selectLangMsgList(HashMap<String, Object> param) throws Exception;

    /**
     * 리스트 조회 (다국어-메시지)
     **/
    public List<HashMap<String, Object>> selectLangMsgListCommon(HashMap<String, Object> param) throws Exception;

    /**
     * 한건 조회 (다국어-메시지)
     **/
    public HashMap<String, Object> selectLangMsgOne(HashMap<String, Object> param);

    /**
     * 한건을 등록한다. (다국어-메시지)
     */
    public HashMap<String, Object> insertLangMsg(HashMap<String, Object> param) throws Exception;

    /**
     * 한건을 수정한다. (다국어-메시지)
     */
    public HashMap<String, Object> updateLangMsg(HashMap<String, Object> param);

    /**
     * 한건을 사용하지 않는다. (다국어-메시지)
     */
    public HashMap<String, Object> disableLangMsg(HashMap<String, Object> param);

    /**
     * 리스트 조회한다. (일반코드)
     * */
    public HashMap<String, Object> selectComCodeList(HashMap<String, Object> param);

    /**
     * 한건 조회한다. (일반코드)
     * */
    public HashMap<String, Object> selectComCodeOne(HashMap<String, Object> param);

    /**
     * 한건을 등록한다. (일반코드)
     */
    public HashMap<String, Object> insertComCode(HashMap<String, Object> param) throws Exception;

    /**
     * 한건을 수정한다. (일반코드)
     */
    public HashMap<String, Object> updateComCode(HashMap<String, Object> param);

    /**
     * 한건을 사용하지 않는다. (일반코드)
     */
    public HashMap<String, Object> disableComCode(HashMap<String, Object> param);

    /**
     * 리스트 조회한다. (일반코드 - CODE_CD 별로)
     */
    public List<HashMap<String, Object>> selectComCodeListByCd(HashMap<String, Object> param);

    /**
     * 한건 조회 (시스템설정)
     */
    public HashMap<String, Object> selectSysSetting(HashMap<String, Object> param);

    /**
     * 한건을 수정한다. (시스템설정)
     */
    public HashMap<String, Object> updateSysSetting(HashMap<String, Object> param);
}
