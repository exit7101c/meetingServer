package select.spring.exquery.service;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import select.spring.exquery.vo.ExResult;


@Service("remoteQuery")
public class RemoteQueryImpl implements RemoteQuery {

    @Inject
    @Named("exqueryService")
	private ExqueryService exqueryService;
    
    private final int SELECT_COUNT	= 1;			// 결과 ROW수
    private final int SELECT_ONE	= 2;			// SELECT 1건
    private final int SELECT_LIST	= 3;			// 일반 SELECT (N건)
    private final int SELECT_PAGE	= 4;			// SELECT문 페이징처리
    private final int INSERT		= 5;			// INSERT
    private final int UPDATE		= 6;			// UPDATE
    private final int DELETE		= 7;			// DELETE
	
	//Get Logger
    Logger log = LoggerFactory.getLogger(this.getClass());

    public ExResult execute(int sqlTypeNo, String sqlId, Object param) {
		
		log.debug("RemoteQuery.execute({}, {}) 실행!", sqlTypeNo, sqlId);
		
		Object data = null;	// 서비스의 실행결과
		try {
			switch (sqlTypeNo) {
			case SELECT_COUNT :
				data = exqueryService.selectCount(sqlId, param);
				break;
			case SELECT_ONE :
				data = exqueryService.selectOne(sqlId, param);
				break;
			case SELECT_LIST :
				data = exqueryService.selectList(sqlId, param);
				break;
			case SELECT_PAGE :
				@SuppressWarnings("rawtypes")
				int pageNo = Integer.parseInt((String)((HashMap)param).get("pageNo"));
				@SuppressWarnings("rawtypes")
				int pageSize = Integer.parseInt((String)((HashMap)param).get("pageSize"));
				data = exqueryService.selectPagingList(sqlId, param, pageNo, pageSize);
				break;
			case INSERT :
				data = exqueryService.insert(sqlId, param);
				break;
			case UPDATE :
				data = exqueryService.update(sqlId, param);
				break;
			case DELETE :
				data = exqueryService.delete(sqlId, param);
				break;
			}
		} catch (Exception e) {
			// TODO  : ExResult의 오류처리 보완할 것
			log.debug("Error::QueryService.execute()", e);
			return new ExResult("-1", e.getMessage());
		}
		return new ExResult(data);
	}

    public ExResult executeMultiple(String sqlId, List<HashMap<String, Object>> paramList) {
		try {
			exqueryService.executeMultiple(sqlId, paramList);
		} catch (Exception e) {
			// TODO  : ExResult의 오류처리 보완할 것
			log.debug("Error::QueryService.executeMultiple()", e);
			return new ExResult("-1", e.getMessage());
		}
		return new ExResult("0", "OK");
    }
	
}
