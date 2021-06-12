package org.clever.graaljs.data.jdbc.support;

import lombok.Getter;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/10 08:32 <br/>
 */
@Getter
public class InsertResult implements Serializable {
    /**
     * 新增数据量
     */
    private final int insertCount;
    /**
     * Insert时，数据库自动生成的key
     */
    private final KeyHolder keyHolder;
    /**
     * 当更新数据只有一个自动生成的key时，才会有这个字段，其值就是自动生成的key的值<br />
     * 一般是自增长主键值
     */
    private final Object keyHolderValue;

    public InsertResult(int insertCount, KeyHolder keyHolder) {
        this.insertCount = insertCount;
        this.keyHolder = keyHolder;
        this.keyHolderValue = keyHolder.key;
    }

    /**
     * Insert时，数据库自动生成的key
     */
    @Getter
    public static class KeyHolder implements Serializable {
        /**
         * 所有自动生成的key
         */
        private final List<Map<String, Object>> keysList;
        /**
         * 当keysList只有一个元素时，才有这个值，值就是那个元素
         */
        private final Map<String, Object> keys;
        /**
         * 当keys只有一个元素时，才有这个值，值就是那个元素的value <br />
         * 一般是自增长主键值
         */
        private final Object key;

        public KeyHolder(List<Map<String, Object>> keysList) {
            this.keysList = keysList;
            if (keysList.size() == 1) {
                this.keys = keysList.get(0);
                if (this.keys.size() == 1) {
                    Iterator<Object> keyIter = this.keys.values().iterator();
                    if (keyIter.hasNext()) {
                        this.key = keyIter.next();
                    } else {
                        this.key = null;
                    }
                } else {
                    this.key = null;
                }
            } else {
                this.keys = null;
                this.key = null;
            }
        }
    }
}
