package com.xq.xtool.excel.excel;

import org.apache.poi.ss.usermodel.CellStyle;

/**
 * 设置默认值，样式，特殊格式
 */
public class Builder<DTO, E extends Excel.Writer<DTO>> {
    boolean xlsx = true;
    Class<E> enumClass;
    CellStyle headerStyle;
    CellStyle defaultStyle;

    public Builder<DTO, E> xlsx(boolean xlsx) {
        this.xlsx = xlsx;
        return this;
    }

    public Builder<DTO, E> headerStyle(CellStyle style) {
        this.headerStyle = style;
        return this;
    }

    public Builder<DTO, E> defaultStyle(CellStyle style) {
        this.defaultStyle = style;
        return this;
    }

    public ExcelWriterHandler<DTO, E> build(Class<E> enumClass) {
        if (!enumClass.isEnum()) {
            throw new IllegalArgumentException("CSVHandler only handle enum");
        }
        this.enumClass = enumClass;
        return new ExcelWriterHandler<>(this);
    }
}
