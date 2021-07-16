package org.clever.graaljs.spring.mvc.builtin.constant;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/07/16 17:51 <br/>
 */
public interface SortTypeEnum {
    /**
     * 排序类型
     */
    final class SortType {
        public static final SortType Instance = new SortType();

        private SortType() {
        }

        /**
         * 由小到大排序
         */
        public final String ASC = "ASC";
        /**
         * 由大到小排序
         */
        public final String DESC = "DESC";
    }
}
