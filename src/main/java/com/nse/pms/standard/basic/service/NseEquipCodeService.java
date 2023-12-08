package com.nse.pms.standard.basic.service;

import java.util.HashMap;
import java.util.List;

public interface NseEquipCodeService {
    /**
     * 설비 정보 조회
     * @param HashMap<String, Object>
     * @return List<HashMap<String, Object>>
     **/
    public List<HashMap<String, Object>> selectEquipCodeList(HashMap<String, Object> param) throws Exception;


    /**
     * 설비 정보 조회
     * @param HashMap<String, Object>
     * @return List<HashMap<String, Object>>
     **/
    public List<HashMap<String, Object>> selectEquipCodeList2(HashMap<String, Object> param) throws Exception;

    /**
     * 딘일 설비 정보 조회
     * @param HashMap<String, Object>
     * @return HashMap<String, Object>
     **/
    public HashMap<String, Object> selectEquipCodeOne(HashMap<String, Object> param) throws Exception;
    /**
     * 설비 정보 등록
     * @param HashMap<String, Object>
     * @return HashMap<String, Object>
     **/
    public HashMap<String, Object> insertEquipCode(HashMap<String, Object> param) throws Exception;
    /**
     * 설비 정보 수정
     * @param HashMap<String, Object>
     * @return HashMap<String, Object>
     **/
    public HashMap<String, Object> updateEquipCode(HashMap<String, Object> param) throws Exception;
    /**
     * 설비 정보 삭제
     * @param HashMap<String, Object>
     * @return HashMap<String, Object>
     **/
    public HashMap<String, Object> disableEquipCode(HashMap<String, Object> param) throws Exception;

    /**
     * 설비 정보 조회
     * @param HashMap<String, Object>
     * @return List<HashMap<String, Object>>
     **/
    public List<HashMap<String, Object>> selectProcEquipList(HashMap<String, Object> param) throws Exception;
    public List<HashMap<String, Object>> selectProcEquipList2(HashMap<String, Object> param) throws Exception;


    /**
     * [설비별 접점매핑] 접점 리스트 조회
     * @param HashMap<String, Object>
     * @return List<HashMap<String, Object>>
     **/
    public List<HashMap<String, Object>> selectTagList(HashMap<String, Object> param) throws Exception;

    /**
     * [설비별 접점매핑] 접점 Mapping
     * @param HashMap<String, Object>
     * @return List<HashMap<String, Object>>
     **/
    public HashMap<String, Object> insertEqpTag(HashMap<String, Object> param) throws Exception;

}
