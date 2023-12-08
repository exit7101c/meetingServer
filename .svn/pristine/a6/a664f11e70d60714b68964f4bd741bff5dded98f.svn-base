package cronies.meeting.user.controller;

//import cronies.meeting.user.config.FTPConnect;
import cronies.meeting.user.service.CommonService;
import cronies.meeting.user.service.UserService;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import select.spring.exquery.service.ExqueryService;


import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@RestController
public class CommonController {

    @Autowired
    private ExqueryService exqueryService;
    @Autowired
    private CommonService commonService;

    /*
    개발테스트 완료후 삭제 반듯이 할것!!
     */

    @RequestMapping(value = "reject", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public HashMap<String, Object> reject(@RequestParam HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> res = new HashMap<String, Object>();
        res.put("reject", "reject");
        return res;
    }

    @RequestMapping(value = "getEncoding", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public HashMap<String, Object> getEncoding(@RequestParam HashMap<String, Object> param) throws Exception {
        String testStr = param.get("testString").toString();

        HashMap<String, Object> res = new HashMap<String, Object>();
        res.put("result", commonService.getEncoding(testStr));
        return res;
    }

    @RequestMapping(value = "getDecoding", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public HashMap<String, Object> getDecoding(@RequestParam HashMap<String, Object> param) throws Exception {
        String testStr = param.get("testString").toString();

        HashMap<String, Object> res = new HashMap<String, Object>();
        res.put("result", commonService.getDecoding(testStr));

        return res;
    }

    @RequestMapping(value = "Geocode", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public HashMap<String, Object> Geocode(@RequestParam String param) throws Exception {

        return commonService.Geocode(param);
    }

//    @RequestMapping(value = "ftptest", method = { RequestMethod.GET, RequestMethod.POST })
//    @ResponseBody
//    public HashMap<String, Object> ftptest(@RequestParam String param) throws Exception {
//
//        FTPConnect ftpc = new FTPConnect();
//
//        ftpc.start();
//
//        return commonService.Geocode(param);
//    }

    /**
     * ==============================================================================================
     */


    int inDbCnt = 0;
    @RequestMapping(value = "inDbCnt", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public HashMap<String, Object> inDbCnt(@RequestParam HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> res = new HashMap<String, Object>();
        res.put("haha", "hoho");

        inDbCnt += 1;
        List<HashMap<String, Object>> resultList = exqueryService.selectList("cronies.app.choice.userInfoTest", param);
       // List<HashMap<String, Object>> resultList = getSqlSession().selectList(id, param)

        //SqlSessionFactory

        res.put("list", resultList);

        return res;
    }
    @RequestMapping(value = "inDbCntOut", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public HashMap<String, Object> inDbCntOut(@RequestParam HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> res = new HashMap<String, Object>();

        res.put("inDbCntOut", inDbCnt);

        return res;
    }
    int noDbCnt = 0;
    @RequestMapping(value = "noDbCnt", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public HashMap<String, Object> noDbCnt(@RequestParam HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> res = new HashMap<String, Object>();
        res.put("haha", "hoho");
        noDbCnt += 1;
        return res;
    }
    @RequestMapping(value = "noDbCntOut", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public HashMap<String, Object> noDbCntOut(@RequestParam HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> res = new HashMap<String, Object>();
        res.put("noDbCntOut", noDbCnt);
        return res;
    }
}
