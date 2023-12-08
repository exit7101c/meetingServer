package com.nse.pms.standard.system.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nse.pms.standard.common.service.CommonFileService;
import com.nse.pms.standard.common.service.ConstraintService;
import com.nse.pms.standard.system.service.NseBoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import select.spring.exquery.service.ExqueryService;

import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("NseBoardService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class NseBoardServiceImpl implements NseBoardService {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExqueryService exqueryService;

	@Autowired
	private ConstraintService constraintService;

	@Autowired
	private CommonFileService commonFileService;
	
	/**
	 * 공지사항 List 를 조회 한다.
	 * @param
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String, Object>> selectNoticeList(HashMap<String, Object> param) {
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		resultList = exqueryService.selectList("nse.pms.system.sqlBoard.selectNoticeList", param);
		return resultList;
	}
	
	
	/**
	 * * @param String startDate, String endDate
	 * 
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> insertNotice(HashMap<String, Object> param, List<MultipartFile> files) throws Exception{
		HashMap<String, Object> resultMap = new HashMap<>();
		HashMap<String, Object> dataMap = new HashMap<>();
	
		
		ObjectMapper mapper = new ObjectMapper();
		dataMap =  mapper.readValue(param.get("data").toString(), new TypeReference<HashMap<String, Object>>() {});
		
		
		// 1. DataInsert
		dataMap.put("ssUserId", param.get("ssUserId"));
		exqueryService.insert("nse.pms.system.sqlBoard.insertNotice", dataMap);
		
		dataMap.replace("referenceFileId", dataMap.get("noticeId"));
		
		// 2. FileInsert
		if(files.size() > 0) {	
			dataMap.put("fileState", "newModified");
			for(int i=0; i<files.size(); i++) {
				commonFileService.insertWithFile("file", dataMap, files.get(i));	
			}
			
		}

		resultMap.put("isExist", !dataMap.get("noticeId").equals("0") ? "true" : "false");
		return resultMap;
	}
	
	
	/**
	 * 첨부파일 List 를 조회 한다. TODO : Controller 이동
	 * @param
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String, Object>> selectAttachFileList(HashMap<String, Object> param) {
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		resultList = exqueryService.selectList("nse.pmsCommon.selectAttachFileList", param);
		return resultList;
	}
	
	/**
	 * @param 
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> selectNoticeOne(HashMap<String, Object> param) {

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String,Object>>();
		
		if(param.get("state").equals("view"))
			exqueryService.update("nse.pms.system.sqlBoard.updateNoticeCnt", param);
		
		
		resultMap = exqueryService.selectOne("nse.pms.system.sqlBoard.selectNoticeOne", param);
		param.put("referenceFileId", resultMap.get("noticeId"));
		resultList = selectAttachFileList(param);
		
		if(resultList.size() > 0) {
			resultMap.put("files", resultList);
		}
		
		return resultMap;
	}

	/**
	 * @param
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> selectNoticeMainPopup(HashMap<String, Object> param) {
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String,Object>>();
		resultMap = exqueryService.selectOne("nse.pms.system.sqlBoard.selectNoticeMainPopup", param);
		if(resultMap != null){
			param.put("referenceFileId", resultMap.get("noticeId"));
			resultList = selectAttachFileList(param);
			if(resultList.size() > 0) 
				resultMap.put("files", resultList);

			resultMap.put("isExist", true);
		}else {
			resultMap = new HashMap<String, Object>(); 
			resultMap.put("isExist", false);
		}
		
		
		
		return resultMap;
	}

	
	/**
	 * @param
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> updateNotice(HashMap<String, Object> param, List<MultipartFile> files) throws Exception{
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		HashMap<String, Object> dataMap = new HashMap<>();
		List<HashMap<String, Object>> deleteFile = new ArrayList<HashMap<String, Object>>();
		
		
		// 1. dataUpdate
		ObjectMapper mapper = new ObjectMapper();
		dataMap =  mapper.readValue(param.get("data").toString(), new TypeReference<HashMap<String, Object>>() {});
		dataMap.put("ssUserId", param.get("ssUserId"));
		exqueryService.update("nse.pms.system.sqlBoard.updateNotice", dataMap);
		dataMap.replace("referenceFileId", dataMap.get("noticeId"));

		// 2. FileInsert
		if(files.size() > 0) {	
			dataMap.put("fileState", "newModified");
			for(int i=0; i<files.size(); i++) {
				commonFileService.insertWithFile("file", dataMap, files.get(i));	
			}
		}
		
		//3. FileDelete
		deleteFile = mapper.readValue(dataMap.get("deletefiles").toString(), new TypeReference<ArrayList<HashMap<String, Object>>>() {});
		if(deleteFile.size() > 0) {
			for(int i=0; i<deleteFile.size(); i++) {
				// TODO: 3-1. 파일 물리적 삭제 고려 후 적용 예정
				// commonFileService.deleteWithFileId(deleteFile.get(i).get("fileId").toString(), deleteFile.get(i));	
				// 3-1. DATA UPDATE
				exqueryService.update("nse.pmsCommon.deleteAttachFile", deleteFile.get(i));
			}
		}
		
		resultMap.put("isExist", !dataMap.get("noticeId").equals("0") ? "true" : "false");
		
		return resultMap;

	}

	/**
	 * @param
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> deleteNotice(HashMap<String, Object> param) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		//List<HashMap<String,Object>> dataList = new ArrayList<HashMap<String,Object>>();

		//ObjectMapper mapper = new ObjectMapper();
		//dataList =  mapper.readValue(param.get("data").toString(), new TypeReference<ArrayList<HashMap<String, Object>>>() {});
		exqueryService.delete("nse.pms.system.sqlBoard.deleteNotice", param);
		resultMap.put("isExist", "true");
				
		return resultMap;
	}


	/** 업데이트 이력관리 **/
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String, Object>> selectBoardList(HashMap<String, Object> param) {
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		resultList = exqueryService.selectList("nse.pms.system.sqlBoard.selectBoardList", param);
		return resultList;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> selectBoardOne(HashMap<String, Object> param) {

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String,Object>>();

		if(param.get("state").equals("view"))
			exqueryService.update("nse.pms.system.sqlBoard.updateBoardViewCnt", param);


		resultMap = exqueryService.selectOne("nse.pms.system.sqlBoard.selectBoardOne", param);
//		param.put("referenceFileId", resultMap.get("noticeId"));
//		resultList = selectAttachFileList(param);
//
//		if(resultList.size() > 0) {
//			resultMap.put("files", resultList);
//		}

		return resultMap;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> insertDistributionHis(HashMap<String, Object> param) {
		HashMap<String, Object> resultMap = new HashMap<>();
		exqueryService.insert("nse.pms.system.sqlBoard.insertDistributionHis", param);
		return resultMap;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> updateDistributionHis(HashMap<String, Object> param) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		exqueryService.selectList("nse.pms.system.sqlBoard.updateDistributionHis", param);
		return resultMap;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> deleteDistributionHis(HashMap<String, Object> param) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		exqueryService.selectList("nse.pms.system.sqlBoard.deleteDistributionHis", param);
		return resultMap;
	}


	/** 자유게시판 **/
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String, Object>> selectFreeBoardList(HashMap<String, Object> param) {
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		resultList = exqueryService.selectList("nse.pms.system.sqlBoard.selectFreeBoardList", param);
		return resultList;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> selectFreeBoardOne(HashMap<String, Object> param) {

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String,Object>>();

		if(param.get("state").equals("view"))
			exqueryService.update("nse.pms.system.sqlBoard.updateBoardViewCnt", param);

		resultMap = exqueryService.selectOne("nse.pms.system.sqlBoard.selectFreeBoardOne", param);
		param.put("articleId", resultMap.get("articleId"));
		resultList = selectFreeBoardAttachFileList(param);

		if(resultList.size() > 0) {
			resultMap.put("files", resultList);
		}

		return resultMap;
	}


	/**
	 * 첨부파일 List 를 조회 한다. TODO : Controller 이동
	 * @param
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String, Object>> selectFreeBoardAttachFileList(HashMap<String, Object> param) {
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		resultList = exqueryService.selectList("nse.pmsCommon.selectFreeBoardAttachFileList", param);
		return resultList;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> insertFreeBoard(HashMap<String, Object> param, List<MultipartFile> files) throws Exception{
		HashMap<String, Object> resultMap = new HashMap<>();
		HashMap<String, Object> dataMap = new HashMap<>();


		ObjectMapper mapper = new ObjectMapper();
		dataMap =  mapper.readValue(param.get("data").toString(), new TypeReference<HashMap<String, Object>>() {});

		// 1. DataInsert
		dataMap.put("ssUserId", param.get("ssUserId"));
		exqueryService.insert("nse.pms.system.sqlBoard.insertFreeBoard", dataMap);

		dataMap.replace("articleId", dataMap.get("newId"));

		//referenceFileId는 쓰지 않지만, 공지사항이랑 겹치기 때문에 "" 을 넣는다.
		dataMap.put("referenceFileId", "");

		// 2. FileInsert
		if(files.size() > 0) {
			dataMap.put("fileState", "newModified");
			for(int i=0; i<files.size(); i++) {
				int result = commonFileService.insertWithFile("file", dataMap, files.get(i));

				String attachFileId = Integer.toString(result);

				HashMap<String, Object> pMap = new HashMap<String, Object>();
				pMap.put("articleId", dataMap.get("articleId").toString());
				pMap.put("attachFileId", attachFileId);
				exqueryService.insert("nse.pms.system.sqlBoard.insertArticleFile", pMap);

			}

		}

		resultMap.put("isExist", !dataMap.get("articleId").equals("0") ? "true" : "false");
		return resultMap;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> updateFreeBoard(HashMap<String, Object> param, List<MultipartFile> files) throws Exception{
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		HashMap<String, Object> dataMap = new HashMap<>();
		List<HashMap<String, Object>> deleteFile = new ArrayList<HashMap<String, Object>>();


		// 1. dataUpdate
		ObjectMapper mapper = new ObjectMapper();
		dataMap =  mapper.readValue(param.get("data").toString(), new TypeReference<HashMap<String, Object>>() {});
		dataMap.put("ssUserId", param.get("ssUserId"));
		exqueryService.update("nse.pms.system.sqlBoard.updateFreeBoard", dataMap);

		//referenceFileId는 쓰지 않지만, 공지사항이랑 겹치기 때문에 "" 을 넣는다.
		dataMap.put("referenceFileId", "");

		// 2. FileInsert
		if(files.size() > 0) {
			dataMap.put("fileState", "newModified");
			for(int i=0; i<files.size(); i++) {
				int result = commonFileService.insertWithFile("file", dataMap, files.get(i));

				String attachFileId = Integer.toString(result);

				HashMap<String, Object> pMap = new HashMap<String, Object>();
				pMap.put("articleId", dataMap.get("articleId").toString());
				pMap.put("attachFileId", attachFileId);
				exqueryService.insert("nse.pms.system.sqlBoard.insertArticleFile", pMap);

			}
		}

		//3. FileDelete
		deleteFile = mapper.readValue(dataMap.get("deletefiles").toString(), new TypeReference<ArrayList<HashMap<String, Object>>>() {});
		if(deleteFile.size() > 0) {
			for(int i=0; i<deleteFile.size(); i++) {
				// TODO: 3-1. 파일 물리적 삭제 고려 후 적용 예정
				// commonFileService.deleteWithFileId(deleteFile.get(i).get("fileId").toString(), deleteFile.get(i));
				// 3-1. DATA UPDATE
				exqueryService.update("nse.pmsCommon.deleteAttachFile", deleteFile.get(i));
			}
		}

		resultMap.put("isExist", !dataMap.get("articleId").equals("0") ? "true" : "false");

		return resultMap;

	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> deleteFreeBoard(HashMap<String, Object> param) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		//List<HashMap<String,Object>> dataList = new ArrayList<HashMap<String,Object>>();

		//ObjectMapper mapper = new ObjectMapper();
		//dataList =  mapper.readValue(param.get("data").toString(), new TypeReference<ArrayList<HashMap<String, Object>>>() {});
		exqueryService.delete("nse.pms.system.sqlBoard.deleteFreeBoard", param);
		resultMap.put("isExist", "true");

		return resultMap;
	}

}