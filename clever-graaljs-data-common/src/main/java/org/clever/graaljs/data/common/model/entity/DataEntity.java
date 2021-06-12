package org.clever.graaljs.data.common.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 含有基本字段的实体类抽象<br/>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-12 9:35 <br/>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class DataEntity extends IdEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 创建者
     */
    protected String createBy;

    /**
     * 创建日期
     */
    protected Date createAt;

    /**
     * 更新者
     */
    protected String updateBy;

    /**
     * 更新日期
     */
    protected Date updateAt;
}
