package cronies.meeting.user.controller;

import cronies.meeting.user.service.AskMeService;
import cronies.meeting.user.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import select.spring.exquery.service.ExqueryService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "askMe")
public class AskMeController {

    private static final String HEADER_X_FORWARDED_FOR = "X-FORWARDED-FOR";

    @Autowired
    private AskMeService askmeService;

    @Autowired
    private ExqueryService exqueryService;

    @Autowired
    private CommonService commonService;

    @RequestMapping(value = "getUserAskMe", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public HashMap<String, Object> getUserAskMe(@RequestParam HashMap<String, Object> param) throws Exception {
        return askmeService.getUserAskMe(param);
    }

    @RequestMapping(value = "setAskMeCode", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public HashMap<String, Object> setAskMeCode(@RequestParam HashMap<String, Object> param) throws Exception {
        return askmeService.setAskMeCode(param);
    }

    @RequestMapping(value = "getAskMeUserInfo", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public HashMap<String, Object> getAskMeUserInfo(@RequestParam HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        returnMap = exqueryService.selectOne("cronies.app.askMe.getAskMeUserInfo", param);
        if(returnMap == null){
            resultMap.put("successYn", "N");
            resultMap.put("message", "이미 만료된 URL입니다.");
        } else {
            param.put("userId", returnMap.get("userKey"));
            try {
                exqueryService.update("cronies.app.askMe.updateAskMeUserViewCnt", param);
            } catch (Exception e) {
//            resultMap.put("successYn", "N");
//            resultMap.put("message", "문의 부탁드립니다.");
            }

            resultMap.put("successYn", "Y");
            returnMap.put("userKey", commonService.getEncoding(String.valueOf(returnMap.get("userKey"))));
            resultMap.put("userInfo", returnMap);
        }
        return resultMap;
    }

    @RequestMapping(value = "sendAskMe", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public HashMap<String, Object> sendAskMe(@RequestParam HashMap<String, Object> param, HttpServletRequest request) throws Exception {
        //사용이력 저장
        String clientIp = request.getHeader(HEADER_X_FORWARDED_FOR);
        if(null == clientIp || clientIp.length() == 0 || clientIp.toLowerCase().equals("unknown")){
            clientIp = request.getRemoteAddr();
        }
        param.put("ipAddr", clientIp);
        return askmeService.sendAskMe(param);
    }

}
