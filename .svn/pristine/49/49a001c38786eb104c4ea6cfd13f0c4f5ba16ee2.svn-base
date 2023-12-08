package cronies.meeting.user.controller;

import cronies.meeting.user.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import select.spring.exquery.service.ExqueryService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class BannerController {

    @Autowired
    private ExqueryService exqueryService;

    @RequestMapping(value = "getBannerList", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public List<HashMap<String, Object>> getBannerList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
        List<HashMap<String, Object>> resultList = exqueryService.selectList("cronies.app.banner.getBannerList", param);
        return resultList;
    }

}
