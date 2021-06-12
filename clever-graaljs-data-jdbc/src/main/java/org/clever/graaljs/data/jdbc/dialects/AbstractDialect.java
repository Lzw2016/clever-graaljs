package org.clever.graaljs.data.jdbc.dialects;

import java.util.Map;

/**
 * 作者： lzw<br/>
 * 创建时间：2019-10-03 12:29 <br/>
 */
public abstract class AbstractDialect implements IDialect {

    private String getParamName(String prefix, Map<String, ?> paramMap) {
        String paramName = null;
        boolean flag = false;
        for (long i = 0; i <= 99999999L; i++) {
            if (i <= 0) {
                paramName = prefix;
            } else {
                paramName = prefix + "_" + Long.toHexString(i);
            }
            if (!paramMap.containsKey(paramName)) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            throw new RuntimeException("生成分页查询参数失败");
        }
        return paramName;
    }

    @Override
    public String buildPaginationSql(String originalSql, long offset, long limit, Map<String, Object> paramMap) {
        String firstMark = getParamName(FIRST_MARK, paramMap);
        paramMap.put(firstMark, offset);
        String secondMark = getParamName(SECOND_MARK, paramMap);
        paramMap.put(secondMark, limit);
        return doBuildPaginationSql(originalSql, offset, limit, paramMap, firstMark, secondMark);
    }

    /**
     * 组装分页语句
     *
     * @param originalSql 原始语句
     * @param offset      偏移量
     * @param limit       界限
     * @param paramMap    Sql参数
     * @param firstMark   分页查询第一个参数名(offset)
     * @param secondMark  分页查询第二个参数名(limit)
     */
    public abstract String doBuildPaginationSql(String originalSql, long offset, long limit, Map<String, Object> paramMap, String firstMark, String secondMark);
}
