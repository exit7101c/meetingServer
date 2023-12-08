package com.nse.pms.standard.common.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

import com.nse.pms.standard.common.service.ConstraintService;
import com.nse.pms.standard.common.service.ExcelPoiService;
import com.nse.pms.standard.common.service.ExqueryLogService;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import select.spring.exquery.service.ExqueryService;
import select.spring.util.ApplicationProperty;

@Service("excelPoiService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ExcelPoiServiceImpl implements ExcelPoiService {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExqueryService exqueryService;
	
	@Autowired
	private ExqueryLogService exqueryLogService;
	
	@Autowired
	private ConstraintService constraintService;
	
	private String uploadPath = ApplicationProperty.get("upload.path");
	
	/**
	 * POI 방식 엑셀다운로드 by Server
	 * @throws Exception  
	 */
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> createExcelPoiByServer(String FileName, List<HashMap<String, Object>> paramList) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		String excelName = FileName;
        XSSFSheet worksheet = null;
        XSSFRow row = null;

        excelName=URLEncoder.encode(excelName,"UTF-8");
        
        //Excel Write
        XSSFWorkbook workbook = new XSSFWorkbook();
        
        worksheet = workbook.createSheet(excelName+ " WorkSheet");

        List<HashMap<String, Object>> list =  paramList;
         
        try{
        	 //내용 
            int rowNo = 0;
            for(int i=0; i<list.size(); i++){
          	  
                rowNo += 1;  
          	  	 row = worksheet.createRow(rowNo);
          	  
                row.createCell(0).setCellValue(list.get(i).get("stdTime").toString());
                row.createCell(1).setCellValue(list.get(i).get("dataVer").toString());
                	if(i == 0){
                		row.createCell(2).setCellValue(list.get(i).get("avgWeight").toString());
                		row.createCell(3).setCellValue(list.get(i).get("val01").toString());
                        row.createCell(4).setCellValue(list.get(i).get("val02").toString());
                        row.createCell(5).setCellValue(list.get(i).get("val03").toString());
                        row.createCell(6).setCellValue(list.get(i).get("val04").toString());
                        row.createCell(7).setCellValue(list.get(i).get("val05").toString());
                        row.createCell(8).setCellValue(list.get(i).get("val06").toString());
                        row.createCell(9).setCellValue(list.get(i).get("val07").toString());
                        row.createCell(10).setCellValue(list.get(i).get("val08").toString());
                        row.createCell(11).setCellValue(list.get(i).get("val09").toString());
                        row.createCell(12).setCellValue(list.get(i).get("val10").toString());
                        row.createCell(13).setCellValue(list.get(i).get("val11").toString());
                        row.createCell(14).setCellValue(list.get(i).get("val12").toString());
                        row.createCell(15).setCellValue(list.get(i).get("val13").toString());
                        row.createCell(16).setCellValue(list.get(i).get("val14").toString());
                        row.createCell(17).setCellValue(list.get(i).get("val15").toString());
                        row.createCell(18).setCellValue(list.get(i).get("val16").toString());
                        row.createCell(19).setCellValue(list.get(i).get("val17").toString());
                        row.createCell(20).setCellValue(list.get(i).get("val18").toString());
                        row.createCell(21).setCellValue(list.get(i).get("val19").toString());
                        row.createCell(22).setCellValue(list.get(i).get("val20").toString());
                        row.createCell(23).setCellValue(list.get(i).get("val21").toString());
                        row.createCell(24).setCellValue(list.get(i).get("val22").toString());
                        row.createCell(25).setCellValue(list.get(i).get("val23").toString());
                        row.createCell(26).setCellValue(list.get(i).get("val24").toString());
                        row.createCell(27).setCellValue(list.get(i).get("val25").toString());
                        row.createCell(28).setCellValue(list.get(i).get("val26").toString());
                        row.createCell(29).setCellValue(list.get(i).get("val27").toString());
                        row.createCell(30).setCellValue(list.get(i).get("val28").toString());
                        row.createCell(31).setCellValue(list.get(i).get("val29").toString());
                        row.createCell(32).setCellValue(list.get(i).get("val30").toString());
                        row.createCell(33).setCellValue(list.get(i).get("val31").toString());
                        row.createCell(34).setCellValue(list.get(i).get("val32").toString());
                        row.createCell(35).setCellValue(list.get(i).get("val33").toString());
                        row.createCell(36).setCellValue(list.get(i).get("val34").toString());
                        row.createCell(37).setCellValue(list.get(i).get("val35").toString());
                        row.createCell(38).setCellValue(list.get(i).get("val36").toString());
                        row.createCell(39).setCellValue(list.get(i).get("val37").toString());
                        row.createCell(40).setCellValue(list.get(i).get("val38").toString());
                        row.createCell(41).setCellValue(list.get(i).get("val39").toString());
                        row.createCell(42).setCellValue(list.get(i).get("val40").toString());
                        row.createCell(43).setCellValue(list.get(i).get("val41").toString());
                        row.createCell(44).setCellValue(list.get(i).get("val42").toString());
                        row.createCell(45).setCellValue(list.get(i).get("val43").toString());
                        row.createCell(46).setCellValue(list.get(i).get("val44").toString());
                        row.createCell(47).setCellValue(list.get(i).get("val45").toString());
                        row.createCell(48).setCellValue(list.get(i).get("val46").toString());
                        row.createCell(49).setCellValue(list.get(i).get("val47").toString());
                        row.createCell(50).setCellValue(list.get(i).get("val48").toString());
                        row.createCell(51).setCellValue(list.get(i).get("val49").toString());
                        row.createCell(52).setCellValue(list.get(i).get("val50").toString());
                	} else {
                		row.createCell(2).setCellValue(Double.parseDouble(list.get(i).get("avgWeight").toString()));
                		row.createCell(3).setCellValue(Double.parseDouble(list.get(i).get("val01").toString()));
                        row.createCell(4).setCellValue(Double.parseDouble(list.get(i).get("val02").toString()));
                        row.createCell(5).setCellValue(Double.parseDouble(list.get(i).get("val03").toString()));
                        row.createCell(6).setCellValue(Double.parseDouble(list.get(i).get("val04").toString()));
                        row.createCell(7).setCellValue(Double.parseDouble(list.get(i).get("val05").toString()));
                        row.createCell(8).setCellValue(Double.parseDouble(list.get(i).get("val06").toString()));
                        row.createCell(9).setCellValue(Double.parseDouble(list.get(i).get("val07").toString()));
                        row.createCell(10).setCellValue(Double.parseDouble(list.get(i).get("val08").toString()));
                        row.createCell(11).setCellValue(Double.parseDouble(list.get(i).get("val09").toString()));
                        row.createCell(12).setCellValue(Double.parseDouble(list.get(i).get("val10").toString()));
                        row.createCell(13).setCellValue(Double.parseDouble(list.get(i).get("val11").toString()));
                        row.createCell(14).setCellValue(Double.parseDouble(list.get(i).get("val12").toString()));
                        row.createCell(15).setCellValue(Double.parseDouble(list.get(i).get("val13").toString()));
                        row.createCell(16).setCellValue(Double.parseDouble(list.get(i).get("val14").toString()));
                        row.createCell(17).setCellValue(Double.parseDouble(list.get(i).get("val15").toString()));
                        row.createCell(18).setCellValue(Double.parseDouble(list.get(i).get("val16").toString()));
                        row.createCell(19).setCellValue(Double.parseDouble(list.get(i).get("val17").toString()));
                        row.createCell(20).setCellValue(Double.parseDouble(list.get(i).get("val18").toString()));
                        row.createCell(21).setCellValue(Double.parseDouble(list.get(i).get("val19").toString()));
                        row.createCell(22).setCellValue(Double.parseDouble(list.get(i).get("val20").toString()));
                        row.createCell(23).setCellValue(Double.parseDouble(list.get(i).get("val21").toString()));
                        row.createCell(24).setCellValue(Double.parseDouble(list.get(i).get("val22").toString()));
                        row.createCell(25).setCellValue(Double.parseDouble(list.get(i).get("val23").toString()));
                        row.createCell(26).setCellValue(Double.parseDouble(list.get(i).get("val24").toString()));
                        row.createCell(27).setCellValue(Double.parseDouble(list.get(i).get("val25").toString()));
                        row.createCell(28).setCellValue(Double.parseDouble(list.get(i).get("val26").toString()));
                        row.createCell(29).setCellValue(Double.parseDouble(list.get(i).get("val27").toString()));
                        row.createCell(30).setCellValue(Double.parseDouble(list.get(i).get("val28").toString()));
                        row.createCell(31).setCellValue(Double.parseDouble(list.get(i).get("val29").toString()));
                        row.createCell(32).setCellValue(Double.parseDouble(list.get(i).get("val30").toString()));
                        row.createCell(33).setCellValue(Double.parseDouble(list.get(i).get("val31").toString()));
                        row.createCell(34).setCellValue(Double.parseDouble(list.get(i).get("val32").toString()));
                        row.createCell(35).setCellValue(Double.parseDouble(list.get(i).get("val33").toString()));
                        row.createCell(36).setCellValue(Double.parseDouble(list.get(i).get("val34").toString()));
                        row.createCell(37).setCellValue(Double.parseDouble(list.get(i).get("val35").toString()));
                        row.createCell(38).setCellValue(Double.parseDouble(list.get(i).get("val36").toString()));
                        row.createCell(39).setCellValue(Double.parseDouble(list.get(i).get("val37").toString()));
                        row.createCell(40).setCellValue(Double.parseDouble(list.get(i).get("val38").toString()));
                        row.createCell(41).setCellValue(Double.parseDouble(list.get(i).get("val39").toString()));
                        row.createCell(42).setCellValue(Double.parseDouble(list.get(i).get("val40").toString()));
                        row.createCell(43).setCellValue(Double.parseDouble(list.get(i).get("val41").toString()));
                        row.createCell(44).setCellValue(Double.parseDouble(list.get(i).get("val42").toString()));
                        row.createCell(45).setCellValue(Double.parseDouble(list.get(i).get("val43").toString()));
                        row.createCell(46).setCellValue(Double.parseDouble(list.get(i).get("val44").toString()));
                        row.createCell(47).setCellValue(Double.parseDouble(list.get(i).get("val45").toString()));
                        row.createCell(48).setCellValue(Double.parseDouble(list.get(i).get("val46").toString()));
                        row.createCell(49).setCellValue(Double.parseDouble(list.get(i).get("val47").toString()));
                        row.createCell(50).setCellValue(Double.parseDouble(list.get(i).get("val48").toString()));
                        row.createCell(51).setCellValue(Double.parseDouble(list.get(i).get("val49").toString()));
                        row.createCell(52).setCellValue(Double.parseDouble(list.get(i).get("val50").toString()));
                	}
                	
                
                

            }

            // 실제로 저장될 파일 풀 경로
            File file = new File(uploadPath, excelName);

            //엑셀 파일을 만듬
            FileOutputStream fileOutput = new FileOutputStream(file);

            workbook.write(fileOutput);
            fileOutput.close();
            
            //파일정보 저장 
            HashMap<String, Object> pMap = new HashMap<String, Object>();
            // new 신규입력후 id 구함
   		 // 파일 저장
   		 pMap.put("fileType", excelName.substring( excelName.lastIndexOf( "." ) + 1 ));
   		 pMap.put("orgFileName", excelName);
   		 pMap.put("saveFileName", excelName);
   		 pMap.put("savePath", uploadPath);
   		 pMap.put("fileSize", file.getFreeSpace());
   		 exqueryService.insert("nse.pmsAttachFile.insert", pMap);
//         			pMap.put("articleId", param.get("articleId"));
//         			exqueryDao.insert("nse.pmsBoardAttach.insert", pMap);
   		 int saveId = ((Integer)pMap.get("fileId")).intValue();
            
            resultMap.put("result", true);
            resultMap.put("saveId", saveId);
        } catch(Exception e){
        	System.out.println(e);
        	e.printStackTrace();
        	
        	resultMap.put("result", false);
        }
        
         
		return resultMap;
	}
	
	
	/**
	 * POI 방식 엑셀다운로드 by Server
	 * @throws Exception  
	 */
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> createExcelPoiByWeight(String FileName, List<HashMap<String, Object>> paramList) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		String excelName = FileName;
        XSSFSheet worksheet = null;
        XSSFRow row = null;

        excelName=URLEncoder.encode(excelName,"UTF-8");
        
        //Excel Write
        XSSFWorkbook workbook = new XSSFWorkbook();
        
        worksheet = workbook.createSheet(excelName+ " WorkSheet");

        List<HashMap<String, Object>> list =  paramList;
         
        try{
        	 //내용 
            int rowNo = 0;
            for(int i=0; i<list.size(); i++){
          	  
                rowNo += 1;  
          	  	 row = worksheet.createRow(rowNo);
          	  
                row.createCell(0).setCellValue(list.get(i).get("procTime").toString());
                row.createCell(1).setCellValue(list.get(i).get("procMsec").toString());
                	if(i == 0){
                		row.createCell(2).setCellValue(list.get(i).get("value").toString());
                	} else {
                		row.createCell(2).setCellValue(Double.parseDouble(list.get(i).get("value").toString()));
                	}
            	row.createCell(3).setCellValue(list.get(i).get("judge").toString());

            }

            // 실제로 저장될 파일 풀 경로
            File file = new File(uploadPath, excelName);

            //엑셀 파일을 만듬
            FileOutputStream fileOutput = new FileOutputStream(file);

            workbook.write(fileOutput);
            fileOutput.close();
            
            //파일정보 저장 
            HashMap<String, Object> pMap = new HashMap<String, Object>();
            // new 신규입력후 id 구함
   		 // 파일 저장
   		 pMap.put("fileType", excelName.substring( excelName.lastIndexOf( "." ) + 1 ));
   		 pMap.put("orgFileName", excelName);
   		 pMap.put("saveFileName", excelName);
   		 pMap.put("savePath", uploadPath);
   		 pMap.put("fileSize", file.getFreeSpace());
   		 exqueryService.insert("nse.pmsAttachFile.insert", pMap);
//         			pMap.put("articleId", param.get("articleId"));
//         			exqueryDao.insert("nse.pmsBoardAttach.insert", pMap);
   		 int saveId = ((Integer)pMap.get("fileId")).intValue();
            
            resultMap.put("result", true);
            resultMap.put("saveId", saveId);
        } catch(Exception e){
        	System.out.println(e);
        	e.printStackTrace();
        	
        	resultMap.put("result", false);
        }
        
         
		return resultMap;
	}
}