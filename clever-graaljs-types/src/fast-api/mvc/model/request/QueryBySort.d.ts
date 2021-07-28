/**
 * 排序类型
 */
enum SortType {
    /**
     * 由小到大排序
     */
    ASC = "ASC",
    /**
     * 由大到小排序
     */
    DESC = "DESC",
}

/**
 * 查询排序参数
 */
interface QueryBySort {
    /**
     * 排序字段(单字段排序-低优先级)
     */
    orderField?: JString;
    /**
     * 排序类型ASC DESC(单字段排序-低优先级)
     */
    sort?: SortType;

    /**
     * 排序字段集合(多字段排序-高优先级)
     */
    orderFields?: Array<JString>;
    /**
     * 排序类型 ASC DESC(多字段排序-高优先级)
     */
    sorts?: Array<SortType>;

    /**
     * 排序字段 映射Map
     */
    fieldsMapping: { [name: string]: JString };
}
