package org.clever.graaljs.spring.core.utils.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.alibaba.excel.metadata.Cell;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.metadata.property.ExcelReadHeadProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.clever.graaljs.core.utils.codec.DigestUtils;
import org.clever.graaljs.core.utils.codec.EncodeDecodeUtils;
import org.clever.graaljs.spring.core.utils.excel.dto.ExcelData;
import org.clever.graaljs.spring.core.utils.excel.dto.ExcelHead;
import org.clever.graaljs.spring.core.utils.excel.dto.ExcelRow;
import org.clever.graaljs.spring.core.utils.validator.ValidatorFactoryUtils;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/08/14 11:27 <br/>
 *
 * @see com.alibaba.excel.EasyExcel
 */
@Slf4j
public class ExcelDataReader<T> {
    /**
     * 默认最大读取数据行数
     */
    public static final int LIMIT_ROWS = 2000;
    /**
     * 上传的Excel文件名称
     */
    @Getter
    private final String filename;
    /**
     * 上传的文件数据流
     */
    private final InputStream inputStream;
    /**
     * 读取Excel文件最大行数
     */
    @Getter
    private final int limitRows;
    /**
     * 是否缓存读取的数据结果到内存中(默认启用)
     */
    @Getter
    private final boolean enableExcelData;
    /**
     * Excel读取结果
     */
    @Getter
    private final LinkedHashMap<String, ExcelData<T>> excelSheetMap = new LinkedHashMap<>(1);
    /**
     * 处理读取Excel异常
     */
    private final ExcelReaderExceptionHand excelReaderExceptionHand;
    /**
     * 处理Excel数据行
     */
    private final ExcelRowReader<T> excelRowReader;
    /**
     * 读取Excel文件的实现
     */
    private final ExcelReaderBuilder excelReaderBuilder;
    /**
     * ExcelDate 数据读取实现
     */
    private final ExcelDateReadListener excelDateReadListener = new ExcelDateReadListener();
    /**
     * 是否启用JSR303校验(默认启用)
     */
    @Setter
    @Getter
    private boolean enableValidation = true;

    /**
     * @param request 上传的文件的请求
     * @param clazz   Excel解析对应的数据类型
     */
    public ExcelDataReader(HttpServletRequest request, Class<T> clazz) throws IOException {
        this(getMultipartFile(request), clazz, LIMIT_ROWS, true, null, null);
    }

    /**
     * @param request        上传的文件的请求
     * @param clazz          Excel解析对应的数据类型
     * @param excelRowReader 处理Excel数据行(用于自定义校验)
     */
    public ExcelDataReader(HttpServletRequest request, Class<T> clazz, ExcelRowReader<T> excelRowReader) throws IOException {
        this(getMultipartFile(request), clazz, LIMIT_ROWS, true, excelRowReader, null);
    }

    /**
     * @param request                  上传的文件的请求
     * @param clazz                    Excel解析对应的数据类型
     * @param excelRowReader           处理Excel数据行(用于自定义校验)
     * @param excelReaderExceptionHand 处理读取Excel异常
     */
    public ExcelDataReader(HttpServletRequest request, Class<T> clazz, ExcelRowReader<T> excelRowReader, ExcelReaderExceptionHand excelReaderExceptionHand) throws IOException {
        this(getMultipartFile(request), clazz, LIMIT_ROWS, true, excelRowReader, excelReaderExceptionHand);
    }

    /**
     * @param request                  上传的文件的请求
     * @param clazz                    Excel解析对应的数据类型
     * @param limitRows                读取Excel文件最大行数
     * @param enableExcelData          是否缓存读取的数据结果到内存中
     * @param excelRowReader           处理Excel数据行(用于自定义校验)
     * @param excelReaderExceptionHand 处理读取Excel异常
     */
    public ExcelDataReader(
            HttpServletRequest request,
            Class<T> clazz,
            int limitRows,
            boolean enableExcelData,
            ExcelRowReader<T> excelRowReader,
            ExcelReaderExceptionHand excelReaderExceptionHand) throws IOException {
        this(getMultipartFile(request), clazz, limitRows, enableExcelData, excelRowReader, excelReaderExceptionHand);
    }

    /**
     * @param request                  上传的文件的请求
     * @param clazz                    Excel解析对应的数据类型
     * @param limitRows                读取Excel文件最大行数
     * @param enableExcelData          是否缓存读取的数据结果到内存中
     * @param useCustomReadListener    是否使用默认的ReadListener
     * @param excelRowReader           处理Excel数据行(用于自定义校验)
     * @param excelReaderExceptionHand 处理读取Excel异常
     */
    public ExcelDataReader(
            HttpServletRequest request,
            Class<T> clazz,
            int limitRows,
            boolean enableExcelData,
            boolean useCustomReadListener,
            ExcelRowReader<T> excelRowReader,
            ExcelReaderExceptionHand excelReaderExceptionHand) throws IOException {
        this(getMultipartFile(request), clazz, limitRows, enableExcelData, useCustomReadListener, excelRowReader, excelReaderExceptionHand);
    }

    private ExcelDataReader(
            MultipartFile multipartFile,
            Class<T> clazz,
            int limitRows,
            boolean enableExcelData,
            ExcelRowReader<T> excelRowReader,
            ExcelReaderExceptionHand excelReaderExceptionHand) throws IOException {
        this(multipartFile.getOriginalFilename(), multipartFile.getInputStream(), clazz, limitRows, enableExcelData, excelRowReader, excelReaderExceptionHand);
    }

    private ExcelDataReader(
            MultipartFile multipartFile,
            Class<T> clazz,
            int limitRows,
            boolean enableExcelData,
            boolean useCustomReadListener,
            ExcelRowReader<T> excelRowReader,
            ExcelReaderExceptionHand excelReaderExceptionHand) throws IOException {
        this(multipartFile.getOriginalFilename(), multipartFile.getInputStream(), clazz, limitRows, enableExcelData, useCustomReadListener, excelRowReader, excelReaderExceptionHand);
    }

    /**
     * @param filename    上传的文件名称
     * @param inputStream 上传的文件内容
     * @param clazz       Excel解析对应的数据类型
     */
    public ExcelDataReader(String filename, InputStream inputStream, Class<T> clazz) {
        this(filename, inputStream, clazz, LIMIT_ROWS, true, null, null);
    }

    /**
     * @param filename       上传的文件名称
     * @param inputStream    上传的文件内容
     * @param clazz          Excel解析对应的数据类型
     * @param excelRowReader 处理Excel数据行(用于自定义校验)
     */
    public ExcelDataReader(String filename, InputStream inputStream, Class<T> clazz, ExcelRowReader<T> excelRowReader) {
        this(filename, inputStream, clazz, LIMIT_ROWS, true, excelRowReader, null);
    }

    /**
     * @param filename                 上传的文件名称
     * @param inputStream              上传的文件内容
     * @param clazz                    Excel解析对应的数据类型
     * @param excelRowReader           处理Excel数据行(用于自定义校验)
     * @param excelReaderExceptionHand 处理读取Excel异常
     */
    public ExcelDataReader(String filename, InputStream inputStream, Class<T> clazz, ExcelRowReader<T> excelRowReader, ExcelReaderExceptionHand excelReaderExceptionHand) {
        this(filename, inputStream, clazz, LIMIT_ROWS, true, excelRowReader, excelReaderExceptionHand);
    }

    /**
     * @param filename                 上传的文件名称
     * @param inputStream              上传的文件内容
     * @param clazz                    Excel解析对应的数据类型
     * @param limitRows                读取Excel文件最大行数
     * @param enableExcelData          是否缓存读取的数据结果到内存中
     * @param excelRowReader           处理Excel数据行(用于自定义校验)
     * @param excelReaderExceptionHand 处理读取Excel异常
     */
    public ExcelDataReader(
            String filename,
            InputStream inputStream,
            Class<T> clazz,
            int limitRows,
            boolean enableExcelData,
            ExcelRowReader<T> excelRowReader,
            ExcelReaderExceptionHand excelReaderExceptionHand) {
        this(filename, inputStream, clazz, limitRows, enableExcelData, true, excelRowReader, excelReaderExceptionHand);
    }

    /**
     * @param filename                 上传的文件名称
     * @param inputStream              上传的文件内容
     * @param clazz                    Excel解析对应的数据类型
     * @param limitRows                读取Excel文件最大行数
     * @param enableExcelData          是否缓存读取的数据结果到内存中
     * @param useCustomReadListener    是否使用默认的ReadListener
     * @param excelRowReader           处理Excel数据行(用于自定义校验)
     * @param excelReaderExceptionHand 处理读取Excel异常
     */
    public ExcelDataReader(
            String filename,
            InputStream inputStream,
            Class<T> clazz,
            int limitRows,
            boolean enableExcelData,
            boolean useCustomReadListener,
            ExcelRowReader<T> excelRowReader,
            ExcelReaderExceptionHand excelReaderExceptionHand) {
        // Assert.hasText(filename, "参数filename不能为空");
        // Assert.notNull(inputStream, "参数inputStream不能为空");
        Assert.notNull(clazz, "参数clazz不能为空");
        Assert.isTrue(enableExcelData || excelRowReader != null, "参数enableExcelData值为false时，excelRowReader参数不能为null");
        this.filename = filename;
        this.inputStream = inputStream;
        this.limitRows = limitRows;
        this.excelReaderBuilder = new ExcelReaderBuilder();
        this.enableExcelData = enableExcelData;
        this.excelRowReader = excelRowReader;
        this.excelReaderExceptionHand = excelReaderExceptionHand;
        init(clazz, useCustomReadListener);
    }

    private void init(Class<T> clazz, boolean useCustomReadListener) {
        excelReaderBuilder.head(clazz);
        excelReaderBuilder.file(inputStream);
        if (useCustomReadListener) {
            excelReaderBuilder.registerReadListener(excelDateReadListener);
        }
        excelReaderBuilder.autoCloseStream(false);
        excelReaderBuilder.ignoreEmptyRow(false);
        excelReaderBuilder.mandatoryUseInputStream(false);
        excelReaderBuilder.useScientificFormat(false);
        excelReaderBuilder.use1904windowing(false);
        excelReaderBuilder.locale(Locale.SIMPLIFIED_CHINESE);
        excelReaderBuilder.autoTrim(true);
        if (Map.class.isAssignableFrom(clazz)) {
            excelReaderBuilder.useDefaultListener(false);
            excelReaderBuilder.headRowNumber(1);
        }
    }

    public ExcelReaderBuilder read() {
        return excelReaderBuilder;
    }

    /**
     * 返回第一个页签数据
     */
    public ExcelData<T> getFirstExcelData() {
        if (excelSheetMap.isEmpty()) {
            return null;
        }
        for (Map.Entry<String, ExcelData<T>> entry : excelSheetMap.entrySet()) {
            return entry.getValue();
        }
        return null;
    }

    /**
     * 根据页签编号返回页签数据
     */
    public ExcelData<T> getExcelData(int sheetNo) {
        for (Map.Entry<String, ExcelData<T>> entry : excelSheetMap.entrySet()) {
            ExcelData<T> excelData = entry.getValue();
            if (excelData != null && Objects.equals(excelData.getSheetNo(), sheetNo)) {
                return excelData;
            }
        }
        return null;
    }

    /**
     * 根据页签名称返回页签数据
     */
    public ExcelData<T> getExcelData(String sheetName) {
        for (Map.Entry<String, ExcelData<T>> entry : excelSheetMap.entrySet()) {
            ExcelData<T> excelData = entry.getValue();
            if (excelData != null && Objects.equals(excelData.getSheetName(), sheetName)) {
                return excelData;
            }
        }
        return null;
    }

    private class ExcelDateReadListener extends AnalysisEventListener<T> {
        @SuppressWarnings("unchecked")
        private ExcelData<T> getExcelData(AnalysisContext context) {
            final Integer sheetNo = context.readSheetHolder().getSheetNo();
            final String sheetName = context.readSheetHolder().getSheetName();
            final Class<T> clazz = context.readSheetHolder().getClazz();
            String key = String.format("%s-%s", sheetNo, sheetName);
            return excelSheetMap.computeIfAbsent(key, s -> new ExcelData<>(clazz, sheetName, sheetNo));
        }

        @Override
        public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
            ExcelData<T> excelData = getExcelData(context);
            if (excelData.getStartTime() == null) {
                excelData.setStartTime(System.currentTimeMillis());
            }
            for (Map.Entry<Integer, String> entry : headMap.entrySet()) {
                Integer index = entry.getKey();
                String head = entry.getValue();
                ExcelHead excelHead;
                if (excelData.getHeads().size() <= index) {
                    excelHead = new ExcelHead(index, head);
                    excelData.getHeads().add(excelHead);
                } else {
                    excelHead = excelData.getHeads().get(index);
                    excelHead.getHeads().add(head);
                }
                ExcelReadHeadProperty headProperty = context.readSheetHolder().getExcelReadHeadProperty();
                if (headProperty != null && headProperty.getContentPropertyMap() != null && headProperty.getContentPropertyMap().containsKey(index)) {
                    ExcelContentProperty property = headProperty.getContentPropertyMap().get(index);
                    Field field = property.getField();
                    if (field != null) {
                        excelHead.setColumnName(field.getName());
                    }
                }
            }
        }

        @SuppressWarnings("DuplicatedCode")
        @Override
        public void invoke(T data, AnalysisContext context) {
            ExcelData<T> excelData = getExcelData(context);
            if (excelData.getStartTime() == null) {
                excelData.setStartTime(System.currentTimeMillis());
            }
            int index = context.readRowHolder().getRowIndex() + 1;
            ExcelRow<T> excelRow = new ExcelRow<>(data, index);
            // 数据签名-防重机制
            Map<Integer, Cell> map = context.readRowHolder().getCellMap();
            StringBuilder sb = new StringBuilder(map.size() * 32);
            for (Map.Entry<Integer, Cell> entry : map.entrySet()) {
                sb.append(entry.getKey()).append("=").append(entry.getValue().toString()).append("|");
            }
            excelRow.setDataSignature(EncodeDecodeUtils.encodeHex(DigestUtils.sha1(sb.toString().getBytes())));
            boolean success = true;
            if (enableExcelData) {
                success = excelData.addRow(excelRow);
            }
            if (!success) {
                log.info("Excel数据导入数据重复，filename={} | data={}", filename, data);
            }
            // 数据校验
            if (enableValidation && !excelRow.hasError() && !(data instanceof Map)) {
                // JSR303校验
                Validator validator = ValidatorFactoryUtils.getValidatorInstance();
                Set<ConstraintViolation<T>> set = validator.validate(excelRow.getData());
                for (ConstraintViolation<T> constraintViolation : set) {
                    excelRow.addErrorInColumn(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
                }
            }
            // 自定义读取行处理逻辑
            if (!excelRow.hasError() && excelRowReader != null) {
                try {
                    excelRowReader.readRow(data, excelRow, context);
                } catch (Throwable e) {
                    excelRow.addErrorInRow(e.getMessage());
                }
            }
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {
            ExcelData<T> excelData = getExcelData(context);
            if (excelData.getEndTime() == null) {
                excelData.setEndTime(System.currentTimeMillis());
            }
            if (excelData.getEndTime() != null && excelData.getStartTime() != null) {
                log.info("Excel Sheet读取完成，sheet={} | 耗时：{}ms", excelData.getSheetName(), excelData.getEndTime() - excelData.getStartTime());
            }
            if (excelRowReader != null) {
                excelRowReader.readEnd(context);
            }
            // if (!enableExcelData) {
            //     excelData.setStartTime(null);
            //     excelData.setEndTime(null);
            // }
        }

        @Override
        public void onException(Exception exception, AnalysisContext context) throws Exception {
            if (excelReaderExceptionHand != null) {
                excelReaderExceptionHand.exceptionHand(exception, context);
            } else {
                // 默认的异常处理
                throw exception;
            }
        }

        @Override
        public boolean hasNext(AnalysisContext context) {
            final ExcelData<T> excelData = getExcelData(context);
            // 是否重复读取
            if (excelData.getEndTime() != null && excelData.getStartTime() != null) {
                log.info("Excel Sheet已经读取完成，当前跳过，sheet={}", excelData.getSheetName());
                return false;
            }
            // 数据是否超出限制 LIMIT_ROWS
            final int rowNum = context.readRowHolder().getRowIndex() + 1;
            final int dataRowNum = rowNum - context.currentReadHolder().excelReadHeadProperty().getHeadRowNumber();
            if (limitRows > 0 && dataRowNum > limitRows) {
                log.info("Excel数据行超出限制：dataRowNum={} | limitRows={}", dataRowNum, limitRows);
                excelData.setInterruptByRowNum(rowNum);
                // 设置已经读取完成
                doAfterAllAnalysed(context);
                return false;
            }
            return true;
        }
    }

    private static MultipartFile getMultipartFile(HttpServletRequest request) {
        if (!(request instanceof MultipartRequest)) {
            throw new ExcelAnalysisException("当前请求未上传文件");
        }
        MultipartRequest multipartRequest = (MultipartRequest) request;
        Map<String, MultipartFile> multipartFileMap = multipartRequest.getFileMap();
        if (multipartFileMap.size() <= 0) {
            throw new ExcelAnalysisException("当前请求未上传文件");
        }
        List<MultipartFile> multipartFileList = new ArrayList<>();
        for (Map.Entry<String, MultipartFile> entry : multipartFileMap.entrySet()) {
            String name = entry.getValue().getOriginalFilename();
            String extension = FilenameUtils.getExtension(name);
            if ("xls".equalsIgnoreCase(extension) || "xlsx".equalsIgnoreCase(extension)) {
                multipartFileList.add(entry.getValue());
            }
        }
        if (multipartFileList.size() <= 0) {
            throw new ExcelAnalysisException("当前请求未上传Excel文件");
        }
        if (multipartFileList.size() >= 2) {
            throw new ExcelAnalysisException("不支持同时导入多个Excel文件");
        }
        return multipartFileList.get(0);
    }

    /**
     * @param request                  上传的文件的请求
     * @param clazz                    Excel解析对应的数据类型
     * @param limitRows                读取Excel文件最大行数
     * @param enableExcelData          是否缓存读取的数据结果到内存中
     * @param excelRowReader           处理Excel数据行(用于自定义校验)
     * @param excelReaderExceptionHand 处理读取Excel异常
     */
    @SneakyThrows
    public static <T> ExcelReaderBuilder read(HttpServletRequest request, Class<T> clazz, int limitRows, boolean enableExcelData, ExcelRowReader<T> excelRowReader, ExcelReaderExceptionHand excelReaderExceptionHand) {
        return new ExcelDataReader<>(request, clazz, limitRows, enableExcelData, excelRowReader, excelReaderExceptionHand).read();
    }

    /**
     * @param request                  上传的文件的请求
     * @param clazz                    Excel解析对应的数据类型
     * @param excelRowReader           处理Excel数据行(用于自定义校验)
     * @param excelReaderExceptionHand 处理读取Excel异常
     */
    @SneakyThrows
    public static <T> ExcelReaderBuilder read(HttpServletRequest request, Class<T> clazz, ExcelRowReader<T> excelRowReader, ExcelReaderExceptionHand excelReaderExceptionHand) {
        return new ExcelDataReader<>(request, clazz, excelRowReader, excelReaderExceptionHand).read();
    }

    /**
     * @param request        上传的文件的请求
     * @param clazz          Excel解析对应的数据类型
     * @param excelRowReader 处理Excel数据行(用于自定义校验)
     */
    @SneakyThrows
    public static <T> ExcelReaderBuilder read(HttpServletRequest request, Class<T> clazz, ExcelRowReader<T> excelRowReader) {
        return new ExcelDataReader<>(request, clazz, excelRowReader).read();
    }

    /**
     * @param request 上传的文件的请求
     * @param clazz   Excel解析对应的数据类型
     */
    @SneakyThrows
    public static <T> ExcelReaderBuilder read(HttpServletRequest request, Class<T> clazz) {
        return new ExcelDataReader<>(request, clazz).read();
    }
}
