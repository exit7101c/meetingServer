package com.cronies.smartmes.service.impl;

import com.cronies.smartmes.service.SmsService;
import com.nse.pms.standard.common.service.ConstraintService;
import com.cronies.smartmes.service.MesLineService;
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

@Service("MseLineService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class MesLineServiceImpl implements MesLineService {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExqueryService exqueryService;

    @Autowired
    private ConstraintService constraintService;

    @Autowired
    private SmsService smsService;

    /**
     * Line 정보 조회
     * @param HashMap<String, Object>
     * @return HashMap<String, Object>
     **/
    @Transactional(propagation = Propagation.REQUIRED , readOnly = false)
    public List<HashMap<String, Object>> selectLineList(HashMap<String, Object> param){

        List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();

        resultList = exqueryService.selectList("nse.pms.smartmes.mesLine.selectLineList", param);

        return resultList;
    }

    /**
     * Line 정보 단건 조회
     * @param HashMap<String, Object>
     * @return HashMap<String, Object>
     **/
    @Transactional(propagation = Propagation.REQUIRED , readOnly = false)
    public HashMap<String, Object> selectLineOne(HashMap<String, Object> param){

        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        resultMap = exqueryService.selectOne("nse.pms.smartmes.mesLine.selectLineOne", param);
        return resultMap;
    }

    /**
     * Line 정보 등록
     * @param HashMap<String, Object>
     * @return HashMap<String, Object>
     **/
    @Transactional(propagation = Propagation.REQUIRED , readOnly = false)
    public HashMap<String, Object> insertLine(HashMap<String, Object> param) throws Exception {

        //constraint check
        // 수정해야됨
        /*String pk = "{'colName':'LINE_CD','colValue':'"+param.get("lineCd")+"'}";
        String con = "["+"{'tableName':'COM_LINE', 'pkList':["+pk+"], 'msgCode':'중복되었습니다.'}"+"]";
        String consResult = constraintService.checkExistsV2(con.replaceAll("'", "\""));*/
        HashMap<String, Object> resultMap = new HashMap<>();
//        resultMap.put("resultMsg", consResult);

        boolean isExist = false;

//        if(consResult.equals("OK")){
            // 라인 추가
            if(exqueryService.insert("nse.pms.smartmes.mesLine.insertLine", param) == 1){
                param.put("enterSeq", param.get("ssCustomerEnterCd"));
                // 기본공정 추가
               /* if(exqueryService.insert("nse.pms.smartmes.mesProcess.insertProcessByJoin", param) == 1){
                    // 기본설비 추가
                    param.put("lineCd", param.get("lineSeq"));
                    if(exqueryService.insert("nse.pms.smartmes.mesEquipment.insertEquipmentByProc", param) == 1){
                        isExist = true;
                    }
                }*/
            }
//        }

        resultMap.put("isExist", isExist);
        return resultMap;
    }

    /**
     * Line 정보 수정
     * @param HashMap<String, Object>
     * @return HashMap<String, Object>
     **/
    @Transactional(propagation = Propagation.REQUIRED , readOnly = false)
    public HashMap<String, Object> updateLine(HashMap<String, Object> param){
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        exqueryService.update("nse.pms.smartmes.mesLine.updateLine", param);
        return resultMap;

    }

    /**
     * Line 정보 삭제
     * @param HashMap<String, Object>
     * @return HashMap<String, Object>
     **/
    @Transactional(propagation = Propagation.REQUIRED , readOnly = false)
    public HashMap<String, Object> deleteLine(HashMap<String, Object> param){
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        List<HashMap<String, Object>> managerList = new ArrayList<HashMap<String, Object>>();

        // 라인에 해당하는 매니저 리스트를 조회한다.
        managerList = exqueryService.selectList("nse.pms.smartmes.mesManager.selectManagerListByLineCd", param);
        //매니저리스트가 존재한다면
        if(managerList.size() > 0){
            for(int i=0,len=managerList.size(); i < len; i++){
                // 해당 라인 및 공정에 관련된 담당자들 삭제
                HashMap<String, Object> paramMap = new HashMap<String, Object>();
                paramMap.put("managerId", managerList.get(i).get("managerId"));
                exqueryService.delete("nse.pms.smartmes.mesManager.deleteManager", paramMap);
            }
        }
        // 라인에 연결된 설비 미사용처리
        exqueryService.update("nse.pms.smartmes.mesEquipment.deleteEquipmentByLine", param);
        // 라인에 연결된 공정 미사용처리
        exqueryService.update("nse.pms.smartmes.mesProcess.deleteProcessByLine", param);
        // 해당 라인 미사용처리
        exqueryService.update("nse.pms.smartmes.mesLine.deleteLine", param);
        return resultMap;

    }

    /**
     * Line 정보 조회(공통)
     * @param HashMap<String, Object>
     * @return HashMap<String, Object>
     **/
    @Transactional(propagation = Propagation.REQUIRED , readOnly = false)
    public List<HashMap<String, Object>> selectComLineList(HashMap<String, Object> param){

        List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();

        resultList = exqueryService.selectList("nse.pms.smartmes.mesLine.selectComLineList", param);

        return resultList;
    }

    /**
     * Line 정보 + Proc 정보 조회
     * @param HashMap<String, Object>
     * @return HashMap<String, Object>
     **/
    @Transactional(propagation = Propagation.REQUIRED , readOnly = false)
    public HashMap<String, Object> selectLineProcList(HashMap<String, Object> param){

        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        List<HashMap<String, Object>> lineList = new ArrayList<HashMap<String, Object>>();
        List<HashMap<String, Object>> procList = new ArrayList<HashMap<String, Object>>();

        lineList = exqueryService.selectList("nse.pms.smartmes.mesLine.selectLineList", param);
        if(lineList.size() > 0){
            for(int i=0,len=lineList.size(); i < len; i++){
                param.put("lineCd", lineList.get(i).get("lineCd"));
                //공정을 조회한다.
                procList = exqueryService.selectList("nse.pms.smartmes.mesProcess.selectProcListByLineCd", param);
                lineList.get(i).put("procList", procList);
            }
        }

        resultMap.put("result", lineList);
        return resultMap;
    }
}
