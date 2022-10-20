package com.xq.xtool.excel.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description:
 *
 * @author 13797
 * @version v0.0.1
 * 2021/12/6 21:44
 */
public class ExcelWriterHandler<DTO, E extends Excel.Writer<DTO>> {
    private final E[] enumConstants;
    private final Builder<DTO, E> builder;

    public ExcelWriterHandler(Builder<DTO, E> builder) {
        enumConstants = builder.enumClass.getEnumConstants();
        this.builder = builder;
    }


    public static <DTO, E extends Excel.Writer<DTO>> Builder<DTO, E> builder() {
        return new Builder<>();
    }

    public void handle(List<DTO> dtoList) {
        Workbook workbook = builder.xlsx ? new XSSFWorkbook() : new HSSFWorkbook();

        List<String> headers = Arrays.stream(enumConstants).map(Excel.Writer::getHeader).collect(Collectors.toList());
        Sheet sheet = workbook.createSheet();
        Row row = sheet.createRow(0);

        for (int i = 0; i < headers.size(); i++) {
            Cell cell = row.createCell(i, CellType.STRING);
            cell.setCellValue(headers.get(i));
            cell.setCellStyle(builder.headerStyle);
        }

        List<List<String>> rows = Arrays.stream(enumConstants)
                .map(e -> dtoList.stream().map(dto -> e.getValue().apply(dto)).collect(Collectors.toList()))
                .collect(Collectors.toList());


    }


}
