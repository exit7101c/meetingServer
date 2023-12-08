package cronies.meeting.user.service.impl;

import com.nse.config.CryptoUtil;
import cronies.meeting.user.service.CommonService;
import cronies.meeting.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import select.spring.exquery.service.ExqueryService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service("UserService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserServiceImpl implements UserService {

    Logger log = LoggerFactory.getLogger(this.getClass());

    private static final String HEADER_X_FORWARDED_FOR = "X-FORWARDED-FOR";

    @Autowired
    private ExqueryService exqueryService;

    @Autowired
    private CommonService commonService;

    @Autowired
    HttpSession session;

    @Autowired
    StringRedisTemplate redisTemplate;


    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> setLogin(HashMap<String, Object> param, HttpServletRequest request) throws Exception {
        /*String userId = String.ValueOf(param.get("userToken"));
        List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
        resultList = exqueryService.selectList("cronies.app.choice.getChoice", param);*/

        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        String userKey = "";

        //시스템 버전조회
        //HashMap<String, Object> versionMap = exqueryService.selectOne("nse.pms.smartmes.mesLogin.getVersion", new HashMap());

        String successYn = "N";//로그인 성공여부
        String message = "";//결과메시지
        String token = "";//로그인KEY


        String clientIp = request.getHeader(HEADER_X_FORWARDED_FOR);
        if(null == clientIp || clientIp.length() == 0 || clientIp.toLowerCase().equals("unknown")){
            clientIp = request.getRemoteAddr();
        }
        param.put("ip", clientIp);

        if( param.get("pw") != null && param.get("email") != null) {
//			BASE64Encoder encoder = new BASE64Encoder();
//			String password = (String) param.get("pw");
//			String encPassword = encoder.encode(password.getBytes()); // encode

            HashMap<String, Object> emailYn = exqueryService.selectOne("cronies.app.login.selectUserEmailYn", param);
            if(!emailYn.get("result").toString().equals("Y")){

                HashMap<String, Object> dropYn = exqueryService.selectOne("cronies.app.login.selectUserDropYn", param);
                if(!dropYn.get("dropYn").toString().equals("Y")){
                    if(!dropYn.get("dayBanYn").toString().equals("Y")){
                        if(!dropYn.get("maintainanceYn").toString().equals("Y")){
                            if(dropYn.get("isBeforeOpen").toString().equals("N")
                               || (dropYn.get("isBeforeOpen").toString().equals("Y") && !dropYn.get("preJoinYn").toString().equals("N"))
                            ){

                                String orgPw = param.get("pw").toString();
                                param.put("orgPw", orgPw);
                                CryptoUtil pwEncyript = new CryptoUtil();
                                String encPassword = pwEncyript.encrypt(param.get("pw").toString());
                                param.put("pw", encPassword);

                                HashMap<String, Object> aMap = exqueryService.selectOne("cronies.app.login.selectUserInfo", param);
                                if(aMap == null){
                                    successYn = "N";
//				msg = "Id does not exist. \n  " +
//						"ID 不存在。";
                                    message = "일치하는 사용자 정보가 없습니다.";
                                } else {
                                    if(aMap.get("connectYn").equals("N")){
                                        successYn = "N";
                                        message = "이 사용자는 이용이 제한 되었습니다. 관리자에게 문의해 주세요.";
                                    } else {

                                        successYn = "Y";
                                        message = "로그인 성공";

                                        // 로그인 성공 : 세션에 저장 -----------
                    /*session.setAttribute("ssUserId", 		aMap.get("userId").toString());
                    session.setAttribute("ssCustomerIp", 		clientIp);*/
                                        // 로그인 마지막 시간 저장
                                        // exqueryService.update("nse.pms.smartmes.mesLogin.updateLoginLast", aMap);

                                        //--------------------------------
                                        token = session.getId();
                                        userKey = commonService.getEncoding(aMap.get("userId").toString());
                                        //  aMap.put("token", token);
//						int logId = exqueryLogService.logIn(clientIp, aMap);
//
//						session.setAttribute("ssLoginLogId", 		logId);
                                        //--------------------------------

                                        //redis에 저장
                                        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
                                        HashMap<String, Object> redisMap = new HashMap<String, Object>();
                                        redisMap.put("ssUserId", aMap.get("userId").toString());
                                        redisMap.put("ssCustomerIp", clientIp);
                                        hashOperations.putAll(token,redisMap);

                    /*if(redisTemplate.opsForHash().hasKey("93jfj3", "userName")){
                        System.out.println("있음");
                    } else {
                        System.out.println("없음");
                    }
                    String userId = redisTemplate.opsForHash().get("93jfj3", "userName").toString();

                    System.out.println(userId);*/

                                    }
                                }


                            } else {
                                successYn = "N";
                                message = "아직 오픈 전입니다! 8월 16일 00시에 오픈됩니다!";
                            }
                        } else {
                            successYn = "N";
                            message = dropYn.get("maintainanceMessage").toString();
                        }
                    } else {
                        successYn = "N";
                        message = "사유: "+dropYn.get("banMessage").toString()+"\n"+
                                "\n기간: " +dropYn.get("banEndTime") + " 까지";
                    }
                } else {
                    successYn = "N";
                    message = "회원탈퇴된 아이디 입니다.";
                }

            } else {
                successYn = "N";
                message = "이메일이 잘못되었습니다. 이메일을 확인해주세요";
            }
        }



        resultMap.put("successYn", successYn);
        resultMap.put("message", message);
        resultMap.put("token", token);
        resultMap.put("userKey", userKey);
       // resultMap.put("version", versionMap.get("version"));

        return resultMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public HashMap<String, Object> setUserToken(HashMap<String, Object> param) throws Exception {



        HashMap<String, Object> resultMap = new HashMap<String, Object>();

        exqueryService.selectOne("cronies.app.login.setUserToken", param);


        resultMap.put("successYn", "Y");

        return resultMap;
    }
}
