package com.xq.xtool.excel.excel;

import org.apache.poi.ss.usermodel.CellStyle;

import java.util.function.Function;

/**
 * Description:
 *
 * @author 13797
 * @version v0.0.1
 * 2021/11/11 23:52
 */
public interface Excel<DTO> {

    public interface Writer<DTO> {
        String getHeader();
        Function<DTO, String> getValue();
    }

    public interface Reader<DTO> {
        String getHeader();
        Function<String, DTO> setValue();
    }

    public interface Temper {
        String getHeader();
        String[] dropData();
    }

    public interface Style<DTO> {
        Function<DTO, CellStyle> getValue();
    }

}
