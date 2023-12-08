package com.nse.pms.standard.common.service.impl;

import com.nse.pms.standard.common.service.CommonFileService;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mortennobel.imagescaling.*;

import select.spring.exquery.dao.ExqueryDao;
import select.spring.util.ApplicationProperty;
import select.spring.util.FileUtils;

@Service("commonFileService")
public class CommonFileServiceImpl implements CommonFileService {


	@Inject
	@Named("exqueryDao")
	private ExqueryDao exqueryDao;

	@Named("commonFileService")
	private CommonFileService commonFileService;

	private String uploadPath = ApplicationProperty.get("upload.path");

	private static final String  NEW_INIT 		= "newInit";
	private static final String  NEW_MODIFIED	= "newModified";
	private static final String  NOT_MODIFIED	= "notModified";
	private static final String  DATA_MODIFIED	= "dataModified";
	private static final String  DATA_DELETED	= "dataDeleted";


	/*
	 * 에디터 파일 생성.
	 */
	@SuppressWarnings("unchecked")
	public int insertWithFile(String key, HashMap<String, Object> param, MultipartFile file) {

		HashMap<String, Object> pMap = new HashMap<String, Object>();

		// 파일정보 설정 & 실제 저장될 파일명
		String saveFileName = "";
		int saveId = 0;

		String fileState = (String)param.get(key + "State");
		// 신규입력 상태에서는 파일상태가 아래의 상태들만 가능함
		if (fileState.equals(NEW_INIT)) {
			// 처리할 내용 없음
			saveId = 0;

		} else if (fileState.equals(NEW_MODIFIED)) {
			// new 신규입력후 id 구함
			// 파일 저장
			try {

				SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
				SimpleDateFormat dataFormatter = new SimpleDateFormat("yyyyMM");
				String today = formatter.format(new java.util.Date());
				String filePath = uploadPath + dataFormatter.format(new java.util.Date()) + "/";
				String realFileNm =  today + "_" + UUID.randomUUID().toString()  + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

				File folderPath = new File(filePath);
				if (!folderPath.exists()) {
					folderPath.mkdirs();
				}

				FileUtils.saveUniqeName(realFileNm, filePath, file.getBytes());


				//썸네일 이미지 저장(원본 이미지 읽어들임)
				//BufferedImage buffImage = ImageIO.read(new File( filePath + realFileNm ));
				//이미지 리사이즈 처리(이미지의 높이를 읽어들여 가로비율에 맞게 이미지 세로 비율을 조정.)

				//첨부하려는 이미지의 크기가 1250보다 작으면 사이즈를 조절하지 않고 1250보다 클때만 사이즈를 조정한다.
				BufferedImage bImageFromConvert = ImageIO.read(new File( filePath + realFileNm ));
				Integer imageWidth = bImageFromConvert.getWidth();
				Integer targetWidth = 1250; //tartget widhth
				Integer targetHeight = 0;
				if(imageWidth <= 1250){
					//크기를 조정하지 않음.
					targetWidth = imageWidth;
					targetHeight = bImageFromConvert.getHeight();
				} else {
					targetHeight = targetWidth * bImageFromConvert.getHeight() / bImageFromConvert.getWidth();
				}

				ResampleOp resampleOp = new ResampleOp(targetWidth, targetHeight);
				resampleOp.setUnsharpenMask( AdvancedResizeOp.UnsharpenMask.Soft );
				BufferedImage rescaledImage = resampleOp.filter(bImageFromConvert, null);

				String destPath = filePath +  realFileNm;
				File destFile = new File(destPath);

				ImageIO.write(rescaledImage, "jpg", destFile);

				pMap.put("fileType", "png");
				pMap.put("orgFileName", file.getOriginalFilename());
				pMap.put("saveFileName", realFileNm);
				pMap.put("thumbnail", "");
				pMap.put("savePath", filePath);
				pMap.put("fileSize", file.getSize());


				if ( param.get("userId") == null || param.get("userId").equals("")) {
					pMap.put("userId", "joinuser");
				} else {
					pMap.put("userId", param.get("userId"));
				}
				//pMap.put("regId", "jongho1329");
				//pMap.put("updId", "jongho1329");
				exqueryDao.insert("cronies.attachFile.insert", pMap);
//				pMap.put("articleId", param.get("articleId"));
//				exqueryDao.insert("nse.bpBoardAttach.insert", pMap);
				saveId = ((Integer)pMap.get("fileId")).intValue();

			} catch (Exception e) {
				// TODO:: 저장오류 발생시 처리 보완
			}
		}

		// 나머지 데이터 처리 로직

		return saveId;
	}

	/*
	 * 에디터 파일 생성.
	 */
	@SuppressWarnings("unchecked")
	public int insertFileInfo(HashMap<String, Object> param) {

		HashMap<String, Object> pMap = new HashMap<String, Object>();

		// 파일정보 설정 & 실제 저장될 파일명
		String saveFileName = "";
		int saveId = 0;

		//String fileState = (String)param.get(key + "State");
		// 신규입력 상태에서는 파일상태가 아래의 상태들만 가능함
		/*if (fileState.equals(NEW_INIT)) {
			// 처리할 내용 없음
			saveId = 0;

		} else if (fileState.equals(NEW_MODIFIED)) {*/
		// new 신규입력후 id 구함
		// 파일 저장
		try {

			pMap.put("fileType", param.get("fileType"));
			pMap.put("orgFileName", param.get("orgFileName"));
			pMap.put("saveFileName", param.get("saveFileName"));
			pMap.put("thumbnail", param.get("thumbnail"));
			pMap.put("savePath", param.get("savePath"));
			pMap.put("fileSize", param.get("fileSize"));

			if ( param.get("userId") == null || param.get("userId").equals("")) {
				pMap.put("userId", "joinuser");
			} else {
				pMap.put("userId", param.get("userId"));
			}
			//pMap.put("regId", "jongho1329");
			//pMap.put("updId", "jongho1329");
			exqueryDao.insert("cronies.attachFile.insert", pMap);
//				pMap.put("articleId", param.get("articleId"));
//				exqueryDao.insert("nse.bpBoardAttach.insert", pMap);
			saveId = ((Integer)pMap.get("fileId")).intValue();

		} catch (Exception e) {
			// TODO:: 저장오류 발생시 처리 보완
		}
		//}

		// 나머지 데이터 처리 로직

		return saveId;
	}

	/*
	 * 썸네일 파일 생성.
	 */
	@SuppressWarnings("unchecked")
	public int insertWithThumbnailFile(String key, HashMap<String, Object> param, MultipartFile file) {

		HashMap<String, Object> pMap = new HashMap<String, Object>();

		// 파일정보 설정 & 실제 저장될 파일명
		int saveId = 0;

		String fileState = (String)param.get(key + "State");
		// 신규입력 상태에서는 파일상태가 아래의 상태들만 가능함
		if (fileState.equals(NEW_INIT)) {
			// 처리할 내용 없음
			saveId = 0;

		} else if (fileState.equals(NEW_MODIFIED)) {
			// new 신규입력후 id 구함
			// 파일 저장
			try {
				//saveFileName = FileUtils.saveUniqeName(file.getOriginalFilename(), uploadPath, file.getBytes());
				SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
				SimpleDateFormat dataFormatter = new SimpleDateFormat("yyyyMM");
				String today = formatter.format(new java.util.Date());
				String filePath = uploadPath + dataFormatter.format(new java.util.Date()) + "/";
				String realFileNm = "thumb_" + today + "_" + UUID.randomUUID().toString()  + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

				File folderPath = new File(filePath);
				if (!folderPath.exists()) {
					folderPath.mkdirs();
				}

				FileUtils.saveUniqeName(realFileNm, filePath, file.getBytes());

				//썸네일 이미지 저장(원본 이미지 읽어들임)
				BufferedImage buffImage = ImageIO.read(new File( filePath + realFileNm ));

				ResampleOp resampleOp = new ResampleOp(195, 130);
				resampleOp.setUnsharpenMask( AdvancedResizeOp.UnsharpenMask.Soft );
				BufferedImage rescaledImage = resampleOp.filter(buffImage, null);

				String destPath = filePath +  realFileNm;
				File destFile = new File(destPath);

				ImageIO.write(rescaledImage, "png", destFile);

				pMap.put("fileType", "png");
				pMap.put("orgFileName", file.getOriginalFilename());
				pMap.put("saveFileName", realFileNm);
				pMap.put("thumbnail", "");
				pMap.put("savePath", filePath);
				pMap.put("fileSize", file.getSize());

				exqueryDao.insert("cronies.attachFile.insert", pMap);
//				pMap.put("articleId", param.get("articleId"));
//				exqueryDao.insert("nse.bpBoardAttach.insert", pMap);
				saveId = ((Integer)pMap.get("fileId")).intValue();
			} catch (IOException e) {
				e.printStackTrace();
				// TODO:: 저장오류 발생시 처리 보완
			}
		}

		// 나머지 데이터 처리 로직

		return saveId;
	}

	@SuppressWarnings("unchecked")
	public int updateWithFile(String key, HashMap<String, Object> param, MultipartFile file) {

		HashMap<String, Object> pMap = new HashMap<String, Object>();

		// 파일정보 설정 & 실제 저장될 파일명
		String saveFileName = "";
		int saveId = 0;

		String fileState = (String)param.get(key + "State");
		// 아래 IF 부분은 전체 상태를 고려한 것으로, 메서드별로 일부만 사용하면 됩니다.
		if (fileState.equals(NEW_INIT) || fileState.equals(NOT_MODIFIED) ) {
			saveId = Integer.parseInt(param.get(key + "OrgFileId").toString());

		} else if (fileState.equals(NEW_MODIFIED)) {
			// new 신규입력후 id 구함
			// 파일 저장
			try {
				saveFileName = FileUtils.saveUniqeName(file.getOriginalFilename(), uploadPath, file.getBytes());
			} catch (Exception e) {
				// TODO:: 저장오류 발생시 처리 보
			}
			pMap.put("fileType", saveFileName.substring( saveFileName.lastIndexOf( "." ) + 1 ));
			pMap.put("orgFileName", file.getOriginalFilename());
			pMap.put("saveFileName", saveFileName);
			pMap.put("savePath", uploadPath);
			pMap.put("fileSize", file.getSize());
			exqueryDao.insert("nse.ngmAttachFile.insert", pMap);
			saveId = ((Integer)pMap.get("fileId")).intValue();

		} else if (fileState.equals(DATA_MODIFIED)) {
			// org 파일의 정보를 읽어서 저장되어 있던 파일을 삭제
			saveId = Integer.parseInt(param.get(key + "OrgFileId").toString());
			pMap.put("fileId", saveId);
			HashMap<String, Object> orgFile = exqueryDao.selectOne("nse.ngmAttachFile.getFileInfo", pMap);
			String orgSavedFileName = (String)orgFile.get("saveFileName");
			String orgSavedPath = (String)orgFile.get("savePath");
			FileUtils.remove(orgSavedFileName, orgSavedPath);

			// org FileId의 정보를 테이블에서 삭제  <-- 삭제후 입력시는 이 부분이 필요함, 단 아래의 update가 insert로 되어야 한다.
			// exqueryDao.delete("com.daehanins.knserp.attachfile.delete", pMap);

			// 새 파일 저장
			try {
				saveFileName = FileUtils.saveUniqeName(file.getOriginalFilename(), uploadPath, file.getBytes());
			} catch (Exception e) {
				// TODO:: 저장오류 발생시 처리 보
			}
			pMap.put("fileType", saveFileName.substring( saveFileName.lastIndexOf( "." ) + 1 ));
			pMap.put("orgFileName", file.getOriginalFilename());
			pMap.put("saveFileName", saveFileName);
			pMap.put("savePath", uploadPath);
			pMap.put("fileSize", file.getSize());

			// 새 파일의 정보를 테이블에 업데이트
			exqueryDao.update("nse.ngmAttachFile.update", pMap);
			saveId = (Integer)pMap.get("fileId");

		} else if (fileState.equals(DATA_DELETED)) {
			// org 파일의 정보를 읽어서 저장되어 있던 파일을 삭제
			saveId = Integer.parseInt(param.get(key + "OrgFileId").toString());
			pMap.put("fileId", saveId);
			pMap.put("articleId", param.get("articleId"));
			HashMap<String, Object> orgFile = exqueryDao.selectOne("nse.ngmAttachFile.getFileInfo", pMap);
			String orgSavedFileName = (String)orgFile.get("saveFileName");
			String orgSavedPath = (String)orgFile.get("savePath");
			FileUtils.remove(orgSavedFileName, orgSavedPath);
			// org FileId의 정보를 테이블에서 삭제
			exqueryDao.delete("nse.ngmBoardAttach.delete", pMap);
			exqueryDao.delete("nse.ngmAttachFile.delete", pMap);
			saveId = 0;

		}

		// 나머지 데이터 처리 로직

		return saveId;
	}

	@SuppressWarnings("unchecked")
	public int deleteWithFile(String key, HashMap<String, Object> param) {

		HashMap<String, Object> pMap = new HashMap<String, Object>();

		int saveId = 0;
		saveId = Integer.parseInt(param.get(key + "OrgFileId").toString());

		// 파일이 포함된 원본이 삭제되므로 state와 무관하게 org 삭제처리함
		// org 파일의 정보를 읽어서 저장되어 있던 파일을 삭제

		if (saveId == 0)  return 0;

		pMap.put("fileId", saveId);
//		pMap.put("articleId", param.get("articleId"));
//		exqueryDao.delete("nse.bpBoardAttach.delete", pMap);

		HashMap<String, Object> orgFile = exqueryDao.selectOne("nse.ngmAttachFile.getFileInfo", pMap);
		String orgSavedFileName = (String)orgFile.get("saveFileName");
		String orgSavedPath = (String)orgFile.get("savePath");
		FileUtils.remove(orgSavedFileName, orgSavedPath);
		// org FileId의 정보를 테이블에서 삭제
		exqueryDao.delete("nse.ngmAttachFile.delete", pMap);

		return saveId;
	}

	@SuppressWarnings("unchecked")
	public int deleteWithFileId(String key, HashMap<String, Object> param) {

		HashMap<String, Object> pMap = new HashMap<String, Object>();

		int saveId = 0;
		saveId = Integer.parseInt(key);

		// 파일이 포함된 원본이 삭제되므로 state와 무관하게 org 삭제처리함
		// org 파일의 정보를 읽어서 저장되어 있던 파일을 삭제

		if (saveId == 0)  return 0;

		pMap.put("fileId", saveId);
		HashMap<String, Object> orgFile = exqueryDao.selectOne("nse.ngmAttachFile.getFileInfo", pMap);
		String orgSavedFileName = (String)orgFile.get("saveFileName");
		String orgSavedPath = (String)orgFile.get("savePath");
		FileUtils.remove(orgSavedFileName, orgSavedPath);
		// org FileId의 정보를 테이블에서 삭제
		exqueryDao.delete("nse.ngmAttachFile.delete", pMap);

		return saveId;
	}

	public HashMap<String, Object> readFile(String key, HashMap<String, Object> param) {

		HashMap<String, Object> pMap = new HashMap<String, Object>();

		int saveId = 0;
		saveId = Integer.parseInt(param.get(key + "OrgFileId").toString());

		pMap.put("fileId", saveId);
		HashMap<String, Object> orgFile = exqueryDao.selectOne("nse.ngmAttachFile.getFileInfo", pMap);

		HashMap<String, Object> rMap = new HashMap<String, Object>();

		rMap.put(key + "State", 		NOT_MODIFIED);
		rMap.put(key + "OrgFileId", 	((Long)orgFile.get("fileId")).intValue());
		rMap.put(key + "OrgFileName", 	(String)orgFile.get("orgFileName"));
		return rMap;

	}

	/**
	 * 파일생성
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> createFile(HashMap<String, Object> param, MultipartFile file) throws Exception {

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean isExist = false;

		//####[file]####
		int saveId = commonFileService.insertWithFile("file", param, file);

		//####[file]####
		HashMap<String, Object> pMap = new HashMap<String, Object>();
		pMap.put("fileId", saveId);
		//파일명 조회
		if(saveId > 0){
			resultMap = exqueryDao.selectOne("nse.ngmAttachFile.getFileInfo", pMap);
		}

		isExist = (saveId > 0);
		resultMap.put("isExist", isExist);
		return resultMap;

	}

	/*
	 * 썸네일 파일 생성.
	 */
	@SuppressWarnings("unchecked")
	public int insertWithFileOnFtp(String key, HashMap<String, Object> param, MultipartFile file) {

		HashMap<String, Object> pMap = new HashMap<String, Object>();

		// 파일정보 설정 & 실제 저장될 파일명
		int saveId = 0;

		String fileState = (String)param.get(key + "State");
		// 신규입력 상태에서는 파일상태가 아래의 상태들만 가능함
		if (fileState.equals(NEW_INIT)) {
			// 처리할 내용 없음
			saveId = 0;

		} else if (fileState.equals(NEW_MODIFIED)) {
			// new 신규입력후 id 구함
			// 파일 저장
			try {
				//saveFileName = FileUtils.saveUniqeName(file.getOriginalFilename(), uploadPath, file.getBytes());
				SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
				SimpleDateFormat dataFormatter = new SimpleDateFormat("yyyyMM");
				String today = formatter.format(new java.util.Date());
				String filePath = uploadPath + dataFormatter.format(new java.util.Date()) + "/";
				String realFileNm = "thumb_" + today + "_" + UUID.randomUUID().toString()  + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

				File folderPath = new File(filePath);
				if (!folderPath.exists()) {
					folderPath.mkdirs();
				}

				FileUtils.saveUniqeName(realFileNm, filePath, file.getBytes());

				//썸네일 이미지 저장(원본 이미지 읽어들임)
				BufferedImage buffImage = ImageIO.read(new File( filePath + realFileNm ));

				ResampleOp resampleOp = new ResampleOp(195, 130);
				resampleOp.setUnsharpenMask( AdvancedResizeOp.UnsharpenMask.Soft );
				BufferedImage rescaledImage = resampleOp.filter(buffImage, null);

				String destPath = filePath +  realFileNm;
				File destFile = new File(destPath);

				ImageIO.write(rescaledImage, "png", destFile);

				pMap.put("fileType", "png");
				pMap.put("orgFileName", file.getOriginalFilename());
				pMap.put("saveFileName", realFileNm);
				pMap.put("thumbnail", "");
				pMap.put("savePath", filePath);
				pMap.put("fileSize", file.getSize());

				exqueryDao.insert("cronies.app.attachFile.insert", pMap);
//				pMap.put("articleId", param.get("articleId"));
//				exqueryDao.insert("nse.bpBoardAttach.insert", pMap);
				saveId = ((Integer)pMap.get("fileId")).intValue();
			} catch (IOException e) {
				e.printStackTrace();
				// TODO:: 저장오류 발생시 처리 보완
			}
		}

		// 나머지 데이터 처리 로직

		return saveId;
	}
	

}
