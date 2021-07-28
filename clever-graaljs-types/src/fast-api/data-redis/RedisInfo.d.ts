enum NodeType {
    Master = "MASTER",
    Slave = "SLAVE",
}

interface RedisNodeInfo {
    /** 节点ID */
    getId(): JString;

    /** 节点名称 */
    getName(): JString;

    /** 节点IP */
    getHost(): JString;

    /** 端口 */
    getPort(): JInt;

    /** 节点类型 */
    getType(): NodeType;

    /** 主节点ID */
    getMasterId(): JString;
}

interface ClusterInfo {
    /** 最大重定向数 */
    getMaxRedirects(): JInt;

    /** 节点列表 */
    getNodes(): JSet<RedisNodeInfo>;
}

interface SentinelInfo {
    /** 主节点名称 */
    getMaster(): JString;

    /** 数据库 */
    getDatabase(): JInt;

    /** 哨兵节点 */
    getSentinels(): JSet<RedisNodeInfo>;
}

interface SocketInfo {
    /** socket 文件 */
    getSocket(): JString;

    /** 数据库 */
    getDatabase(): JInt;
}

interface StandaloneInfo {
    /** 节点IP */
    getHostName(): JString;

    /** 节点端口 */
    getPort(): JInt;

    /** 数据库 */
    getDatabase(): JInt;
}

interface StaticMasterReplicaInfo {
    /** 节点列表 */
    getNodes(): JList<StandaloneInfo>;

    /** 数据库 */
    getDatabase(): JInt;
}

interface RedisInfo {
    /** 集群配置 */
    getClusterInfo(): ClusterInfo | null;

    /** 哨兵配置 */
    getSentinelInfo(): SentinelInfo | null;

    /** Socket配置 */
    getSocketInfo(): SocketInfo | null;

    /** 单节点配置 */
    getStandaloneInfo(): StandaloneInfo | null;

    /** 固定集群配置 */
    getStaticMasterReplicaInfo(): StaticMasterReplicaInfo | null;
}
