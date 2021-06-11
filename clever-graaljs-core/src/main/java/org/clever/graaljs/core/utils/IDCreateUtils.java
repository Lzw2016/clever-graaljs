package org.clever.graaljs.core.utils;

import java.lang.management.ManagementFactory;
import java.net.NetworkInterface;
import java.nio.ByteBuffer;
import java.util.Enumeration;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 封装各种生成唯一性ID算法的工具类<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-8 16:14 <br/>
 */
public class IDCreateUtils {
    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间无"-"分割.<br/>
     * 例如：57d7058dbc79444db7e57a5d0b955cc8<br/>
     */
    public static String uuidNotSplit() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 封装JDK自带的UUID<br/>
     * 例如：57d7058d-bc79-444d-b7e5-7a5d0b955cc8<br/>
     */
    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * 以62进制（字母加数字）生成19位UUID，最短的UUID
     */
    public static String shortUuid() {
        UUID uuid = UUID.randomUUID();
        return digits(uuid.getMostSignificantBits() >> 32, 8) +
                digits(uuid.getMostSignificantBits() >> 16, 4) +
                digits(uuid.getMostSignificantBits(), 4) +
                digits(uuid.getLeastSignificantBits() >> 48, 4) +
                digits(uuid.getLeastSignificantBits(), 12);
    }

    private static String digits(long val, int digits) {
        long hi = 1L << (digits * 4);
        return CustomNumbers.toString(hi | (val & (hi - 1)), CustomNumbers.MAX_RADIX)
                .substring(1);
    }

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
    public static String objectId() {
        return ObjectId.next();
    }

    /**
     * MongoDB ID生成策略实现<br>
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
     * @author looly
     * @since 4.0.0
     */
    public static class ObjectId {

        /**
         * 线程安全的下一个随机数,每次生成自增+1
         */
        private static final AtomicInteger nextInc = new AtomicInteger(RandomUtil.randomInt());
        /**
         * 机器信息
         */
        private static final int machine = getMachinePiece() | getProcessPiece();

        /**
         * 给定的字符串是否为有效的ObjectId
         *
         * @param s 字符串
         * @return 是否为有效的ObjectId
         */
        public static boolean isValid(String s) {
            if (s == null) {
                return false;
            }
            s = s.replace("-", "");
            final int len = s.length();
            if (len != 24) {
                return false;
            }
            char c;
            for (int i = 0; i < len; i++) {
                c = s.charAt(i);
                if (c >= '0' && c <= '9') {
                    continue;
                }
                if (c >= 'a' && c <= 'f') {
                    continue;
                }
                if (c >= 'A' && c <= 'F') {
                    continue;
                }
                return false;
            }
            return true;
        }

        /**
         * 获取一个objectId的bytes表现形式
         *
         * @return objectId
         * @since 4.1.15
         */
        public static byte[] nextBytes() {
            final ByteBuffer bb = ByteBuffer.wrap(new byte[12]);
            bb.putInt((int) System.currentTimeMillis() / 1000);// 4位
            bb.putInt(machine);// 4位
            bb.putInt(nextInc.getAndIncrement());// 4位
            return bb.array();
        }

        /**
         * 获取一个objectId用下划线分割
         *
         * @return objectId
         */
        public static String next() {
            return next(false);
        }

        /**
         * 获取一个objectId
         *
         * @param withHyphen 是否包含分隔符
         * @return objectId
         */
        public static String next(boolean withHyphen) {
            byte[] array = nextBytes();
            final StringBuilder buf = new StringBuilder(withHyphen ? 26 : 24);
            int t;
            for (int i = 0; i < array.length; i++) {
                if (withHyphen && i % 4 == 0 && i != 0) {
                    buf.append("-");
                }
                t = array[i] & 0xff;
                if (t < 16) {
                    buf.append('0');
                }
                buf.append(Integer.toHexString(t));
            }
            return buf.toString();
        }

        /**
         * 获取机器码片段
         *
         * @return 机器码片段
         */
        private static int getMachinePiece() {
            // 机器码
            int machinePiece;
            try {
                StringBuilder netSb = new StringBuilder();
                // 返回机器所有的网络接口
                Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
                // 遍历网络接口
                while (e.hasMoreElements()) {
                    NetworkInterface ni = e.nextElement();
                    // 网络接口信息
                    netSb.append(ni.toString());
                }
                // 保留后两位
                machinePiece = netSb.toString().hashCode() << 16;
            } catch (Throwable e) {
                // 出问题随机生成,保留后两位
                machinePiece = (RandomUtil.randomInt()) << 16;
            }
            return machinePiece;
        }

        /**
         * 获取进程码片段
         *
         * @return 进程码片段
         */
        private static int getProcessPiece() {
            // 进程码
            // 因为静态变量类加载可能相同,所以要获取进程ID + 加载对象的ID值
            final int processPiece;
            // 进程ID初始化
            int processId;
            try {
                // 获取进程ID
                final String processName = ManagementFactory.getRuntimeMXBean().getName();
                final int atIndex = processName.indexOf('@');
                if (atIndex > 0) {
                    processId = Integer.parseInt(processName.substring(0, atIndex));
                } else {
                    processId = processName.hashCode();
                }
            } catch (Throwable t) {
                processId = RandomUtil.randomInt();
            }
            final ClassLoader loader = getClassLoader();
            // 返回对象哈希码,无论是否重写hashCode方法
            int loaderId = (loader != null) ? System.identityHashCode(loader) : 0;
            // 进程ID + 对象加载ID
            // 保留前2位
            String processSb = Integer.toHexString(processId) + Integer.toHexString(loaderId);
            processPiece = processSb.hashCode() & 0xFFFF;
            return processPiece;
        }

        private static ClassLoader getClassLoader() {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            if (classLoader == null) {
                classLoader = ObjectId.class.getClassLoader();
                if (null == classLoader) {
                    classLoader = ClassLoader.getSystemClassLoader();
                }
            }
            return classLoader;
        }
    }
}
