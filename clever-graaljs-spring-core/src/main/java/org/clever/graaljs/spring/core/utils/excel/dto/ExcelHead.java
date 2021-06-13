package org.clever.graaljs.spring.core.utils.excel.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者： lzw<br/>
 * 创建时间：2019-05-13 11:44 <br/>
 */
@Data
public class ExcelHead implements Serializable {
    /**
     * 字段位置
     */
    private final Integer index;
    /**
     * Excel表头名称(允许多级表头)
     */
    private final List<String> heads = new ArrayList<>();
    /**
     * 对应实体类字段名
     */
    private String columnName;

    public ExcelHead(Integer index, String head) {
        this.index = index;
        this.heads.add(head);
    }

    /**
     * 第一级表头
     */
    public String getFirstHead() {
        if (heads.isEmpty()) {
            return null;
        }
        return heads.get(0);
    }

    /**
     * 最后一级表头(建议前端使用此值)
     */
    public String getLastHead() {
        if (heads.isEmpty()) {
            return null;
        }
        return heads.get(heads.size() - 1);
    }
}
