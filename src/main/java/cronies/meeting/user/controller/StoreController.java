package cronies.meeting.user.controller;

import cronies.meeting.user.service.CommonService;
import cronies.meeting.user.service.PointService;
import cronies.meeting.user.service.StoreCommonService;
import cronies.meeting.user.service.StoreProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import select.spring.exquery.service.ExqueryService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class StoreController {

    @Autowired
    private StoreCommonService storeCommonService;
    @Autowired
    private StoreProcessService storeProcessService;
    @Autowired
    private CommonService commonService;

    @Autowired
    private PointService pointService;

    @Autowired
    private ExqueryService exqueryService;

    @RequestMapping(value = "getStoreCategoryList", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public List<HashMap<String, Object>> getStoreCategoryList(@RequestParam HashMap<String, Object> param) throws Exception {
        return storeCommonService.getStoreCategoryList(param);
    }

    @RequestMapping(value = "getStoreItemList", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public List<HashMap<String, Object>> getStoreItemList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
        return storeCommonService.getStoreItemList(param);
    }

    @RequestMapping(value = "getStoreInitList", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public List<HashMap<String, Object>> getStoreInitList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
        return storeCommonService.getStoreInitList(param);
    }

    @RequestMapping(value = "getUserCurrentCouponList", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public List<HashMap<String, Object>> getUserCurrentCouponList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
        List<HashMap<String, Object>> resultList = new ArrayList<>();
        param.put("userId", param.get("ssUserId"));
        resultList = exqueryService.selectList("cronies.app.storeCommon.getUserCurrentCouponList", param);
        return resultList;
    }

    @RequestMapping(value = "getUserCouponDataOne", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public HashMap<String, Object> getUserCouponDataOne(@RequestParam HashMap<String, Object> param) throws Exception {
        return storeCommonService.getUserCouponDataOne(param);
    }

    @RequestMapping(value = "getProfileAdList", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public List<HashMap<String, Object>> getProfileAdList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
        return storeCommonService.getProfileAdList(param);
    }

    @RequestMapping(value = "getUserSwiperInfo", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public HashMap<String, Object> getUserSwiperInfo(@RequestParam HashMap<String, Object> param) throws Exception {
        return storeCommonService.getUserSwiperInfo(param);
    }

    @RequestMapping(value = "getUserSubscribeInfo", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public HashMap<String, Object> getUserSubscribeInfo(@RequestParam HashMap<String, Object> param) throws Exception {
        return storeCommonService.getUserSubscribeInfo(param);
    }
    @RequestMapping(value = "getTargetUserSubscribeInfo", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public HashMap<String, Object> getTargetUserSubscribeInfo(@RequestParam HashMap<String, Object> param) throws Exception {
        return storeCommonService.getTargetUserSubscribeInfo(param);
    }

    @RequestMapping(value = "getSubscribeList", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public List<HashMap<String, Object>> getSubscribeList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
        return storeCommonService.getSubscribeList(param);
    }

    @RequestMapping(value = "buySubscribeCheck", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public HashMap<String, Object> buySubscribeCheck(@RequestParam HashMap<String, Object> param) throws Exception {
        return storeCommonService.buySubscribeCheck(param);
    }


    /****
     * 버프 사용여부 조회
     ****/
    @RequestMapping(value = "getBuffUsingInfo", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public HashMap<String, Object> getBuffUsingInfo(@RequestParam HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        param.put("userId", param.get("ssUserId"));
        if(param.get("couponCd").toString().equals("CU002")){
            resultMap = exqueryService.selectOne("cronies.app.storeCommon.getUsingHoneyBoost", param);
        } else if (param.get("couponCd").toString().equals("CU003")){
            resultMap = exqueryService.selectOne("cronies.app.storeCommon.getUsingReceivedLike", param);
        } else if (param.get("couponCd").toString().equals("CU008")){
            param.put("openchatId", commonService.getDecoding(String.valueOf(param.get("openChatKey"))));
            resultMap = exqueryService.selectOne("cronies.app.storeCommon.getUsingOpenChatBoost", param);
        }
        return resultMap;
    }


    /****
     *
     * StoreProcess
     *
     * **/

    @RequestMapping(value = "setBuyHeart", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public HashMap<String, Object> setBuyHeart(@RequestParam HashMap<String, Object> param) throws Exception {
        return pointService.setPointByBuyHeart(param);
    }

    @RequestMapping(value = "setBuyItem", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public HashMap<String, Object> setBuyItem(@RequestParam HashMap<String, Object> param) throws Exception {
        return storeProcessService.setBuyItem(param);
    }

    @RequestMapping(value = "setBuyPackage", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public HashMap<String, Object> setBuyPackage(@RequestParam HashMap<String, Object> param) throws Exception {
        return storeProcessService.setBuyPackage(param);
    }

    @RequestMapping(value = "useHeartAttack", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public HashMap<String, Object> useHeartAttack(@RequestParam HashMap<String, Object> param) throws Exception {
        return storeProcessService.useHeartAttack(param);
    }

    @RequestMapping(value = "useReceivedLike", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public HashMap<String, Object> useReceivedLike(@RequestParam HashMap<String, Object> param) throws Exception {
        return storeProcessService.useReceivedLike(param);
    }

    @RequestMapping(value = "useHoneyBoost", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public HashMap<String, Object> useHoneyBoost(@RequestParam HashMap<String, Object> param) throws Exception {
        return storeProcessService.useHoneyBoost(param);
    }
    @RequestMapping(value = "checkBoost", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public HashMap<String, Object> checkBoost(@RequestParam HashMap<String, Object> param) throws Exception {
        return storeProcessService.checkBoost(param);
    }

    @RequestMapping(value = "useProfileAd", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public HashMap<String, Object> useProfileAd(@RequestParam HashMap<String, Object> param) throws Exception {
        return storeProcessService.useProfileAd(param);
    }

    @RequestMapping(value = "useSwiperPlus", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public HashMap<String, Object> useSwiperPlus(@RequestParam HashMap<String, Object> param) throws Exception {
        return storeProcessService.useSwiperPlus(param);
    }

    @RequestMapping(value = "useOpenChatBoost", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public HashMap<String, Object> useOpenChatBoost(@RequestParam HashMap<String, Object> param) throws Exception {
        return storeProcessService.useOpenChatBoost(param);
    }
    @RequestMapping(value = "useContestVote", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public HashMap<String, Object> useContestVote(@RequestParam HashMap<String, Object> param) throws Exception {
        return storeProcessService.useContestVote(param);
    }

    @RequestMapping(value = "buySubscribeComplete", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public HashMap<String, Object> buySubscribeComplete(@RequestParam HashMap<String, Object> param) throws Exception {
        return storeProcessService.buySubscribeComplete(param);
    }

    @RequestMapping(value = "cancelSubscribe", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public HashMap<String, Object> cancelSubscribe(@RequestParam HashMap<String, Object> param) throws Exception {
        return storeProcessService.cancelSubscribe(param);
    }

}
