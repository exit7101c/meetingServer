package com.nse.pms.standard.basic.controller;

import com.nse.pms.standard.basic.service.NseEquipCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value="equipCode")
public class NseEquipCodeController {

    @Autowired
    private NseEquipCodeService equipCodeService;

    //사용여부 (전체)
    @RequestMapping( value="selectEquipCodeList", method = {RequestMethod.GET, RequestMethod.POST})
    public List<HashMap<String, Object>> selectEquipCodeList(@RequestParam HashMap<String, Object> param) throws Exception {
        return equipCodeService.selectEquipCodeList(param);
    }

    //사용여부 (사용)
    @RequestMapping( value="selectEquipCodeList2", method = {RequestMethod.GET, RequestMethod.POST})
    public List<HashMap<String, Object>> selectEquipCodeList2(@RequestParam HashMap<String, Object> param) throws Exception {
        return equipCodeService.selectEquipCodeList2(param);
    }

    @RequestMapping( value="selectEquipCodeOne", method = {RequestMethod.GET, RequestMethod.POST})
    public HashMap<String, Object> selectEquipCodeOne(@RequestParam HashMap<String, Object> param) throws Exception {
        return equipCodeService.selectEquipCodeOne(param);
    }

    @RequestMapping( value="insertEquipCode", method = {RequestMethod.GET, RequestMethod.POST})
    public HashMap<String, Object> insertEquipCode(@RequestParam HashMap<String, Object> param) throws Exception {
        return equipCodeService.insertEquipCode(param);
    }

    @RequestMapping( value="updateEquipCode", method = {RequestMethod.GET, RequestMethod.POST})
    public HashMap<String, Object> updateEquipCode(@RequestParam HashMap<String, Object> param) throws Exception {
        return equipCodeService.updateEquipCode(param);
    }

    @RequestMapping( value="disableEquipCode", method = {RequestMethod.GET, RequestMethod.POST})
    public HashMap<String, Object> disableEquipCode(@RequestParam HashMap<String, Object> param) throws Exception {
        return equipCodeService.disableEquipCode(param);
    }

    @RequestMapping( value="selectProcEquipList", method = {RequestMethod.GET, RequestMethod.POST})
    public List<HashMap<String, Object>> selectProcEquipList(@RequestParam HashMap<String, Object> param) throws Exception {
        return equipCodeService.selectProcEquipList(param);
    }

    @RequestMapping( value="selectProcEquipList2", method = {RequestMethod.GET, RequestMethod.POST})
    public List<HashMap<String, Object>> selectProcEquipList2(@RequestParam HashMap<String, Object> param) throws Exception {
        return equipCodeService.selectProcEquipList2(param);
    }


    /**
     * [설비별 접점매핑] 접점 리스트 조회
     **/
    @RequestMapping( value="selectTagList", method = {RequestMethod.GET, RequestMethod.POST})
    public List<HashMap<String, Object>> selectTagList(@RequestParam HashMap<String, Object> param) throws Exception {
        return equipCodeService.selectTagList(param);
    }
    /**
     * [설비별 접점매핑] 접점 Mapping
     **/
    @RequestMapping( value="insertEqpTag", method = {RequestMethod.GET, RequestMethod.POST})
    public HashMap<String, Object> insertEqpTag(@RequestParam HashMap<String, Object> param) throws Exception {
        return equipCodeService.insertEqpTag(param);
    }
}