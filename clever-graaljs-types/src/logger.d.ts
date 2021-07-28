/**
 * Java slf4j 实现的日志记录器
 */
interface Logger {
    /**
     * trace打印输出
     * @param format    格式化文本
     * @param message   数据
     */
    trace(format: string, ...message: any[]): void;

    /**
     * debug打印输出
     * @param format    格式化文本
     * @param message   数据
     */
    debug(format: string, ...message: any[]): void;

    /**
     * info打印输出
     * @param format    格式化文本
     * @param message   数据
     */
    info(format: string, ...message: any[]): void;

    /**
     * warn打印输出
     * @param format    格式化文本
     * @param message   数据
     */
    warn(format: string, ...message: any[]): void;

    /**
     * error打印输出
     * @param format    格式化文本
     * @param message   数据
     */
    error(format: string, ...message: any[]): void;
}

/**
 * 日志记录器工厂
 */
interface LoggerFactory {
    /**
     * 获取日志对象
     *
     * @param name 名称
     */
    getLogger(name: string): Logger;
}

/**
 * 日志记录器工厂(用于创建日志记录器)
 */
declare const LoggerFactory: LoggerFactory;
