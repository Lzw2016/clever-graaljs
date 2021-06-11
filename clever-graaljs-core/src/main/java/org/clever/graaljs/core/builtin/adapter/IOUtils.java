package org.clever.graaljs.core.builtin.adapter;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/09/09 18:48 <br/>
 */
public class IOUtils {
    public static final IOUtils Instance = new IOUtils();

    private IOUtils() {
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------- File

    /**
     * 获取绝对路径
     *
     * @param path 路径
     */
    public String getAbsolutePath(String path) {
        File file = new File(path);
        return FilenameUtils.normalize(file.getAbsolutePath());
    }

    /**
     * 是否是文件
     *
     * @param path 路径
     */
    public boolean isFile(String path) {
        File file = new File(path);
        return file.isFile();
    }

    /**
     * 是否是目录
     *
     * @param path 路径
     */
    public boolean isDirectory(String path) {
        File file = new File(path);
        return file.isDirectory();
    }

    /**
     * 路径是否存在
     *
     * @param path 路径
     */
    public boolean exists(String path) {
        File file = new File(path);
        return file.exists();
    }

    /**
     * 规范化路径
     *
     * @param path 路径
     */
    public String normalize(String path) {
        return FilenameUtils.normalize(path);
    }

    /**
     * 获取文件扩展名
     *
     * @param filename 文件名
     */
    public String getExtension(String filename) {
        return FilenameUtils.getExtension(filename);
    }

    /**
     * 打开输入流
     *
     * @param filePath 文件路径
     */
    public FileInputStream openInputStream(String filePath) throws IOException {
        return FileUtils.openInputStream(new File(filePath));
    }

    /**
     * 打开输出流
     *
     * @param filePath 文件路径
     */
    public FileOutputStream openOutputStream(String filePath) throws IOException {
        return FileUtils.openOutputStream(new File(filePath));
    }

    /**
     * 打开输出流
     *
     * @param filePath 文件路径
     * @param append   是否追加
     */
    public FileOutputStream openOutputStream(String filePath, boolean append) throws IOException {
        return FileUtils.openOutputStream(new File(filePath), append);
    }

    /**
     * 读取字符串
     */
    public String readFileToString(String filePath, String encoding) throws IOException {
        return FileUtils.readFileToString(new File(filePath), encoding);
    }

    /**
     * 读取字符串
     */
    public String readFileToString(String filePath) throws IOException {
        return FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
    }

    /**
     * 读取行数据
     */
    public List<String> readLines(String filePath, String encoding) throws IOException {
        return FileUtils.readLines(new File(filePath), encoding);
    }

    /**
     * 读取行数据
     */
    public List<String> readLines(String filePath) throws IOException {
        return FileUtils.readLines(new File(filePath), StandardCharsets.UTF_8);
    }

    /**
     * 读取二进制数据
     */
    public byte[] readFileToByteArray(String filePath) throws IOException {
        return FileUtils.readFileToByteArray(new File(filePath));
    }

    /**
     * 写字符串到文件
     */
    public void writeStringToFile(String filePath, String data, String encoding, boolean append) throws IOException {
        FileUtils.writeStringToFile(new File(filePath), data, encoding, append);
    }

    /**
     * 写字符串到文件
     */
    public void writeStringToFile(String filePath, String data, String encoding) throws IOException {
        FileUtils.writeStringToFile(new File(filePath), data, encoding);
    }

    /**
     * 写字符串到文件
     */
    public void writeStringToFile(String filePath, String data) throws IOException {
        FileUtils.writeStringToFile(new File(filePath), data, StandardCharsets.UTF_8);
    }

    /**
     * 写行数据到文件
     */
    public void writeLines(String filePath, Collection<?> lines, String lineEnding, String encoding, boolean append) throws IOException {
        FileUtils.writeLines(new File(filePath), encoding, lines, lineEnding, append);
    }

    /**
     * 写行数据到文件
     */
    public void writeLines(String filePath, Collection<?> lines, String lineEnding, String encoding) throws IOException {
        FileUtils.writeLines(new File(filePath), encoding, lines, lineEnding);
    }

    /**
     * 写行数据到文件
     */
    public void writeLines(String filePath, Collection<?> lines, String lineEnding) throws IOException {
        FileUtils.writeLines(new File(filePath), "UTF-8", lines, lineEnding);
    }

    /**
     * 写行数据到文件
     */
    public void writeLines(String filePath, Collection<?> lines) throws IOException {
        FileUtils.writeLines(new File(filePath), "UTF-8", lines);
    }

    /**
     * 写二进制数据到文件
     */
    public void writeByteArrayToFile(String filePath, byte[] data, boolean append) throws IOException {
        FileUtils.writeByteArrayToFile(new File(filePath), data, append);
    }

    /**
     * 写二进制数据到文件
     */
    public void writeByteArrayToFile(String filePath, byte[] data) throws IOException {
        FileUtils.writeByteArrayToFile(new File(filePath), data);
    }

    /**
     * 返回文件大小的可读版本
     */
    public String byteCountToDisplaySize(long size) {
        return FileUtils.byteCountToDisplaySize(size);
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------- IO

    /**
     * 读取文本
     */
    public String readToString(InputStream input, String encoding) throws IOException {
        return org.apache.commons.io.IOUtils.toString(input, encoding);
    }

    /**
     * 读取文本
     */
    public String readToString(InputStream input) throws IOException {
        return org.apache.commons.io.IOUtils.toString(input, StandardCharsets.UTF_8);
    }

    /**
     * 读取行数据
     */
    public List<String> readLines(InputStream input, String encoding) throws IOException {
        return org.apache.commons.io.IOUtils.readLines(input, encoding);
    }

    /**
     * 读取行数据
     */
    public List<String> readLines(InputStream input) throws IOException {
        return org.apache.commons.io.IOUtils.readLines(input, StandardCharsets.UTF_8);
    }

    /**
     * 读取二进制数据
     */
    public byte[] readToByteArray(InputStream input) throws IOException {
        return org.apache.commons.io.IOUtils.toByteArray(input);
    }

    /**
     * 写入文本
     */
    public void writeString(OutputStream output, String data, String encoding) throws IOException {
        org.apache.commons.io.IOUtils.write(data, output, encoding);
    }

    /**
     * 写入文本
     */
    public void writeString(OutputStream output, String data) throws IOException {
        org.apache.commons.io.IOUtils.write(data, output, StandardCharsets.UTF_8);
    }

    /**
     * 写入文本
     *
     * @param output     输出流
     * @param lines      行内容
     * @param lineEnding 行尾
     * @param encoding   编码
     */
    public void writeLines(OutputStream output, Collection<?> lines, String lineEnding, String encoding) throws IOException {
        org.apache.commons.io.IOUtils.writeLines(lines, lineEnding, output, encoding);
    }

    /**
     * 写入文本
     *
     * @param output     输出流
     * @param lines      行内容
     * @param lineEnding 行尾
     */
    public void writeLines(OutputStream output, Collection<?> lines, String lineEnding) throws IOException {
        org.apache.commons.io.IOUtils.writeLines(lines, lineEnding, output, StandardCharsets.UTF_8);
    }

    /**
     * 写入文本
     */
    public void writeLines(OutputStream output, Collection<?> lines) throws IOException {
        org.apache.commons.io.IOUtils.writeLines(lines, null, output, StandardCharsets.UTF_8);
    }

    /**
     * 写入二进制数据
     */
    public void writeByteArray(OutputStream output, byte[] data) throws IOException {
        org.apache.commons.io.IOUtils.write(data, output);
    }

    /**
     * 复制流
     */
    public void copy(InputStream input, OutputStream output) throws IOException {
        org.apache.commons.io.IOUtils.copy(input, output);
    }

    /**
     * 关闭流
     */
    public void close(Closeable closeable) throws IOException {
        if (closeable != null) {
            closeable.close();
        }
    }
}
