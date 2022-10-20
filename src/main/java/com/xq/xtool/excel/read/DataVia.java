package com.xq.xtool.excel.read;

import org.apache.poi.hssf.usermodel.HSSFName;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author xiongqiang
 * @version 1.0
 * @projectname xtool
 * @date 2022/10/18 下午3:03
 */
public class DataVia {
    public static void main(String[] args) throws IOException {

        HSSFWorkbook workbook = new HSSFWorkbook(Files.newInputStream(Paths.get("/home/qiang/Downloads/项目-需求批量导入模版.xls")));

        workbook.sheetIterator().forEachRemaining(sheet -> {
            List<? extends DataValidation> dataValidations = sheet.getDataValidations();
            for (DataValidation dataValidation : dataValidations) {
                DataValidationConstraint constraint = dataValidation.getValidationConstraint();

                String formula1 = constraint.getFormula1();

                HSSFName name = workbook.getName(formula1);
                String sheetName = name.getSheetName();
                Set<String> dataList = getDataList(workbook, sheetName);

                CellRangeAddressList addressList = dataValidation.getRegions();
                CellRangeAddress[] cellRangeAddresses = addressList.getCellRangeAddresses();
                for (CellRangeAddress address : cellRangeAddresses) {
                    int firstColumn = address.getFirstColumn();
                    int startRowNum = 2;
                    int lastRowNum = sheet.getLastRowNum();
                    for (int i = startRowNum; i <= lastRowNum; i++) {
                        Cell cell = sheet.getRow(i).getCell(firstColumn);
                        if (cell == null) {
                            continue;
                        }
                        String value = cell.getStringCellValue();

                        if (value != null && !value.isEmpty() && !dataList.contains(value)) {
                            throw new RuntimeException("--------");
                        }
                    }
                }

            }
        });

    }

    private static Set<String> getDataList(HSSFWorkbook workbook, String sheetName) {

        HSSFSheet sheet = workbook.getSheet(sheetName);

        Set<String> list = new HashSet<>();
        sheet.rowIterator().forEachRemaining(row -> {
            short firstCellNum = row.getFirstCellNum();
            String value = row.getCell(firstCellNum).getStringCellValue();
            list.add(value);
        });
        return list;
    }
}
