package org.clever.graaljs.core.utils.tree;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Tree节点数据
 *
 * @author LiZhiWei
 * @version 2015年6月4日 下午9:51:39
 */
@JsonInclude(Include.NON_NULL)
@Data
public class SimpleTreeNode<T extends Serializable> implements ITreeNode {
    private static final long serialVersionUID = 1L;
    /**
     * 节点标识
     */
    private Long id;
    /**
     * 父级编号
     */
    private Long parentId;
    /**
     * 是否被添加到父节点下
     */
    private boolean isBuild = false;
    /**
     * 子节点
     */
    private List<ITreeNode> children;

    /**
     * 绑定到节点的对象
     */
    @JsonUnwrapped
    private T attributes;

    public SimpleTreeNode() {
    }

    /**
     * @param parentId 父级编号
     * @param id       节点标识
     */
    public SimpleTreeNode(Long parentId, Long id) {
        this.parentId = parentId;
        this.id = id;
    }

    /**
     * @param parentId   父级编号
     * @param id         节点标识
     * @param attributes 被添加到节点的自定义属性
     */
    public SimpleTreeNode(Long parentId, Long id, T attributes) {
        this.parentId = parentId;
        this.id = id;
        this.attributes = attributes;
    }

    /**
     * @param parentId   父级编号
     * @param id         节点标识
     * @param attributes 被添加到节点的自定义属性
     * @param children   子节点
     */
    public SimpleTreeNode(Long parentId, Long id, T attributes, Collection<SimpleTreeNode<?>> children) {
        this.parentId = parentId;
        this.id = id;
        this.attributes = attributes;
        this.addChildren(children);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public Long getParentId() {
        return parentId;
    }

    @Override
    public boolean isBuild() {
        return isBuild;
    }

    @Override
    public void setBuild(boolean isBuild) {
        this.isBuild = isBuild;
    }

    @Override
    public List<? extends ITreeNode> getChildren() {
        return children;
    }

    @Override
    public void addChildren(ITreeNode node) {
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
        this.children.add(node);
    }

    /**
     * 增加子节点<br>
     */
    public SimpleTreeNode<?> addChildren(SimpleTreeNode<?> node) {
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
        this.children.add(node);
        return this;
    }

    /**
     * 增加子节点集合<br>
     */
    @SuppressWarnings("UnusedReturnValue")
    public SimpleTreeNode<?> addChildren(Collection<SimpleTreeNode<?>> nodes) {
        if (nodes == null || nodes.isEmpty()) {
            return this;
        }
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
        this.children.addAll(nodes);
        return this;
    }
}
