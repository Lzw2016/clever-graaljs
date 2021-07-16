package org.clever.graaljs.core.constant;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/07/16 16:36 <br/>
 */
public interface HttpEnum {

    final class HttpMethod {
        public static final HttpMethod Instance = new HttpMethod();

        private HttpMethod() {
        }

        public final String GET = "GET";
        public final String POST = "POST";
        public final String DELETE = "DELETE";
        public final String PUT = "PUT";
        public final String HEAD = "HEAD";
        public final String OPTIONS = "OPTIONS";
        public final String PATCH = "PATCH";
        public final String TRACE = "TRACE";
        public final String CONNECT = "CONNECT";
        public final String MOVE = "MOVE";
        public final String PROPPATCH = "PROPPATCH";
        public final String REPORT = "REPORT";
    }
}
