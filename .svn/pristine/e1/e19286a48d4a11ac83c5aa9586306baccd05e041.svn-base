package select.spring.exquery.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import select.spring.exquery.dao.ExqueryDao;

@Service("exqueryService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ExqueryServiceImpl implements ExqueryService {
    @Inject
    @Named("exqueryDao")
	private ExqueryDao exqueryDao;
    
    // 페이지 크기
    private static int PAGE_SIZE = 10;

	// 로거
    Logger log = LoggerFactory.getLogger(this.getClass());

	public int selectCount(String id, Object param) {
 		
		String selectCountStr = exqueryDao.getSelectCountString(id, param);
		HashMap<String,String> pMap = new HashMap<String,String>();
		pMap.put("sqlStmt", selectCountStr);
		HashMap<String,Object> rMap = exqueryDao.selectOne("select.spring.exquery.getCount", pMap);
		// return ((BigDecimal)rMap.get("cnt")).intValueExact(); 	// oracle
		return (int) Double.parseDouble(rMap.get("cnt").toString());
	}
    
	// 연관된 데이터가 존재하는지 여부 확인,   param = { tableName, colName, colValue }
	public boolean checkExists(HashMap<String, Object> param) {
		HashMap<String, Object> rMap = new HashMap<String, Object>();
		rMap = exqueryDao.selectOne("select.spring.exquery.checkExists", param);
		// int rowCnt = ((BigDecimal)rMap.get("cnt")).intValueExact();	// oracle
		int rowCnt = (int) Double.parseDouble(rMap.get("cnt").toString());
		// 연관된 데이터값이 존재하는 경우 
		return rowCnt > 0 ;
	}
	
	// 연관된 데이터가 존재하는지 여부 확인,   param = { tableName, [ {colName, colValue}, ....  ] }
	public boolean checkExistsV2(HashMap<String, Object> param) {
		HashMap<String, Object> rMap = new HashMap<String, Object>();
		rMap = exqueryDao.selectOne("select.spring.exquery.checkExistsV2", param);
		// int rowCnt = ((BigDecimal)rMap.get("cnt")).intValueExact();	// oracle
		int rowCnt = (int) Double.parseDouble(rMap.get("cnt").toString());
		// 연관된 데이터값이 존재하는 경우 
		return rowCnt > 0 ;
	}

//	
//	// 연관된 데이터가 존재하는지 여부 확인     pkListJson = [ { colName: '' , colValue: ''}, ...   ]
//	public boolean checkExists(String tableName, String pkListJson) throws Exception {
//		HashMap<String, Object> pMap = new HashMap<String, Object>();
//		pMap.put("tableName", tableName);
//		
//		ObjectMapper objectMapper = new ObjectMapper();
//
//	    List<HashMap<String, Object>> pkList = objectMapper.readValue(
//	            pkListJson, new TypeReference<ArrayList<HashMap<String, Object>>>(){} );
////	            objectMapper.getTypeFactory().constructCollectionType(
////	                    List.class, ExColumn.class));
//	    pMap.put("pkList", pkList);
//	    
//		HashMap<String, Object> rMap = new HashMap<String, Object>();
//		rMap = exqueryDao.selectOne("select.spring.exquery.checkExistsV2", pMap);
//		
//		int rowCnt = (int) Double.parseDouble(rMap.get("cnt").toString());
//		// 연관된 데이터값이 존재하는 경우 
//		return rowCnt > 0 ;
//	}
	
	public HashMap<String, Object> selectOne(String id, Object param) {
		HashMap<String, Object> rMap = exqueryDao.selectOne(id, param);
		return rMap;
	}

	public HashMap<String, Object> selectOneCap(String id, Object param) {
		HashMap<String, Object> rMap = new HashMap<String,Object>();
		rMap.put("result", this.selectOne(id, param));
		if (rMap.get("result") == null) {
			rMap.put("isExist", false);
		} else {
			rMap.put("isExist", true);
		}
		return rMap;
	}
	
	public List<HashMap<String,Object>> selectList(String id, Object param) {
		List<HashMap<String,Object>> rList = exqueryDao.selectList(id, param);
		return rList;
	}

	public HashMap<String,Object> selectListCap(String id, Object param) {
		HashMap<String, Object> rMap = new HashMap<String,Object>();
		List<HashMap<String,Object>> rList = this.selectList(id, param);
		if (rList == null) {
			rList = new ArrayList<HashMap<String,Object>>();
			rMap.put("isExist", false);
		} else {
			if (rList.size() > 0) {
				rMap.put("isExist", true);
			} else {
				rMap.put("isExist", false);
			}
		}
		rMap.put("result", rList);
		return rMap;
	}

	
	public HashMap<String,Object> selectPagingList(String id, Object param) {
		return selectPagingList(id, param, 1, PAGE_SIZE);
	}

	public HashMap<String,Object> selectPagingList(String id, Object param, int pageNo) {
		return selectPagingList(id, param, pageNo, PAGE_SIZE);
	}

	@SuppressWarnings("unchecked")
	public HashMap<String,Object> selectPagingListWithRownum(String id, Object param, int pageSize) {
		// 대상 총 ROW수
		int totalCount = selectCount(id, param);
		
		HashMap<String, Object> rMap = new HashMap<String, Object>();
		rMap.put("totalCount", totalCount);
		
		// 기본 설정 
		int pageNo = 1;
		String pagePk = "";
		
		rMap.put("pageNo", pageNo);
		rMap.put("pageSize", pageSize);

		// 대상 자료가 없는 경우 바로 리턴 (totalCount, pageNo, pageSize, pageResult)
		if (totalCount == 0) {
			rMap.put("pagePk", pagePk);
			rMap.put("isExist", false);
			rMap.put("result", new ArrayList<HashMap<String,Object>>());
			return rMap;
		}
		
		// param내에 pagePk가 포함되어 있어야 한다.
		String sql = exqueryDao.getSelectString(id, param);
		HashMap<String, Object> pMap = new HashMap<String, Object>();
		pMap.put("sqlStmt", sql);
		pMap.put("pageSize", pageSize);
		pMap.put("pagePk", ((HashMap<String,Object>)param).get("pagePk"));
		
		HashMap<String, Object> tmpMap = new HashMap<String, Object>();
		tmpMap = exqueryDao.selectOne("select.spring.exquery.getPagingListRnum", pMap);
		

		if (tmpMap != null) {
			pageNo = (int) Double.parseDouble(tmpMap.get("pageNo").toString());
			pagePk = tmpMap.get("pagePk").toString();
			rMap.put("pageNo", pageNo);
			rMap.put("pagePk", pagePk);
		}
		
		// 페이지번호, 페이지크기 정상여부 확인
		if (pageNo   < 1) pageNo = 1;
		if (pageSize < 1) pageSize = PAGE_SIZE;

		// 시작인덱스, 끝인덱스 설정
		int startIndex, endIndex;
		startIndex = (pageNo -1) * pageSize + 1;
		
		// 인덱스들이 유효범위 값으로 설정
		if (startIndex > totalCount) {
			pageNo = (int)Math.ceil( totalCount / pageSize);
			startIndex =  (pageNo -1) * pageSize + 1;
		}
		endIndex = startIndex + (pageSize -1);
		
		pMap.put("sqlStmt", sql);
		pMap.put("startIndex", startIndex);
		pMap.put("endIndex", endIndex);
		pMap.put("limitStart", startIndex - 1);
		pMap.put("pageSize", pageSize);
		
		// 반환할 정보 구성 (totalCount, pageNo, pageSize, pageResult)
		rMap.put("pageNo",   pageNo);
		rMap.put("pageSize", pageSize);
		List<HashMap<String, Object>> rList = exqueryDao.selectList("select.spring.exquery.getPagingList", pMap);
		rMap.put("result", rList);
		if (rList.size() > 0) {
			rMap.put("isExist", true);
		} else {
			rMap.put("isExist", false);
		}
		
		return rMap;
	}

	@SuppressWarnings("unchecked")
	public HashMap<String,Object> selectPagingList(String id, Object param, int pageNo, int pageSize) {
		HashMap<String, Object> checkMap = (HashMap<String, Object>)param;
		String pagePk = (String)checkMap.get("pagePk");
		
		if (checkMap.containsKey("pagePk") && !checkMap.get("pagePk").equals("")) {
			return selectPagingListWithRownum(id, param, pageSize);
		}
		
		// 대상 총 ROW수
		int totalCount = selectCount(id, param);
		
		HashMap<String, Object> rMap = new HashMap<String, Object>();
		rMap.put("totalCount", totalCount);
		// 대상 자료가 없는 경우 바로 리턴 (totalCount, pageNo, pageSize, pageResult)
		if (totalCount == 0) {
			rMap.put("pageNo", 1);
			rMap.put("pageSize", pageSize);
			rMap.put("isExist", false);
			rMap.put("result", new ArrayList<HashMap<String,Object>>());
			return rMap;
		}
		
		// 페이지번호, 페이지크기 정상여부 확인
		if (pageNo   < 1) pageNo = 1;
		if (pageSize < 1) pageSize = PAGE_SIZE;

		// 시작인덱스, 끝인덱스 설정
		int startIndex, endIndex;
		startIndex = (pageNo -1) * pageSize + 1;
		
		// 인덱스들이 유효범위 값으로 설정
		/*if (startIndex > totalCount) {
			pageNo = (int)Math.ceil( totalCount / pageSize);
			startIndex =  (pageNo -1) * pageSize + 1;
		}*/
		endIndex = startIndex + (pageSize -1);

		HashMap<String, Object> pMap = new HashMap<String, Object>();
		String sql = exqueryDao.getSelectString(id, param);
		pMap.put("sqlStmt", sql);
		pMap.put("startIndex", startIndex);
		pMap.put("endIndex", endIndex);
		pMap.put("limitStart", startIndex - 1);
		pMap.put("pageSize", pageSize);
		
		// 반환할 정보 구성 (totalCount, pageNo, pageSize, pageResult)
		rMap.put("pageNo",   pageNo);
		rMap.put("pageSize", pageSize);

		List<HashMap<String, Object>> rList = exqueryDao.selectList("select.spring.exquery.getPagingList", pMap);
		rMap.put("result", rList);
		if (rList.size() > 0) {
			rMap.put("isExist", true);
		} else {
			rMap.put("isExist", false);
		}
		
		return rMap;
	}
	
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public int insert(String id, Object param) {
		return exqueryDao.insert(id, param);
	}

	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public int update(String id, Object param) {
		return exqueryDao.update(id, param);
	}
	
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public int delete(String id, Object param) {
		return exqueryDao.delete(id, param);
	}
	
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public void executeMultiple(String id, List<HashMap<String,Object>> paramList) {
		exqueryDao.executeMultiple(id, paramList);
	}
	
}