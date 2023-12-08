package cronies.meeting.user.controller;

import cronies.meeting.user.service.InvitePointService;
import cronies.meeting.user.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import select.spring.exquery.service.ExqueryService;

import java.util.HashMap;

@RestController
public class InvitePointController {

    @Autowired
    private InvitePointService invitePointService;

    @Autowired
    private ExqueryService exqueryService;

    @Autowired
    private CommonService commonService;

    @RequestMapping(value = "getUserAskMeA", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public HashMap<String, Object> getUserAskMeA(@RequestParam HashMap<String, Object> param) throws Exception {
        return invitePointService.getUserAskMeA(param);
    }

}
