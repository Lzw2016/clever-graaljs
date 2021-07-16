package org.clever.graaljs.core.constant;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/07/16 17:18 <br/>
 */
public interface PinyinUtilsEnum {
    final class HanyuPinyinCaseType {
        public static final HanyuPinyinCaseType Instance = new HanyuPinyinCaseType();

        private HanyuPinyinCaseType() {
        }

        /**
         * 大写
         */
        public final String UPPERCASE = "UPPERCASE";
        /**
         * 小写
         */
        public final String LOWERCASE = "LOWERCASE";
    }

    final class HanyuPinyinToneType {
        public static final HanyuPinyinToneType Instance = new HanyuPinyinToneType();

        private HanyuPinyinToneType() {
        }

        /**
         * 此选项表示以声调数字输出汉语拼音
         */
        public final String WITH_TONE_NUMBER = "WITH_TONE_NUMBER";
        /**
         * 此选项表示输出的汉语拼音没有声调数字或声调标记
         */
        public final String WITHOUT_TONE = "WITHOUT_TONE";
        /**
         * 此选项表示输出的汉语拼音带有声调标记
         */
        public final String WITH_TONE_MARK = "WITH_TONE_MARK";
    }

    final class HanyuPinyinVCharType {
        public static final HanyuPinyinVCharType Instance = new HanyuPinyinVCharType();

        private HanyuPinyinVCharType() {
        }

        /**
         * 该选项表示'ü'的输出是'u:'
         */
        public final String WITH_U_AND_COLON = "WITH_U_AND_COLON";
        /**
         * 该选项表示'ü'的输出是'v'
         */
        public final String WITH_V = "WITH_V";
        /**
         * 该选项表示'ü'的输出是'ü'
         */
        public final String WITH_U_UNICODE = "WITH_U_UNICODE";
    }
}
