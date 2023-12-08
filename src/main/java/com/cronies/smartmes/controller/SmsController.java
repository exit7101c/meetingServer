package com.cronies.smartmes.controller;

import com.cronies.smartmes.service.ProdRecordService;
import com.cronies.smartmes.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "sms")
public class SmsController {

	@Autowired
	private SmsService smsService;

	@RequestMapping(value = "sendManagerProdRegByEnter", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Object> sendManagerProdRegByEnter(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return smsService.sendManagerProdRegByEnter(param);
	}

}