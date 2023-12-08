package cronies.app.controller;

import cronies.app.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

    @RestController
    @RequestMapping(value = "message")
    public class MessageController {

        @Autowired
        private MessageService messageService;

        @RequestMapping(value = "getMessageList", method = { RequestMethod.GET, RequestMethod.POST })
        @ResponseBody
        public List<HashMap<String, Object>> getMessageList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
            return messageService.getMessageList(param);
        }

        @RequestMapping(value = "getChatList", method = { RequestMethod.GET, RequestMethod.POST })
        @ResponseBody
        public List<HashMap<String, Object>> getChatList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
            return messageService.getChatList(param);
        }

        @RequestMapping(value = "sendMsg", method = { RequestMethod.GET, RequestMethod.POST })
        @ResponseBody
        public HashMap<String, Object> sendMsg(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
            return messageService.sendMsg(param);
        }

        @RequestMapping(value = "getChannelId", method = { RequestMethod.GET, RequestMethod.POST })
        @ResponseBody
        public HashMap<String, Object> getChannelId(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
            return messageService.getChannelId(param);
        }

        @RequestMapping(value = "openChannel", method = { RequestMethod.GET, RequestMethod.POST })
        @ResponseBody
        public HashMap<String, Object> openChannel(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
            return messageService.openChannel(param);
        }

    }
