package org.clever.graaljs.core.internal;

import lombok.Getter;
import org.clever.graaljs.core.internal.support.ObjectToString;
import org.clever.graaljs.core.utils.Assert;
import org.clever.graaljs.core.utils.TupleTow;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/26 11:26 <br/>
 */
public class Logger {
    /**
     * 日志记录器
     */
    protected final org.slf4j.Logger logger;

    /**
     * toString实现
     */
    @Getter
    protected ObjectToString objectToString = ObjectToString.Instance;

    public Logger(String name) {
        logger = org.slf4j.LoggerFactory.getLogger(name);
    }

    public void setObjectToString(ObjectToString objectToString) {
        Assert.notNull(objectToString, "参数objectToString不能为空");
        this.objectToString = objectToString;
    }

    public void trace(String msg) {
        Object[] args = new Object[]{};
        trace(msg, args);
    }

//    TODO fixme https://github.com/graalvm/graaljs/issues/286
//    public void trace(String format, Object arg) {
//        Object[] args = new Object[]{arg};
//        trace(format, args);
//    }
//
//    public void trace(String format, Object arg1, Object arg2) {
//        Object[] args = new Object[]{arg1, arg2};
//        trace(format, args);
//    }
//
//    public void trace(String msg, Throwable t) {
//        Object[] args = new Object[]{t};
//        trace(msg, args);
//    }

    /**
     * 打印输出
     *
     * @param args 输出数据
     */
    public void trace(String format, Object... args) {
        if (logger.isTraceEnabled()) {
            if (args == null) {
                args = new Object[]{null};
            }
            TupleTow<String, Throwable> tupleTow = logString(format, args);
            if (tupleTow.getValue2() == null) {
                logger.trace(tupleTow.getValue1());
            } else {
                logger.trace(tupleTow.getValue1(), tupleTow.getValue2());
            }
        }
    }

    public void debug(String msg) {
        Object[] args = new Object[]{};
        debug(msg, args);
    }

//    public void debug(String format, Object arg) {
//        Object[] args = new Object[]{arg};
//        debug(format, args);
//    }
//
//    public void debug(String format, Object arg1, Object arg2) {
//        Object[] args = new Object[]{arg1, arg2};
//        debug(format, args);
//    }
//
//    public void debug(String msg, Throwable t) {
//        Object[] args = new Object[]{t};
//        debug(msg, args);
//    }

    /**
     * debug打印输出
     *
     * @param args 输出数据
     */
    public void debug(String format, Object... args) {
        if (logger.isDebugEnabled()) {
            if (args == null) {
                args = new Object[]{null};
            }
            TupleTow<String, Throwable> tupleTow = logString(format, args);
            if (tupleTow.getValue2() == null) {
                logger.debug(tupleTow.getValue1());
            } else {
                logger.debug(tupleTow.getValue1(), tupleTow.getValue2());
            }
        }
    }

    public void info(String msg) {
        Object[] args = new Object[]{};
        info(msg, args);
    }
//
//    public void info(String format, Object arg) {
//        Object[] args = new Object[]{arg};
//        info(format, args);
//    }
//
//    public void info(String format, Object arg1, Object arg2) {
//        Object[] args = new Object[]{arg1, arg2};
//        info(format, args);
//    }
//
//    public void info(String msg, Throwable t) {
//        Object[] args = new Object[]{t};
//        info(msg, args);
//    }

    /**
     * info打印输出
     *
     * @param args 输出数据
     */
    public void info(String format, Object... args) {
        if (logger.isInfoEnabled()) {
            if (args == null) {
                args = new Object[]{null};
            }
            TupleTow<String, Throwable> tupleTow = logString(format, args);
            if (tupleTow.getValue2() == null) {
                logger.info(tupleTow.getValue1());
            } else {
                logger.info(tupleTow.getValue1(), tupleTow.getValue2());
            }
        }
    }

    public void warn(String msg) {
        Object[] args = new Object[]{};
        warn(msg, args);
    }

//    public void warn(String format, Object arg) {
//        Object[] args = new Object[]{arg};
//        warn(format, args);
//    }
//
//    public void warn(String format, Object arg1, Object arg2) {
//        Object[] args = new Object[]{arg1, arg2};
//        warn(format, args);
//    }
//
//    public void warn(String msg, Throwable t) {
//        Object[] args = new Object[]{t};
//        warn(msg, args);
//    }

    /**
     * warn打印输出
     *
     * @param args 输出数据
     */
    public void warn(String format, Object... args) {
        if (logger.isWarnEnabled()) {
            if (args == null) {
                args = new Object[]{null};
            }
            TupleTow<String, Throwable> tupleTow = logString(format, args);
            if (tupleTow.getValue2() == null) {
                logger.warn(tupleTow.getValue1());
            } else {
                logger.warn(tupleTow.getValue1(), tupleTow.getValue2());
            }
        }
    }

    public void error(String msg) {
        Object[] args = new Object[]{};
        error(msg, args);
    }

//    public void error(String format, Object arg) {
//        Object[] args = new Object[]{arg};
//        error(format, args);
//    }
//
//    public void error(String format, Object arg1, Object arg2) {
//        Object[] args = new Object[]{arg1, arg2};
//        error(format, args);
//    }
//
//    public void error(String msg, Throwable t) {
//        Object[] args = new Object[]{t};
//        error(msg, args);
//    }

    /**
     * error打印输出
     *
     * @param args 输出数据
     */
    public void error(String format, Object... args) {
        if (logger.isErrorEnabled()) {
            if (args == null) {
                args = new Object[]{null};
            }
            TupleTow<String, Throwable> tupleTow = logString(format, args);
            if (tupleTow.getValue2() == null) {
                logger.error(tupleTow.getValue1());
            } else {
                logger.error(tupleTow.getValue1(), tupleTow.getValue2());
            }
        }
    }

    /**
     * 根据日志输出参数得到日志字符串
     */
    protected TupleTow<String, Throwable> logString(String format, Object... args) {
        if (args == null || args.length <= 0) {
            return TupleTow.creat(format, null);
        }
        Throwable throwable = null;
        if (args[args.length - 1] instanceof Throwable) {
            throwable = (Throwable) args[args.length - 1];
        }
        String logsText;
        if (throwable == null) {
            logsText = objectToString.format(format, args);
        } else {
            int length = args.length - 1;
            Object[] array = new Object[length];
            System.arraycopy(args, 0, array, 0, length);
            logsText = objectToString.format(format, array);
        }
        return TupleTow.creat(logsText, throwable);
    }
}
