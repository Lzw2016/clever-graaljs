package org.clever.graaljs.core.constant;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/07/16 17:15 <br/>
 */
public interface RoundingModeEnum {

    final class RoundingMode {
        public static final RoundingMode Instance = new RoundingMode();

        private RoundingMode() {
        }

        /**
         * 向正无穷方向对齐
         */
        public final byte UP = 0;
        /**
         * 向负无穷方向对齐
         */
        public final byte DOWN = 1;
        /**
         * 向原点的反方向对齐
         */
        public final byte CEILING = 2;
        /**
         * 向原点方向对齐
         */
        public final byte FLOOR = 3;
        /**
         * “四舍五入”，如果舍弃部分的最高位大于等于 5，向正无穷方向对齐，否则向负无穷方向对齐
         */
        public final byte HALF_UP = 4;
        /**
         * 五舍六入”，如果舍弃部分的最高位大于 5，向正无穷方向对齐，否则向负无穷方向对齐
         */
        public final byte HALF_DOWN = 5;
        /**
         * “四舍六入五成双”，如果舍弃部分的最高位大于等于六，或等于五并且前一位是奇数，向正无穷方向对齐，否则向负无穷方向对齐
         */
        public final byte HALF_EVEN = 6;
        /**
         * 如果需要舍入，就抛出算术异常
         */
        public final byte UNNECESSARY = 7;
    }
}
