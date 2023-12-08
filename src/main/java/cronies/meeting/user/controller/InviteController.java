package cronies.meeting.user.controller;

import cronies.meeting.user.service.InviteService;
import cronies.meeting.user.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import select.spring.exquery.service.ExqueryService;

import java.util.HashMap;

@RestController
public class InviteController {

    @Autowired
    private InviteService inviteService;

    @Autowired
    private ExqueryService exqueryService;

    @Autowired
    private CommonService commonService;

    @RequestMapping(value = "getUserInviteCode", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public HashMap<String, Object> getUserInviteCode(@RequestParam HashMap<String, Object> param) throws Exception {
        return inviteService.getUserInviteCode(param);
    }

    @RequestMapping(value = "saveTargetInviteCode", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public HashMap<String, Object> saveTargetInviteCode(@RequestParam HashMap<String, Object> param) throws Exception {
        return inviteService.saveTargetInviteCode(param);
    }


}
