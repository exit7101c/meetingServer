package com.nse.config.excel.download;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nse.config.excel.PoiRowHandler;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jay Lee
 * Excel 파일 생성 함수
 * */
public class ExcelCreator implements ExcelGenerator {

    private boolean isXlsx = true;
    private String extension;
    private Workbook currentWorkbook;
    // 임시 사용 안힘
    // private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 생성자 영역
     * =============================
     */
    public ExcelCreator() {
        // NO OP
    }

    public ExcelCreator(boolean isXlsx) {
        this.isXlsx = isXlsx;
    }

    private Workbook createWorkbook() {
        setExtension();
        if (this.isXlsx) {
            this.currentWorkbook = new XSSFWorkbook();
        } else {
            this.currentWorkbook = new HSSFWorkbook();
        }
        return this.currentWorkbook;
    }

    /**
     * Public Method 영역
     * =============================
     */

    /**
     * Default Templates
     * =============================
     */
    public static Font getDefaultHeaderFont(Workbook workbook) {
        // Create a Font for styling header cells
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontName("돋움");
        headerFont.setFontHeight((short) 18);
        headerFont.setColor(IndexedColors.BLACK.getIndex());
        return headerFont;
    }

    public static CellStyle getDefaultHeaderStyle(Workbook workbook) {
        // Create a CellStyle with the font
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
        headerCellStyle.setBorderBottom(BorderStyle.THIN);
        headerCellStyle.setBorderTop(BorderStyle.THIN);
        headerCellStyle.setBorderRight(BorderStyle.THIN);
        headerCellStyle.setBorderLeft(BorderStyle.THIN);
        headerCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
      //  headerCellStyle.setWrapText(true);
        return headerCellStyle;
    }

    public CellStyle getDefaultCellStyle(Workbook workbook) {
        // Create a CellStyle with the font
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setWrapText(true);
        return cellStyle;
    }

    public CellStyle getDefaultFirstColumnCellStyle(Workbook workbook) {
        // Create a CellStyle with the font
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setBorderTop(BorderStyle.THIN);
        headerCellStyle.setBorderBottom(BorderStyle.THIN);
        headerCellStyle.setBorderLeft(BorderStyle.THIN);
        headerCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return headerCellStyle;
    }

    public CellStyle getDefaultLastColumnCellStyle(Workbook workbook) {
        // Create a CellStyle with the font
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setBorderTop(BorderStyle.THIN);
        headerCellStyle.setBorderBottom(BorderStyle.THIN);
        headerCellStyle.setBorderRight(BorderStyle.THIN);
        headerCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return headerCellStyle;
    }

    public Workbook getWorkbook() {
        return currentWorkbook;
    }

    /**
     * @param fileName 파일명. 확장자 제외
     * @param headers Column 의 definition 정의. Key 값은 Database column key 값. 실제 값은 excel header 의 텍스트
     * @param dataList DB 에서 쿼리해온 데이터
     * @param response
     * */
    public void generateExcelSheet(String fileName, List<HashMap<String, Object>> headers, List<HashMap<String, Object>> dataList, HttpServletResponse response) throws IOException {

        // Workbook 생성
        Workbook workbook = createWorkbook();

        int colCount = headers.size();

        // Sheet 생성
        Sheet sheet = workbook.createSheet(fileName.split("\\.")[0]);

        // Row 생성
        Row headerRow = sheet.createRow(0);

        // 공통 변수 선언
        Cell headerCell;                // First Header row
        Map<String, Object> datum;      // 데이터 Row
        Map<String, Object> columnDef;  // Header Row

        // header 스타일
        CellStyle headerCellStyle = getDefaultHeaderStyle(workbook);
        // Column Style
        CellStyle cellStyle = getDefaultCellStyle(workbook);

        // header width
        int[] headerWidths = new int[headers.size()];

        // Header Row 생성
        for (int i = 0; i < headers.size(); i++) {
            // Map 에 row 하나 있음
            columnDef = headers.get(i);
            for (String key : columnDef.keySet()) {
                headerCell = headerRow.createCell(i);
                headerCell.setCellValue(columnDef.get(key).toString());
                headerCell.setCellStyle(headerCellStyle);
            }
            headerWidths[i] = sheet.getColumnWidth(i);
        }

        // Header column (0) skip
        int rowNum = 1;
        Cell cell;
        // 데이터 Row 생성
        for (int i = 0; i < dataList.size(); i++) {
            Row row = sheet.createRow(rowNum++);

            // 데이터
            datum = dataList.get(i);

            // Column 생성 (n 개)
            for (int j = 0; j < colCount; j++) {
                // Map 에 row 하나 있음
                columnDef = headers.get(j);

                // Row size = 1 always
                for (String key : columnDef.keySet()) {
                    cell = row.createCell(j);
                    if (datum.containsKey(key) && datum.get(key) != null) {
                        cell.setCellValue(datum.get(key).toString());
                    } else {
                        cell.setCellValue("");
                    }
                    cell.setCellStyle(cellStyle);
                }
            }
        }

        // 첫 row 틀 고정
        sheet.createFreezePane(0, 1);

        // Column Resize
        for (int i = 0; i < headers.size(); i++) {
            sheet.autoSizeColumn(i);
            int width = sheet.getColumnWidth(i);
            // Responsive auto-sizing. Header width 가
            // content width 보다 크면
            if (width < headerWidths[i]) {
                sheet.setColumnWidth(i, headerWidths[i]);
            }
        }


        // Response 에 파일 담기
        writeFileToResponse(fileName, workbook, response);
    }
    
    /**
     * Excel MuliSheet Down 
     * @param fileName 파일명. 확장자 제외
     * @param dataList Sheet의 DataList
     * @param response
     * 
     * */
    public void generateExcelMultiSheet(String fileName, List<HashMap<String, Object>> excelDownList, HttpServletResponse response) throws IOException {

        // Workbook 생성
        Workbook workbook = createWorkbook();
        ObjectMapper mapper = new ObjectMapper();
        Sheet sheet;
        String sheetName;
        int colCount;
        int sheetIdx;
        
        for(int i=0; i<excelDownList.size(); i++) {
        	
        	List<HashMap<String, Object>> headerList = new ArrayList<HashMap<String, Object>>();		// Title
        	List<HashMap<String, Object>> bodyList = new ArrayList<HashMap<String, Object>>();          // DataRow
        	
        	headerList = mapper.readValue(excelDownList.get(i).get("colDefs").toString(), new TypeReference<List<HashMap<String, Object>>>(){});
        	bodyList  =(List<HashMap<String, Object>>) excelDownList.get(i).get("dataList");
        	
        	colCount = headerList.size();
        	sheetName = excelDownList.get(i).containsKey("sheetName") ? excelDownList.get(i).get("sheetName").toString() : "Sheet"+i ;

        	// SHEET 생성
        	sheetIdx = workbook.getSheetIndex(sheetName);
        	sheetName = sheetIdx != -1 ? sheetName+"("+i+")" : sheetName;
        	sheet = workbook.createSheet(sheetName);
        	
        	 // Row 생성
            Row headerRow = sheet.createRow(0);


            // 공통 변수 선언
            Cell headerCell;                // First Header row
            Map<String, Object> datum;      // 데이터 Row
            Map<String, Object> columnDef;  // Header Row

            
            CellStyle headerCellStyle = getDefaultHeaderStyle(workbook); // header 스타일
            CellStyle cellStyle = getDefaultCellStyle(workbook);         // Column Style
            
            // header width
            int[] headerWidths = new int[headerList.size()];
            
            // Header Row 생성
            for (int h = 0; h < headerList.size(); h++) {
                // Map 에 row 하나 있음
                columnDef = headerList.get(h);
                for (String key : columnDef.keySet()) {
                    headerCell = headerRow.createCell(h);
                    headerCell.setCellValue(columnDef.get(key).toString());
                    headerCell.setCellStyle(headerCellStyle);
                }
                headerWidths[h] = sheet.getColumnWidth(h);
            }

            // Header column (0) skip
            Cell cell;
            int rowNum = 1;
            
            // 데이터 Row 생성 S
            for (int b = 0; b < bodyList.size(); b++) {
                Row row = sheet.createRow(rowNum++);
                // 데이터
                datum = bodyList.get(b);
                // Column 생성 (n 개)
                for (int j = 0; j < colCount; j++) {
                    // Map 에 row 하나 있음
                    columnDef = headerList.get(j);

                    // Row size = 1 always
                    for (String key : columnDef.keySet()) {
                        cell = row.createCell(j);
                        if (datum.containsKey(key) && datum.get(key) != null) {
                            cell.setCellValue(datum.get(key).toString());
                        } else {
                            cell.setCellValue("");
                        }
                        cell.setCellStyle(cellStyle);
                    }
                }
            }
            // 데이터 Row 생성  E
            
            
            sheet.createFreezePane(0, 1); 	// 첫 row 틀고정
            
            // Column Resize
            for (int c = 0; c < headerList.size(); c++) {
                sheet.autoSizeColumn(c);
                int width = sheet.getColumnWidth(c);
                // Responsive auto-sizing. Header width 가
                // content width 보다 크면
                if (width < headerWidths[c]) {
                    sheet.setColumnWidth(c, headerWidths[c]);
                }
            }
            
        }

        // Response 에 파일 담기
        writeFileToResponse(fileName, workbook, response);
    }
    

    /**
     * 사용자 Customization 영역 - 아직 사용 안함
     * ==========================================
     * */
    @Override
    public void forEach(List<Map<String, Object>> rows, PoiRowHandler rowHandler) {
        for (Map<String, Object> row : rows) {
            // 아직은 불필요함
        }
    }

    @Override
    public void forEach(Map<String, Object> columns, PoiRowHandler row) {

    }

    /**
     * Response 객체에 엑셀파일 쓰기
     * @param fileName 파일명. 확장제 제외
     * @param workbook POI Excel workbook 객체
     * @param response
     * */
    private void writeFileToResponse(String fileName, Workbook workbook, HttpServletResponse response) throws IOException {
        // Response 객체에 Excel 데이터 담기
        String fullFileName = fileName + extension;
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fullFileName + "\";");

        try (FileOutputStream fos = new FileOutputStream(fullFileName)) {
            workbook.write(fos);
            workbook.write(response.getOutputStream());
        } finally {
            workbook.close();
        }
    }

    private void setCurrentWorkbook(Workbook workbook) {
        this.currentWorkbook = workbook;
    }

    private void setExtension() {
        this.extension = this.isXlsx ? ".xlsx" : ".xls";
    }
}