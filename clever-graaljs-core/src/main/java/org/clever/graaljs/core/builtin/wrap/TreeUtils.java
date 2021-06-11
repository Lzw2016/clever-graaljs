package org.clever.graaljs.core.builtin.wrap;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.clever.graaljs.core.internal.utils.InteropScriptToJavaUtils;
import org.clever.graaljs.core.utils.tree.ITreeNode;

import java.util.*;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/08/16 09:32 <br/>
 */
public class TreeUtils {

    public static final TreeUtils Instance = new TreeUtils();

    private final org.clever.graaljs.core.builtin.adapter.TreeUtils delegate;

    private TreeUtils() {
        delegate = org.clever.graaljs.core.builtin.adapter.TreeUtils.Instance;
    }

    // 层级字符串
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 判断层级串是否合法<br/>
     *
     * @param levelString 层级串
     * @return 层级串是否合法返回True
     */
    public boolean isLevelString(String levelString) {
        return delegate.isLevelString(levelString);
    }

    /**
     * 获取当前层级串的下一个层级串，如“001003005”的下一个层级串是“001003006”<br/>
     * 1.若当前层级串已是当前层级的最大层级串，如“001003FFF”，则会抛出异常<br/>
     *
     * @param levelLength 层级长度
     * @param levelString 层级串
     * @return 下一个层级串
     */
    public String nextLevelString(int levelLength, String levelString) {
        return delegate.nextLevelString(levelLength, levelString);
    }

    /**
     * 得到根层级串，如：“000”、“0000”、“000000”<br/>
     *
     * @param levelLength 层级长度
     * @return 根层级串
     */
    public String rootLevelString(int levelLength) {
        return delegate.rootLevelString(levelLength);
    }

    // 构建树结构
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 构建树结构，可能有多棵树<br/>
     *
     * @param nodes 所有要构建树的节点
     * @return 构建的所有树的根节点
     */
    public List<StringTreeNode> buildStringTree(Collection<Map<String, Object>> nodes) {
        if (nodes == null || nodes.isEmpty()) {
            return Collections.emptyList();
        }
        List<StringTreeNode> list = new ArrayList<>(nodes.size());
        for (Map<String, Object> node : nodes) {
            StringTreeNode stringTreeNode = toStringTreeNode(node);
            if (stringTreeNode == null) {
                continue;
            }
            list.add(stringTreeNode);
        }
        return delegate.buildTree(list);
    }

    /**
     * 构建树结构，可能有多棵树<br/>
     *
     * @param nodes 所有要构建树的节点
     * @return 构建的所有树的根节点
     */
    public List<LongTreeNode> buildLongTree(Collection<Map<String, Object>> nodes) {
        if (nodes == null || nodes.isEmpty()) {
            return Collections.emptyList();
        }
        List<LongTreeNode> list = new ArrayList<>(nodes.size());
        for (Map<String, Object> node : nodes) {
            LongTreeNode longTreeNode = toLongTreeNode(node);
            if (longTreeNode == null) {
                continue;
            }
            list.add(longTreeNode);
        }
        return delegate.buildTree(list);
    }

    //----------------------------------------------------------------------------------------------------------------------------------------------

    private static final String IdName = "id";
    private static final String ParentIdName = "parentId";

    private StringTreeNode toStringTreeNode(Map<String, Object> map) {
        if (map == null) {
            return null;
        }
        Object id = map.get(IdName);
        Object parentId = map.get(ParentIdName);
        StringTreeNode stringTreeNode = new StringTreeNode();
        if (id instanceof String && (parentId == null || parentId instanceof String)) {
            stringTreeNode.setId((String) id);
            if (parentId != null) {
                stringTreeNode.setParentId((String) parentId);
            }
        } else {
            throw new IllegalArgumentException("属性id和parentId值类型必须同是String类型");
        }
        stringTreeNode.setAttributes(getAttributesMap(map));
        return stringTreeNode;
    }

    private LongTreeNode toLongTreeNode(Map<String, Object> map) {
        if (map == null) {
            return null;
        }
        Object id = map.get(IdName);
        Object parentId = map.get(ParentIdName);
        LongTreeNode longTreeNode = new LongTreeNode();
        if (id instanceof Number && (parentId == null || parentId instanceof Number)) {
            longTreeNode.setId(((Number) id).longValue());
            if (parentId != null) {
                longTreeNode.setParentId(((Number) parentId).longValue());
            }
        } else {
            throw new IllegalArgumentException("属性id和parentId值类型必须同是Long类型");
        }
        longTreeNode.setAttributes(getAttributesMap(map));
        return longTreeNode;
    }

    private Map<String, Object> getAttributesMap(Map<String, Object> map) {
        int size = map.size() - 2;
        Map<String, Object> attributes = new HashMap<>(Math.max(size, 0));
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            if (Objects.equals(key, IdName) || Objects.equals(key, ParentIdName)) {
                continue;
            }
            Object value = entry.getValue();
            attributes.put(key, InteropScriptToJavaUtils.Instance.toJavaObject(value));
        }
        return attributes;
    }

    /**
     * 节点ID为String类型的树节点(层级字符串) <br />
     * 根节点的 parentId 为空字符串<br />
     */
    @Data
    public static class StringTreeNode implements ITreeNode {
        /**
         * 节点标识
         */
        private String id;
        /**
         * 父级编号
         */
        private String parentId;
        /**
         * 是否被添加到父节点下
         */
        private boolean isBuild = false;
        /**
         * 子节点
         */
        private List<ITreeNode> children = new ArrayList<>();
        /**
         * 绑定到节点的对象
         */
        //@JsonUnwrapped
        private Map<String, Object> attributes;

        public StringTreeNode() {
        }

        /**
         * @param id         节点标识
         * @param parentId   父级编号(根节点的父节点ID <= -1)
         * @param attributes 是否被添加到父节点下
         */
        public StringTreeNode(String id, String parentId, Map<String, Object> attributes) {
            this.id = id;
            this.parentId = parentId;
            this.attributes = attributes;
        }

        @JsonAnyGetter
        public Map<String, Object> getAttributes() {
            return attributes;
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public String getParentId() {
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
        public List<ITreeNode> getChildren() {
            return children;
        }

        @Override
        public void addChildren(ITreeNode node) {
            if (this.children == null) {
                this.children = new ArrayList<>();
            }
            this.children.add(node);
        }

        @Override
        public Boolean isRoot() {
            return StringUtils.isBlank(parentId);
        }
    }

    /**
     * 节点ID为Long类型的树节点 <br />
     * 根节点的 parentId <= -1 <br />
     */
    @Data
    public static class LongTreeNode implements ITreeNode {
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
        private List<ITreeNode> children = new ArrayList<>();
        /**
         * 绑定到节点的对象
         */
        //@JsonUnwrapped
        private Map<String, Object> attributes;

        public LongTreeNode() {
        }

        /**
         * @param id         节点标识
         * @param parentId   父级编号(根节点的父节点ID <= -1)
         * @param attributes 是否被添加到父节点下
         */
        public LongTreeNode(Long id, Long parentId, Map<String, Object> attributes) {
            this.id = id;
            this.parentId = parentId;
            this.attributes = attributes;
        }

        @JsonAnyGetter
        public Map<String, Object> getAttributes() {
            return attributes;
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
        public List<ITreeNode> getChildren() {
            return children;
        }

        @Override
        public void addChildren(ITreeNode node) {
            if (this.children == null) {
                this.children = new ArrayList<>();
            }
            this.children.add(node);
        }

        @Override
        public Boolean isRoot() {
            return parentId == null || parentId <= -1;
        }
    }
}
