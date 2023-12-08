package cronies.meeting.user.controller;

import cronies.meeting.user.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import select.spring.exquery.service.ExqueryService;

import java.util.HashMap;

@RestController
public class PointController {

    @Autowired
    private PointService pointService;

    @Autowired
    private ExqueryService exqueryService;

    @RequestMapping(value = "getUserLastPoint", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public HashMap<String, Object> getUserLastPoint(@RequestParam HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        param.put("userId", param.get("ssUserId"));
        resultMap = exqueryService.selectOne("cronies.app.point.getUserLastPoint", param);
        return resultMap;
    }
    
}
