package com.cronies.smartmes.service;

import java.util.HashMap;
import java.util.List;

public interface ProdRecordService {

	public List<HashMap<String, Object>> selectDailyProdRecordList(HashMap<String, Object> param) throws Exception;

}