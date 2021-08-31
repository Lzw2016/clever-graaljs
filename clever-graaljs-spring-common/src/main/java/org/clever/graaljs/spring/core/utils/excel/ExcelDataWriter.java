package org.clever.graaljs.spring.core.utils.excel;

import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.clever.graaljs.core.utils.codec.EncodeDecodeUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.Locale;

/**
 * 作者： lzw<br/>
 * 创建时间：2019-05-14 17:58 <br/>
 *
 * @see com.alibaba.excel.EasyExcel
 */
@Slf4j
public class ExcelDataWriter {
    private final ExcelWriterBuilder excelWriterBuilder;

    /**
     * @param request  HTTP请求
     * @param response HTTP响应
     * @param fileName 下载Excel文件名称
     * @param clazz    Excel解析对应的数据类型
     */
    public ExcelDataWriter(HttpServletRequest request, HttpServletResponse response, String fileName, Class<?> clazz) {
        this(getDownloadFileNameStream(request, response, fileName), clazz);
    }

    /**
     * @param outputStream Excel文件对应输出流
     * @param clazz        Excel解析对应的数据类型
     */
    public ExcelDataWriter(OutputStream outputStream, Class<?> clazz) {
        // Assert.notNull(outputStream, "参数outputStream不能为空");
        // Assert.notNull(clazz, "参数clazz不能为空");
        excelWriterBuilder = new ExcelWriterBuilder();
        excelWriterBuilder.file(outputStream);
        if (clazz != null) {
            excelWriterBuilder.head(clazz);
        }
        excelWriterBuilder.locale(Locale.SIMPLIFIED_CHINESE);
        excelWriterBuilder.autoTrim(true);
    }

    public ExcelWriterBuilder write() {
        return excelWriterBuilder;
    }

    /**
     * 导出Excel给浏览器下载
     *
     * @param request  请求
     * @param response 响应
     * @param fileName 文件名称
     */
    @SneakyThrows
    private static OutputStream getDownloadFileNameStream(HttpServletRequest request, HttpServletResponse response, String fileName) {
        if (StringUtils.isBlank(fileName)) {
            fileName = "数据导出.xlsx";
        }
        fileName = StringUtils.trim(fileName);
        if (!fileName.toLowerCase().endsWith(".xlsx") && !fileName.toLowerCase().endsWith(".xls")) {
            fileName = fileName + ".xlsx";
        }
        fileName = EncodeDecodeUtils.urlEncode(fileName).replaceAll("\\+", "%20");
        String contentDisposition = "attachment;" +
                "filename=" + fileName + ";" +
                "filename*=" + "utf-8''" + fileName;
        response.setHeader("Content-Disposition", contentDisposition);
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        return response.getOutputStream();
    }

    /**
     * @param outputStream Excel文件对应输出流
     * @param clazz        Excel解析对应的数据类型
     */
    public static ExcelWriterBuilder write(OutputStream outputStream, Class<?> clazz) {
        return new ExcelDataWriter(outputStream, clazz).write();
    }

    /**
     * @param request  HTTP请求
     * @param response HTTP响应
     * @param fileName 下载Excel文件名称
     * @param clazz    Excel解析对应的数据类型
     */
    public static ExcelWriterBuilder write(HttpServletRequest request, HttpServletResponse response, String fileName, Class<?> clazz) {
        return new ExcelDataWriter(request, response, fileName, clazz).write();
    }
}
