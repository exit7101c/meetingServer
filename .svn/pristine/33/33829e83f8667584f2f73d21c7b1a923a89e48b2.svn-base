package com.cronies.smartmes.controller;

import com.cronies.smartmes.service.ProdRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "prodRecord")
public class ProdRecordController {

	@Autowired
	private ProdRecordService prodRecordService;

	@RequestMapping(value = "selectDailyProdRecordList", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<HashMap<String, Object>> selectDailyProdRecord(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return prodRecordService.selectDailyProdRecordList(param);
	}

}