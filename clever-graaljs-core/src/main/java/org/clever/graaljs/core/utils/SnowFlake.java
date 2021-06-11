package org.clever.graaljs.core.utils;

/**
 * twitter的snowflake算法 -- java实现
 * 雪花算法简单描述：
 * 最高位是符号位，始终为0，不可用。
 * 41位的时间序列，精确到毫秒级，41位的长度可以使用69年。时间位还有一个很重要的作用是可以根据时间进行排序。
 * 10位的机器标识，10位的长度最多支持部署1024个节点。
 * 12位的计数序列号，序列号即一系列的自增id，可以支持同一节点同一毫秒生成多个ID序号，12位的计数序列号支持每个节点每毫秒产生4096个ID序号。
 */
public class SnowFlake {

    /**
     * SnowFlake 全局单例，“数据中心ID”和“机器号ID”都是0
     */
    public static final SnowFlake SNOW_FLAKE = new SnowFlake(0L, 0L);

    /**
     * 起始的时间戳
     */
    private final static long START_STAMP = 1480166465631L;

    /**
     * 每一部分占用的位数
     * 10位的机器标识 ( 机器标识占用的位数 + 数据中心占用的位数)
     * 12位的计数序列号
     */
    private final static long DATA_CENTER_BIT = 5; //数据中心占用的位数
    private final static long MACHINE_BIT = 5;     //机器标识占用的位数
    private final static long SEQUENCE_BIT = 12;   //序列号占用的位数

    /**
     * 每一部分的最大值
     */
    private final static long MAX_DATA_CENTER_NUM = ~(-1L << DATA_CENTER_BIT);
    private final static long MAX_MACHINE_NUM = ~(-1L << MACHINE_BIT);
    private final static long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);

    /**
     * 每一部分向左的位移
     */
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    private final static long DATA_CENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private final static long TIMESTAMP_LEFT = DATA_CENTER_LEFT + DATA_CENTER_BIT;

    private final long dataCenterId;    //数据中心
    private final long machineId;       //机器标识
    private long sequence = 0L;         //序列号
    private long lastStamp = -1L;       //上一次时间戳

    /**
     * @param dataCenterId 数据中心ID
     * @param machineId    机器号ID
     */
    public SnowFlake(long dataCenterId, long machineId) {
        if (dataCenterId > MAX_DATA_CENTER_NUM || dataCenterId < 0) {
            throw new IllegalArgumentException("dataCenterId can't be greater than MAX_DATA_CENTER_NUM or less than 0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
        }
        this.dataCenterId = dataCenterId;
        this.machineId = machineId;
    }

    /**
     * 产生下一个ID
     */
    public synchronized long nextId() {
        long currStamp = getNewStamp();
        if (currStamp < lastStamp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }

        if (currStamp == lastStamp) {
            //相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                currStamp = getNextMill();
            }
        } else {
            //不同毫秒内，序列号置为0
            sequence = 0L;
        }

        lastStamp = currStamp;

        return (currStamp - START_STAMP) << TIMESTAMP_LEFT //时间戳部分
                | dataCenterId << DATA_CENTER_LEFT         //数据中心部分
                | machineId << MACHINE_LEFT                //机器标识部分
                | sequence;                                //序列号部分
    }

    private long getNextMill() {
        long mill = getNewStamp();
        while (mill <= lastStamp) {
            mill = getNewStamp();
        }
        return mill;
    }

    private long getNewStamp() {
        return System.currentTimeMillis();
    }
}