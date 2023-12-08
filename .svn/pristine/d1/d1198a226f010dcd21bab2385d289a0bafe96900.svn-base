package cronies.app.controller;

import cronies.app.service.UserListService;
import javax.tools.JavaFileManager.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "userList")
public class UserListController {

	@Autowired
	private UserListService userListService;

	@RequestMapping(value = "getUserList", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<HashMap<String, Object>> getUserList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return userListService.getUserList(param);
	}

	@RequestMapping(value = "checkMsgChannel", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Object> checkMsgChannel(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return userListService.checkMsgChannel(param);
	}


}