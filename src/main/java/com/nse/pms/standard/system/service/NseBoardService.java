package com.nse.pms.standard.system.service;

import java.util.HashMap;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.ModelMap;

public interface NseBoardService {

	public List<HashMap<String, Object>> selectNoticeList(HashMap<String, Object> param);
	public List<HashMap<String, Object>> selectAttachFileList(HashMap<String, Object> param);
	public HashMap<String, Object> insertNotice(HashMap<String, Object> param, List<MultipartFile> files) throws Exception;
	
	public HashMap<String, Object> selectNoticeOne(HashMap<String, Object> param);
	public HashMap<String, Object> selectNoticeMainPopup(HashMap<String, Object> param);
	public HashMap<String, Object> updateNotice(HashMap<String, Object> param, List<MultipartFile> files) throws Exception;
	public HashMap<String, Object> deleteNotice(HashMap<String, Object> param) throws Exception;

	public List<HashMap<String, Object>> selectBoardList(HashMap<String, Object> param);
	public HashMap<String, Object> selectBoardOne(HashMap<String, Object> param);
	public HashMap<String, Object> insertDistributionHis(HashMap<String, Object> param);
	public HashMap<String, Object> updateDistributionHis(HashMap<String, Object> param);
	public HashMap<String, Object> deleteDistributionHis(HashMap<String, Object> param);


	public List<HashMap<String, Object>> selectFreeBoardList(HashMap<String, Object> param);
	public HashMap<String, Object> selectFreeBoardOne(HashMap<String, Object> param);
	public HashMap<String, Object> insertFreeBoard(HashMap<String, Object> param, List<MultipartFile> files) throws Exception;
	public HashMap<String, Object> updateFreeBoard(HashMap<String, Object> param, List<MultipartFile> files) throws Exception;
	public HashMap<String, Object> deleteFreeBoard(HashMap<String, Object> param) throws Exception;

}