package org.clever.graaljs.core.utils.tree;

import java.io.Serializable;
import java.util.List;

/**
 * 树节点接口，如果对象需要构建成一个树结构，则需要实现此接口<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-8 22:07 <br/>
 *
 * @see org.clever.graaljs.core.utils.tree.BuildTreeUtils
 */
public interface ITreeNode extends Serializable {

    /**
     * 节点ID(必须非空)
     */
    Object getId();

    /**
     * 父节点ID(根节点的ParentId等于null 或 空字符串)
     */
    Object getParentId();

    /**
     * 判断当前节点是否被构建到树中了
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean isBuild();

    /**
     * 设置当前节点是否构建到树中
     */
    void setBuild(boolean isBuild);

    /**
     * 返回所有子节点，必须是List否则顺序会不一致
     */
    List<? extends ITreeNode> getChildren();

    /**
     * 增加子节点
     *
     * @param node 子节点
     */
    void addChildren(ITreeNode node);

    /**
     * 当前节点是否是根节点
     *
     * @return true:是根节点 false:不是根节点 null:未知
     */
    default Boolean isRoot() {
        return null;
    }
}
