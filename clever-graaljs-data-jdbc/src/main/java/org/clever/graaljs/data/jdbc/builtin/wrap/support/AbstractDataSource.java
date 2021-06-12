package org.clever.graaljs.data.jdbc.builtin.wrap.support;

import org.clever.graaljs.data.common.model.request.QueryByPage;
import org.clever.graaljs.data.common.model.request.QueryBySort;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.Map;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/09/02 21:07 <br/>
 */
public abstract class AbstractDataSource {
    protected static final String Order_Field_Name = "orderField";
    protected static final String Sort_Name = "sort";
    protected static final String Order_Fields_Name = "orderFields";
    protected static final String Sorts_Name = "sorts";
    protected static final String Fields_Mapping_Name = "fieldsMapping";
    protected static final String Page_Size_Name = "pageSize";
    protected static final String Page_No_Name = "pageNo";
    protected static final String Is_Search_Count_Name = "isSearchCount";

    protected QueryByPage getQueryByPage(Map<String, Object> paginationMap) {
        QueryByPage queryByPage = new QueryByPage();
        getQueryBySort(paginationMap, queryByPage);
        Object pageSize = paginationMap.get(Page_Size_Name);
        Object pageNo = paginationMap.get(Page_No_Name);
        Object isSearchCount = paginationMap.get(Is_Search_Count_Name);
        Assert.isTrue(pageSize == null || pageSize instanceof Number, "参数pageSize必须是一个数组");
        Assert.isTrue(pageNo == null || pageNo instanceof Number, "参数pageNo必须是一个数组");
        Assert.isTrue(isSearchCount == null || isSearchCount instanceof Boolean, "参数isSearchCount必须是一个Boolean值");
        if (pageSize != null) {
            queryByPage.setPageSize((Integer) pageSize);
        }
        if (pageNo != null) {
            queryByPage.setPageNo((Integer) pageNo);
        }
        if (isSearchCount != null) {
            queryByPage.setSearchCount((Boolean) isSearchCount);
        }
        return queryByPage;
    }

    protected QueryBySort getQueryBySort(Map<String, Object> sortMap, QueryBySort queryBySort) {
        Object orderField = sortMap.get(Order_Field_Name);
        Object sort = sortMap.get(Sort_Name);
        Object orderFields = sortMap.get(Order_Fields_Name);
        Object sorts = sortMap.get(Sorts_Name);
        Object fieldsMapping = sortMap.get(Fields_Mapping_Name);
        Assert.isTrue(orderField == null || orderField instanceof String, "参数orderField必须是一个字符串");
        Assert.isTrue(sort == null || sort instanceof String, "参数sort必须是一个字符串");
        Assert.isTrue(orderFields == null || orderFields instanceof Object[], "参数orderFields必须是一个字符串数组");
        Assert.isTrue(sorts == null || sorts instanceof Object[], "参数sorts必须是一个字符串数组");
        Assert.isTrue(fieldsMapping == null || fieldsMapping instanceof Map, "参数sorts必须是一个字符串数组");
        // 排序参数转换
        if (queryBySort == null) {
            queryBySort = new QueryBySort();
        }
        queryBySort.setOrderField((String) orderField);
        queryBySort.setSort((String) sort);
        if (orderFields != null) {
            String[] orderFieldsArr = new String[((Object[]) orderFields).length];
            for (int i = 0; i < orderFieldsArr.length; i++) {
                Object item = ((Object[]) orderFields)[i];
                if (item == null) {
                    orderFieldsArr[i] = null;
                } else if (item instanceof String) {
                    orderFieldsArr[i] = (String) item;
                } else {
                    throw new IllegalArgumentException("参数orderFields必须是一个字符串数组");
                }
            }
            queryBySort.setOrderFields(Arrays.asList(orderFieldsArr));
        }
        if (sorts != null) {
            String[] sortsArr = new String[((Object[]) sorts).length];
            for (int i = 0; i < sortsArr.length; i++) {
                Object item = ((Object[]) sorts)[i];
                if (item == null) {
                    sortsArr[i] = null;
                } else if (item instanceof String) {
                    sortsArr[i] = (String) item;
                } else {
                    throw new IllegalArgumentException("参数sorts必须是一个字符串数组");
                }
            }
            queryBySort.setSorts(Arrays.asList(sortsArr));
        }
        if (fieldsMapping != null) {
            Map<?, ?> map = (Map<?, ?>) fieldsMapping;
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                Object key = entry.getKey();
                Object value = entry.getValue();
                queryBySort.addOrderFieldMapping(String.valueOf(key), String.valueOf(value));
            }
        }
        return queryBySort;
    }
}
