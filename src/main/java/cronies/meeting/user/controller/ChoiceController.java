package cronies.meeting.user.controller;

import cronies.meeting.user.service.ChoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
    public class ChoiceController {

        @Autowired
        private ChoiceService choiceService;

        @RequestMapping(value = "getChoice", method = { RequestMethod.GET, RequestMethod.POST })
        @ResponseBody
        public List<HashMap<String, Object>> getChoice(@RequestParam HashMap<String, Object> param) throws Exception {
            return choiceService.getChoice(param);
        }

        @RequestMapping(value = "getChiceImsiTap", method = { RequestMethod.GET, RequestMethod.POST })
        @ResponseBody
        public HashMap<String, Object> getChiceImsiTap(@RequestParam HashMap<String, Object> param) throws Exception {
            return choiceService.getChiceImsiTap(param);
        }

        @RequestMapping(value = "getChiceImsiTapLocation", method = { RequestMethod.GET, RequestMethod.POST })
        @ResponseBody
        public HashMap<String, Object> getChiceImsiTapLocation(@RequestParam HashMap<String, Object> param) throws Exception {
            return choiceService.getChiceImsiTapLocation(param);
        }

        @RequestMapping(value = "getChoiceOne", method = { RequestMethod.GET, RequestMethod.POST })
        @ResponseBody
        public List<HashMap<String, Object>> getChoiceOne(@RequestParam HashMap<String, Object> param) throws Exception {
            return choiceService.getChoiceOne(param);
        }

        @RequestMapping(value = "getChoiceLeftRightSwiper", method = { RequestMethod.GET, RequestMethod.POST })
        @ResponseBody
        public HashMap<String, Object> getChoiceLeftRightSwiper(@RequestParam HashMap<String, Object> param) throws Exception {
            return choiceService.getChoiceLeftRightSwiper(param);
        }

        @RequestMapping(value = "setLike", method = { RequestMethod.GET, RequestMethod.POST })
        @ResponseBody
        public HashMap<String, Object> setLike(@RequestParam HashMap<String, Object> param) throws Exception {
            return choiceService.setLike(param);
        }

        @RequestMapping(value = "setNope", method = { RequestMethod.GET, RequestMethod.POST })
        @ResponseBody
        public HashMap<String, Object> setNope(@RequestParam HashMap<String, Object> param) throws Exception {
            return choiceService.setNope(param);
        }

        @RequestMapping(value = "setSkip", method = { RequestMethod.GET, RequestMethod.POST })
        @ResponseBody
        public HashMap<String, Object> setSkip(@RequestParam HashMap<String, Object> param) throws Exception {
            return choiceService.setSkip(param);
        }

        @RequestMapping(value = "setPartnerSkip", method = { RequestMethod.GET, RequestMethod.POST })
        @ResponseBody
        public HashMap<String, Object> setPartnerSkip(@RequestParam HashMap<String, Object> param) throws Exception {
            return choiceService.setPartnerSkip(param);
        }

        @RequestMapping(value = "getSendLike", method = { RequestMethod.GET, RequestMethod.POST })
        @ResponseBody
        public List<HashMap<String, Object>> getSendLike(@RequestParam HashMap<String, Object> param) throws Exception {
            return choiceService.getSendLike(param);
        }

        @RequestMapping(value = "getReceiveLike", method = { RequestMethod.GET, RequestMethod.POST })
        @ResponseBody
        public List<HashMap<String, Object>> getReceiveLike(@RequestParam HashMap<String, Object> param) throws Exception {
            return choiceService.getReceiveLike(param);
        }

        @RequestMapping(value = "setAccept", method = { RequestMethod.GET, RequestMethod.POST })
        @ResponseBody
        public HashMap<String, Object> setAccept(@RequestParam HashMap<String, Object> param) throws Exception {
            return choiceService.setAccept(param);
        }

        @RequestMapping(value = "getChoiceCategoryList", method = { RequestMethod.GET, RequestMethod.POST })
        @ResponseBody
        public List<HashMap<String, Object>> getCategoryList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
            return choiceService.getChoiceCategoryList(param);
        }

        @RequestMapping(value = "revertBeforeActionCheck", method = { RequestMethod.GET, RequestMethod.POST })
        @ResponseBody
        public HashMap<String, Object> revertBeforeActionCheck(@RequestParam HashMap<String, Object> param) throws Exception {
            return choiceService.revertBeforeActionCheck(param);
        }

        @RequestMapping(value = "revertBeforeAction", method = { RequestMethod.GET, RequestMethod.POST })
        @ResponseBody
        public HashMap<String, Object> revertBeforeAction(@RequestParam HashMap<String, Object> param) throws Exception {
            return choiceService.revertBeforeAction(param);
        }

        @RequestMapping(value = "getUserReceiveLikeCnt", method = { RequestMethod.GET, RequestMethod.POST })
        @ResponseBody
        public HashMap<String, Object> getUserReceiveLikeCnt(@RequestParam HashMap<String, Object> param) throws Exception {
            return choiceService.getUserReceiveLikeCnt(param);
        }

        @RequestMapping(value = "resetChoiceLogic", method = { RequestMethod.GET, RequestMethod.POST })
        @ResponseBody
        public HashMap<String, Object> resetChoiceLogic(@RequestParam HashMap<String, Object> param) throws Exception {
            return choiceService.resetChoiceLogic(param);
        }

        @RequestMapping(value = "getChoiceFileter", method = { RequestMethod.GET, RequestMethod.POST })
        @ResponseBody
        public HashMap<String, Object> getChoiceFileter(@RequestParam HashMap<String, Object> param) throws Exception {
            return choiceService.getChoiceFileter(param);
        }

        @RequestMapping(value = "filterSave", method = { RequestMethod.GET, RequestMethod.POST })
        @ResponseBody
        public HashMap<String, Object> filterSave(@RequestParam HashMap<String, Object> param) throws Exception {
            return choiceService.filterSave(param);
        }

        @RequestMapping(value = "getFreeFlipIsPossible", method = { RequestMethod.GET, RequestMethod.POST })
        @ResponseBody
        public HashMap<String, Object> getFreeFlipIsPossible(@RequestParam HashMap<String, Object> param) throws Exception {
            return choiceService.getFreeFlipIsPossible(param);
        }

        @RequestMapping(value = "setFreeFlip", method = { RequestMethod.GET, RequestMethod.POST })
        @ResponseBody
        public HashMap<String, Object> setFreeFlip(@RequestParam HashMap<String, Object> param) throws Exception {
            return choiceService.setFreeFlip(param);
        }

    }
