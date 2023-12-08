package com.cronies.smartmes.controller;

import com.cronies.smartmes.service.MesLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value="mesLine")
public class MesLineController {

    @Autowired
    private MesLineService mesLineService;

    /**
     * Line 정보 조회
     * @param
     * @param
     * @return
     **/
    @RequestMapping( value="selectLineList", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public List<HashMap<String, Object>> selectLineList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
        return mesLineService.selectLineList(param);
    }
    /**
     * Line 정보 단건 조회
     * @param
     * @param
     * @return
     **/
    @RequestMapping( value="selectLineOne", method = {RequestMethod.GET, RequestMethod.POST})
    public HashMap<String, Object> selectLineOne(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
        return mesLineService.selectLineOne(param);
    }
    /**
     * Line 정보 등록
     * @param
     * @param
     * @return
     **/
    @RequestMapping( value="insertLine", method = {RequestMethod.GET, RequestMethod.POST})
    public HashMap<String, Object> insertLine(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
        return mesLineService.insertLine(param);
    }
    /**
     * Line 정보 수정
     * @param
     * @param
     * @return
     **/
    @RequestMapping( value="updateLine", method = {RequestMethod.GET, RequestMethod.POST})
    public HashMap<String, Object> updateLine(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
        return mesLineService.updateLine(param);
    }
    /**
     * Line 정보 삭제
     * @param
     * @param
     * @return
     **/
    @RequestMapping( value="deleteLine", method = {RequestMethod.GET, RequestMethod.POST})
    public HashMap<String, Object> deleteLine(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
        return mesLineService.deleteLine(param);
    }
    /**
     * Line 정보 조회(공통)
     * @param
     * @param
     * @return
     **/
    @RequestMapping( value="selectComLineList", method = {RequestMethod.GET, RequestMethod.POST})
    public List<HashMap<String, Object>> selectComLineList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
        return mesLineService.selectComLineList(param);
    }

    /**
     * Line 정보 삭제
     * @param
     * @param
     * @return
     **/
    @RequestMapping( value="selectLineProcList", method = {RequestMethod.GET, RequestMethod.POST})
    public HashMap<String, Object> selectLineProcList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
        return mesLineService.selectLineProcList(param);
    }
}
