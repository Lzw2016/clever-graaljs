package org.clever.graaljs.core.utils;

import java.text.DecimalFormat;

/**
 * 存储空间大小换算工具，B、KB、MB、GB、TB、PB<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-9 21:43 <br/>
 */
public class DataSizeUtils {
    private static final long ONE_KB = 1024;
    private static final long ONE_MB = ONE_KB * 1024;
    private static final long ONE_GB = ONE_MB * 1024;
    private static final long ONE_TB = ONE_GB * 1024;
    private static final long ONE_PB = ONE_TB * 1024;

    /**
     * 返回容易读取的数据大小
     *
     * @param size     数据大小 单位：bit
     * @param unit     返回数据的单位进​率​，ONE_KB、ONE_MB、ONE_GB、ONE_TB、ONE_PB
     * @param unitName 单位名称：KB、MB、GB、TB、PB
     * @return 结果大于1返回换算之后的数据，否则返回null
     */
    private static String getHumanReadableSize(long size, long unit, String unitName) {
        if (size == 0) {
            return "0";
        }
        if (size / unit >= 1) {
            double value = size / (double) unit;
            DecimalFormat df = new DecimalFormat("######.##" + unitName);
            return df.format(value);
        }
        return null;
    }

    /**
     * 返回容易读取的数据大小,如：15B、12.36KB、1.58MB、25.3GB
     *
     * @param size 数据大小 单位：bit
     * @return 返回容易读取的文件大小数据, 如：15B、12.36KB、1.58MB、25.3GB
     */
    private static String getHumanReadableSize(long size) {
        if (size < 0) {
            return String.valueOf(size);
        }
        String result = getHumanReadableSize(size, ONE_PB, "PB");
        if (result != null) {
            return result;
        }
        result = getHumanReadableSize(size, ONE_TB, "TB");
        if (result != null) {
            return result;
        }
        result = getHumanReadableSize(size, ONE_GB, "GB");
        if (result != null) {
            return result;
        }
        result = getHumanReadableSize(size, ONE_MB, "MB");
        if (result != null) {
            return result;
        }
        result = getHumanReadableSize(size, ONE_KB, "KB");
        if (result != null) {
            return result;
        }
        return size + "B";
    }

    /**
     * 返回容易读取的数据大小,如：15B、12.36KB、1.58MB、25.3GB
     *
     * @param size 文件大小 单位：bit
     * @return 返回容易读取的文件大小数据, 参数是null，返回null
     */
    public static String getHumanReadableSize(Long size) {
        if (size == null) {
            return null;
        }
        return getHumanReadableSize(size.longValue());
    }
}
