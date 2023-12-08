package cronies.meeting.user.controller;

import cronies.meeting.user.service.MessageMainService;
import cronies.meeting.user.service.PushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "push")
public class PushController {

  @Autowired
  private PushService pushService;

  @RequestMapping(value = "sendPushMessage", method = { RequestMethod.GET, RequestMethod.POST })
  @ResponseBody
  public HashMap<String, Object> sendPushMessage(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
    return pushService.sendPushMessage(param);
  }


}
