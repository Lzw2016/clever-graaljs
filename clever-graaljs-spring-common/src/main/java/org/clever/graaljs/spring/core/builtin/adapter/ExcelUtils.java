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
 * ?????????lizw <br/>
 * ???????????????2020/07/28 22:33 <br/>
 */
public class ExcelUtils {
    public static final ExcelUtils Instance = new ExcelUtils();

    private ExcelUtils() {
    }

    @SuppressWarnings("rawtypes")
    @SneakyThrows
    public ExcelDataReader<Map> createReader(ExcelDataReaderConfig config) {
        Assert.notNull(config, "??????config?????????null");
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
        // ?????????????????????
        builder.useDefaultListener(false);
        builder.registerReadListener(new ExcelDateReadListener(config, excelDataReader));
        return excelDataReader;
    }

    public ExcelDataWriter createWriter(ExcelDataWriterConfig config) {
        Assert.notNull(config, "??????config?????????null");
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
        // ??????????????????????????? WriteHandler ??????AbstractHeadColumnWidthStyleStrategy???AbstractVerticalCellStyleStrategy????????? AbstractWriteHolder
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
            // ???????????????????????????
            if (headConfig.contentLoopMerge.isSetValue()) {
                builder.registerWriteHandler(new LoopMergeStrategy(headConfig.contentLoopMerge.eachRow, headConfig.contentLoopMerge.columnExtend, headConfig));
            }
        }
        // ??????????????????
        if (hasColumnWidth) {
            builder.registerWriteHandler(new ColumnWidthStyleStrategy());
        }
        // ??????????????????
        if (hasStyle
                || config.styleConfig.headStyle.isSetValue()
                || config.styleConfig.headFontStyle.isSetValue()
                || config.styleConfig.contentStyle.isSetValue()
                || config.styleConfig.contentFontStyle.isSetValue()) {
            builder.registerWriteHandler(new StyleStrategy());
        }
        // ??????????????????
        RowHeightProperty headRowHeightProperty = config.styleConfig.headRowHeight.getRowHeightProperty();
        RowHeightProperty contentRowHeightProperty = config.styleConfig.contentRowHeight.getRowHeightProperty();
        Short headRowHeight = headRowHeightProperty.getHeight();
        Short contentRowHeight = contentRowHeightProperty.getHeight();
        if (headRowHeight != null || contentRowHeight != null) {
            builder.registerWriteHandler(new SimpleRowHeightStyleStrategy(headRowHeight, contentRowHeight));
        }
        // ??????OnceAbsoluteMerge??????
        if (config.styleConfig.onceAbsoluteMerge.isSetValue()) {
            OnceAbsoluteMergeProperty onceAbsoluteMergeProperty = config.styleConfig.onceAbsoluteMerge.getOnceAbsoluteMergeProperty();
            builder.registerWriteHandler(new OnceAbsoluteMergeStrategy(onceAbsoluteMergeProperty));
        }
        return excelDataWriter;
    }

    // ?????????
    //----------------------------------------------------------------------------------------------------------------------------------------------

    @Data
    public static class ExcelDataReaderConfig implements Serializable {
        /**
         * Excel???????????????????????????
         */
        private HttpServletRequest request;
        /**
         * ?????????Excel????????????
         */
        private String filename;
        /**
         * ????????????????????????
         */
        private InputStream inputStream;
        /**
         * ??????Excel??????????????????
         */
        private int limitRows = ExcelDataReader.LIMIT_ROWS;
        /**
         * ?????????????????????????????????????????????(????????????)
         */
        private boolean enableExcelData = true;
        /**
         * ????????????????????????(????????????)
         */
        private boolean enableValidation = true;
        /**
         * ????????????Excel??????
         */
        private ExcelReaderExceptionHand excelReaderExceptionHand;
        /**
         * ??????Excel?????????
         */
        @SuppressWarnings("rawtypes")
        private ExcelRowReader<Map> excelRowReader;

        // ----------------------------------------------------------------------

        /**
         * ???????????????????????????
         */
        private boolean autoCloseStream = false;

        /**
         * ????????????????????????
         */
        private CellExtraTypeEnum[] extraRead = new CellExtraTypeEnum[]{};

        /**
         * ??????????????????
         */
        private boolean ignoreEmptyRow = false;

        /**
         * ?????????????????????????????????false????????????inputStream???????????????????????????????????????
         */
        private boolean mandatoryUseInputStream = false;

        /**
         * Excel????????????
         */
        private String password;

        /**
         * Excel????????????(???0??????)
         */
        private Integer sheetNo;

        /**
         * Excel????????????(xlsx???????????????)
         */
        private String sheetName;

        /**
         * ????????????
         */
        private Integer headRowNumber;

        /**
         * ??????????????????
         */
        private boolean useScientificFormat = false;

        /**
         * ??????????????????1904???????????????True???????????????1900?????????????????????false
         */
        private boolean use1904windowing = false;

        /**
         * Locale???????????????????????????????????????????????????????????????????????????????????????????????????
         */
        private Locale locale = Locale.SIMPLIFIED_CHINESE;

        /**
         * ????????????????????????
         */
        private boolean autoTrim = true;

        /**
         * ??????????????????????????????????????????????????????????????????(AnalysisContext.getCustom())
         */
        private Object customObject;

        /**
         * Excel?????????(??????) {@code Map<Entity.propertyName, ExcelReaderHeadConfig>}
         */
        private final LinkedHashMap<String, ExcelReaderHeadConfig> columns = new LinkedHashMap<>();

        /**
         * ??????????????????
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
         * Excel??????????????????
         */
        private HttpServletRequest request;
        /**
         * Excel??????????????????
         */
        private HttpServletResponse response;
        /**
         * Excel???????????????
         */
        private String fileName;
        /**
         * Excel?????????????????????
         */
        private OutputStream outputStream;
        /**
         * ???????????????????????????
         */
        private boolean autoCloseStream = false;
        /**
         * ??????????????????excel????????????false???????????????????????????????????????excel??????????????????????????????Comment???RichTextString
         */
        private boolean inMemory = false;
        /**
         * Excel??????????????????
         */
        private String template;
        /**
         * Excel?????????????????????
         */
        private InputStream templateInputStream;
        /**
         * ??????Excel???????????????????????????????????????
         */
        private boolean writeExcelOnException = false;
        /**
         * ????????????????????????
         */
        private boolean automaticMergeHead = true;
        /**
         * ??????????????????
         */
        private final List<String> excludeColumnFiledNames = new ArrayList<>();
        /**
         * ??????????????????
         */
        private final List<Integer> excludeColumnIndexes = new ArrayList<>();
        /**
         * ?????????????????????
         */
        private final List<String> includeColumnFiledNames = new ArrayList<>();
        /**
         * ?????????????????????
         */
        private final List<Integer> includeColumnIndexes = new ArrayList<>();
        /**
         * ??????????????????
         */
        private boolean needHead = true;
        /**
         * ????????????????????????
         */
        private int relativeHeadRowIndex = 0;
        /**
         * ????????????????????????
         */
        private boolean useDefaultStyle = true;
        /**
         * Excel??????
         */
        private ExcelTypeEnum excelType = ExcelTypeEnum.XLSX;
        /**
         * Excel????????????
         */
        private String password;
        /**
         * Excel????????????(???0??????)
         */
        private Integer sheetNo;
        /**
         * Excel????????????(xlsx???????????????)
         */
        private String sheetName;
        /**
         * ??????????????????1904???????????????True???????????????1900?????????????????????false
         */
        private boolean use1904windowing = false;
        /**
         * Locale???????????????????????????????????????????????????????????????????????????????????????????????????
         */
        private Locale locale = Locale.SIMPLIFIED_CHINESE;
        /**
         * ????????????????????????
         */
        private boolean autoTrim = true;
        /**
         * Excel?????? {@code Map<Entity.propertyName, ExcelWriterHeadConfig>}
         */
        private final LinkedHashMap<String, ExcelWriterHeadConfig> columns = new LinkedHashMap<>();
        /**
         * ??????????????????
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
            // ????????????
            List<TupleTow<String, ExcelWriterHeadConfig>> headConfigs = new ArrayList<>(indexMax);
            for (int i = 0; i < indexMax; i++) {
                headConfigs.add(null);
            }
            List<TupleTow<String, ExcelWriterHeadConfig>> tmp = new ArrayList<>(list.size());
            // ?????????index?????????Head
            for (TupleTow<String, ExcelWriterHeadConfig> tupleTow : list) {
                String propertyName = tupleTow.getValue1();
                ExcelWriterHeadConfig headConfig = tupleTow.getValue2();
                if (headConfig.excelProperty.index != null && headConfig.excelProperty.index >= 0) {
                    headConfigs.set(headConfig.excelProperty.index, TupleTow.creat(propertyName, headConfig));
                } else {
                    tmp.add(TupleTow.creat(propertyName, headConfig));
                }
            }
            // ???????????????Head
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
            // ????????????heads
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
         * ?????????
         */
        private final List<String> column = new ArrayList<>();
        /**
         * ?????????????????????
         */
        private Boolean ignore;

        /**
         * ???????????????????????????????????????????????????-1?????????Java????????????????????????index>????????????
         */
        private Integer index = -1;
    }

    @Data
    public static class DateTimeFormat implements Serializable {
        /**
         * ??????????????????????????????
         */
        private String dateFormat;

        /**
         * ??????????????????1904???????????????True???????????????1900?????????????????????false
         */
        private Boolean use1904windowing;

        /**
         * ??????????????????
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
         * ???????????????
         */
        private String numberFormat;

        /**
         * ??????????????????
         */
        private RoundingMode roundingMode;

        /**
         * ??????????????????
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
         * ??????
         */
        private Integer columnWidth;
    }

    @Data
    public static class ExcelFontStyle implements Serializable {
        /**
         * ?????????????????????: Arial???
         */
        private String fontName;

        /**
         * ???????????????????????????????????????- points
         */
        private Short fontHeightInPoints;

        /**
         * ??????????????????
         */
        private Boolean italic;

        /**
         * ??????????????????????????????????????????
         */
        private Boolean strikeout;

        /**
         * ???????????????
         */
        private Short color;

        /**
         * ??????normal???super???subscript
         */
        private Short typeOffset;

        /**
         * ???????????????????????????
         */
        private Byte underline;

        /**
         * ???????????????????????????
         */
        private Integer charset;

        /**
         * ??????
         */
        private Boolean bold;

        /**
         * ??????????????????
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
         * ???
         */
        private Integer eachRow;

        /**
         * ???
         */
        private Integer columnExtend;

        /**
         * ??????????????????
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
         * ??????
         */
        private Short rowHeight;

        public RowHeightProperty getRowHeightProperty() {
            return new RowHeightProperty(rowHeight);
        }
    }

    @Data
    public static class ExcelCellStyle implements Serializable {
        /**
         * ???????????????????????????????????????????????????????????????????????????????????? {@link BuiltinFormats}.
         */
        private Short dataFormat;

        /**
         * ??????????????????????????????????????????
         */
        private Boolean hidden;

        /**
         * ??????????????????????????????????????????
         */
        private Boolean locked;

        /**
         * ???????????????????????????Quote Prefix?????????123 Prefix??????
         * ????????????Excel???????????????????????????????????????????????????????????????
         * ?????????????????????????????????????????????????????????IgnoredErrorType???????????????Excel??????????????????????????????
         * {@link IgnoredErrorType})
         */
        private Boolean quotePrefix;

        /**
         * ????????????????????????????????????
         */
        private HorizontalAlignment horizontalAlignment;

        /**
         * ????????????????????????????????????????????????true??????????????????????????????????????????????????????????????????????????????
         */
        private Boolean wrapped;

        /**
         * ????????????????????????????????????
         */
        private VerticalAlignment verticalAlignment;

        /**
         * ????????????????????????????????????<br />
         * ?????????HSSF??????-90???90???????????????XSSF??????0???180????????????
         * ???????????????????????????????????????????????????????????????
         * ??????????????????getter?????????CellStyle??????????????????Excel????????????????????????????????????????????????
         */
        private Short rotation;

        /**
         * ?????????????????????????????????????????????
         */
        private Short indent;

        /**
         * ????????????????????????????????????????????????
         */
        private BorderStyle borderLeft;

        /**
         * ?????????????????????????????????????????????
         */
        private BorderStyle borderRight;

        /**
         * ???????????????????????????????????????????????????
         */
        private BorderStyle borderTop;

        /**
         * ????????????????????????????????????????????????
         */
        private BorderStyle borderBottom;

        /**
         * ??????????????????????????????
         *
         * @see IndexedColors
         */
        private Short leftBorderColor;

        /**
         * ??????????????????????????????
         *
         * @see IndexedColors
         */
        private Short rightBorderColor;

        /**
         * ????????????????????????????????????
         *
         * @see IndexedColors
         */
        private Short topBorderColor;

        /**
         * ??????????????????????????????
         *
         * @see IndexedColors
         */
        private Short bottomBorderColor;

        /**
         * ?????????1??????????????????????????????...??????????????????
         *
         * @see FillPatternType#SOLID_FOREGROUND
         */
        private FillPatternType fillPatternType;

        /**
         * ????????????????????????
         *
         * @see IndexedColors
         */
        private Short fillBackgroundColor;

        /**
         * ???????????????????????????<br />
         * ??????????????????????????????????????????????????????
         *
         * @see IndexedColors
         */
        private Short fillForegroundColor;

        /**
         * ????????????????????????????????????????????????????????????????????????????????????
         */
        private Boolean shrinkToFit;

        /**
         * ??????????????????
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
         * ??????????????????
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
         * ??????????????????
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
         * Head??????
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
         * ??????????????????
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
         * ?????????
         */
        private Integer firstRowIndex;

        /**
         * ????????????
         */
        private Integer lastRowIndex;

        /**
         * ?????????
         */
        private Integer firstColumnIndex;

        /**
         * ????????????
         */
        private Integer lastColumnIndex;

        /**
         * ??????????????????
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
         * ??????????????????
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

    // ??????????????????????????????
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
            Assert.notNull(config, "??????config?????????null");
            Assert.notNull(excelDataReader, "??????excelDataReader?????????null");
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

        // ??????????????????
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
                    // ??????index??????
                    if (Objects.equals(index, headConfigTmp.excelProperty.index)) {
                        propertyName = propertyNameTmp;
                        headConfig = headConfigTmp;
                        break;
                    }
                    // ??????column(????????????)??????
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
                // ???????????????
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
            Assert.notEmpty(columns, "????????????Excel????????????????????????????????????");
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
            // ???????????????????????????
            if (columns == null) {
                parseHeadMap(context);
            }
            ExcelData<Map> excelData = getExcelData(context);
            if (excelData.getStartTime() == null) {
                excelData.setStartTime(System.currentTimeMillis());
            }
            int index = context.readRowHolder().getRowIndex() + 1;
            ExcelRow<Map> excelRow = new ExcelRow<>(new HashMap(data.size()), index);
            // ????????????-????????????
            Map<Integer, Cell> map = context.readRowHolder().getCellMap();
            StringBuilder sb = new StringBuilder(map.size() * 32);
            for (Map.Entry<Integer, Cell> entry : map.entrySet()) {
                sb.append(entry.getKey()).append("=").append(entry.getValue().toString()).append("|");
            }
            excelRow.setDataSignature(EncodeDecodeUtils.encodeHex(DigestUtils.sha1(sb.toString().getBytes())));
            // ??????????????????????????????
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
                // ??????????????????(propertyName)
                if (Objects.equals(headConfig.excelProperty.ignore, Boolean.TRUE)) {
                    continue;
                }
                // ??????????????????
                if (headConfig.dataType == null) {
                    headConfig.dataType = getCellDataType(cellData);
                }
                // ???????????????
                Object value;
                if (Objects.equals(Void.class, headConfig.dataType)) {
                    value = null;
                } else {
                    // ??????????????? dateFormat numberFormat
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
                // ???????????????
                excelRow.getData().put(propertyName, value);
            }
            boolean success = true;
            final boolean enableExcelData = config.isEnableExcelData();
            if (enableExcelData) {
                success = excelData.addRow(excelRow);
            }
            if (!success) {
                log.info("Excel???????????????????????????filename={} | data={}", config.getFilename(), data);
            }
            // ????????????
            //final boolean enableValidation = config.isEnableValidation();
            //if (enableValidation && !excelRow.hasError()) {
            //    // TODO ????????????
            //}
            // ??????????????????????????????
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
                log.info("Excel Sheet???????????????sheet={} | ?????????{}ms", excelData.getSheetName(), excelData.getEndTime() - excelData.getStartTime());
            }
            ExcelRowReader<Map> excelRowReader = config.getExcelRowReader();
            if (excelRowReader != null) {
                excelRowReader.readEnd(context);
            }
            // ????????????????????????
            columns = null;
            headsMap.clear();
        }

        @Override
        public void onException(Exception exception, AnalysisContext context) throws Exception {
            ExcelReaderExceptionHand excelReaderExceptionHand = config.getExcelReaderExceptionHand();
            if (excelReaderExceptionHand != null) {
                excelReaderExceptionHand.exceptionHand(exception, context);
            } else {
                // ?????????????????????
                throw exception;
            }
        }

        @Override
        public boolean hasNext(AnalysisContext context) {
            // ???????????? - ????????????
            if (context.readSheetHolder().getHeadRowNumber() > 0 && columns != null && columns.isEmpty()) {
                log.warn("?????????????????????");
                return false;

            }
            final ExcelData<Map> excelData = getExcelData(context);
            // ??????????????????
            if (excelData.getEndTime() != null && excelData.getStartTime() != null) {
                log.info("Excel Sheet????????????????????????????????????sheet={}", excelData.getSheetName());
                return false;
            }
            // ???????????????????????? LIMIT_ROWS
            final int limitRows = config.getLimitRows();
            final int rowNum = context.readRowHolder().getRowIndex() + 1;
            final int dataRowNum = rowNum - context.currentReadHolder().excelReadHeadProperty().getHeadRowNumber();
            if (limitRows > 0 && dataRowNum > limitRows) {
                log.info("Excel????????????????????????dataRowNum={} | limitRows={}", dataRowNum, limitRows);
                excelData.setInterruptByRowNum(rowNum);
                // ????????????????????????
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
        // ????????????????????????index Map<index, filled>
        private final Map<Integer, Boolean> filledMap = new HashMap<>();
        // ????????????????????????
        private final Set<String> propertyNameParsed = new HashSet<>();

        public FillHeadStrategy(ExcelDataWriterConfig config) {
            Assert.notNull(config, "??????config?????????null");
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
            // Excel?????? Map<Entity.propertyName, ExcelWriterHeadConfig>
            ExcelWriterHeadConfig headConfig = null;
            String headsStr = StringUtils.join(head.getHeadNameList(), "|");
            for (Map.Entry<String, ExcelWriterHeadConfig> entry : config.columns.entrySet()) {
                String propertyNameTmp = entry.getKey();
                ExcelWriterHeadConfig headConfigTmp = entry.getValue();
                // ??????index??????
                if (Objects.equals(headConfigTmp.excelProperty.index, columnIndex)) {
                    propertyNameParsed.add(propertyNameTmp);
                    headConfig = headConfigTmp;
                    break;
                }
                // ??????column(????????????)??????
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
            // ????????????
            head.setHeadStyleProperty(headConfig.headStyle.getStyleProperty(config.styleConfig.headStyle));
            head.setHeadFontProperty(headConfig.headFontStyle.getFontProperty(config.styleConfig.headFontStyle));
            head.setContentStyleProperty(headConfig.contentStyle.getStyleProperty(config.styleConfig.contentStyle));
            head.setContentFontProperty(headConfig.contentFontStyle.getFontProperty(config.styleConfig.contentFontStyle));
            // ???????????????
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
         * ?????????
         */
        private final int eachRow;
        /**
         * ?????????
         */
        private final int columnExtend;
        /**
         * ????????????
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
