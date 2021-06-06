package org.clever.graaljs.core.internal;

import org.apache.commons.lang3.StringUtils;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/25 21:49 <br/>
 */
public class LoggerConsole extends AbstractConsole {
    public static final LoggerConsole Instance = new LoggerConsole(StringUtils.EMPTY);

    /**
     * 日志记录对象
     */
    private final org.slf4j.Logger logger;

    /**
     * @param name 日志记录器的名称
     */
    public LoggerConsole(String name) {
        logger = org.slf4j.LoggerFactory.getLogger(name);
    }

    @Override
    protected boolean isTraceEnabled() {
        return logger.isTraceEnabled();
    }

    @Override
    protected boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    @Override
    protected boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }

    @Override
    protected boolean isWarnEnabled() {
        return logger.isWarnEnabled();
    }

    @Override
    protected boolean isErrorEnabled() {
        return logger.isErrorEnabled();
    }

    @Override
    protected void doLog(String logsText, Object[] args) {
        logger.info(logsText);
    }

    @Override
    protected void doTrace(String logsText, Object[] args) {
        logger.trace(logsText);
    }

    @Override
    protected void doDebug(String logsText, Object[] args) {
        logger.debug(logsText);
    }

    @Override
    protected void doInfo(String logsText, Object[] args) {
        logger.info(logsText);
    }

    @Override
    protected void doWarn(String logsText, Object[] args) {
        logger.warn(logsText);
    }

    @Override
    protected void doError(String logsText, Object[] args) {
        logger.error(logsText);
    }

    @Override
    protected void doPrint(String logsText, Object[] args) {
        logger.info(logsText);
    }
}
