package com.xq.xtool.excel.csv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description: csv处理类
 *
 * @author 13797
 * @version v0.0.1
 * 2021/11/11 22:40
 */
public class CSVHandler<DTO, E extends CSVField<DTO>> {

    /**
     * 是否启用在每个字段的前后增加特殊字符处理，及：value 前后增加 "
     * 即使不启用，在 value 中有特殊字符（",）时也会在 value 前后增加 "
     */
    public static boolean enable_default_special = false;

    private final E[] enumConstants;

    public CSVHandler(Class<E> enumClass) {
        if (!enumClass.isEnum()) {
            throw new IllegalArgumentException("CSVHandler only handle enum");
        }
        this.enumConstants = enumClass.getEnumConstants();
    }

    public static <DTO, E extends CSVField<DTO>> String handle(Class<E> enumClass, List<DTO> data) {
        // 创建处理类
        CSVHandler<DTO, E> handler = new CSVHandler<>(enumClass);

        List<String> lines = new ArrayList<>();
        // 获取表头
        lines.add(String.join(",", handler.getNames()));
        // 将DTO转为每行的数据
        data.stream().map(handler::getValues)
                .map(line -> String.join(",", line))
                .forEachOrdered(lines::add);

        return String.join(System.lineSeparator(), lines);
    }

    public List<String> getNames() {
        return Arrays.stream(enumConstants)
                .sorted()
                .map(CSVField::getName)
                .map(this::specialCharacters)
                .collect(Collectors.toList());
    }

    public List<String> getValues(DTO dto) {
        return Arrays.stream(enumConstants)
                .sorted()
                .map(item -> item.getValue().apply(dto))
                .map(this::specialCharacters)
                .collect(Collectors.toList());
    }

    /**
     * 处理csv中特殊字符
     * 默认在每个字段前后增加 "
     * 字符中有 ", 等特殊字符时 在 value 前后增加 "
     * " -> ""
     *
     * @param resource 原始字符
     * @return 处理后字符
     */
    private String specialCharacters(String resource) {
        if (resource == null || resource.isEmpty()) {
            return "";
        }
        boolean containsQuotationMarks = resource.contains("\"");
        if (enable_default_special || containsQuotationMarks || resource.contains(",")) {
            if (containsQuotationMarks) {
                resource = resource.replaceAll("\"", "\"\"");
            }
            resource = "\"\t" + resource + "\"";
        }

        return resource;
    }
}
