package cronies.meeting.user.config;

import com.nse.config.HttpSessionCollector;
//import com.nse.pms.standard.common.service.CommonAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

public class UserSessionAdvice extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(com.nse.config.aspect.UrlAuthAdvice.class);

	/*@Inject
	@Named("commonAuthService")
	private CommonAuthService commonAuthService;*/


	@Autowired
	StringRedisTemplate redisTemplate;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){

		String url = request.getRequestURI();
		String[] parseUrl = url.split("/");
		String menuUrl = parseUrl[parseUrl.length - 1];

		menuUrl = menuUrl.split(";")[0];	// JSessionId 붙는 경우 제거용
		String rootPath   =  request.getContextPath();
		HttpSession session  =  request.getSession(false);

		//String userToken = request.getParameter("userToken");
		String userToken = request.getParameter("userToken");
		//HttpSession nSession = HttpSessionCollector.find(userToken);
		HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();

			try {
				//redis에 토큰이 유요한지 체크한다.
				if(!redisTemplate.hasKey(userToken)){
					logger.debug("=== redirected by UrlAuthAdvice :: has not logged in !! ===");
					response.sendRedirect(rootPath + "/reject");
					return false;
				}


				/*if (nSession == null || nSession.getAttribute("ssUserId") == null) {
					logger.debug("=== redirected by UrlAuthAdvice :: has not logged in !! ===");
					response.sendRedirect(rootPath + "/reject");
					return false;
				}*/

				/*String userId = (String)nSession.getAttribute("ssUserId");
				if (
						!menuUrl.equals("muPopEquipData") &&
						!menuUrl.equals("muPopEquipData2") &&
						!commonAuthService.isValidAccess(userId, menuUrl)
				) {
					logger.debug("=== redirected by UrlAuthAdvice :: not authorized access!! ===");
					//  response.sendRedirect(rootPath + "/index");
					response.sendRedirect(rootPath + "/error");
					return false;
				}*/

				/*if (!commonAuthService.isValidAccess(userId, menuUrl)) {
					logger.debug("=== redirected by UrlAuthAdvice :: not authorized access!! ===");
					//  response.sendRedirect(rootPath + "/index");
					response.sendRedirect(rootPath + "/error");
					return false;
				}*/
		} catch (Exception e) {
			return false;
		}

		return true;
	}

}
