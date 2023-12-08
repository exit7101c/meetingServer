package select.spring.exquery.service;

import java.util.HashMap;
import java.util.List;

import select.spring.exquery.vo.ExResult;

public interface RemoteQuery {

	public ExResult execute(int sqlTypeNo, String sqlId, Object param) ;

	public ExResult executeMultiple(String sqlId, List<HashMap<String, Object>> paramList) ;

}
