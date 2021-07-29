/**
 * twitter的snowflake算法 -- java实现
 * 雪花算法简单描述：
 * 最高位是符号位，始终为0，不可用。
 * 41位的时间序列，精确到毫秒级，41位的长度可以使用69年。时间位还有一个很重要的作用是可以根据时间进行排序。
 * 10位的机器标识，10位的长度最多支持部署1024个节点。
 * 12位的计数序列号，序列号即一系列的自增id，可以支持同一节点同一毫秒生成多个ID序号，12位的计数序列号支持每个节点每毫秒产生4096个ID序号。
 */
interface SnowFlake extends JObject {
    /**
     * 产生下一个ID
     */
    nextId(): JLong;
}

declare const SnowFlake: SnowFlake;
