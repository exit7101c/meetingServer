package cronies.app.controller;

import cronies.app.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "beforSetting")
public class BeforeSettingController {

	@Autowired
	private SettingService settingService;

	@RequestMapping(value = "getUserOne", method = { RequestMethod.GET, RequestMethod.POST })
	public List<HashMap<String, Object>> getUserOne(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return settingService.getUserOne(param);
	}

	@RequestMapping(value = "updateUser", method = { RequestMethod.GET, RequestMethod.POST })
	public HashMap<String, Object> updateUser(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return settingService.updateUser(param);
	}

	@RequestMapping(value = "updatePw", method = { RequestMethod.GET, RequestMethod.POST })
	public HashMap<String, Object> updatePw(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return settingService.updatePw(param);
	}

	@RequestMapping(value = "uploadFile", method = { RequestMethod.GET, RequestMethod.POST })
	public HashMap<String, Object> uploadFile(@RequestParam(value="file", required=false) MultipartFile file, @RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return settingService.uploadFile(param, file);
	}

	@RequestMapping(value = "changeFile", method = { RequestMethod.GET, RequestMethod.POST })
	public HashMap<String, Object> changeFile(@RequestParam(value="file", required=false) MultipartFile file, @RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return settingService.changeFile(param, file);
	}

	@RequestMapping(value = "deleteProfilePhoto", method = { RequestMethod.GET, RequestMethod.POST })
	public HashMap<String, Object> deleteProfilePhoto(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return settingService.deleteProfilePhoto(param);
	}

	@RequestMapping(value = "deleteUserPhoto", method = { RequestMethod.GET, RequestMethod.POST })
	public HashMap<String, Object> deleteUserPhoto(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return settingService.deleteUserPhoto(param);
	}

	@RequestMapping(value = "resetMainPhoto", method = { RequestMethod.GET, RequestMethod.POST })
	public HashMap<String, Object> resetMainPhoto(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return settingService.resetMainPhoto(param);
	}

	@RequestMapping(value = "updateMainPhoto", method = { RequestMethod.GET, RequestMethod.POST })
	public HashMap<String, Object> updateMainPhoto(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return settingService.updateMainPhoto(param);
	}

	@RequestMapping(value = "updateUserLocation", method = { RequestMethod.GET, RequestMethod.POST })
	public HashMap<String, Object> updateUserLocation(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return settingService.updateUserLocation(param);
	}

}