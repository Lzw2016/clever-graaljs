package org.clever.graaljs.spring.core.builtin.adapter;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ConverterKeyBuild;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.metadata.Cell;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.property.*;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.metadata.holder.ReadHolder;
import com.alibaba.excel.read.metadata.property.ExcelReadHeadProperty;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.handler.AbstractCellWriteHandler;
import com.alibaba.excel.write.handler.AbstractRowWriteHandler;
import com.alibaba.excel.write.merge.OnceAbsoluteMergeStrategy;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.AbstractVerticalCellStyleStrategy;
import com.alibaba.excel.write.style.column.AbstractHeadColumnWidthStyleStrategy;
import com.alibaba.excel.write.style.row.SimpleRowHeightStyleStrategy;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.clever.graaljs.core.utils.TupleTow;
import org.clever.graaljs.core.utils.codec.DigestUtils;
import org.clever.graaljs.core.utils.codec.EncodeDecodeUtils;
import org.clever.graaljs.spring.core.utils.excel.ExcelDataReader;
import org.clever.graaljs.spring.core.utils.excel.ExcelDataWriter;
import org.clever.graaljs.spring.core.utils.excel.ExcelReaderExceptionHand;
import org.clever.graaljs.spring.core.utils.excel.ExcelRowReader;
import org.clever.graaljs.spring.core.utils.excel.dto.ExcelData;
import org.clever.graaljs.spring.core.utils.excel.dto.ExcelRow;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/28 22:33 <br/>
 */
public class ExcelUtils {
    public static final ExcelUtils Instance = new ExcelUtils();

    private ExcelUtils() {
    }

    @SuppressWarnings("rawtypes")
    @SneakyThrows
    public ExcelDataReader<Map> createReader(ExcelDataReaderConfig config) {
        Assert.notNull(config, "参数config不能为null");
        ExcelDataReader<Map> excelDataReader;
        if (config.getRequest() != null) {
            excelDataReader = new ExcelDataReader<>(
                    config.getRequest(),
                    Map.class,
                    config.limitRows,
                    config.enableExcelData,
                    false,
                    config.excelRowReader,
                    config.excelReaderExceptionHand);
        } else {
            excelDataReader = new ExcelDataReader<>(
                    config.filename,
                    config.inputStream,
                    Map.class,
                    config.limitRows,
                    config.enableExcelData,
                    false,
                    config.excelRowReader,
                    config.excelReaderExceptionHand);
        }
        excelDataReader.setEnableValidation(false);
        ExcelReaderBuilder builder = excelDataReader.read();
        builder.autoCloseStream(config.autoCloseStream);
        if (config.extraRead != null) {
            for (CellExtraTypeEnum typeEnum : config.extraRead) {
                if (typeEnum != null) {
                    builder.extraRead(typeEnum);
                }
            }
        }
        builder.ignoreEmptyRow(config.ignoreEmptyRow);
        builder.mandatoryUseInputStream(config.mandatoryUseInputStream);
        if (config.password != null) {
            builder.password(config.password);
        }
        if (StringUtils.isNotBlank(config.sheetName)) {
            builder.sheet(config.sheetName);
        }
        if (config.sheetNo != null) {
            builder.sheet(config.sheetNo);
        }
        if (config.headRowNumber != null) {
            builder.headRowNumber(config.headRowNumber);
        } else {
            builder.headRowNumber(config.getHeadRowCount());
        }
        builder.useScientificFormat(config.useScientificFormat);
        builder.use1904windowing(config.use1904windowing);
        if (config.locale != null) {
            builder.locale(config.locale);
        }
        builder.autoTrim(config.autoTrim);
        builder.customObject(config.customObject);
        // 自定义解析逻辑
        builder.useDefaultListener(false);
        builder.registerReadListener(new ExcelDateReadListener(config, excelDataReader));
        return excelDataReader;
    }

    public ExcelDataWriter createWriter(ExcelDataWriterConfig config) {
        Assert.notNull(config, "参数config不能为null");
        ExcelDataWriter excelDataWriter;
        if (config.request != null && config.response != null) {
            excelDataWriter = new ExcelDataWriter(config.request, config.response, config.fileName, null);
        } else {
            excelDataWriter = new ExcelDataWriter(config.outputStream, null);
            if (StringUtils.isNotBlank(config.fileName)) {
                excelDataWriter.write().file(config.fileName);
            }
        }
        ExcelWriterBuilder builder = excelDataWriter.write();
        List<List<String>> heads = config.getHeads();
        if (heads.isEmpty() || heads.get(0).isEmpty()) {
            builder.needHead(false);
        } else {
            builder.head(heads);
        }
        builder.autoCloseStream(config.autoCloseStream);
        builder.inMemory(config.inMemory);
        if (StringUtils.isNotBlank(config.template)) {
            builder.withTemplate(config.template);
        }
        if (config.templateInputStream != null) {
            builder.withTemplate(config.templateInputStream);
        }
        builder.automaticMergeHead(config.automaticMergeHead);
        if (!config.excludeColumnFiledNames.isEmpty()) {
            builder.excludeColumnFiledNames(config.excludeColumnFiledNames);
        }
        if (!config.excludeColumnIndexes.isEmpty()) {
            builder.excludeColumnIndexes(config.excludeColumnIndexes);
        }
        if (!config.includeColumnFiledNames.isEmpty()) {
            builder.includeColumnFiledNames(config.includeColumnFiledNames);
        }
        if (!config.includeColumnIndexes.isEmpty()) {
            builder.includeColumnIndexes(config.includeColumnIndexes);
        }
        builder.needHead(config.needHead);
        builder.relativeHeadRowIndex(config.relativeHeadRowIndex);
        builder.useDefaultStyle(config.useDefaultStyle);
        builder.excelType(config.excelType);
        if (config.password != null) {
            builder.password(config.password);
        }
        if (config.sheetNo != null) {
            builder.sheet(config.sheetNo);
        }
        if (StringUtils.isNotBlank(config.sheetName)) {
            builder.sheet(config.sheetName);
        }
        builder.use1904windowing(config.use1904windowing);
        if (config.locale != null) {
            builder.locale(config.locale);
        }
        builder.autoTrim(config.autoTrim);
        // 根据列配置加入各种 WriteHandler 如：AbstractHeadColumnWidthStyleStrategy、AbstractVerticalCellStyleStrategy。参考 AbstractWriteHolder
        builder.registerWriteHandler(new FillHeadStrategy(config));
        boolean hasColumnWidth = false;
        boolean hasStyle = false;
        for (Map.Entry<String, ExcelWriterHeadConfig> entry : config.columns.entrySet()) {
            ExcelWriterHeadConfig headConfig = entry.getValue();
            if (headConfig.columnWidth.columnWidth != null) {
                hasColumnWidth = true;
            }
            if (!hasStyle && (headConfig.headStyle.isSetValue()
                    || headConfig.headFontStyle.isSetValue()
                    || headConfig.contentStyle.isSetValue()
                    || headConfig.contentFontStyle.isSetValue())) {
                hasStyle = true;
            }
            // 应用合并单元格配置
            if (headConfig.contentLoopMerge.isSetValue()) {
                builder.registerWriteHandler(new LoopMergeStrategy(headConfig.contentLoopMerge.eachRow, headConfig.contentLoopMerge.columnExtend, headConfig));
            }
        }
        // 应用列宽配置
        if (hasColumnWidth) {
            builder.registerWriteHandler(new ColumnWidthStyleStrategy());
        }
        // 应用样式配置
        if (hasStyle
                || config.styleConfig.headStyle.isSetValue()
                || config.styleConfig.headFontStyle.isSetValue()
                || config.styleConfig.contentStyle.isSetValue()
                || config.styleConfig.contentFontStyle.isSetValue()) {
            builder.registerWriteHandler(new StyleStrategy());
        }
        // 应用行高配置
        RowHeightProperty headRowHeightProperty = config.styleConfig.headRowHeight.getRowHeightProperty();
        RowHeightProperty contentRowHeightProperty = config.styleConfig.contentRowHeight.getRowHeightProperty();
        Short headRowHeight = headRowHeightProperty.getHeight();
        Short contentRowHeight = contentRowHeightProperty.getHeight();
        if (headRowHeight != null || contentRowHeight != null) {
            builder.registerWriteHandler(new SimpleRowHeightStyleStrategy(headRowHeight, contentRowHeight));
        }
        // 应用OnceAbsoluteMerge配置
        if (config.styleConfig.onceAbsoluteMerge.isSetValue()) {
            OnceAbsoluteMergeProperty onceAbsoluteMergeProperty = config.styleConfig.onceAbsoluteMerge.getOnceAbsoluteMergeProperty();
            builder.registerWriteHandler(new OnceAbsoluteMergeStrategy(onceAbsoluteMergeProperty));
        }
        return excelDataWriter;
    }

    // 配置类
    //----------------------------------------------------------------------------------------------------------------------------------------------

    @Data
    public static class ExcelDataReaderConfig implements Serializable {
        /**
         * Excel文件上传的请求对象
         */
        private HttpServletRequest request;
        /**
         * 上传的Excel文件名称
         */
        private String filename;
        /**
         * 上传的文件数据流
         */
        private InputStream inputStream;
        /**
         * 读取Excel文件最大行数
         */
        private int limitRows = ExcelDataReader.LIMIT_ROWS;
        /**
         * 是否缓存读取的数据结果到内存中(默认启用)
         */
        private boolean enableExcelData = true;
        /**
         * 是否启用数据校验(默认启用)
         */
        private boolean enableValidation = true;
        /**
         * 处理读取Excel异常
         */
        private ExcelReaderExceptionHand excelReaderExceptionHand;
        /**
         * 处理Excel数据行
         */
        @SuppressWarnings("rawtypes")
        private ExcelRowReader<Map> excelRowReader;

        // ----------------------------------------------------------------------

        /**
         * 是否自动关闭输入流
         */
        private boolean autoCloseStream = false;

        /**
         * 读取扩展信息配置
         */
        private CellExtraTypeEnum[] extraRead = new CellExtraTypeEnum[]{};

        /**
         * 是否忽略空行
         */
        private boolean ignoreEmptyRow = false;

        /**
         * 强制使用输入流，如果为false，则将“inputStream”传输到临时文件以提高效率
         */
        private boolean mandatoryUseInputStream = false;

        /**
         * Excel文件密码
         */
        private String password;

        /**
         * Excel页签编号(从0开始)
         */
        private Integer sheetNo;

        /**
         * Excel页签名称(xlsx格式才支持)
         */
        private String sheetName;

        /**
         * 表头行数
         */
        private Integer headRowNumber;

        /**
         * 使用科学格式
         */
        private boolean useScientificFormat = false;

        /**
         * 如果日期使用1904窗口，则为True；如果使用1900日期窗口，则为false
         */
        private boolean use1904windowing = false;

        /**
         * Locale对象表示特定的地理、政治或文化区域。设置日期和数字格式时使用此参数
         */
        private Locale locale = Locale.SIMPLIFIED_CHINESE;

        /**
         * 自动删除空格字符
         */
        private boolean autoTrim = true;

        /**
         * 设置一个自定义对象，可以在侦听器中读取此对象(AnalysisContext.getCustom())
         */
        private Object customObject;

        /**
         * Excel列配置(表头) {@code Map<Entity.propertyName, ExcelReaderHeadConfig>}
         */
        private final LinkedHashMap<String, ExcelReaderHeadConfig> columns = new LinkedHashMap<>();

        /**
         * 返回表头行数
         */
        public int getHeadRowCount() {
            int headRowCount = 0;
            for (Map.Entry<String, ExcelReaderHeadConfig> entry : columns.entrySet()) {
                ExcelReaderHeadConfig headConfig = entry.getValue();
                if (headConfig != null && headConfig.excelProperty.column.size() > headRowCount) {
                    headRowCount = headConfig.excelProperty.column.size();
                }
            }
            return headRowCount;
        }
    }

    @Data
    public static class ExcelDataWriterConfig implements Serializable {
        /**
         * Excel导出请求对象
         */
        private HttpServletRequest request;
        /**
         * Excel导出响应对象
         */
        private HttpServletResponse response;
        /**
         * Excel导出文件名
         */
        private String fileName;
        /**
         * Excel文件对应输出流
         */
        private OutputStream outputStream;
        /**
         * 是否自动关闭输入流
         */
        private boolean autoCloseStream = false;
        /**
         * 在内存中编写excel。默认为false，则创建缓存文件并最终写入excel。仅在内存模式下支持Comment和RichTextString
         */
        private boolean inMemory = false;
        /**
         * Excel模板文件路径
         */
        private String template;
        /**
         * Excel模板文件输入流
         */
        private InputStream templateInputStream;
        /**
         * 写入Excel时出现异常是否仍然继续导出
         */
        private boolean writeExcelOnException = false;
        /**
         * 是否自动合并表头
         */
        private boolean automaticMergeHead = true;
        /**
         * 忽略自定义列
         */
        private final List<String> excludeColumnFiledNames = new ArrayList<>();
        /**
         * 忽略自定义列
         */
        private final List<Integer> excludeColumnIndexes = new ArrayList<>();
        /**
         * 只输出自定义列
         */
        private final List<String> includeColumnFiledNames = new ArrayList<>();
        /**
         * 只输出自定义列
         */
        private final List<Integer> includeColumnIndexes = new ArrayList<>();
        /**
         * 是否输出表头
         */
        private boolean needHead = true;
        /**
         * 输出第一行的位置
         */
        private int relativeHeadRowIndex = 0;
        /**
         * 是否使用默认样式
         */
        private boolean useDefaultStyle = true;
        /**
         * Excel类型
         */
        private ExcelTypeEnum excelType = ExcelTypeEnum.XLSX;
        /**
         * Excel文件密码
         */
        private String password;
        /**
         * Excel页签编号(从0开始)
         */
        private Integer sheetNo;
        /**
         * Excel页签名称(xlsx格式才支持)
         */
        private String sheetName;
        /**
         * 如果日期使用1904窗口，则为True；如果使用1900日期窗口，则为false
         */
        private boolean use1904windowing = false;
        /**
         * Locale对象表示特定的地理、政治或文化区域。设置日期和数字格式时使用此参数
         */
        private Locale locale = Locale.SIMPLIFIED_CHINESE;
        /**
         * 自动删除空格字符
         */
        private boolean autoTrim = true;
        /**
         * Excel表头 {@code Map<Entity.propertyName, ExcelWriterHeadConfig>}
         */
        private final LinkedHashMap<String, ExcelWriterHeadConfig> columns = new LinkedHashMap<>();
        /**
         * 全局样式配置
         */
        private final WriterStyleConfig styleConfig = new WriterStyleConfig();

        public List<TupleTow<String, ExcelWriterHeadConfig>> getHeadConfigs() {
            List<TupleTow<String, ExcelWriterHeadConfig>> list = new ArrayList<>(columns.size());
            columns.forEach((propertyName, headConfig) -> list.add(TupleTow.creat(propertyName, headConfig)));
            list.sort((o1, o2) -> {
                int idx1 = o1.getValue2().excelProperty.index == null ? -1 : o1.getValue2().excelProperty.index;
                int idx2 = o2.getValue2().excelProperty.index == null ? -1 : o2.getValue2().excelProperty.index;
                return Integer.compare(idx1, idx2);
            });
            Integer indexMax = null;
            if (!list.isEmpty()) {
                indexMax = list.get(list.size() - 1).getValue2().excelProperty.index;
            }
            if (indexMax == null) {
                indexMax = 0;
            }
            if (indexMax < list.size()) {
                indexMax = list.size();
            }
            // indexMax += 1;
            // 构造表头
            List<TupleTow<String, ExcelWriterHeadConfig>> headConfigs = new ArrayList<>(indexMax);
            for (int i = 0; i < indexMax; i++) {
                headConfigs.add(null);
            }
            List<TupleTow<String, ExcelWriterHeadConfig>> tmp = new ArrayList<>(list.size());
            // 先设置index有值的Head
            for (TupleTow<String, ExcelWriterHeadConfig> tupleTow : list) {
                String propertyName = tupleTow.getValue1();
                ExcelWriterHeadConfig headConfig = tupleTow.getValue2();
                if (headConfig.excelProperty.index != null && headConfig.excelProperty.index >= 0) {
                    headConfigs.set(headConfig.excelProperty.index, TupleTow.creat(propertyName, headConfig));
                } else {
                    tmp.add(TupleTow.creat(propertyName, headConfig));
                }
            }
            // 再设置其它Head
            for (TupleTow<String, ExcelWriterHeadConfig> tupleTow : tmp) {
                String propertyName = tupleTow.getValue1();
                ExcelWriterHeadConfig headConfig = tupleTow.getValue2();
                for (int i = 0; i < headConfigs.size(); i++) {
                    if (headConfigs.get(i) == null) {
                        headConfigs.set(i, TupleTow.creat(propertyName, headConfig));
                        break;
                    }
                }
            }
            // 最后填充heads
            for (int i = 0; i < headConfigs.size(); i++) {
                if (headConfigs.get(i) == null) {
                    TupleTow<String, ExcelWriterHeadConfig> tupleTow = TupleTow.creat(null, new ExcelWriterHeadConfig(""));
                    headConfigs.set(i, tupleTow);
                    break;
                }
            }
            return headConfigs;
        }

        public List<List<String>> getHeads() {
            List<TupleTow<String, ExcelWriterHeadConfig>> headConfigs = getHeadConfigs();
            return headConfigs.stream().filter(Objects::nonNull).map(tupleTow -> tupleTow.getValue2().excelProperty.column).collect(Collectors.toList());
        }
    }

    @Data
    public static class ExcelProperty implements Serializable {
        /**
         * 列名称
         */
        private final List<String> column = new ArrayList<>();
        /**
         * 是否忽略当前列
         */
        private Boolean ignore;

        /**
         * 列的索引在列的索引上读写，如果等于-1，则按Java类排序。优先级：index>默认排序
         */
        private Integer index = -1;
    }

    @Data
    public static class DateTimeFormat implements Serializable {
        /**
         * 时间格式化的格式定义
         */
        private String dateFormat;

        /**
         * 如果日期使用1904窗口，则为True；如果使用1900日期窗口，则为false
         */
        private Boolean use1904windowing;

        /**
         * 是否设置过值
         */
        public boolean isSetValue() {
            return dateFormat != null || use1904windowing != null;
        }

        public DateTimeFormatProperty getDateTimeFormatProperty() {
            return new DateTimeFormatProperty(dateFormat, use1904windowing);
        }
    }

    @Data
    public static class NumberFormat implements Serializable {
        /**
         * 数字格式化
         */
        private String numberFormat;

        /**
         * 四舍五入模式
         */
        private RoundingMode roundingMode;

        /**
         * 是否设置过值
         */
        public boolean isSetValue() {
            return numberFormat != null || roundingMode != null;
        }

        public NumberFormatProperty getNumberFormatProperty() {
            return new NumberFormatProperty(numberFormat, roundingMode);
        }
    }

    @Data
    public static class ColumnWidth implements Serializable {
        /**
         * 列宽
         */
        private Integer columnWidth;
    }

    @Data
    public static class ExcelFontStyle implements Serializable {
        /**
         * 字体的名称（如: Arial）
         */
        private String fontName;

        /**
         * 以熟悉的测量单位表示的高度- points
         */
        private Short fontHeightInPoints;

        /**
         * 是否使用斜体
         */
        private Boolean italic;

        /**
         * 是否在文本中使用删除线水平线
         */
        private Boolean strikeout;

        /**
         * 字体的颜色
         */
        private Short color;

        /**
         * 设置normal、super或subscript
         */
        private Short typeOffset;

        /**
         * 要使用的文本下划线
         */
        private Byte underline;

        /**
         * 设置要使用的字符集
         */
        private Integer charset;

        /**
         * 粗体
         */
        private Boolean bold;

        /**
         * 是否设置过值
         */
        public boolean isSetValue() {
            return fontName != null || fontHeightInPoints != null || italic != null || strikeout != null
                    || color != null || typeOffset != null || underline != null
                    || charset != null || bold != null;
        }

        public FontProperty getFontProperty() {
            FontProperty fontProperty = new FontProperty();
            fontProperty.setFontName(fontName);
            fontProperty.setFontHeightInPoints(fontHeightInPoints);
            fontProperty.setItalic(italic);
            fontProperty.setStrikeout(strikeout);
            fontProperty.setColor(color);
            fontProperty.setTypeOffset(typeOffset);
            fontProperty.setUnderline(underline);
            fontProperty.setCharset(charset);
            fontProperty.setBold(bold);
            return fontProperty;
        }

        public FontProperty getFontProperty(ExcelFontStyle second) {
            FontProperty fontProperty = new FontProperty();
            fontProperty.setFontName(fontName == null ? second.fontName : fontName);
            fontProperty.setFontHeightInPoints(fontHeightInPoints == null ? second.fontHeightInPoints : fontHeightInPoints);
            fontProperty.setItalic(italic == null ? second.italic : italic);
            fontProperty.setStrikeout(strikeout == null ? second.strikeout : strikeout);
            fontProperty.setColor(color == null ? second.color : color);
            fontProperty.setTypeOffset(typeOffset == null ? second.typeOffset : typeOffset);
            fontProperty.setUnderline(underline == null ? second.underline : underline);
            fontProperty.setCharset(charset == null ? second.charset : charset);
            fontProperty.setBold(bold == null ? second.bold : bold);
            return fontProperty;
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class ContentFontStyle extends ExcelFontStyle {
    }

    @Data
    public static class ContentLoopMerge implements Serializable {
        /**
         * 行
         */
        private Integer eachRow;

        /**
         * 列
         */
        private Integer columnExtend;

        /**
         * 是否设置过值
         */
        public boolean isSetValue() {
            return eachRow != null || columnExtend != null;
        }

        // public LoopMergeProperty getLoopMergeProperty() {
        //     return new LoopMergeProperty(eachRow, columnExtend);
        // }
    }

    @Data
    public static class ContentRowHeight implements Serializable {
        /**
         * 行高
         */
        private Short rowHeight;

        public RowHeightProperty getRowHeightProperty() {
            return new RowHeightProperty(rowHeight);
        }
    }

    @Data
    public static class ExcelCellStyle implements Serializable {
        /**
         * 设置数据格式（必须是有效格式）。内置格式在内置信息中定义 {@link BuiltinFormats}.
         */
        private Short dataFormat;

        /**
         * 将单元格使用此样式设置为隐藏
         */
        private Boolean hidden;

        /**
         * 将单元格使用此样式设置为锁定
         */
        private Boolean locked;

        /**
         * 打开或关闭样式的“Quote Prefix”或“123 Prefix”，
         * 用于告诉Excel，看起来像数字或公式的内容不应被视为打开。
         * 打开此选项有点（但不是完全打开，请参见IgnoredErrorType）类似于在Excel中为单元格值添加前缀
         * {@link IgnoredErrorType})
         */
        private Boolean quotePrefix;

        /**
         * 设置单元格的水平对齐方式
         */
        private HorizontalAlignment horizontalAlignment;

        /**
         * 设置是否应该换行。将此标志设置为true可以通过在多行上显示所有内容来使其在一个单元格中可见
         */
        private Boolean wrapped;

        /**
         * 设置单元格的垂直对齐方式
         */
        private VerticalAlignment verticalAlignment;

        /**
         * 设置单元格中文本的旋转度<br />
         * 注意：HSSF使用-90至90度的值，而XSSF使用0至180度的值。
         * 此方法的实现将在这两个值范围之间进行映射，
         * 但是，相应的getter返回此CellStyle所应用的当前Excel文件格式类型所要求的范围内的值。
         */
        private Short rotation;

        /**
         * 设置空格数以缩进单元格中的文本
         */
        private Short indent;

        /**
         * 设置要用于单元格左边框的边框类型
         */
        private BorderStyle borderLeft;

        /**
         * 设置用于单元格右边框的边框类型
         */
        private BorderStyle borderRight;

        /**
         * 设置要用于单元格顶部边框的边框类型
         */
        private BorderStyle borderTop;

        /**
         * 设置用于单元格底部边框的边框类型
         */
        private BorderStyle borderBottom;

        /**
         * 设置用于左边框的颜色
         *
         * @see IndexedColors
         */
        private Short leftBorderColor;

        /**
         * 设置用于右边框的颜色
         *
         * @see IndexedColors
         */
        private Short rightBorderColor;

        /**
         * 设置要用于顶部边框的颜色
         *
         * @see IndexedColors
         */
        private Short topBorderColor;

        /**
         * 设置用于底边框的颜色
         *
         * @see IndexedColors
         */
        private Short bottomBorderColor;

        /**
         * 设置为1会使单元格充满前景色...不知道其他值
         *
         * @see FillPatternType#SOLID_FOREGROUND
         */
        private FillPatternType fillPatternType;

        /**
         * 设置背景填充颜色
         *
         * @see IndexedColors
         */
        private Short fillBackgroundColor;

        /**
         * 设置前景色填充颜色<br />
         * 注意：确保将前景色设置为背景颜色之前
         *
         * @see IndexedColors
         */
        private Short fillForegroundColor;

        /**
         * 控制如果文本太长，是否应自动调整单元格的大小以缩小以适合
         */
        private Boolean shrinkToFit;

        /**
         * 是否设置过值
         */
        public boolean isSetValue() {
            return dataFormat != null || hidden != null || locked != null || quotePrefix != null
                    || horizontalAlignment != null || wrapped != null || verticalAlignment != null
                    || rotation != null || indent != null || borderLeft != null
                    || borderRight != null || borderTop != null || borderBottom != null
                    || leftBorderColor != null || rightBorderColor != null || topBorderColor != null
                    || bottomBorderColor != null || fillPatternType != null || fillBackgroundColor != null
                    || fillForegroundColor != null || shrinkToFit != null;
        }

        public StyleProperty getStyleProperty() {
            StyleProperty styleProperty = new StyleProperty();
            styleProperty.setDataFormat(dataFormat);
            // styleProperty.setWriteFont();
            styleProperty.setHidden(hidden);
            styleProperty.setLocked(locked);
            styleProperty.setQuotePrefix(quotePrefix);
            styleProperty.setHorizontalAlignment(horizontalAlignment);
            styleProperty.setWrapped(wrapped);
            styleProperty.setVerticalAlignment(verticalAlignment);
            styleProperty.setRotation(rotation);
            styleProperty.setIndent(indent);
            styleProperty.setBorderLeft(borderLeft);
            styleProperty.setBorderRight(borderRight);
            styleProperty.setBorderTop(borderTop);
            styleProperty.setBorderBottom(borderBottom);
            styleProperty.setLeftBorderColor(leftBorderColor);
            styleProperty.setRightBorderColor(rightBorderColor);
            styleProperty.setTopBorderColor(topBorderColor);
            styleProperty.setBottomBorderColor(bottomBorderColor);
            styleProperty.setFillPatternType(fillPatternType);
            styleProperty.setFillBackgroundColor(fillBackgroundColor);
            styleProperty.setFillForegroundColor(fillForegroundColor);
            styleProperty.setShrinkToFit(shrinkToFit);
            return styleProperty;
        }

        public StyleProperty getStyleProperty(ExcelCellStyle second) {
            StyleProperty styleProperty = new StyleProperty();
            styleProperty.setDataFormat(dataFormat == null ? second.dataFormat : dataFormat);
            // styleProperty.setWriteFont();
            styleProperty.setHidden(hidden == null ? second.hidden : hidden);
            styleProperty.setLocked(locked == null ? second.locked : locked);
            styleProperty.setQuotePrefix(quotePrefix == null ? second.quotePrefix : quotePrefix);
            styleProperty.setHorizontalAlignment(horizontalAlignment == null ? second.horizontalAlignment : horizontalAlignment);
            styleProperty.setWrapped(wrapped == null ? second.wrapped : wrapped);
            styleProperty.setVerticalAlignment(verticalAlignment == null ? second.verticalAlignment : verticalAlignment);
            styleProperty.setRotation(rotation == null ? second.rotation : rotation);
            styleProperty.setIndent(indent == null ? second.indent : indent);
            styleProperty.setBorderLeft(borderLeft == null ? second.borderLeft : borderLeft);
            styleProperty.setBorderRight(borderRight == null ? second.borderRight : borderRight);
            styleProperty.setBorderTop(borderTop == null ? second.borderTop : borderTop);
            styleProperty.setBorderBottom(borderBottom == null ? second.borderBottom : borderBottom);
            styleProperty.setLeftBorderColor(leftBorderColor == null ? second.leftBorderColor : leftBorderColor);
            styleProperty.setRightBorderColor(rightBorderColor == null ? second.rightBorderColor : rightBorderColor);
            styleProperty.setTopBorderColor(topBorderColor == null ? second.topBorderColor : topBorderColor);
            styleProperty.setBottomBorderColor(bottomBorderColor == null ? second.bottomBorderColor : bottomBorderColor);
            styleProperty.setFillPatternType(fillPatternType == null ? second.fillPatternType : fillPatternType);
            styleProperty.setFillBackgroundColor(fillBackgroundColor == null ? second.fillBackgroundColor : fillBackgroundColor);
            styleProperty.setFillForegroundColor(fillForegroundColor == null ? second.fillForegroundColor : fillForegroundColor);
            styleProperty.setShrinkToFit(shrinkToFit == null ? second.shrinkToFit : shrinkToFit);
            return styleProperty;
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class ContentStyle extends ExcelCellStyle {
        /**
         * 是否设置过值
         */
        @Override
        public boolean isSetValue() {
            return super.isSetValue();
        }

        @Override
        public StyleProperty getStyleProperty() {
            return super.getStyleProperty();
        }

        public StyleProperty getStyleProperty(ContentStyle second) {
            return super.getStyleProperty(second);
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class HeadFontStyle extends ExcelFontStyle {
        /**
         * 是否设置过值
         */
        @Override
        public boolean isSetValue() {
            return super.isSetValue();
        }

        @Override
        public FontProperty getFontProperty() {
            return super.getFontProperty();
        }

        public FontProperty getFontProperty(HeadFontStyle second) {
            return super.getFontProperty(second);
        }
    }

    @Data
    public static class HeadRowHeight implements Serializable {
        /**
         * Head行高
         */
        private Short headRowHeight;

        public RowHeightProperty getRowHeightProperty() {
            return new RowHeightProperty(headRowHeight);
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class HeadStyle extends ExcelCellStyle {
        /**
         * 是否设置过值
         */
        @Override
        public boolean isSetValue() {
            return super.isSetValue();
        }

        @Override
        public StyleProperty getStyleProperty() {
            return super.getStyleProperty();
        }

        public StyleProperty getStyleProperty(HeadStyle second) {
            return super.getStyleProperty(second);
        }
    }

    @Data
    public static class OnceAbsoluteMerge implements Serializable {
        /**
         * 第一行
         */
        private Integer firstRowIndex;

        /**
         * 最后一行
         */
        private Integer lastRowIndex;

        /**
         * 第一列
         */
        private Integer firstColumnIndex;

        /**
         * 最后一列
         */
        private Integer lastColumnIndex;

        /**
         * 是否设置过值
         */
        public boolean isSetValue() {
            return firstRowIndex != null || lastRowIndex != null || firstColumnIndex != null || lastColumnIndex != null;
        }

        public OnceAbsoluteMergeProperty getOnceAbsoluteMergeProperty() {
            int firstRowIndex = this.firstRowIndex == null ? -1 : this.firstRowIndex;
            int lastRowIndex = this.lastRowIndex == null ? -1 : this.lastRowIndex;
            int firstColumnIndex = this.firstColumnIndex == null ? -1 : this.firstColumnIndex;
            int lastColumnIndex = this.lastColumnIndex == null ? -1 : this.lastColumnIndex;
            return new OnceAbsoluteMergeProperty(firstRowIndex, lastRowIndex, firstColumnIndex, lastColumnIndex);
        }
    }

    @NoArgsConstructor
    @Data
    public static class ExcelReaderHeadConfig implements Serializable {
        /**
         * 列的数据类型
         */
        private Class<?> dataType;
        private final ExcelProperty excelProperty = new ExcelProperty();
        private final DateTimeFormat dateTimeFormat = new DateTimeFormat();
        private final NumberFormat numberFormat = new NumberFormat();

        public ExcelReaderHeadConfig(Class<?> dataType, String... names) {
            this.dataType = dataType;
            if (names != null) {
                this.excelProperty.column.addAll(Arrays.asList(names));
            }
        }
    }

    @NoArgsConstructor
    @Data
    public static class ExcelWriterHeadConfig implements Serializable {
        private final ExcelProperty excelProperty = new ExcelProperty();
        private final DateTimeFormat dateTimeFormat = new DateTimeFormat();
        private final NumberFormat numberFormat = new NumberFormat();
        private final ColumnWidth columnWidth = new ColumnWidth();

        private final HeadFontStyle headFontStyle = new HeadFontStyle();
        private final HeadStyle headStyle = new HeadStyle();

        private final ContentFontStyle contentFontStyle = new ContentFontStyle();
        private final ContentStyle contentStyle = new ContentStyle();

        private final ContentLoopMerge contentLoopMerge = new ContentLoopMerge();

        public ExcelWriterHeadConfig(String... names) {
            if (names != null) {
                this.excelProperty.column.addAll(Arrays.asList(names));
            }
        }
    }

    @Data
    public static class WriterStyleConfig implements Serializable {
        private final HeadRowHeight headRowHeight = new HeadRowHeight();
        private final ContentRowHeight contentRowHeight = new ContentRowHeight();

        private final HeadFontStyle headFontStyle = new HeadFontStyle();
        private final HeadStyle headStyle = new HeadStyle();

        private final ContentFontStyle contentFontStyle = new ContentFontStyle();
        private final ContentStyle contentStyle = new ContentStyle();

        private final OnceAbsoluteMerge onceAbsoluteMerge = new OnceAbsoluteMerge();
    }

    // 自定义读取、写入操作
    //----------------------------------------------------------------------------------------------------------------------------------------------

    @SuppressWarnings("rawtypes")
    @Slf4j
    private static class ExcelDateReadListener extends AnalysisEventListener<Map<Integer, CellData<?>>> {
        private final ExcelDataReaderConfig config;
        private final ExcelDataReader<Map> excelDataReader;
        private final Map<Integer, List<String>> headsMap = new HashMap<>();
        /**
         * {@code Map<index, TupleTow<ExcelReaderHeadConfig, Entity.propertyName>>}
         */
        private Map<Integer, TupleTow<String, ExcelReaderHeadConfig>> columns;

        public ExcelDateReadListener(ExcelDataReaderConfig config, ExcelDataReader<Map> excelDataReader) {
            Assert.notNull(config, "参数config不能为null");
            Assert.notNull(excelDataReader, "参数excelDataReader不能为null");
            this.config = config;
            this.excelDataReader = excelDataReader;
        }

        private ExcelData<Map> getExcelData(AnalysisContext context) {
            final Integer sheetNo = context.readSheetHolder().getSheetNo();
            final String sheetName = context.readSheetHolder().getSheetName();
            String key = String.format("%s-%s", sheetNo, sheetName);
            return excelDataReader.getExcelSheetMap().computeIfAbsent(key, sheetKey -> new ExcelData<>(Map.class, sheetName, sheetNo));
        }

        private Class<?> getCellDataType(CellData<?> cellData) {
            if (cellData.getType() == null) {
                return Void.class;
            }
            switch (cellData.getType()) {
                case NUMBER:
                    return BigDecimal.class;
                case BOOLEAN:
                    return Boolean.class;
                case DIRECT_STRING:
                case STRING:
                case ERROR:
                    return String.class;
                case IMAGE:
                    return Byte[].class;
                default:
                    return Void.class;
            }
        }

        // 解析表头配置
        public void parseHeadMap(AnalysisContext context) {
            columns = new HashMap<>();
            LinkedHashMap<String, ExcelReaderHeadConfig> columnsConfig = config.columns;
            Set<String> propertyNameParsed = new HashSet<>(columnsConfig.size());
            for (Map.Entry<Integer, List<String>> entry : headsMap.entrySet()) {
                int index = entry.getKey();
                List<String> heads = entry.getValue();
                String headsStr = StringUtils.join(heads, "|");
                String propertyName = null;
                ExcelReaderHeadConfig headConfig = null;
                for (Map.Entry<String, ExcelReaderHeadConfig> configEntry : columnsConfig.entrySet()) {
                    String propertyNameTmp = configEntry.getKey();
                    ExcelReaderHeadConfig headConfigTmp = configEntry.getValue();
                    // 根据index匹配
                    if (Objects.equals(index, headConfigTmp.excelProperty.index)) {
                        propertyName = propertyNameTmp;
                        headConfig = headConfigTmp;
                        break;
                    }
                    // 根据column(表头列名)匹配
                    if (propertyNameParsed.contains(propertyNameTmp)) {
                        continue;
                    }
                    String columnStr = StringUtils.join(headConfigTmp.excelProperty.column, "|");
                    if (headsStr.endsWith(columnStr) || columnStr.endsWith(headsStr)) {
                        propertyNameParsed.add(propertyNameTmp);
                        propertyName = propertyNameTmp;
                        headConfig = headConfigTmp;
                        headConfig.excelProperty.index = index;
                        break;
                    }
                }
                if (propertyName == null) {
                    continue;
                }
                columns.put(index, TupleTow.creat(propertyName, headConfig));
                // 格式化配置
                boolean useDateTimeFormat = headConfig.dateTimeFormat.isSetValue();
                boolean useNumberFormat = headConfig.numberFormat.isSetValue();
                ExcelReadHeadProperty excelReadHeadProperty = context.currentReadHolder().excelReadHeadProperty();
                if ((useDateTimeFormat || useNumberFormat) && excelReadHeadProperty != null && excelReadHeadProperty.getContentPropertyMap() != null) {
                    ExcelContentProperty property = excelReadHeadProperty.getContentPropertyMap().computeIfAbsent(index, idx -> new ExcelContentProperty());
                    if (useDateTimeFormat) {
                        property.setDateTimeFormatProperty(headConfig.dateTimeFormat.getDateTimeFormatProperty());
                    }
                    if (useNumberFormat) {
                        property.setNumberFormatProperty(headConfig.numberFormat.getNumberFormatProperty());
                    }
                }
            }
            Assert.notEmpty(columns, "无法解析Excel表头，请查看配置是否正确");
        }

        @Override
        public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
            ExcelData<Map> excelData = getExcelData(context);
            if (excelData.getStartTime() == null) {
                excelData.setStartTime(System.currentTimeMillis());
            }
            for (Map.Entry<Integer, String> entry : headMap.entrySet()) {
                Integer index = entry.getKey();
                String head = entry.getValue();
                List<String> list = headsMap.computeIfAbsent(index, idx -> new ArrayList<>());
                list.add(head);
            }
        }

        @SuppressWarnings({"unchecked", "DuplicatedCode"})
        @Override
        public void invoke(Map<Integer, CellData<?>> data, AnalysisContext context) {
            // 第一次需要解析表头
            if (columns == null) {
                parseHeadMap(context);
            }
            ExcelData<Map> excelData = getExcelData(context);
            if (excelData.getStartTime() == null) {
                excelData.setStartTime(System.currentTimeMillis());
            }
            int index = context.readRowHolder().getRowIndex() + 1;
            ExcelRow<Map> excelRow = new ExcelRow<>(new HashMap(data.size()), index);
            // 数据签名-防重机制
            Map<Integer, Cell> map = context.readRowHolder().getCellMap();
            StringBuilder sb = new StringBuilder(map.size() * 32);
            for (Map.Entry<Integer, Cell> entry : map.entrySet()) {
                sb.append(entry.getKey()).append("=").append(entry.getValue().toString()).append("|");
            }
            excelRow.setDataSignature(EncodeDecodeUtils.encodeHex(DigestUtils.sha1(sb.toString().getBytes())));
            // 读取数据需要类型转换
            ReadHolder currentReadHolder = context.currentReadHolder();
            ExcelReadHeadProperty excelReadHeadProperty = context.currentReadHolder().excelReadHeadProperty();
            Map<Integer, ExcelContentProperty> contentPropertyMap = excelReadHeadProperty.getContentPropertyMap();
            for (Map.Entry<Integer, CellData<?>> entry : data.entrySet()) {
                Integer idx = entry.getKey();
                CellData<?> cellData = entry.getValue();
                TupleTow<String, ExcelReaderHeadConfig> tupleTow = columns.get(idx);
                if (tupleTow == null) {
                    continue;
                }
                String propertyName = tupleTow.getValue1();
                ExcelReaderHeadConfig headConfig = tupleTow.getValue2();
                // 忽略当前字段(propertyName)
                if (Objects.equals(headConfig.excelProperty.ignore, Boolean.TRUE)) {
                    continue;
                }
                // 获取字段类型
                if (headConfig.dataType == null) {
                    headConfig.dataType = getCellDataType(cellData);
                }
                // 获取字段值
                Object value;
                if (Objects.equals(Void.class, headConfig.dataType)) {
                    value = null;
                } else {
                    // 格式化操作 dateFormat numberFormat
                    ExcelContentProperty excelContentProperty = contentPropertyMap.get(idx);
                    value = ConverterUtils.convertToJavaObject(
                            cellData,
                            headConfig.dataType,
                            excelContentProperty,
                            currentReadHolder.converterMap(),
                            currentReadHolder.globalConfiguration(),
                            context.readRowHolder().getRowIndex(),
                            idx);
                }
                // 写入字段值
                excelRow.getData().put(propertyName, value);
            }
            boolean success = true;
            final boolean enableExcelData = config.isEnableExcelData();
            if (enableExcelData) {
                success = excelData.addRow(excelRow);
            }
            if (!success) {
                log.info("Excel数据导入数据重复，filename={} | data={}", config.getFilename(), data);
            }
            // 数据校验
            //final boolean enableValidation = config.isEnableValidation();
            //if (enableValidation && !excelRow.hasError()) {
            //    // TODO 数据校验
            //}
            // 自定义读取行处理逻辑
            final ExcelRowReader<Map> excelRowReader = config.getExcelRowReader();
            if (!excelRow.hasError() && excelRowReader != null) {
                try {
                    excelRowReader.readRow(excelRow.getData(), excelRow, context);
                } catch (Throwable e) {
                    excelRow.addErrorInRow(e.getMessage());
                }
            }
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {
            ExcelData<Map> excelData = getExcelData(context);
            if (excelData.getEndTime() == null) {
                excelData.setEndTime(System.currentTimeMillis());
            }
            if (excelData.getEndTime() != null && excelData.getStartTime() != null) {
                log.info("Excel Sheet读取完成，sheet={} | 耗时：{}ms", excelData.getSheetName(), excelData.getEndTime() - excelData.getStartTime());
            }
            ExcelRowReader<Map> excelRowReader = config.getExcelRowReader();
            if (excelRowReader != null) {
                excelRowReader.readEnd(context);
            }
            // 清空表头解析数据
            columns = null;
            headsMap.clear();
        }

        @Override
        public void onException(Exception exception, AnalysisContext context) throws Exception {
            ExcelReaderExceptionHand excelReaderExceptionHand = config.getExcelReaderExceptionHand();
            if (excelReaderExceptionHand != null) {
                excelReaderExceptionHand.exceptionHand(exception, context);
            } else {
                // 默认的异常处理
                throw exception;
            }
        }

        @Override
        public boolean hasNext(AnalysisContext context) {
            // 未配置列 - 提前退出
            if (context.readSheetHolder().getHeadRowNumber() > 0 && columns != null && columns.isEmpty()) {
                log.warn("未匹配到列配置");
                return false;

            }
            final ExcelData<Map> excelData = getExcelData(context);
            // 是否重复读取
            if (excelData.getEndTime() != null && excelData.getStartTime() != null) {
                log.info("Excel Sheet已经读取完成，当前跳过，sheet={}", excelData.getSheetName());
                return false;
            }
            // 数据是否超出限制 LIMIT_ROWS
            final int limitRows = config.getLimitRows();
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

    public static class ConverterUtils {
        private ConverterUtils() {
        }

        @SuppressWarnings("rawtypes")
        public static Object convertToJavaObject(
                CellData<?> cellData,
                Class<?> clazz,
                ExcelContentProperty contentProperty,
                Map<String, Converter> converterMap,
                GlobalConfiguration globalConfiguration,
                Integer rowIndex,
                Integer columnIndex) {
            if (clazz == null) {
                clazz = String.class;
            }
            if (Objects.equals(cellData.getType(), CellDataTypeEnum.EMPTY)) {
                if (Objects.equals(String.class, clazz)) {
                    return StringUtils.EMPTY;
                } else {
                    return null;
                }
            }
            Converter<?> converter = null;
            if (contentProperty != null) {
                converter = contentProperty.getConverter();
            }
            if (converter == null) {
                converter = converterMap.get(ConverterKeyBuild.buildKey(clazz, cellData.getType()));
            }
            if (converter == null) {
                throw new ExcelDataConvertException(rowIndex, columnIndex, cellData, contentProperty, "Converter not found, convert " + cellData.getType() + " to " + clazz.getName());
            }
            try {
                return converter.convertToJavaData(cellData, contentProperty, globalConfiguration);
            } catch (Exception e) {
                throw new ExcelDataConvertException(rowIndex, columnIndex, cellData, contentProperty, "Convert data " + cellData + " to " + clazz + " error ", e);
            }
        }
    }

    @Slf4j
    private static class FillHeadStrategy extends AbstractCellWriteHandler {
        private final ExcelDataWriterConfig config;
        // 保存已填充的表头index Map<index, filled>
        private final Map<Integer, Boolean> filledMap = new HashMap<>();
        // 保存已处理的属性
        private final Set<String> propertyNameParsed = new HashSet<>();

        public FillHeadStrategy(ExcelDataWriterConfig config) {
            Assert.notNull(config, "参数config不能为null");
            this.config = config;
        }

        @Override
        public void beforeCellCreate(
                WriteSheetHolder writeSheetHolder,
                WriteTableHolder writeTableHolder,
                Row row,
                Head head,
                Integer columnIndex,
                Integer relativeRowIndex,
                Boolean isHead) {
            boolean filled = filledMap.computeIfAbsent(columnIndex, idx -> false);
            if (filled) {
                return;
            }
            filledMap.put(columnIndex, true);
            if (config.columns.isEmpty() || head == null || head.getHeadNameList() == null) {
                return;
            }
            // Excel表头 Map<Entity.propertyName, ExcelWriterHeadConfig>
            ExcelWriterHeadConfig headConfig = null;
            String headsStr = StringUtils.join(head.getHeadNameList(), "|");
            for (Map.Entry<String, ExcelWriterHeadConfig> entry : config.columns.entrySet()) {
                String propertyNameTmp = entry.getKey();
                ExcelWriterHeadConfig headConfigTmp = entry.getValue();
                // 根据index匹配
                if (Objects.equals(headConfigTmp.excelProperty.index, columnIndex)) {
                    propertyNameParsed.add(propertyNameTmp);
                    headConfig = headConfigTmp;
                    break;
                }
                // 根据column(表头列名)匹配
                if (propertyNameParsed.contains(propertyNameTmp)) {
                    continue;
                }
                String columnStr = StringUtils.join(headConfigTmp.excelProperty.column, "|");
                if (headsStr.endsWith(columnStr) || columnStr.endsWith(headsStr)) {
                    propertyNameParsed.add(propertyNameTmp);
                    headConfig = headConfigTmp;
                    headConfig.excelProperty.index = columnIndex;
                    break;
                }
            }
            if (headConfig == null) {
                return;
            }
            if (headConfig.columnWidth.columnWidth != null) {
                head.setColumnWidthProperty(new ColumnWidthProperty(headConfig.columnWidth.columnWidth));
            }
            // 合并配置
            head.setHeadStyleProperty(headConfig.headStyle.getStyleProperty(config.styleConfig.headStyle));
            head.setHeadFontProperty(headConfig.headFontStyle.getFontProperty(config.styleConfig.headFontStyle));
            head.setContentStyleProperty(headConfig.contentStyle.getStyleProperty(config.styleConfig.contentStyle));
            head.setContentFontProperty(headConfig.contentFontStyle.getFontProperty(config.styleConfig.contentFontStyle));
            // 格式化配置
            boolean useDateTimeFormat = headConfig.dateTimeFormat.isSetValue();
            boolean useNumberFormat = headConfig.numberFormat.isSetValue();
            if ((useDateTimeFormat || useNumberFormat) && writeSheetHolder.getExcelWriteHeadProperty() != null && writeSheetHolder.getExcelWriteHeadProperty().getContentPropertyMap() != null) {
                ExcelContentProperty property = writeSheetHolder.getExcelWriteHeadProperty().getContentPropertyMap().computeIfAbsent(columnIndex, idx -> new ExcelContentProperty());
                if (useDateTimeFormat) {
                    property.setDateTimeFormatProperty(headConfig.dateTimeFormat.getDateTimeFormatProperty());
                }
                if (useNumberFormat) {
                    property.setNumberFormatProperty(headConfig.numberFormat.getNumberFormatProperty());
                }
            }
        }
    }

    private static class ColumnWidthStyleStrategy extends AbstractHeadColumnWidthStyleStrategy {
        @Override
        protected Integer columnWidth(Head head, Integer columnIndex) {
            if (head == null) {
                return null;
            }
            if (head.getColumnWidthProperty() != null) {
                return head.getColumnWidthProperty().getWidth();
            }
            return null;
        }
    }

    private static class StyleStrategy extends AbstractVerticalCellStyleStrategy {
        @Override
        protected WriteCellStyle headCellStyle(Head head) {
            return build(head.getHeadStyleProperty(), head.getHeadFontProperty());
        }

        @Override
        protected WriteCellStyle contentCellStyle(Head head) {
            return build(head.getContentStyleProperty(), head.getContentFontProperty());
        }
    }

    private static class LoopMergeStrategy extends AbstractRowWriteHandler {
        /**
         * 每一行
         */
        private final int eachRow;
        /**
         * 延伸栏
         */
        private final int columnExtend;
        /**
         * 当前列数
         */
        private Integer columnIndex;

        private final ExcelWriterHeadConfig headConfig;

        public LoopMergeStrategy(int eachRow, int columnExtend, ExcelWriterHeadConfig headConfig) {
            this.eachRow = eachRow;
            this.columnExtend = columnExtend;
            this.headConfig = headConfig;
            this.columnIndex = headConfig.excelProperty.index;
        }

        @Override
        public void afterRowDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Integer relativeRowIndex, Boolean isHead) {
            if (isHead) {
                return;
            }
            if (this.columnIndex == null || this.columnIndex < 0) {
                columnIndex = headConfig.excelProperty.index;
                if (columnIndex == null) {
                    return;
                }
            }
            if (relativeRowIndex % eachRow == 0) {
                CellRangeAddress cellRangeAddress = new CellRangeAddress(
                        row.getRowNum(),
                        row.getRowNum() + eachRow - 1,
                        columnIndex,
                        columnIndex + columnExtend - 1
                );
                writeSheetHolder.getSheet().addMergedRegionUnsafe(cellRangeAddress);
            }
        }
    }

    private static WriteCellStyle build(StyleProperty styleProperty, FontProperty fontProperty) {
        if (styleProperty == null && fontProperty == null) {
            return null;
        }
        WriteCellStyle writeCellStyle = new WriteCellStyle();
        if (styleProperty != null) {
            if (styleProperty.getDataFormat() != null && styleProperty.getDataFormat() >= 0) {
                writeCellStyle.setDataFormat(styleProperty.getDataFormat());
            }
            writeCellStyle.setHidden(styleProperty.getHidden());
            writeCellStyle.setLocked(styleProperty.getLocked());
            writeCellStyle.setQuotePrefix(styleProperty.getQuotePrefix());
            writeCellStyle.setHorizontalAlignment(styleProperty.getHorizontalAlignment());
            writeCellStyle.setWrapped(styleProperty.getWrapped());
            writeCellStyle.setVerticalAlignment(styleProperty.getVerticalAlignment());
            if (styleProperty.getRotation() != null && styleProperty.getRotation() >= 0) {
                writeCellStyle.setRotation(styleProperty.getRotation());
            }
            if (styleProperty.getIndent() != null && styleProperty.getIndent() >= 0) {
                writeCellStyle.setIndent(styleProperty.getIndent());
            }
            writeCellStyle.setBorderLeft(styleProperty.getBorderLeft());
            writeCellStyle.setBorderRight(styleProperty.getBorderRight());
            writeCellStyle.setBorderTop(styleProperty.getBorderTop());
            writeCellStyle.setBorderBottom(styleProperty.getBorderBottom());
            if (styleProperty.getLeftBorderColor() != null && styleProperty.getLeftBorderColor() >= 0) {
                writeCellStyle.setLeftBorderColor(styleProperty.getLeftBorderColor());
            }
            if (styleProperty.getRightBorderColor() != null && styleProperty.getRightBorderColor() >= 0) {
                writeCellStyle.setRightBorderColor(styleProperty.getRightBorderColor());
            }
            if (styleProperty.getTopBorderColor() != null && styleProperty.getTopBorderColor() >= 0) {
                writeCellStyle.setTopBorderColor(styleProperty.getTopBorderColor());
            }
            if (styleProperty.getBottomBorderColor() != null && styleProperty.getBottomBorderColor() >= 0) {
                writeCellStyle.setBottomBorderColor(styleProperty.getBottomBorderColor());
            }
            writeCellStyle.setFillPatternType(styleProperty.getFillPatternType());
            if (styleProperty.getFillBackgroundColor() != null && styleProperty.getFillBackgroundColor() >= 0) {
                writeCellStyle.setFillBackgroundColor(styleProperty.getFillBackgroundColor());
            }
            if (styleProperty.getFillForegroundColor() != null && styleProperty.getFillForegroundColor() >= 0) {
                writeCellStyle.setFillForegroundColor(styleProperty.getFillForegroundColor());
            }
            writeCellStyle.setShrinkToFit(styleProperty.getShrinkToFit());
        }
        if (fontProperty != null) {
            WriteFont writeFont = new WriteFont();
            writeCellStyle.setWriteFont(writeFont);
            if (!com.alibaba.excel.util.StringUtils.isEmpty(fontProperty.getFontName())) {
                writeFont.setFontName(fontProperty.getFontName());
            }
            writeFont.setFontHeightInPoints(fontProperty.getFontHeightInPoints());
            writeFont.setItalic(fontProperty.getItalic());
            writeFont.setStrikeout(fontProperty.getStrikeout());
            if (fontProperty.getColor() != null && fontProperty.getColor() >= 0) {
                writeFont.setColor(fontProperty.getColor());
            }
            if (fontProperty.getTypeOffset() != null && fontProperty.getTypeOffset() >= 0) {
                writeFont.setTypeOffset(fontProperty.getTypeOffset());
            }
            if (fontProperty.getUnderline() != null && fontProperty.getUnderline() >= 0) {
                writeFont.setUnderline(fontProperty.getUnderline());
            }
            if (fontProperty.getCharset() != null && fontProperty.getCharset() >= 0) {
                writeFont.setCharset(fontProperty.getCharset());
            }
            writeFont.setBold(fontProperty.getBold());
        }
        return writeCellStyle;
    }
}
