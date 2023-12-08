package cronies.meeting.user.controller;

import cronies.meeting.user.service.FcmService;
import cronies.meeting.user.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import select.spring.exquery.service.ExqueryService;

import java.util.HashMap;

@RestController
public class FcmController {

    @Autowired
    private FcmService fcmService;

    @RequestMapping(value = "sendMessageAdministrator", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public HashMap<String, Object> sendMessageAdministrator(@RequestParam HashMap<String, Object> param) throws Exception {
        String targetToken = param.get("targetToken").toString();
        String title = param.get("title").toString();
        String body = param.get("body").toString();
        fcmService.sendMessageTo(targetToken, title, body);

        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("result", "Y");
        return resultMap;
    }
    
}
