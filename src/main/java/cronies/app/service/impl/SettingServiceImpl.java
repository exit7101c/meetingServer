package cronies.app.service.impl;

import com.nse.pms.standard.common.service.CommonFileService;
import cronies.app.service.SettingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import select.spring.exquery.dao.ExqueryDao;
import select.spring.exquery.service.ExqueryService;
import select.spring.util.FileUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service("settingService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SettingServiceImpl implements SettingService {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CommonFileService commonFileService;

	@Autowired
	private ExqueryService exqueryService;

	@Override
	public List<HashMap<String, Object>> getUserOne(HashMap<String, Object> param) throws Exception {
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		resultList = exqueryService.selectList("cronies.app.setting.getUserOne", param);
		return resultList;
	}

	@Override
	public HashMap<String, Object> updateUser(HashMap<String, Object> param) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = exqueryService.selectOne("cronies.app.setting.updateUser", param);
		return resultMap;
	}

	@Override
	public HashMap<String, Object> updatePw(HashMap<String, Object> param) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = exqueryService.selectOne("cronies.app.setting.updatePw", param);
		return resultMap;
	}


	@Override
	public HashMap<String, Object> uploadFile(HashMap<String, Object> param, MultipartFile file) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		//param 안의 data에서 값 추출하기
		String data = (String) param.get("data");
		String[] array = data.split(",");

		String userId = array[0].replaceAll("[^0-9]", "");
		String privateYn = array[1].substring(array[1].length() - 3, array[1].length() - 2);


		//원본과 썸네일을 저장한다.
		param.put("fileState", "newModified");
		param.put("userId", userId);
		int attachFileId = commonFileService.insertWithThumbnailFile("file", param, file);

		//사용자 ID와 매칭
		param.put("privateYn", privateYn);
		param.put("attachFileId", attachFileId);
		exqueryService.insert("cronies.app.setting.insertUserPhoto", param);

		return resultMap;
	}

	@Override
	public HashMap<String, Object> changeFile(HashMap<String, Object> param, MultipartFile file) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		//param 안의 data에서 값 추출하기
		String data = (String) param.get("data");
		String[] array = data.split(",");

		String userSeq = array[0].replaceAll("[^0-9]", "");
		String fileId = array[1].replaceAll("[^0-9]", ""); // 기존의 fileId, 교체될 자리
		
		//원본과 썸네일을 저장한다.
		param.put("fileState", "newModified");
		param.put("userSeq", userSeq);
		int newFileId = commonFileService.insertWithThumbnailFile("file", param, file); // 새로 저장된 fileId
		
		param.put("fileId", fileId);
		param.put("newFileId", newFileId);

		// 삭제할 파일정보를 불러온다 (파일이름, 경로)
		HashMap<String, Object> pMap = new HashMap<String, Object>();
		pMap.put("attachFileId", fileId);
		HashMap<String, Object> fileInfo = exqueryService.selectOne("cronies.app.setting.getFileOne", pMap);
		
		// 불러온 파일정보를 이용해 local에 있는 원본과 썸네일 삭제
		String serverFileNm = (String)fileInfo.get("serverFileNm");
		String filePath = (String)fileInfo.get("filePath");
		String thumbnail = (String)fileInfo.get("thumbnail");

		FileUtils.remove(serverFileNm, filePath); //원본
		FileUtils.remove(thumbnail, filePath); //썸네일

		//COM_ATTACH_FILE 테이블의 기존 사진 컬럼 삭제
		exqueryService.delete("cronies.app.setting.deleteProfilePhoto", pMap);

		//새롭게 insert한 fileId를 갈아끼울 기존의 id로 update
		exqueryService.update("cronies.app.setting.updateAttachPhoto", param);

		return resultMap;
	}

	@Override
	public HashMap<String, Object> deleteProfilePhoto(HashMap<String, Object> param) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		// 삭제할 파일정보를 불러온다 (파일이름, 경로)
		HashMap<String, Object> pMap = new HashMap<String, Object>();
		String attachFileId =(String) param.get("attachFileId");
		pMap.put("attachFileId", attachFileId);
		HashMap<String, Object> fileInfo = exqueryService.selectOne("cronies.app.setting.getFileOne", pMap);

		// 불러온 파일정보를 이용해 local에 있는 원본과 썸네일 삭제
		String serverFileNm = (String)fileInfo.get("serverFileNm");
		String filePath = (String)fileInfo.get("filePath");
		String thumbnail = (String)fileInfo.get("thumbnail");

		FileUtils.remove(serverFileNm, filePath); //원본
		FileUtils.remove(thumbnail, filePath); //썸네일


		exqueryService.delete("cronies.app.setting.deleteProfilePhoto", param);
		return resultMap;
	}

	@Override
	public HashMap<String, Object> deleteUserPhoto(HashMap<String, Object> param) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		exqueryService.delete("cronies.app.setting.deleteUserPhoto", param);
		return resultMap;
	}

	@Override
	public HashMap<String, Object> resetMainPhoto(HashMap<String, Object> param) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = exqueryService.selectOne("cronies.app.setting.resetMainPhoto", param);
		return resultMap;
	}

	@Override
	public HashMap<String, Object> updateMainPhoto(HashMap<String, Object> param) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = exqueryService.selectOne("cronies.app.setting.updateMainPhoto", param);
		return resultMap;
	}

	@Override
	public HashMap<String, Object> updateUserLocation(HashMap<String, Object> param) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		int result = 0;
		result = exqueryService.update("cronies.app.setting.updateUserLocation", param);
		resultMap.put("result", result);
		return resultMap;
	}



}