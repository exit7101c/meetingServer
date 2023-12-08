package com.nse.config.excel.download;

import com.nse.config.excel.PoiRowHandler;

import java.util.List;
import java.util.Map;

public interface ExcelGenerator {

    /**
     * iterate through rows and populate sheet
     * @param rows 데이터 row
     * */
    void forEach(List<Map<String, Object>> rows, PoiRowHandler rowHandler);

    /**
     * Column 값 Processing
     * @param columns 특정 데이터 set 의 갈럼값
     * */
    void forEach(Map<String, Object> columns, PoiRowHandler row);
}
