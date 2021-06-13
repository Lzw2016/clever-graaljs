package org.clever.graaljs.data.common.model.request;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 排序查询基础类
 * <p>
 * 作者：lzw <br/>
 * 创建时间：2017-09-03 22:15 <br/>
 */
public class QueryBySort implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String ASC = "ASC";
    public static final String DESC = "DESC";

    /**
     * 排序字段(单字段排序-低优先级)
     */
    @Setter
    @Getter
    private String orderField;
    /**
     * 排序类型ASC DESC(单字段排序-低优先级)
     */
    @Setter
    @Getter
    private String sort;

    /**
     * 排序字段集合
     */
    @Setter
    private List<String> orderFields = new ArrayList<>(1);
    /**
     * 排序类型 ASC DESC
     */
    @Setter
    private List<String> sorts = new ArrayList<>(1);

    /**
     * 排序字段 映射Map
     */
    private Map<String, String> fieldsMapping = new HashMap<>(1);

    /**
     * 排序字段集合
     */
    public List<String> getOrderFields() {
        if (orderFields == null) {
            orderFields = new ArrayList<>(1);
        }
        if (orderFields.size() <= 0 && StringUtils.isNotBlank(orderField)) {
            orderFields.add(orderField);
        }
        return orderFields;
    }

    /**
     * 排序类型
     */
    public List<String> getSorts() {
        if (sorts == null) {
            sorts = new ArrayList<>(1);
        }
        if (StringUtils.isNotBlank(orderField) && StringUtils.isBlank(sort)) {
            sort = ASC;
        }
        if (sorts.size() <= 0 && StringUtils.isNotBlank(sort)) {
            sorts.add(sort);
        }
        orderFields = getOrderFields();
        List<String> sortsTmp = new ArrayList<>();
        for (int index = 0; index < orderFields.size(); index++) {
            if (sorts.size() > index) {
                String s = StringUtils.trim(sorts.get(index));
                if (!DESC.equalsIgnoreCase(s) && !ASC.equalsIgnoreCase(s)) {
                    s = ASC;
                }
                sortsTmp.add(s);
                continue;
            }
            sortsTmp.add(ASC);
        }
        sorts = sortsTmp;
        return sorts;
    }

    /**
     * 手动添加排序字段
     *
     * @param fieldParam 前端参数
     * @param sort       排序类型 ASC DESC
     */
    public QueryBySort addOrderField(String fieldParam, String sort) {
        if (orderFields == null) {
            orderFields = new ArrayList<>(1);
        }
        if (sorts == null) {
            sorts = new ArrayList<>(1);
        }
        if (StringUtils.isNotBlank(fieldParam)) {
            orderFields.add(StringUtils.trim(fieldParam));
            sorts.add(StringUtils.isNotBlank(sort) ? StringUtils.trim(sort) : ASC);
        }
        return this;
    }

    /**
     * 排序字段与前端参数映射
     *
     * @param fieldParam 前端参数
     * @param fieldSql   排序字段
     */
    public QueryBySort addOrderFieldMapping(String fieldParam, String fieldSql) {
        if (fieldsMapping == null) {
            fieldsMapping = new HashMap<>(1);
        }
        if (StringUtils.isNotBlank(fieldParam) && StringUtils.isNotBlank(fieldSql)) {
            fieldsMapping.put(StringUtils.trim(fieldParam), StringUtils.trim(fieldSql));
        }
        return this;
    }

    public List<String> getOrderFieldsSql() {
        List<String> orderFieldsSql = new ArrayList<>();
        List<String> orderFieldsTmp = getOrderFields();
        for (String fieldParam : orderFieldsTmp) {
            String fieldSql = fieldsMapping.get(StringUtils.trim(fieldParam));
            if (StringUtils.isNotBlank(fieldSql)) {
                orderFieldsSql.add(StringUtils.trim(fieldSql));
            }
        }
        return orderFieldsSql;
    }

    public List<String> getSortsSql() {
        List<String> sortsSql = new ArrayList<>();
        List<String> orderFieldsTmp = getOrderFields();
        List<String> sortsTmp = getSorts();
        for (int index = 0; index < orderFieldsTmp.size(); index++) {
            String fieldParam = orderFieldsTmp.get(index);
            String fieldSql = fieldsMapping.get(StringUtils.trim(fieldParam));
            if (StringUtils.isNotBlank(fieldSql)) {
                if (index < sortsTmp.size()) {
                    sortsSql.add(sortsTmp.get(index));
                } else {
                    sortsSql.add(ASC);
                }
            }
        }
        return sortsSql;
    }
}
