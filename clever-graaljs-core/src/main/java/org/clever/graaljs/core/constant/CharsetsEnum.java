package org.clever.graaljs.core.constant;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/07/16 17:11 <br/>
 */
public interface CharsetsEnum {
    final class Charsets {
        public static final Charsets Instance = new Charsets();

        private Charsets() {
        }

        public final String US_ASCII = "US-ASCII";
        public final String ISO_8859_1 = "ISO-8859-1";
        public final String UTF_8 = "UTF-8";
        public final String UTF_16BE = "UTF-16BE";
        public final String UTF_16LE = "UTF-16LE";
        public final String UTF_16 = "UTF-16";
    }
}
