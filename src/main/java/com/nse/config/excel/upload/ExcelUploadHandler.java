package com.nse.config.excel.upload;

import com.nse.config.excel.PoiCellHandler;
import com.nse.config.excel.PoiRowHandler;
import com.nse.config.excel.PoiSheetHandler;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

/**
 * @author Jay Lee
 * Apache POI backwards compatibility
 * 지원 가능한 Poi Wrapper Library 입니다.
 * created on 2018.08.22
 * */
public class ExcelUploadHandler {

    // Excel workbook
    private Workbook workbook;
    private static final DataFormatter dataFormatter = new DataFormatter();

    /**
     * Constructor section
     * ===============================
     * */

    public ExcelUploadHandler(ByteArrayInputStream byteArrayInputStream) throws IOException {
        initializeWrapper(byteArrayInputStream);
    }

    public ExcelUploadHandler(byte[] fileBytes) throws IOException {
        initializeWrapper(fileBytes);
    }

    public ExcelUploadHandler(ByteArrayInputStream byteArrayInputStream, String extension) throws IOException {
        initializeWrapper(byteArrayInputStream, extension);
    }

    /**
     * Public methods
     * ===========================
     * */

    /**
     * Excel 파일 데이터 Reading 영역
     * ============================================
     * */

    /**
     * Excel 파일에 있는 모든 Cell data 처리
     * */
    public ProcessResult processByCell(PoiCellHandler dataHandler) {
        int sheetCount = this.workbook.getNumberOfSheets();
        for (int i = 0; i < sheetCount; i++) {
            Sheet sheet = this.workbook.getSheetAt(i);
            for (Row row : sheet) {
                for (Cell cell : row) {
                    dataHandler.handle(sheet, row, cell);
                }
            }
        }
        return new ProcessResult(true, "success");
    }

    /**
     * Excel 파일에 있는 모든 Row data 처리
     * */
    public ProcessResult processByRow(PoiRowHandler rowHandler) {
        int sheetCount = this.workbook.getNumberOfSheets();
        for (int i = 0; i < sheetCount; i++) {
            Sheet sheet = this.workbook.getSheetAt(i);
            for (Row row : sheet) {
                rowHandler.handle(sheet, row);
            }
        }
        return new ProcessResult(true, "success");
    }

    public ProcessResult processByRow(PoiRowHandler rowHandler, int startIndex) {
        int sheetCount = this.workbook.getNumberOfSheets();
        for (int i = 0; i < sheetCount; i++) {
            Sheet sheet = this.workbook.getSheetAt(i);
            int rowCount = sheet.getPhysicalNumberOfRows();
            Row row;
            for (; startIndex < rowCount; startIndex++) {
                row = sheet.getRow(startIndex);
                if (row != null)
                    rowHandler.handle(sheet, row);
            }
        }
        return new ProcessResult(true, "success");
    }

    /**
     * Excel 파일에 있는 모든 Sheet data 처리
     * */
    public ProcessResult processBySheet(PoiSheetHandler<T> sheetHandler) {
        int sheetCount = this.workbook.getNumberOfSheets();
        for (int i = 0; i < sheetCount; i++) {
            T data = sheetHandler.handle(this.workbook.getSheetAt(i));
        }
        return new ProcessResult(true, "success");
    }

    /**
     * Excel upload 로 대용량 데이터 insert 시 사용하는문
     * @param tableName Insert 처리 대상 테이블
     * @param excelToDbMap Db 컬럼과 Excel 칼럼 Index 과 Mapping 해주는 Map
     * */
    public boolean insertIntoDatabase(String tableName,
                                      Map<String, Integer> excelToDbMap) {
        int sheetCount = this.workbook.getNumberOfSheets();
        Set<String> keySet = excelToDbMap.keySet();
        List<Map<String, Object>> itemsToInsert = new ArrayList<>();
        Map<String, Object> data = new HashMap<>();
        for (int i = 0; i < sheetCount; i++) {
            Sheet sheet = this.workbook.getSheetAt(i);
            for (Row row : sheet) {
                for (String columnName : keySet) {
                    data.put(columnName,
                            getCellValue(row.getCell(excelToDbMap.get(columnName)))
                            );
                }
            }
            itemsToInsert.add(data);
        }
        // TODO: 테이블에 Insert 하는 쿼리 추가
        return true;
    }

    public static String getCellValue(Cell cell) {
        return dataFormatter.formatCellValue(cell);
    }

    /**
     * Getter / Setter section
     * ========================================
     * */
    public Workbook getWorkbook() {
        return workbook;
    }

    public void setWorkbook(Workbook workbook) {
        this.workbook = workbook;
    }

    public boolean isValidExcelFile() {
        return this.workbook != null;
    }


    /**
     * Private methods
     * ===========================
     * */

    /**
     * Trial and error initialization
     * @param byteArrayInputStream
     * */
    private void initializeWrapper(ByteArrayInputStream byteArrayInputStream) throws IOException {
        try {
            this.workbook = new XSSFWorkbook(byteArrayInputStream);
        } catch (Exception e) {
            this.workbook = new HSSFWorkbook(byteArrayInputStream);
        }
    }

    /**
     * Trial and error initialization
     * @param fileBytes
     * */
    private void initializeWrapper(byte[] fileBytes) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(fileBytes);
        try {
            this.workbook = new XSSFWorkbook(bis);
        } catch (Exception e) {
            this.workbook = new HSSFWorkbook(bis);
        }
    }

    /**
     * @param byteArrayInputStream
     * @param extension 파일 확장자명
     * */
    private void initializeWrapper(ByteArrayInputStream byteArrayInputStream,
                                   String extension) throws IOException {
        boolean isNewerExcel = extension.toLowerCase().equals("xlsx");
        if (isNewerExcel) {
            this.workbook = new XSSFWorkbook(byteArrayInputStream);
        } else {
            this.workbook = new HSSFWorkbook(byteArrayInputStream);
        }
    }

    // Sheet empty check
    private boolean isSheetEmpty(Sheet sheet){
        Iterator rows = sheet.rowIterator();
        while (rows.hasNext()) {
            Row row = (Row) rows.next();
            Iterator cells = row.cellIterator();
            while (cells.hasNext()) {
                Cell cell = (Cell) cells.next();
                if(!cell.getStringCellValue().isEmpty()){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 처리결과 객체
     * */
    private class ProcessResult {
        private boolean success;
        private String message;

        public ProcessResult(boolean success, String message) {
            this.success = success;
            this.message = message;
        }
    }
}
