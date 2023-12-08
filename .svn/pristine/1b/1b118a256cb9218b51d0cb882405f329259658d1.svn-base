package com.nse.config.excel;

import org.apache.poi.ss.usermodel.Sheet;

/**
 * @author Jay Lee
 * Excel 의 개별 sheet 처리하는 interface
 * created on 2018.08.22
 * */
@FunctionalInterface
public interface PoiSheetHandler<T> {
    <T, S extends Sheet> T handle(S sheet);
}
