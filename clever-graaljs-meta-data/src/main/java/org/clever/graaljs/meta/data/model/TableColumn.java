package org.clever.graaljs.meta.data.model;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-6-20 14:37 <br/>
 */
@Data
public class TableColumn implements Serializable {
    /**
     * 数据库名称
     */
    private String schemaName;
    /**
     * 表名称
     */
    private String tableName;
    /**
     * 序号位置
     */
    private Integer ordinalPosition;
    /**
     * 列名称
     */
    private String columnName;
    /**
     * 数据类型
     */
    private String dataType;
    /**
     * 字符串最大长度，数字精度
     */
    private Integer size;
    /**
     * 字段精度表示
     */
    private String width;
    /**
     * 小数位数
     */
    private Integer decimalDigits;
    /**
     * 是否不能为空
     */
    private Boolean notNull;
    /**
     * 是否自增长
     */
    private Boolean autoIncrement;
    /**
     * 默认值
     */
    private String defaultValue;
    /**
     * 列注释
     */
    private String comment;
    /**
     * 所谓Generated Column，就是数据库中这一列由其他列计算而得
     */
    private Boolean generated;
    /**
     * 是否是隐藏的列
     */
    private Boolean hidden;
    /**
     * 是否是外键
     */
    private Boolean partOfForeignKey;
    /**
     * 是否建了索引
     */
    private Boolean partOfIndex;
    /**
     * 是否是主键
     */
    private Boolean partOfPrimaryKey;
    /**
     * 是否唯一约束
     */
    private Boolean partOfUniqueIndex;
    /**
     * 字段映射的Java类型
     */
    private Class<?> mappedClass;
    /**
     * 其他列属性
     */
    private final Map<String, Object> attributes = new HashMap<>();
}
