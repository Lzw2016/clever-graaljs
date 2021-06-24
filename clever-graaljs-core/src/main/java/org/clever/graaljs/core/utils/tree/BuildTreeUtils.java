package org.clever.graaljs.core.utils.tree;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * 构建对象树结构的工具类<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-8 22:05 <br/>
 */
@Slf4j
public class BuildTreeUtils {
    /**
     * 构建树结构，可能有多棵树<br/>
     *
     * @param nodes 所有要构建树的节点
     * @return 构建的所有树的根节点
     */
    public static <T extends ITreeNode> List<T> buildTree(Collection<T> nodes) {
        log.debug("开始构建树结构...");
        final long startTime = System.currentTimeMillis();
        long tmpTime = System.currentTimeMillis();
        // 需要构建树的节点，还未构建到树中的节点
        List<T> allTreeNodeList = getCanBuildTreeNodes(nodes);
        log.debug("1 耗时：{}ms", (System.currentTimeMillis() - tmpTime));
        // 清除构建状态
        tmpTime = System.currentTimeMillis();
        clearBuild(allTreeNodeList);
        log.debug("2 耗时：{}ms", (System.currentTimeMillis() - tmpTime));
        // 查找所有根节点
        tmpTime = System.currentTimeMillis();
        List<T> rootNodeList = findRootNode(allTreeNodeList);
        log.debug("3 耗时：{}ms", (System.currentTimeMillis() - tmpTime));
        // 刷新还未构建到树中的节点，减少循环次数
        tmpTime = System.currentTimeMillis();
        List<T> noBuildTreeNodeList = refreshNoBuildNodes(allTreeNodeList);
        log.debug("4 耗时：{}ms", (System.currentTimeMillis() - tmpTime));
        // 循环根节点，构建多棵树
        tmpTime = System.currentTimeMillis();
        buildTree(rootNodeList, noBuildTreeNodeList);
        log.debug("5 耗时：{}ms", (System.currentTimeMillis() - tmpTime));
        // 刷新还未构建到树中的节点，减少循环次数
        noBuildTreeNodeList = refreshNoBuildNodes(noBuildTreeNodeList);
        final long endTime = System.currentTimeMillis();
        // 校验构建是否正确
        if (noBuildTreeNodeList.size() <= 0) {
            log.debug("树构建成功！耗时：{}ms | 数据量：{}", (endTime - startTime), nodes.size());
        } else {
            log.error("树构建失败！耗时：{}ms | [{}]", (endTime - startTime), nodesToString(noBuildTreeNodeList));
        }
        return rootNodeList;
    }

    private static <T extends ITreeNode> String nodesToString(Collection<T> nodes) {
        StringBuilder sb = new StringBuilder();
        for (ITreeNode treeNode : nodes) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(treeNode.getId());
        }
        return sb.toString();
    }

    /**
     * 刷新还未构建到树中的节点<br/>
     *
     * @param noBuildTreeNodeList 还未构建到树中的节点集合
     * @return 刷新后的还未构建到树中的节点集合
     */
    private static <T extends ITreeNode> List<T> refreshNoBuildNodes(List<T> noBuildTreeNodeList) {
        List<T> newNoBuildTreeNodeList = new ArrayList<>();
        for (T node : noBuildTreeNodeList) {
            if (!node.isBuild()) {
                newNoBuildTreeNodeList.add(node);
            }
        }
        return newNoBuildTreeNodeList;
    }

    /**
     * 生成树(一层一层的查找子节点)<br/>
     *
     * @param parentNodeList      父节点集合
     * @param noBuildTreeNodeList 所有未被添加到父节点下的节点
     */
    private static <T extends ITreeNode> void buildTree(List<T> parentNodeList, List<T> noBuildTreeNodeList) {
        while (true) {
            // 下一次遍历的父节点
            List<T> nextParentNodeList = new ArrayList<>();
            for (T childNode : noBuildTreeNodeList) {
                for (T parentNode : parentNodeList) {
                    if (!childNode.isBuild() && Objects.equals(childNode.getParentId(), parentNode.getId())) {
                        // 设置已经被添加到父节点下了
                        childNode.setBuild(true);
                        parentNode.addChildren(childNode);
                        // 下一次遍历的父节点-增加
                        nextParentNodeList.add(childNode);
                    }
                }
            }
            // 没有找到下一级节点
            if (nextParentNodeList.size() <= 0) {
                break;
            }
            // 父节点集合
            parentNodeList = nextParentNodeList;
            // 踢除已经构建好的节点
            noBuildTreeNodeList = refreshNoBuildNodes(noBuildTreeNodeList);
            // 没有未构建的节点了
            if (noBuildTreeNodeList.size() <= 0) {
                break;
            }
        }
    }

    /**
     * 过滤节点对象，排除不能构建树的节点，不能构建树的节点满足以下条件：<br/>
     * 1.节点对象为null (node == null)<br/>
     * 2.节点ID为null (node.getId() == null)<br/>
     *
     * @param nodes 所有要构建树的节点
     * @return 所有可以构建树的节点，即节点数据验证通过的节点
     */
    private static <T extends ITreeNode> List<T> getCanBuildTreeNodes(Collection<T> nodes) {
        List<T> treeNodeList = new ArrayList<>();
        nodes.forEach(node -> {
            if (node != null && node.getId() != null) {
                treeNodeList.add(node);
            }
        });
        return treeNodeList;
    }

    /**
     * 清除节点的构建状态，以用于重新构建树<br/>
     *
     * @param noBuildTreeNodeList 所有要构建树的节点
     */
    private static <T extends ITreeNode> void clearBuild(List<T> noBuildTreeNodeList) {
        for (T node : noBuildTreeNodeList) {
            node.setBuild(false);
        }
    }

    /**
     * 在节点中查找所有根节点，根节点满足以下条件：<br/>
     * 1.节点的父节点ID等于 null 或 空字符串<br/>
     * 2.在节点集合中找不到某个节点的父节点，那么这个节点就是根节点<br/>
     *
     * @param noBuildTreeNodeList 所有要构建树的节点
     * @return 所有根节点
     */
    private static <T extends ITreeNode> List<T> findRootNode(List<T> noBuildTreeNodeList) {
        // 所有根节点
        List<T> rootNodeList = new ArrayList<>();
        for (T root : noBuildTreeNodeList) {
            // 节点的父节点ID等于-1
            if (root.getParentId() == null || StringUtils.isBlank(String.valueOf(root.getParentId()))) {
                rootNodeList.add(root);
                root.setBuild(true);
                continue;
            }
            Boolean isRoot = root.isRoot();
            if (Objects.equals(isRoot, true)) {
                rootNodeList.add(root);
                root.setBuild(true);
                continue;
            }
            if (Objects.equals(isRoot, false)) {
                continue;
            }
            // 在节点集合中找不到某个节点的父节点，那么这个节点就是根节点
            if (noBuildTreeNodeList.stream().noneMatch(children -> !root.equals(children) && Objects.equals(root.getParentId(), children.getId()))) {
                rootNodeList.add(root);
                root.setBuild(true);
            }
        }
        return rootNodeList;
    }

    /**
     * 查找指定节点
     *
     * @param treeList 已经构建好的树
     * @param id       查找的节点ID
     * @param <T>      节点数据类型
     * @return 如未找到返回null
     */
    @SuppressWarnings("unchecked")
    public static <T extends ITreeNode> T findNode(List<T> treeList, Long id) {
        List<T> currentLevel = treeList;
        List<T> nextLevel;
        T match = null;
        while (currentLevel != null && !currentLevel.isEmpty()) {
            nextLevel = new ArrayList<>();
            for (T treeNode : currentLevel) {
                // id 一致找到了
                if (Objects.equals(id, treeNode.getId())) {
                    match = treeNode;
                    break;
                }
                // 加入下一层节点
                if (treeNode.getChildren() != null && !treeNode.getChildren().isEmpty()) {
                    nextLevel.addAll((List<T>) treeNode.getChildren());
                }
            }
            if (match != null) {
                break;
            }
            currentLevel = nextLevel;
        }
        return match;
    }

    /**
     * 平铺树
     *
     * @param treeList 已经构建好的树
     * @param <T>      节点数据类型
     * @return 包含所有节点的集合
     */
    @SuppressWarnings("unchecked")
    public static <T extends ITreeNode> List<T> flattenTree(List<T> treeList) {
        List<T> currentLevel = treeList;
        List<T> nextLevel;
        List<T> flattenNode = new ArrayList<>();
        while (currentLevel != null && !currentLevel.isEmpty()) {
            nextLevel = new ArrayList<>();
            flattenNode.addAll(currentLevel);
            for (T treeNode : currentLevel) {
                if (treeNode.getChildren() != null && !treeNode.getChildren().isEmpty()) {
                    nextLevel.addAll((List<T>) treeNode.getChildren());
                }
            }
            currentLevel = nextLevel;
        }
        return flattenNode;
    }

    /**
     * 平铺树节点
     *
     * @param node 已经构建好的树的一个节点
     * @param <T>  节点数据类型
     * @return 包含所有节点的集合
     */
    public static <T extends ITreeNode> List<T> flattenNode(T node) {
        return flattenTree(Collections.singletonList(node));
    }
}
