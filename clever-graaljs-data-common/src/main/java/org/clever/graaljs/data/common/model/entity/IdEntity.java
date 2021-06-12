package org.clever.graaljs.data.common.model.entity;

import lombok.Data;

/**
 * 含有基本字段和主键字段的实体类抽象<br/>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-12 9:25 <br/>
 */
@Data
public abstract class IdEntity implements BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号，Entity主键 ，使用统一的主键生成策略
     */
    protected Long id;
}
