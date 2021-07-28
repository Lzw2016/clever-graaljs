/**
 * 查询分页参数
 */
interface QueryByPage extends Partial<QueryBySort> {
    /**
     * 每页的数据量(1 <= pageSize <= 100)
     */
    pageSize?: JInt;
    /**
     * 当前页面的页码数(pageNo >= 1)
     */
    pageNo?: JInt;
    /**
     * 是否进行 count 查询
     */
    isSearchCount?: JBoolean;

    /** 查询参数 */
    [name: string]: any;
}
