package select.spring.exquery.service;

import java.util.HashMap;
import java.util.List;

public interface ExqueryService {
	
	public int selectCount(String id, Object param);

	public boolean checkExists(HashMap<String, Object> param);

	public boolean checkExistsV2(HashMap<String, Object> param);

// 	public boolean checkExists(String tableName, String pkListJson) throws Exception;
	
	public HashMap<String, Object> selectOne(String id, Object param);

	public HashMap<String, Object> selectOneCap(String id, Object param);
	

	public List<HashMap<String,Object>> selectList(String id, Object param);

	public HashMap<String,Object> selectListCap(String id, Object param);
	
	
	public HashMap<String,Object> selectPagingList(String id, Object param);

	public HashMap<String,Object> selectPagingList(String id, Object param, int pageNo);
	
	public HashMap<String,Object> selectPagingList(String id, Object param, int pageNo, int pageSize);
	
	public HashMap<String,Object> selectPagingListWithRownum(String id, Object param, int pageSize);

	
	public int insert(String id, Object param);

	public int update(String id, Object param);

	public int delete(String id, Object param);

	public void executeMultiple(String id, List<HashMap<String,Object>> paramList);

}
