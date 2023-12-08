package cronies.meeting.user.controller;

import cronies.meeting.user.service.UsePointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import select.spring.exquery.service.ExqueryService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class UsePointController {

    @Autowired
    private UsePointService usePointService;

    @Autowired
    private ExqueryService exqueryService;

    @RequestMapping(value = "asdf", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public List<HashMap<String, Object>> asdf(@RequestParam HashMap<String, Object> param) throws Exception {
//        List<HashMap<String, Object>> a = new ArrayList<>();
        return new ArrayList<>();
    }

    @RequestMapping(value = "getUserQrcode", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public HashMap<String, Object> getUserQrcode(@RequestParam HashMap<String, Object> param) throws Exception {
        return usePointService.getUserQrcode(param);
    }

    @RequestMapping(value = "setUserQrcode", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public HashMap<String, Object> setUserQrcode(@RequestParam HashMap<String, Object> param) throws Exception {
        return usePointService.setUserQrcode(param);
    }

}
