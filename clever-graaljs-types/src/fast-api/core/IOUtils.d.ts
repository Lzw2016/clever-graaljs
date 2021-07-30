/**
 * 作者：lizw <br/>
 * 创建时间：2020/09/09 18:48 <br/>
 */
interface IOUtils extends JObject {
    // ---------------------------------------------------------------------------------------------------------------------------------------------------- File
    /**
     * 获取绝对路径
     *
     * @param path 路径
     */
    getAbsolutePath(path: JString): JString;

    /**
     * 是否是文件
     *
     * @param path 路径
     */
    isFile(path: JString): JBoolean;

    /**
     * 是否是目录
     *
     * @param path 路径
     */
    isDirectory(path: JString): JBoolean;

    /**
     * 路径是否存在
     *
     * @param path 路径
     */
    exists(path: JString): JBoolean;

    /**
     * 规范化路径
     *
     * @param path 路径
     */
    normalize(path: JString): JString;

    /**
     * 获取文件扩展名
     *
     * @param filename 文件名
     */
    getExtension(filename: JString): JString;

    /**
     * 打开输入流
     *
     * @param filePath 文件路径
     */
    openInputStream(filePath: JString): JFileInputStream;

    /**
     * 打开输出流
     *
     * @param filePath 文件路径
     */
    openOutputStream(filePath: JString): JFileOutputStream;

    /**
     * 打开输出流
     *
     * @param filePath 文件路径
     * @param append   是否追加
     */
    openOutputStream(filePath: JString, append: JBoolean): JFileOutputStream;

    /**
     * 读取字符串
     */
    readFileToString(filePath: JString, encoding: JString): JString;

    /**
     * 读取字符串
     */
    readFileToString(filePath: JString): JString;

    /**
     * 读取行数据
     */
    readLines(filePath: JString, encoding: JString): List<String>;

    /**
     * 读取行数据
     */
    readLines(filePath: JString): List<String>;

    /**
     * 读取二进制数据
     */
    readFileToByteArray(filePath: JString): JByte[];

    /**
     * 写字符串到文件
     */
    writeStringToFile(filePath: JString, data: JString, encoding: JString, append: JBoolean): void;

    /**
     * 写字符串到文件
     */
    writeStringToFile(filePath: JString, data: JString, encoding: JString): void;

    /**
     * 写字符串到文件
     */
    writeStringToFile(filePath: JString, data: JString): void;

    /**
     * 写行数据到文件
     */
    writeLines(filePath: JString, lines: JCollection<any>, lineEnding: JString, encoding: JString, append: JBoolean): void;

    /**
     * 写行数据到文件
     */
    writeLines(filePath: JString, lines: JCollection<any>, lineEnding: JString, encoding: JString): void;

    /**
     * 写行数据到文件
     */
    writeLines(filePath: JString, lines: JCollection<any>, lineEnding: JString): void;

    /**
     * 写行数据到文件
     */
    writeLines(filePath: JString, lines: JCollection<any>): void;

    /**
     * 写二进制数据到文件
     */
    writeByteArrayToFile(filePath: JString, data: JByte[], append: JBoolean): void;

    /**
     * 写二进制数据到文件
     */
    writeByteArrayToFile(filePath: JString, data: JByte[]): void;

    /**
     * 返回文件大小的可读版本
     */
    byteCountToDisplaySize(size: JLong): JString;

    // ---------------------------------------------------------------------------------------------------------------------------------------------------- IO
    /**
     * 读取文本
     */
    readToString(input: JInputStream, encoding: JString): JString;

    /**
     * 读取文本
     */
    readToString(input: JInputStream): JString;

    /**
     * 读取行数据
     */
    readLines(input: JInputStream, encoding: JString): List<String>;

    /**
     * 读取行数据
     */
    readLines(input: JInputStream): List<String>;

    /**
     * 读取二进制数据
     */
    readToByteArray(input: JInputStream): JByte[];

    /**
     * 写入文本
     */
    writeString(output: JOutputStream, data: JString, encoding: JString): void;

    /**
     * 写入文本
     */
    writeString(output: JOutputStream, data: JString): void;

    /**
     * 写入文本
     *
     * @param output     输出流
     * @param lines      行内容
     * @param lineEnding 行尾
     * @param encoding   编码
     */
    writeLines(output: JOutputStream, lines: JCollection<any>, lineEnding: JString, encoding: JString): void;

    /**
     * 写入文本
     *
     * @param output     输出流
     * @param lines      行内容
     * @param lineEnding 行尾
     */
    writeLines(output: JOutputStream, lines: JCollection<any>, lineEnding: JString): void;

    /**
     * 写入文本
     */
    writeLines(output: JOutputStream, lines: JCollection<any>): void;

    /**
     * 写入二进制数据
     */
    writeByteArray(output: JOutputStream, data: JByte[]): void;

    /**
     * 复制流
     */
    copy(input: JInputStream, output: JOutputStream): void;

    /**
     * 关闭流
     */
    close(closeable: JCloseable): void;
}

declare const IOUtils: IOUtils;
