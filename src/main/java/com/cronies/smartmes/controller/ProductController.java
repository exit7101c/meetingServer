package com.cronies.smartmes.controller;

import com.cronies.smartmes.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@RequestMapping(value = "getProdList", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<HashMap<String, Object>> getProdList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return productService.getProdList(param);
	}

	@RequestMapping(value = "insertProduct", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Object> insertProduct(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return productService.insertProduct(param);
	}

	@RequestMapping(value = "updateProduct", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Object> updateProduct(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return productService.updateProduct(param);
	}

	@RequestMapping(value = "deleteProduct", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Object> deleteProduct(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return productService.deleteProduct(param);
	}

	@RequestMapping(value = "selectProdOne", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Object> selectProdOne(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return productService.selectProdOne(param);
	}

}