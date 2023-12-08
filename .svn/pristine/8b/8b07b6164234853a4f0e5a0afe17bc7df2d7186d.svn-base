package cronies.app.controller;

import cronies.app.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

	@RestController
	@RequestMapping(value = "login")
	public class UserLoginController {

		@Autowired
		private LoginService loginService;

		@RequestMapping(value = "userLogin", method = { RequestMethod.GET, RequestMethod.POST })
		@ResponseBody
		public List<HashMap<String, Object>> userLogin(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
			return loginService.userLogin(param);
		}


	}
