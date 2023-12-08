package cronies.meeting.user.controller;

import cronies.meeting.user.service.AlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class AlarmController {

    @Autowired
    private AlarmService alarmService;


    @RequestMapping(value = "getAlarmList", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public List<HashMap<String, Object>> getAlarmList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
        return alarmService.getAlarmList(param);
    }

    @RequestMapping(value = "removeAlarm", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public HashMap<String, Object> removeAlarm(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
        return alarmService.removeAlarm(param);
    }

    @RequestMapping(value = "getAlarmCount", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public HashMap<String, Object> getAlarmCount(@RequestParam HashMap<String, Object> param) throws Exception {
        return alarmService.getAlarmCount(param);
    }

    @RequestMapping(value = "setAlarmConfirm", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public HashMap<String, Object> setAlarmConfirm(@RequestParam HashMap<String, Object> param) throws Exception {
        return alarmService.setAlarmConfirm(param);
    }

    @RequestMapping(value = "getAlarmOpenChatDel", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public HashMap<String, Object> getAlarmOpenChatDel(@RequestParam HashMap<String, Object> param) throws Exception {
        return alarmService.getAlarmOpenChatDel(param);
    }

    @RequestMapping(value = "getAlarmOpenChatMeetDelCheck", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public HashMap<String, Object> getAlarmOpenChatMeetDelCheck(@RequestParam HashMap<String, Object> param) throws Exception {
        return alarmService.getAlarmOpenChatMeetDelCheck(param);
    }

    @RequestMapping(value = "getAlarmCommunityDel", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public HashMap<String, Object> getAlarmCommunityDel(@RequestParam HashMap<String, Object> param) throws Exception {
        return alarmService.getAlarmCommunityDel(param);
    }

}
