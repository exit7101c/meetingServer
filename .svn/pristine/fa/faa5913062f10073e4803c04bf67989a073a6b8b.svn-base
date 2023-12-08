package com.nse.config.aspect;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import select.spring.exquery.service.ExqueryService;

public class UrlParamAdvice implements MethodInterceptor{

	@Autowired
	HttpServletRequest request;

	@Autowired
	HttpSession session;
	
	@Autowired
	private ExqueryService exqueryService;
	
	@SuppressWarnings("unchecked")
	@Override
	public final Object invoke(MethodInvocation invocation) throws Throwable {

		HashMap<String, Object> aMap = new HashMap<String, Object>();
		
		String menuUrl = request.getServletPath();
		String menuTab = request.getParameter("menuTab");
		
		// html body tag에 class를 삽입하기 위함
//		String langStyle = "";
//		if(session.getAttribute("ssUserLang") != null){
//			langStyle = session.getAttribute("ssUserLang").toString();
//			if(langStyle.equals("ko")){
//				langStyle = "";
//				aMap.put("ssSqlLang", 	"");
//			} else {
//				// 중국어인 경우에는 반영됨.
//				langStyle = "ch";
//				aMap.put("ssSqlLang", 	"_A");
//			}
//		}
		
		if (menuUrl != null && menuUrl.length() > 0) {
			menuUrl = menuUrl.substring(1);
			aMap.put("menuUrl", 	menuUrl);
		}

		String requestUrl = request.getRequestURL().toString();
		String rootPath = requestUrl;
		int pos = requestUrl.lastIndexOf(menuUrl);
		if (pos > 0) {
			rootPath = requestUrl.substring(0, pos);
		}
		
		String checkUrl = request.getServletPath();
		//중량모니터링 화면에서 세션이없을경우 자동으로 로그인되도록 처리 20161219 진의현
		if(session == null || session.getAttribute("ssUserId") == null){
			if(checkUrl.equals("/mainView") || checkUrl.equals("/mainCtq") || checkUrl.equals("/weightBag") || checkUrl.equals("/weightBowl") || checkUrl.equals("/moniCtq") || checkUrl.equals("/moniAlarmCtq") || checkUrl.equals("/weightBagNew") || checkUrl.equals("/frame") || checkUrl.equals("/mixerProcessStatus") || checkUrl.equals("/thicknessCollect")){
				session.setAttribute("ssUserId", "moniter");
				session.setAttribute("ssUserName", "모니터링");
			}
		}
		
		//로그인한 사용자 
		String userId = session.getAttribute("ssUserId").toString();
		aMap.put("ssUserId", userId);
				
		// 메서드의 argument 타입 ModelMap에 값을 추가한다. 
		Object[] invoArgs = invocation.getArguments();

		for(int i = 0; i < invoArgs.length; i++){
			if (invoArgs[i] instanceof ModelMap) {
				if(menuUrl != null){
					((Map<String,Object>)invoArgs[i]).put("ssMenuNavi", 	exqueryService.selectList("nse.pmsProgram.selectMenuNavi", aMap));	
				}
				((Map<String,Object>)invoArgs[i]).put("ssMenuUrl", 		menuUrl);	
				((Map<String,Object>)invoArgs[i]).put("ssMenuTab", 		menuTab);	
				((Map<String,Object>)invoArgs[i]).put("ssContextPath", 	rootPath);
//				((Map<String,Object>)invoArgs[i]).put("ssLangStyle", 	langStyle);

				//화면 스타일을 조회한다.
				HashMap<String, Object> pMap = exqueryService.selectOne("nse.pmsProgram.selectUserStyle", aMap);
				if(pMap==null){
					pMap = new HashMap<String, Object>();
					pMap.put("styleType", "BLACK");
				}
				String userStyle = (String)pMap.get("styleType");
				userStyle = userStyle.toLowerCase();
				((Map<String,Object>)invoArgs[i]).put("ssUserStyle", userStyle);	     
			}
		}

		// 메서드의 Annotation이 RequestParam인 argument에 값을 추가한다. 
		/*
		Annotation[][] aaa = invocation.getMethod().getParameterAnnotations();
		int argIdx = 0;
		for(Annotation[] aa : aaa){
			for(Annotation a : aa){
				if(a instanceof RequestParam){
					Object argument = invocation.getArguments()[argIdx];
					((Map<String,Object>)argument).put("ssContextPath", rootPath);
					break;
				}
			}
			argIdx++;
		}		
		*/
		
		//return invocation.getMethod().invoke(invocation.getThis(), invocation.getArguments());
		return invocation.proceed();
	
	}

}
