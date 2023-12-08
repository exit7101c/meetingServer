package com.nse.config.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * @author Jay Lee
 * Excel 의 개별 cell 처리하는 interface
 * created on 2018.08.22
 * */
@FunctionalInterface
public interface PoiCellHandler  {
     <S extends Sheet, R extends Row, C extends Cell> void handle(S sheet, R row, C cell);
}

