package com.xq.xtool.excel.csv;

import java.util.function.Function;

/**
 * Description:
 *
 * @author 13797
 * @version v0.0.1
 * 2021/11/11 22:41
 */
public interface CSVField<DTO> {

    /**
     * 对应唯一键
     *
     * @return  唯一键
     */
    String getDict();

    /**
     * 对应表头
     *
     * @return  表头
     */
    String getName();

    /**
     * 获取单元格的 value
     *
     * @return  通过DTO获取对应 单元格的函数
     */
    Function<DTO, String> getValue();
}
