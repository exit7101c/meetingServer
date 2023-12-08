package com.nse.pms.standard.basic.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nse.pms.standard.basic.service.NseEquipCodeService;
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

@Service("NseEquipCodeService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class NseEquipCodeServiceImpl implements NseEquipCodeService {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExqueryService exqueryService;

    @Autowired
    private ConstraintService constraintService;

    @Override
    public List<HashMap<String, Object>> selectEquipCodeList(HashMap<String, Object> param) {
        return exqueryService.selectList("nse.pms.basic.sqlEquipCode.selectEquipCodeList", param);
    }

    @Override
    public List<HashMap<String, Object>> selectEquipCodeList2(HashMap<String, Object> param) {
        return exqueryService.selectList("nse.pms.basic.sqlEquipCode.selectEquipCodeList2", param);
    }

    @Override
    public HashMap<String, Object> selectEquipCodeOne(HashMap<String, Object> param) throws Exception {
        return exqueryService.selectOne("nse.pms.basic.sqlEquipCode.selectEquipCodeOne", param);
    }

    @Override
    public HashMap<String, Object> insertEquipCode(HashMap<String, Object> param) throws Exception {
        String pk = "{'colName':'EQP_CD','colValue':'"+param.get("eqpCd")+"'}";
        String con = "["+"{'tableName':'COM_EQUIPMENT', 'pkList':["+pk+"], 'msgCode':'중복되었습니다.'}"+"]";
        String consResult = constraintService.checkExistsV2(con.replaceAll("'", "\""));
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("resultMsg", consResult);


        boolean isExist = false;
        if(consResult.equals("OK")){
            exqueryService.insert("nse.pms.basic.sqlEquipCode.insertEquipCode", param);
            isExist = true;
        }
        resultMap.put("isExist", isExist);
        return resultMap;
    }

    @Override
    public HashMap<String, Object> updateEquipCode(HashMap<String, Object> param) throws Exception {
        for (String s : param.keySet()) {
            //System.out.println("Key: " + s + ". Value: " + param.get(s));
        }
        HashMap<String, Object> result = new HashMap<>();
        int updateCount = exqueryService.update("nse.pms.basic.sqlEquipCode.updateEquipCode", param);
        result.put("isExist", updateCount > 0);
        return result;
    }

    @Override
    public HashMap<String, Object> disableEquipCode(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> result = new HashMap<>();
        // 실제로는 delete 안하고 update 한다 ...
        int updateCount = exqueryService.update("nse.pms.basic.sqlEquipCode.disableEquipCode", param);
        result.put("isExist", updateCount > 0);
        return result;
    }

    @Override
    public List<HashMap<String, Object>> selectProcEquipList(HashMap<String, Object> param) {
        return exqueryService.selectList("nse.pms.basic.sqlEquipCode.selectProcEquipList", param);
    }


    @Override
    public List<HashMap<String, Object>> selectProcEquipList2(HashMap<String, Object> param) {
        return exqueryService.selectList("nse.pms.basic.sqlEquipCode.selectProcEquipList2", param);
    }



    /**
     * [설비별 접점매핑] 접점 리스트 조회
     * @param HashMap<String, Object>
     * @return HashMap<String, Object>
     **/
    @Transactional(propagation = Propagation.REQUIRED , readOnly = false)
    public List<HashMap<String, Object>> selectTagList(HashMap<String, Object> param){

        List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();

        resultList = exqueryService.selectList("nse.pms.basic.sqlEquipCode.selectTagList", param);

        return resultList;
    }

    /**
     * [설비별 접점매핑] 접점 Mapping
     * @param HashMap<String, Object>
     * @return HashMap<String, Object>
     **/
    @Transactional(propagation = Propagation.REQUIRED , readOnly = false)
    public HashMap<String,Object> insertEqpTag(HashMap<String, Object> param) throws Exception {

        HashMap<String,Object> resultHashMap = new HashMap<String,Object>();
        List<HashMap<String,Object>> dataList = new ArrayList<HashMap<String,Object>>();

        ObjectMapper mapper = new ObjectMapper();
        dataList =  mapper.readValue(param.get("data").toString(), new TypeReference<ArrayList<HashMap<String, Object>>>() {});

        // 1. 삭체 처리
        exqueryService.delete("nse.pms.basic.sqlEquipCode.deleteEqpTag", param);

        // 2. Checked 데이터 INSERT
        if(dataList.size()>0) {
            for(int i=0; i<dataList.size(); i++) {
                dataList.get(i).put("eqpCd", param.get("eqpCd"));
                dataList.get(i).put("ssUserId", param.get("ssUserId"));
                exqueryService.insert("nse.pms.basic.sqlEquipCode.insertEqpTag", dataList.get(i));
            }
        }
        resultHashMap.put("isUpdate", true);
        return resultHashMap;
    }





}
