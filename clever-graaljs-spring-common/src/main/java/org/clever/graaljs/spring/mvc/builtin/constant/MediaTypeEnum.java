package org.clever.graaljs.spring.mvc.builtin.constant;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/07/16 17:57 <br/>
 */
public interface MediaTypeEnum {
    /**
     * 响应数据类型
     */
    final class MediaType {
        public static final MediaType Instance = new MediaType();

        private MediaType() {
        }

        // ----------------------------------------------------------------------------------------- 文本
        /**
         * json
         */
        public final String Json = "application/json;charset=UTF-8";
        /**
         * xml
         */
        public final String Xml = "application/xml;charset=UTF-8";
        /**
         * xhtml + xml
         */
        public final String XhtmlXml = "application/xhtml+xml";
        /**
         * html
         */
        public final String Html = "text/html";
        /**
         * plain
         */
        public final String Plain = "text/plain;charset=UTF-8";
        // ----------------------------------------------------------------------------------------- 图片
        /**
         * git
         */
        public final String Gif = "image/gif";
        /**
         * jpeg
         */
        public final String Jpeg = "image/jpeg";
        /**
         * png
         */
        public final String Png = "image/png";
        // ----------------------------------------------------------------------------------------- 音频、视频
        /**
         * x-musicnet-stream
         */
        public final String AudioMusicNet = "audio/x-musicnet-stream";
        /**
         * mp3
         */
        public final String Mp3 = "audio/mp3";
        /**
         * mpeg4
         */
        public final String Mpeg4 = "video/mpeg4";
        /**
         * mpg
         */
        public final String Mpeg = "video/mpg";
        /**
         * rmvb
         */
        public final String Rmvb = "application/vnd.rn-realmedia-vbr";
        // ----------------------------------------------------------------------------------------- 常见文件类型
        /**
         * pdf
         */
        public final String Pdf = "application/pdf";
        /**
         * excel
         */
        public final String Excel = "application/vnd.ms-excel";
        /**
         * word
         */
        public final String Word = "application/msword";
        /**
         * ppt
         */
        public final String Ppt = "application/x-ppt";
        // ----------------------------------------------------------------------------------------- 文件
        /**
         * 二进制流
         */
        public final String OctetStream = "application/octet-stream";
    }
}
