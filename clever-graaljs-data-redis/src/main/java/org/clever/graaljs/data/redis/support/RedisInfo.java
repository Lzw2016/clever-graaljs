package org.clever.graaljs.data.redis.support;

import lombok.Data;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/11/12 16:04 <br/>
 */
@Data
public class RedisInfo implements Serializable {
    /**
     * 集群配置
     */
    private ClusterInfo clusterInfo;
    /**
     * 哨兵配置
     */
    private SentinelInfo sentinelInfo;
    /**
     * Socket配置
     */
    private SocketInfo socketInfo;
    /**
     * 单节点配置
     */
    private StandaloneInfo standaloneInfo;
    /**
     * 固定集群配置
     */
    private StaticMasterReplicaInfo staticMasterReplicaInfo;

    @Data
    public static class ClusterInfo implements Serializable {
        /**
         * 最大重定向数
         */
        private Integer maxRedirects;
        /**
         * 节点列表
         */
        private Set<RedisNodeInfo> nodes;
    }

    @Data
    public static class SentinelInfo implements Serializable {
        /**
         * 主节点名称
         */
        private String master;
        /**
         * 数据库
         */
        private int database;
        /**
         * 哨兵节点
         */
        private Set<RedisNodeInfo> sentinels;
    }

    @Data
    public static class SocketInfo implements Serializable {
        /**
         * socket 文件
         */
        private String socket;
        /**
         * 数据库
         */
        private int database;
    }

    @Data
    public static class StandaloneInfo implements Serializable {
        public StandaloneInfo() {
        }

        public StandaloneInfo(RedisStandaloneConfiguration node) {
            hostName = node.getHostName();
            port = node.getPort();
            database = node.getDatabase();
        }

        /**
         * 节点IP
         */
        private String hostName;
        /**
         * 节点端口
         */
        private int port;
        /**
         * 数据库
         */
        private int database;
    }

    @Data
    public static class StaticMasterReplicaInfo implements Serializable {
        /**
         * 节点列表
         */
        private List<StandaloneInfo> nodes = new ArrayList<>();
        /**
         * 数据库
         */
        private int database;
    }

    @Data
    public static class RedisNodeInfo implements Serializable {
        public RedisNodeInfo() {
        }

        public RedisNodeInfo(RedisNode node) {
            id = node.getId();
            name = node.getName();
            host = node.getHost();
            port = node.getPort();
            type = String.valueOf(node.getType());
            masterId = node.getMasterId();
        }

        /**
         * 节点ID
         */
        private String id;
        /**
         * 节点名称
         */
        private String name;
        /**
         * 节点IP
         */
        private String host;
        /**
         * 端口
         */
        private Integer port;
        /**
         * 节点类型
         */
        private String type;
        /**
         * 主节点ID
         */
        private String masterId;
    }
}
