/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/28 22:33 <br/>
 */
interface IDGenerateUtils extends JObject {
    /**
     * 封装JDK自带的UUID<br/>
     * 例如：57d7058d-bc79-444d-b7e5-7a5d0b955cc8<br/>
     */
    uuid(): JString;

    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间无"-"分割.<br/>
     * 例如：57d7058dbc79444db7e57a5d0b955cc8<br/>
     */
    uuidNotSplit(): JString;

    /**
     * 以62进制（字母加数字）生成19位UUID，最短的UUID
     */
    shortUuid(): JString;

    /**
     * 创建MongoDB ID生成策略实现<br>
     * ObjectId由以下几部分组成：
     *
     * <pre>
     * 1. Time 时间戳。
     * 2. Machine 所在主机的唯一标识符，一般是机器主机名的散列值。
     * 3. PID 进程ID。确保同一机器中不冲突
     * 4. INC 自增计数器。确保同一秒内产生objectId的唯一性。
     * </pre>
     * <p>
     * 参考：http://blog.csdn.net/qxc1281/article/details/54021882
     *
     * @return ObjectId
     */
    objectId(): JString;

    /**
     * 雪花算法ID(“数据中心ID”和“机器号ID”都是0)
     */
    snowFlakeId(): JLong;

    /**
     * @param dataCenterId 数据中心ID
     * @param machineId    机器号ID
     */
    getSnowFlake(dataCenterId: JLong, machineId: JLong): SnowFlake;
}

declare const IDGenerateUtils: IDGenerateUtils;
