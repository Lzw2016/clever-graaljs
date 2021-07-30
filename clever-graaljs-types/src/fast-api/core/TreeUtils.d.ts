/**
 * 树节点绑定数据的类型
 */
type NodeDataType = JChar | JString | JInt | JLong | JFloat | JDouble | JBigDecimal | JBoolean | JDate | JSqlDate | JSqlTime | JSqlTimestamp | Date | number | string | boolean | null | undefined;

/**
 * 节点ID为String类型的树节点(层级字符串) <br />
 * 根节点的 parentId 为空字符串<br />
 */
interface StringTreeNode {
    /** 节点ID */
    id: JString;
    /** 父节点ID，根节点的父节点ID为空字符串 */
    parentId?: JString;

    /** 树节点绑定的节点数据 */
    [key: string]: NodeDataType;
}

/**
 * 节点ID为Long类型的树节点 <br />
 * 根节点的 parentId <= -1 <br />
 */
interface LongTreeNode {
    /** 节点ID */
    id: JLong;
    /** 父节点ID，根节点的父节点ID <= -1 */
    parentId: JLong;

    /** 树节点绑定的节点数据 */
    [key: string]: NodeDataType;
}

interface TreeNodeRes<IDT> {
    /** 节点ID */
    getId(): IDT;

    /** 父节点ID */
    getParentId(): IDT;

    /** 判断当前节点是否被构建到树中了 */
    isBuild(): JBoolean;

    /** 当前节点是否是根节点 */
    isRoot(): JBoolean;

    /** 返回所有子节点 */
    getChildren(): JList<TreeNodeRes<IDT>>;

    /** 绑定到节点的对象 */
    getAttributes(): JMap<JString, NodeDataType>;
}

interface StringTreeNodeRes extends TreeNodeRes<JString> {
    /** 返回所有子节点 */
    getChildren(): JList<StringTreeNodeRes>;
}

interface LongTreeNodeRes extends TreeNodeRes<JLong> {
    /** 返回所有子节点 */
    getChildren(): JList<LongTreeNodeRes>;
}

interface TreeUtils {
    // 层级字符串
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 判断层级串是否合法<br/>
     *
     * @param levelString 层级串
     * @return 层级串是否合法返回True
     */
    isLevelString(levelString: JString): JBoolean;

    /**
     * 获取当前层级串的下一个层级串，如“001003005”的下一个层级串是“001003006”<br/>
     * 1.若当前层级串已是当前层级的最大层级串，如“001003FFF”，则会抛出异常<br/>
     *
     * @param levelLength 层级长度
     * @param levelString 层级串
     * @return 下一个层级串
     */
    nextLevelString(levelLength: JInt, levelString: JString): JString;

    /**
     * 得到根层级串，如：“000”、“0000”、“000000”<br/>
     *
     * @param levelLength 层级长度
     * @return 根层级串
     */
    rootLevelString(levelLength: JInt): JString;

    // 构建树结构
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 构建树结构，可能有多棵树<br/>
     *
     * @param nodes 所有要构建树的节点
     * @return 构建的所有树的根节点
     */
    buildStringTree<T extends StringTreeNodeRes = StringTreeNodeRes>(nodes: JCollection<StringTreeNode>): JList<T>;

    /**
     * 构建树结构，可能有多棵树<br/>
     *
     * @param nodes 所有要构建树的节点
     * @return 构建的所有树的根节点
     */
    buildLongTree<T extends LongTreeNodeRes = LongTreeNodeRes>(nodes: JCollection<LongTreeNode>): JList<T>;
}

declare const TreeUtils: TreeUtils;

// /**
//  * 根节点的“父节点ID”值
//  */
// declare const RootTreeNode = {
//     /** String类型 - 根节点的“父节点ID”值 */
//     StringParentId: "",
//     /** Long类型 - 根节点的“父节点ID”值 */
//     LongParentId: Interop.asJLong("-1"),
// }
