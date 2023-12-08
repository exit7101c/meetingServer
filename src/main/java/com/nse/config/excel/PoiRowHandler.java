package com.nse.config.excel;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * @author Jay Lee
 * Excel 의 개별 row 처리하는 interface
 * created on 2018.08.22
 * */
@FunctionalInterface
public interface PoiRowHandler {
    <S extends Sheet, R extends Row> void handle(S sheet, R row);
}
