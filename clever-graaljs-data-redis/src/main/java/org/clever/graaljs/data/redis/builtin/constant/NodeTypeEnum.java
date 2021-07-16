package org.clever.graaljs.data.redis.builtin.constant;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/07/16 17:40 <br/>
 */
public interface NodeTypeEnum {
    final class NodeType {
        public static final NodeType Instance = new NodeType();

        private NodeType() {
        }

        public final String Master = "MASTER";
        public final String Slave = "SLAVE";
    }
}
