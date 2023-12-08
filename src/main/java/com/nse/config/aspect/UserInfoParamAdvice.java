package com.nse.config.aspect;

import com.nse.config.HttpSessionCollector;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.annotation.Annotation;
import java.util.Map;

public class UserInfoParamAdvice implements MethodInterceptor{

	@Autowired
	HttpServletRequest request;
	
	@Autowired
	HttpSession session;
	
	@SuppressWarnings("unchecked")
	@Override
	public final Object invoke(MethodInvocation invocation) throws Throwable {

		Annotation[][] aaa = invocation.getMethod().getParameterAnnotations();

		int argIdx = 0;
		for(Annotation[] aa : aaa){
			for(Annotation a : aa){
				// 실행될 메서드에서 @RequestParam 유형을 검색 					
				if(a instanceof RequestParam){
					Object argument = invocation.getArguments()[argIdx];

					if(argument instanceof Map == true && !((Map<String,Object>)argument).isEmpty() ){
						HttpSession nSession = HttpSessionCollector.find(((Map<String,Object>)argument).get("userToken").toString());
						if(nSession != null){
							//넘겨받은 토큰이 있음
							((Map<String,Object>)argument).put("ssIsLogin", 		true);
							((Map<String,Object>)argument).put("ssUserId", 		nSession.getAttribute("ssUserId"));
							((Map<String,Object>)argument).put("ssUserName",  	nSession.getAttribute("ssUserName"));
							((Map<String,Object>)argument).put("ssUserLang",  	nSession.getAttribute("ssUserLang"));
							((Map<String,Object>)argument).put("ssUserAreaCd",  	nSession.getAttribute("ssUserAreaCd"));
							((Map<String,Object>)argument).put("lang",  		nSession.getAttribute("ssUserLang"));
							((Map<String,Object>)argument).put("ssUserIp",  	nSession.getAttribute("ssUserIp"));
						} else {
							//넘겨받은 토큰이 없거나 유효하지 않음.
							((Map<String,Object>)argument).put("ssIsLogin", 		false);
							((Map<String,Object>)argument).put("ssUserId", 		"");
							((Map<String,Object>)argument).put("ssUserName",  	"");
						}
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
