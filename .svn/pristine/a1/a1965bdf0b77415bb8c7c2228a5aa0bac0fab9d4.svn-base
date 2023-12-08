package cronies.meeting.user.controller;

import cronies.meeting.user.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class InfoController {

    @Autowired
    private InfoService infoService;

    @RequestMapping(value = "getTerm", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public HashMap<String, Object> getTerm(@RequestParam HashMap<String, Object> param) throws Exception {

        return infoService.getTerm(param);
    }

}
