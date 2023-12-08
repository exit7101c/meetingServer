package select.spring.exquery.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.session.SqlSessionFactory;

import select.spring.exquery.vo.ExFileInfo;

@Repository("exqueryDao")
public class ExqueryDaoImpl extends SqlSessionDaoSupport implements ExqueryDao {

    Logger log = LoggerFactory.getLogger(this.getClass());
    
    @Inject
	public void setMySqlSessionFactory(@Named("sqlSessionFactory")SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}
	
	// SQLMAP id로 변수가 바인딩된 SQL문장을 문자열로 구함
    public String getSelectString(String id, Object param) {    
        MappedStatement ms = getSqlSession().getConfiguration().getMappedStatement(id);
        
        // SQL 변수위치에 ? 로 표시된 문장
        BoundSql boundSql = ms.getBoundSql(param);
        String sql = boundSql.getSql();
        
        // From절 이하의 ? 를 파라미터로 변환하기 위한 처리
        /*
        int fromPos = getFromPosition(sql);
        String selectSql = sql.substring(0, fromPos);
        String fromSql = sql.substring(fromPos);
        */
        
        // 단일 숫자 
        if (param instanceof Integer || param instanceof Long || param instanceof Float || param instanceof Double) {
        	sql = sql.replaceFirst("\\?", param.toString());
        } 
        // 단일 문자
        else if(param instanceof String){
        	sql = sql.replaceFirst("\\?", "'" + param + "'");
        } 
        // Map
        else if(param instanceof Map){	
        	List<ParameterMapping> paramMappings = boundSql.getParameterMappings();	
        	
        	for(ParameterMapping mapping : paramMappings){
        	    String propValue = mapping.getProperty();
        	    @SuppressWarnings("rawtypes")
				Object value = ((Map) param).get(propValue);
        	    if (value instanceof String) {
        	    	sql = sql.replaceFirst("\\?", "'" + value + "'");
        	    } else if (value == null) {
        	    	sql = sql.replaceFirst("\\?", "null");
        	    } else {
        	    	sql = sql.replaceFirst("\\?", value.toString());
        	    }
        	    
        	}
        } 
        // 사용자정의 클래스
        else if (param != null) {
        	List<ParameterMapping> paramMapping = boundSql.getParameterMappings();
        	
        	Class<? extends Object> paramClass = param.getClass();
        	for(ParameterMapping mapping : paramMapping) {
        		try {
            	    String propValue = mapping.getProperty();			
            	    Field field = paramClass.getDeclaredField(propValue);
            	    field.setAccessible(true);	
            	    Class<?> javaType = mapping.getJavaType();	
            	    
            	    if (String.class == javaType) {
            	    	sql = sql.replaceFirst("\\?", "'" + field.get(param) + "'");
            	    } else {
            	    	sql = sql.replaceFirst("\\?", field.get(param).toString());
            	    }
				} catch (Exception e) {
					sql = sql.replaceAll("\\?", "''");
					break;
				}
        	}
        } 
        // 파라미터 없음
        else {
        	sql = sql.replaceAll("\\?", "''");
        }
        
        // sql = selectSql + fromSql;
        return sql;
        
    }    

	// SQLMAP id로 변수가 바인딩된 SQL문장을 문자열로 구함  == 백업 
    public String getSelectString2(String id, Object param) {    
        MappedStatement ms = getSqlSession().getConfiguration().getMappedStatement(id);
        
        // SQL 변수위치에 ? 로 표시된 문장
        BoundSql boundSql = ms.getBoundSql(param);
        String sql = boundSql.getSql();
        
        // From절 이하의 ? 를 파라미터로 변환하기 위한 처리
        int fromPos = getFromPosition(sql);
        String selectSql = sql.substring(0, fromPos);
        String fromSql = sql.substring(fromPos);
        
        // 단일 숫자 
        if (param instanceof Integer || param instanceof Long || param instanceof Float || param instanceof Double) {
        	fromSql = fromSql.replaceFirst("\\?", param.toString());
        } 
        // 단일 문자
        else if(param instanceof String){
        	fromSql = fromSql.replaceFirst("\\?", "'" + param + "'");
        } 
        // Map
        else if(param instanceof Map){	
        	List<ParameterMapping> paramMappings = boundSql.getParameterMappings();	
        	
        	for(ParameterMapping mapping : paramMappings){
        	    String propValue = mapping.getProperty();
        	    @SuppressWarnings("rawtypes")
				Object value = ((Map) param).get(propValue);
        	    if (value instanceof String) {
        	    	fromSql = fromSql.replaceFirst("\\?", "'" + value + "'");
        	    } else if (value == null) {
        	    	fromSql = fromSql.replaceFirst("\\?", "null");
        	    } else {
        	    	fromSql = fromSql.replaceFirst("\\?", value.toString());
        	    }
        	    
        	}
        } 
        // 사용자정의 클래스
        else if (param != null) {
        	List<ParameterMapping> paramMapping = boundSql.getParameterMappings();
        	
        	Class<? extends Object> paramClass = param.getClass();
        	for(ParameterMapping mapping : paramMapping) {
        		try {
            	    String propValue = mapping.getProperty();			
            	    Field field = paramClass.getDeclaredField(propValue);
            	    field.setAccessible(true);	
            	    Class<?> javaType = mapping.getJavaType();	
            	    
            	    if (String.class == javaType) {
            	    	fromSql = fromSql.replaceFirst("\\?", "'" + field.get(param) + "'");
            	    } else {
            	    	fromSql = fromSql.replaceFirst("\\?", field.get(param).toString());
            	    }
				} catch (Exception e) {
					fromSql = fromSql.replaceAll("\\?", "''");
					break;
				}
        	}
        } 
        // 파라미터 없음
        else {
        	fromSql = fromSql.replaceAll("\\?", "''");
        }
        
        sql = selectSql + fromSql;
        return sql;
        
    }    
    
    // SELECT문에서 조회 필드 부분을 COUNT구문으로 대체
    public String getSelectCountString(String id, Object param) {
    	String sql = getSelectString(id, param);
    	return getSelectCountString(sql);
    }
    
    // SELECT문에서 조회 필드 부분을 COUNT구문으로 대체
    public String getSelectCountString(String sql) {
        int fromPos = getFromPosition(sql);
        if (fromPos > 0) {
//        	sql = "SELECT COUNT(*) AS CNT " + sql.substring(fromPos);
        	sql = "SELECT CASE WHEN COUNT(*) = 1 THEN MAX(CNT) ELSE COUNT(*) END CNT FROM (SELECT COUNT(*) AS CNT " + sql.substring(fromPos) + " ) R";
        }
        
    	return sql;
    }
    
    // 단일 ROW SELECT, 결과 ROW수는 반드시 1 or 0건
	public HashMap<String, Object> selectOne(String id, Object param) {
		log.debug(this.writeLog(id, param));
		return getSqlSession().selectOne(id, param);
	}
	
	// 일반적인 SELECT, 결과 ROW수 상관 없음
	public List<HashMap<String, Object>> selectList(String id, Object param) {
		log.debug(this.writeLog(id, param));
		return getSqlSession().selectList(id, param);
		
	}

    // 단일 파일 정보 가져옴
	public ExFileInfo selectFile(String id, Object param) {
		log.debug(this.writeLog(id, param));
		return getSqlSession().selectOne(id, param);
	}

	// INSERT
	public int insert(String id, Object param) {
		log.debug(this.writeLog(id, param));
		return getSqlSession().insert(id, param);
	}

	// UPDATE
	public int update(String id, Object param) {
		log.debug(this.writeLog(id, param));
		return getSqlSession().update(id, param);
	}
	
	// DELETE
	public int delete(String id, Object param) {
		log.debug(this.writeLog(id, param));
		return getSqlSession().delete(id, param);
	}
	
	// SQL문의 다중수행, BATCH
	public void executeMultiple(String id, List<HashMap<String,Object>> paramList) {
		for(Object param : paramList){
			getSqlSession().update(id, param);
		}		
	}
	
	// SQL 문장내에서 FROM절의 시작위치를 구함
    private int getFromPosition(String sql) {
    	String lowerSql = sql.toLowerCase().replaceAll("\\s", " ");
    	String match = " from ";
        
    	List<Integer> fromPosList = new ArrayList<Integer>();
    	for (int i = -1; (i = lowerSql.indexOf(match, i + 1)) != -1; ) {
    		fromPosList.add(i);
    	}
    	
    	String checkSql = "";
    	int pos = 0;
    	int lbCnt, rbCnt, quoteMod;
    	for(int fromPos : fromPosList) {
    		checkSql = sql.substring(0, fromPos);
    		// from 이 ( ), ' ' 내에 있지 않은 경우 
    		lbCnt = StringUtils.countOccurrencesOf(checkSql, "(");
    		rbCnt = StringUtils.countOccurrencesOf(checkSql, ")");
    		quoteMod = StringUtils.countOccurrencesOf(checkSql, "'") % 2;
    		if (lbCnt == rbCnt && quoteMod == 0) {
    			pos = fromPos;
    			break;
    		}
    	}
    	return pos;
    }
    
    private String writeLog(String id, Object param) {
 
    	String logStr = "\n================== sqlMapId : " + id + "============\n";
		logStr += this.getSelectString(id, param);
		logStr += "\n===================================================================\n";
		return logStr;
    }

}
