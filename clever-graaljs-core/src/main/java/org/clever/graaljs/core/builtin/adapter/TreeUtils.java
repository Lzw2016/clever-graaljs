package org.clever.graaljs.core.builtin.adapter;

import org.clever.graaljs.core.utils.tree.BuildTreeUtils;
import org.clever.graaljs.core.utils.tree.ITreeNode;
import org.clever.graaljs.core.utils.tree.LevelStringUtils;

import java.util.Collection;
import java.util.List;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/28 22:33 <br/>
 */
public class TreeUtils {
    public static final TreeUtils Instance = new TreeUtils();

    private TreeUtils() {
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
        return LevelStringUtils.isLevelString(levelString);
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
        return LevelStringUtils.nextLevelString(levelLength, levelString);
    }

    /**
     * 得到根层级串，如：“000”、“0000”、“000000”<br/>
     *
     * @param levelLength 层级长度
     * @return 根层级串
     */
    public String rootLevelString(int levelLength) {
        return LevelStringUtils.rootLevelString(levelLength);
    }

    // 构建树结构
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 构建树结构，可能有多棵树<br/>
     *
     * @param nodes 所有要构建树的节点
     * @return 构建的所有树的根节点
     */
    public <T extends ITreeNode> List<T> buildTree(Collection<T> nodes) {
        return BuildTreeUtils.buildTree(nodes);
    }
}
