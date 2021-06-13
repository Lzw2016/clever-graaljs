package org.clever.graaljs.spring.core.utils.excel.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.google.common.collect.Lists;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者： lzw<br/>
 * 创建时间：2019-05-13 11:43 <br/>
 */
@Data
public class ExcelRow<T> implements Serializable {
    /**
     * 数据在Excel文件中的行号
     */
    private final int excelRowNum;

    /**
     * 读取的原始数据
     */
    @JsonUnwrapped
    private final T data;

    /**
     * 数据签名
     */
    @JsonIgnore
    private String dataSignature;

    /**
     * 列错误
     */
    private final Map<String, List<String>> columnError = new LinkedHashMap<>();

    /**
     * 行错误
     */
    private final List<String> rowError = new ArrayList<>();

    /**
     * @param data        读取的原始数据
     * @param excelRowNum 数据在Excel文件中的行号
     */
    public ExcelRow(T data, int excelRowNum) {
        this.data = data;
        this.excelRowNum = excelRowNum;
    }

    /**
     * 当前数据是否有解析错误
     */
    public boolean hasError() {
        return columnError.size() > 0 || rowError.size() > 0;
    }

    /**
     * 增加数据列错误
     */
    public void addErrorInColumn(String columnName, String msg) {
        List<String> errors = columnError.computeIfAbsent(columnName, k -> Lists.newArrayList());
        errors.add(msg);
    }

    /**
     * 增加数据行错误
     */
    public void addErrorInRow(String msg) {
        rowError.add(msg);
    }
}
