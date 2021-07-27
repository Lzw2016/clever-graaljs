/**
 * 打印输出
 */
interface Print {
    /**
     * 打印输出
     *
     * @param message 输出数据
     */
    (...message: any[]): void;
}

/**
 * 打印输出
 */
declare const print: Print;
