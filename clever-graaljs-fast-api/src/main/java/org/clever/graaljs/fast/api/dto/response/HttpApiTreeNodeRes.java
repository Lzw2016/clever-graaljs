//package org.clever.graaljs.fast.api.dto.response;
//
//import lombok.Data;
//import org.clever.graaljs.core.utils.tree.ITreeNode;
//
//import java.util.List;
//
///**
// * 作者：lizw <br/>
// * 创建时间：2021/06/23 21:52 <br/>
// */
//@Data
//public class HttpApiTreeNodeRes implements ITreeNode {
//    /**
//     * 主键id
//     */
//    private Long httpApiId;
//    /**
//     * 资源文件id
//     */
//    private Long fileResourceId;
//    /**
//     * 命名空间
//     */
//    private String namespace;
//    /**
//     * 文件路径(以"/"结束)
//     */
//    private String path;
//    /**
//     * 文件名称
//     */
//    private String name;
//    /**
//     * 数据类型：0-文件夹，1-文件
//     */
//    private Integer isFile;
//    /**
//     * 读写权限：0-可读可写，1-只读
//     */
//    private Integer readOnly;
//    /**
//     * http请求路径
//     */
//    private String requestMapping;
//    /**
//     * http请求method，ALL GET HEAD POST PUT DELETE CONNECT OPTIONS TRACE PATCH
//     */
//    private String requestMethod;
//    /**
//     * 禁用http请求：0-启用，1-禁用
//     */
//    private Integer disableRequest;
//
//    @Override
//    public Object getId() {
//        return fileResourceId;
//    }
//
//    @Override
//    public Object getParentId() {
//        return null;
//    }
//
//    @Override
//    public boolean isBuild() {
//        return false;
//    }
//
//    @Override
//    public void setBuild(boolean isBuild) {
//
//    }
//
//    @Override
//    public List<? extends ITreeNode> getChildren() {
//        return null;
//    }
//
//    @Override
//    public void addChildren(ITreeNode node) {
//
//    }
//
//    @Override
//    public Boolean isRoot() {
//        return ITreeNode.super.isRoot();
//    }
//}
