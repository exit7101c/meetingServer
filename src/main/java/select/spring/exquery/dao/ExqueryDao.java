package select.spring.exquery.dao;

import java.util.HashMap;
import java.util.List;

import select.spring.exquery.vo.ExFileInfo;

public interface ExqueryDao {
	
	public String getSelectString(String id, Object param);
	
	public String getSelectString2(String id, Object param);

	public String getSelectCountString(String id, Object param);
	
	public String getSelectCountString(String sql);

	public HashMap<String, Object> selectOne(String id, Object param);

	public List<HashMap<String, Object>> selectList(String id, Object param);
	
	public ExFileInfo selectFile(String id, Object param);

	public int insert(String id, Object param);

	public int update(String id, Object param);
	
	public int delete(String id, Object param);

	public void executeMultiple(String id, List<HashMap<String, Object>> paramList);

}
