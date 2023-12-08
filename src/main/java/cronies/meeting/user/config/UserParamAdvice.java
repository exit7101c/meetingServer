package cronies.meeting.user.config;

import com.nse.config.HttpSessionCollector;
import cronies.meeting.user.service.CommonService;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.Map;

public class UserParamAdvice implements MethodInterceptor{

	@Autowired
	HttpServletRequest request;
	
	@Autowired
	HttpSession session;

	@Autowired
	CommonService commonService;

	@Autowired
	StringRedisTemplate redisTemplate;

	@SuppressWarnings("unchecked")
	@Override
	public final Object invoke(MethodInvocation invocation) throws Throwable {

		Annotation[][] aaa = invocation.getMethod().getParameterAnnotations();
		HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();

		int argIdx = 0;
		for(Annotation[] aa : aaa){
			for(Annotation a : aa){
				// 실행될 메서드에서 @RequestParam 유형을 검색 					
				if(a instanceof RequestParam){
					Object argument = invocation.getArguments()[argIdx];

					if(argument instanceof Map == true && !((Map<String,Object>)argument).isEmpty() ){

						/*Iterator<String> keys = ((Map<String,Object>)argument).keySet().iterator();*/
						/*while( keys.hasNext() ){
							String key = keys.next();
							System.out.println( String.format("키 -> %s, 값 -> %s", key, ((Map<String,Object>)argument).get(key)) );

							if(key.equals("userToken")){*/

						if(((Map<String,Object>)argument).containsKey("userToken")){
							String currToken = ((Map<String,Object>)argument).get("userToken").toString();
							if(redisTemplate.hasKey(currToken)){
								//String ssUserId = redisTemplate.opsForHash().get(((Map<String,Object>)argument).get("userToken").toString(), "ssUserId").toString();
								String ssUserId = hashOperations.get(currToken, "ssUserId").toString();
										//넘겨받은 토큰이 있음
								((Map<String,Object>)argument).put("ssIsLogin", 		true);
								((Map<String,Object>)argument).put("ssUserId", 		ssUserId);
								((Map<String,Object>)argument).put("ssUserKey", 	commonService.getEncoding(ssUserId));
							} else {
								//넘겨받은 토큰이 없거나 유효하지 않음.
								((Map<String,Object>)argument).put("ssIsLogin", 		false);
								((Map<String,Object>)argument).put("ssUserId", 		"0");
							}
							//HttpSession nSession = HttpSessionCollector.find(((Map<String,Object>)argument).get("userToken").toString());
							/*if(nSession != null){
								//넘겨받은 토큰이 있음
								((Map<String,Object>)argument).put("ssIsLogin", 		true);
								((Map<String,Object>)argument).put("ssUserId", 		nSession.getAttribute("ssUserId"));
								((Map<String,Object>)argument).put("ssUserKey", 		commonService.getEncoding(nSession.getAttribute("ssUserId").toString()));
							} else {
								//넘겨받은 토큰이 없거나 유효하지 않음.
								((Map<String,Object>)argument).put("ssIsLogin", 		false);
								((Map<String,Object>)argument).put("ssUserId", 		"NONE");
							}*/
						} else {
							((Map<String,Object>)argument).put("ssUserId", 		"0");
						}
						if(((Map<String,Object>)argument).containsKey("userKey")){
							((Map<String,Object>)argument).put("userId", 		commonService.getDecoding(((Map<String,Object>)argument).get("userKey").toString()));
						}
						if(((Map<String,Object>)argument).containsKey("fileKey")){
							((Map<String,Object>)argument).put("fileId", 		commonService.getDecoding(((Map<String,Object>)argument).get("fileKey").toString()));
						}

							/*} else if(key.equals("userKey")){
								((Map<String,Object>)argument).put("userId", 		commonService.getDecoding(((Map<String,Object>)argument).get(key).toString()));
							} else if(key.equals("fileKey")){
								((Map<String,Object>)argument).put("fileId", 		commonService.getDecoding(((Map<String,Object>)argument).get(key).toString()));
							}
						}*/


					}

				/*	if(argument instanceof Map == true && session.getAttribute("ssUserId") != null){
						((Map<String,Object>)argument).put("ssUserId", 		session.getAttribute("ssUserId"));
						((Map<String,Object>)argument).put("ssUserName",  	session.getAttribute("ssUserName"));
					} else {
						// 임시 하드코딩
						((Map<String,Object>)argument).put("ssUserId", 		"ADMIN");
						((Map<String,Object>)argument).put("lang",  	"ko");
					}*/
				}
			}
			argIdx++;
		}		
		//return invocation.getMethod().invoke(invocation.getThis(), invocation.getArguments());
		return invocation.proceed();
	}

}
