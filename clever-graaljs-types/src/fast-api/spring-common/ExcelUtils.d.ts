/**
 * 基础类型
 */
type BaseType = undefined | null | Date
    | JByte | JShort | JInt | JLong | JFloat | JDouble | JBoolean | JChar | JString
    | JBigDecimal | JBigInteger | JDate | JCharSequence;


interface BaseEntity {
    [field: string]: BaseType;
}

//---------------------------------------------------------------------------------------------------------------------------------------------- Excel枚举
/** Excel文件格式 */
enum ExcelTypeEnum {
    /** .xls */
    XLS = "XLS",
    /** .xlsx */
    XLSX = "XLSX",
}

/** Excel扩展信息 */
enum CellExtraTypeEnum {
    /** 批注信息 */
    COMMENT = "COMMENT",
    /** 超链接 */
    HYPERLINK = "HYPERLINK",
    /** 合并单元格 */
    MERGE = "MERGE",
}

/** 单元格类型 */
enum CellDataTypeEnum {
    /** 字符串 */
    STRING = "STRING",
    /** 不需要在sharedStrings.xml，它只用于过度使用，并且数据将存储为字符串 */
    DIRECT_STRING = "DIRECT_STRING",
    /** 数字 */
    NUMBER = "NUMBER",
    /** boolean值 */
    BOOLEAN = "BOOLEAN",
    /** 空单元格 */
    EMPTY = "EMPTY",
    /** 错误格 */
    ERROR = "ERROR",
    /** 当前仅在写入时支持图像 */
    IMAGE = "IMAGE",
}

/** 语言选项 */
enum ExcelLocale {
    /** 英语 */
    ENGLISH = "ENGLISH",
    /** 中文 */
    CHINESE = "CHINESE",
    /** 简体中文 */
    SIMPLIFIED_CHINESE = "SIMPLIFIED_CHINESE",
    /** 繁体中文 */
    TRADITIONAL_CHINESE = "TRADITIONAL_CHINESE",
}

/** 内置格式化选项 */
enum BuiltinFormats {
    General = 0,
    /** "0" */
    Fmt_1 = 1,
    /** "0.00" */
    Fmt_2 = 2,
    /** "#,##0" */
    Fmt_3 = 3,
    /** "#,##0.00" */
    Fmt_4 = 4,
    /** "$#,##0_);($#,##0)" */
    Fmt_5 = 5,
    /** "$#,##0_);[Red]($#,##0)" */
    Fmt_6 = 6,
    /** "$#,##0.00);($#,##0.00)" */
    Fmt_7 = 7,
    /** "$#,##0.00_);[Red]($#,##0.00)" */
    Fmt_8 = 8,
    /** "0%" */
    Fmt_9 = 9,
    /** "0.00%" */
    Fmt_10 = 0xa,
    /** "0.00E+00" */
    Fmt_11 = 0xb,
    /** "# ?/?" */
    Fmt_12 = 0xc,
    /** "# ??/??" */
    Fmt_13 = 0xd,
    /** "m/d/yy" */
    Fmt_14 = 0xe,
    /** "d-mmm-yy" */
    Fmt_15 = 0xf,
    /** "d-mmm" */
    Fmt_16 = 0x10,
    /** "mmm-yy" */
    Fmt_17 = 0x11,
    /** "h:mm AM/PM" */
    Fmt_18 = 0x12,
    /** "h:mm:ss AM/PM" */
    Fmt_19 = 0x13,
    /** "h:mm" */
    Fmt_20 = 0x14,
    /** "h:mm:ss" */
    Fmt_21 = 0x15,
    /** "m/d/yy h:mm" */
    Fmt_22 = 0x16,
    // 0x17 - 0x24 reserved for international and undocume
    /** "#,##0_);(#,##0)" */
    Fmt_37 = 0x25,
    /** "#,##0_);[Red](#,##0)" */
    Fmt_38 = 0x26,
    /** "#,##0.00_);(#,##0.00)" */
    Fmt_39 = 0x27,
    /** "#,##0.00_);[Red](#,##0.00)" */
    Fmt_40 = 0x28,
    /** "_(* #,##0_);_(* (#,##0);_(* \"-\"_);_(@_)" */
    Fmt_41 = 0x29,
    /** "_($* #,##0_);_($* (#,##0);_($* \"-\"_);_(@_)" */
    Fmt_42 = 0x2a,
    /** "_(* #,##0.00_);_(* (#,##0.00);_(* \"-\"??_);_(@_)" */
    Fmt_43 = 0x2b,
    /** "_($* #,##0.00_);_($* (#,##0.00);_($* \"-\"??_);_(@_)" */
    Fmt_44 = 0x2c,
    /** "mm:ss" */
    Fmt_45 = 0x2d,
    /** "[h]:mm:ss" */
    Fmt_46 = 0x2e,
    /** "mm:ss.0" */
    Fmt_47 = 0x2f,
    /** "##0.0E+0" */
    Fmt_48 = 0x30,
    /** "@" - This is text format. */
    Fmt_49 = 0x31,
    /** "text" - Alias for "@" */
    Fmt_50 = 0x31,
}

/** 水平对齐选项 */
enum HorizontalAlignment {
    GENERAL = "GENERAL",
    LEFT = "LEFT",
    CENTER = "CENTER",
    RIGHT = "RIGHT",
    FILL = "FILL",
    JUSTIFY = "JUSTIFY",
    CENTER_SELECTION = "CENTER_SELECTION",
    DISTRIBUTED = "DISTRIBUTED",
}

/** 垂直对齐选项 */
enum VerticalAlignment {
    TOP = "TOP",
    CENTER = "CENTER",
    BOTTOM = "BOTTOM",
    JUSTIFY = "JUSTIFY",
    DISTRIBUTED = "DISTRIBUTED",
}

/** 边框样式 */
enum BorderStyle {
    NONE = (0x0),
    THIN = (0x1),
    MEDIUM = (0x2),
    DASHED = (0x3),
    DOTTED = (0x4),
    THICK = (0x5),
    DOUBLE = (0x6),
    HAIR = (0x7),
    MEDIUM_DASHED = (0x8),
    DASH_DOT = (0x9),
    MEDIUM_DASH_DOT = (0xA),
    DASH_DOT_DOT = (0xB),
    MEDIUM_DASH_DOT_DOT = (0xC),
    SLANTED_DASH_DOT = (0xD),
}

/** 颜色 */
enum IndexedColors {
    BLACK1 = (0),
    WHITE1 = (1),
    RED1 = (2),
    BRIGHT_GREEN1 = (3),
    BLUE1 = (4),
    YELLOW1 = (5),
    PINK1 = (6),
    TURQUOISE1 = (7),
    BLACK = (8),
    WHITE = (9),
    RED = (10),
    BRIGHT_GREEN = (11),
    BLUE = (12),
    YELLOW = (13),
    PINK = (14),
    TURQUOISE = (15),
    DARK_RED = (16),
    GREEN = (17),
    DARK_BLUE = (18),
    DARK_YELLOW = (19),
    VIOLET = (20),
    TEAL = (21),
    GREY_25_PERCENT = (22),
    GREY_50_PERCENT = (23),
    CORNFLOWER_BLUE = (24),
    MAROON = (25),
    LEMON_CHIFFON = (26),
    LIGHT_TURQUOISE1 = (27),
    ORCHID = (28),
    CORAL = (29),
    ROYAL_BLUE = (30),
    LIGHT_CORNFLOWER_BLUE = (31),
    SKY_BLUE = (40),
    LIGHT_TURQUOISE = (41),
    LIGHT_GREEN = (42),
    LIGHT_YELLOW = (43),
    PALE_BLUE = (44),
    ROSE = (45),
    LAVENDER = (46),
    TAN = (47),
    LIGHT_BLUE = (48),
    AQUA = (49),
    LIME = (50),
    GOLD = (51),
    LIGHT_ORANGE = (52),
    ORANGE = (53),
    BLUE_GREY = (54),
    GREY_40_PERCENT = (55),
    DARK_TEAL = (56),
    SEA_GREEN = (57),
    DARK_GREEN = (58),
    OLIVE_GREEN = (59),
    BROWN = (60),
    PLUM = (61),
    INDIGO = (62),
    GREY_80_PERCENT = (63),
    AUTOMATIC = (64),
}

/** 填充模式 */
enum FillPatternType {
    NO_FILL = (0),
    SOLID_FOREGROUND = (1),
    FINE_DOTS = (2),
    ALT_BARS = (3),
    SPARSE_DOTS = (4),
    THICK_HORZ_BANDS = (5),
    THICK_VERT_BANDS = (6),
    THICK_BACKWARD_DIAG = (7),
    THICK_FORWARD_DIAG = (8),
    BIG_SPOTS = (9),
    BRICKS = (10),
    THIN_HORZ_BANDS = (11),
    THIN_VERT_BANDS = (12),
    THIN_BACKWARD_DIAG = (13),
    THIN_FORWARD_DIAG = (14),
    SQUARES = (15),
    DIAMONDS = (16),
    LESS_DOTS = (17),
    LEAST_DOTS = (18),
}

/** 单元格数据类型 */
enum ExcelDataType {
    JString = "JString",
    JBigDecimal = "JBigDecimal",
    JBoolean = "JBoolean",
    JDate = "JDate",
    JInteger = "JInteger",
    JDouble = "JDouble",
    JLong = "JLong",
    JFloat = "JFloat",
    JShort = "JShort",
    JByte = "JByte",
    JByteArray = "JByte[]",
}

/** 下划线类型 */
enum ExcelUnderline {
    /** 不加下划线 */
    U_NONE = 0,
    /** 单下划线 */
    U_SINGLE = 1,
    /** 双下划线 */
    U_DOUBLE = 2,
    /** 会计风格单下划线 */
    U_SINGLE_ACCOUNTING = 0x21,
    /** 会计风格双下划线 */
    U_DOUBLE_ACCOUNTING = 0x22,
}

/** 普通，上标或下标 */
enum ExcelTypeOffset {
    /** 没有类型偏移（不是上标或下标） */
    SS_NONE = 0,
    /** 上标 */
    SS_SUPER = 1,
    /** 下标 */
    SS_SUB = 2,
}

enum ExcelFontCharset {
    ANSI = "ANSI",
    DEFAULT = "DEFAULT",
    SYMBOL = "SYMBOL",
    MAC = "MAC",
    SHIFTJIS = "SHIFTJIS",
    HANGUL = "HANGUL",
    JOHAB = "JOHAB",
    GB2312 = "GB2312",
    CHINESEBIG5 = "CHINESEBIG5",
    GREEK = "GREEK",
    TURKISH = "TURKISH",
    VIETNAMESE = "VIETNAMESE",
    HEBREW = "HEBREW",
    ARABIC = "ARABIC",
    BALTIC = "BALTIC",
    RUSSIAN = "RUSSIAN",
    THAI_ = "THAI_",
    EASTEUROPE = "EASTEUROPE",
    OEM = "OEM",
}

enum WriteDirectionEnum {
    /**
     * 垂直写入
     */
    VERTICAL = "VERTICAL",
    /**
     * 横向写
     */
    HORIZONTAL = "HORIZONTAL",
}

//---------------------------------------------------------------------------------------------------------------------------------------------- Excel内置对象

interface AbstractParameterBuilder<T extends AbstractParameterBuilder<T>> {
    /** 如果日期使用1904窗口，则为True；如果使用1900日期窗口，则为false */
    use1904windowing(use1904windowing: JBoolean): T;

    /** 自动删除空格字符 */
    autoTrim(autoTrim: JBoolean): void;
}

interface AbstractExcelReaderParameterBuilder<T extends AbstractExcelReaderParameterBuilder<T>> extends AbstractParameterBuilder<T> {
    /** 表头行数 */
    headRowNumber(headRowNumber: JInt): T;

    /** 使用科学格式 */
    useScientificFormat(useScientificFormat: JBoolean): T;
}

interface ExcelReaderSheetBuilder<E> extends AbstractExcelReaderParameterBuilder<ExcelReaderSheetBuilder<E>> {
    /** 页签编号(从0开始) */
    sheetNo(sheetNo: JInt): ExcelReaderSheetBuilder<E>;

    /** 页签名称(xlsx格式才支持) */
    sheetName(sheetName: JString): ExcelReaderSheetBuilder<E>;

    /** 开始读取Excel数据(推荐) */
    doRead(): void;

    /** 读取Excel数据，并返回所有结果(数据量大时，会消耗大量内存) */
    doReadSync(): JList<E>;
}

interface ExcelReaderBuilder<E> extends AbstractExcelReaderParameterBuilder<ExcelReaderBuilder<JMap<JString, BaseType>>> {
    /** 文件输入流 */
    file(inputStream: JInputStream): ExcelReaderBuilder<JMap<JString, BaseType>>;

    /** 强制使用输入流，如果为false，则将“inputStream”传输到临时文件以提高效率 */
    mandatoryUseInputStream(mandatoryUseInputStream: JBoolean): ExcelReaderBuilder<JMap<JString, BaseType>>;

    /** 是否自动关闭输入流 */
    autoCloseStream(autoCloseStream: JBoolean): ExcelReaderBuilder<JMap<JString, BaseType>>;

    /** 是否忽略空行 */
    ignoreEmptyRow(ignoreEmptyRow: JBoolean): ExcelReaderBuilder<JMap<JString, BaseType>>;

    /** 设置一个自定义对象，可以在侦听器中读取此对象(AnalysisContext.getCustom()) */
    customObject(customObject: any): ExcelReaderBuilder<JMap<JString, BaseType>>;

    /** Excel文件密码 */
    password(password: JString): ExcelReaderBuilder<JMap<JString, BaseType>>;

    /**
     * 设置读取的页签
     * @param sheetNo 页签编号(从0开始)
     */
    sheet(sheetNo: JInt): ExcelReaderSheetBuilder<JMap<JString, BaseType>>;

    /**
     * 设置读取的页签
     * @param sheetName 页签名称(xlsx格式才支持)
     */
    sheet(sheetName: JString): ExcelReaderSheetBuilder<JMap<JString, BaseType>>;

    /** 开始读取所有的页签数据 */
    doReadAll(): void;

    /** 开始读取所有的页签数据，并返回所有结果(数据量大时，会消耗大量内存) */
    doReadAllSync(): JList<JMap<JString, BaseType>>;
}

// interface AbstractExcelWriterParameterBuilder<T extends AbstractExcelWriterParameterBuilder<T>> extends AbstractParameterBuilder<T> {
//     /** 设置Excel表头所在行，从0开始 */
//     relativeHeadRowIndex(relativeHeadRowIndex: JInt): T;
//
//     /** 是否需要输出表头 */
//     needHead(needHead: JBoolean): T;
//
//     /** 是否使用默认样式 */
//     useDefaultStyle(useDefaultStyle: JBoolean): T;
//
//     /** 是否自动合并表头 */
//     automaticMergeHead(automaticMergeHead: JBoolean): T;
//
//     /** 忽略自定义列 */
//     excludeColumnIndexes(excludeColumnIndexes: JCollection<JInt>): T;
//
//     /** 忽略自定义列 */
//     excludeColumnFiledNames(excludeColumnFiledNames: JCollection<JString>): T;
//
//     /** 只输出自定义列 */
//     includeColumnIndexes(includeColumnIndexes: JCollection<JInt>): T;
//
//     /** 只输出自定义列 */
//     includeColumnFiledNames(includeColumnFiledNames: JCollection<JString>): T;
// }
//
// interface ExcelWriterSheetBuilder extends AbstractExcelWriterParameterBuilder<ExcelWriterSheetBuilder> {
//     /** 页签编号(从0开始) */
//     sheetNo(sheetNo: number): ExcelWriterSheetBuilder;
//
//     /** 页签名称(xlsx格式才支持) */
//     sheetName(sheetName: string): ExcelWriterSheetBuilder;
//
//     /**  */
//     doWrite(data: JList<object>): void
//
//     /**  */
//     doFill(data: object): void
//
//     /**  */
//     table(): ExcelWriterSheetBuilder;
//
//     /**  */
//     table(tableNo: JInt): ExcelWriterSheetBuilder;
// }
//
// interface ExcelWriterTableBuilder extends AbstractExcelWriterParameterBuilder<ExcelWriterTableBuilder> {
//     /**  */
//     tableNo(tableNo: JInt): ExcelWriterTableBuilder;
//
//     /**  */
//     doWrite(data: JList<object>): void
// }
//
// interface ExcelWriterBuilder extends AbstractExcelWriterParameterBuilder<ExcelWriterBuilder> {
//     /**  */
//     file(outputStream: JOutputStream): void;
//
//     /**  */
//     withTemplate(templateInputStream: JInputStream): void;
//
//     /**  */
//     autoCloseStream(autoCloseStream: JBoolean): ExcelWriterBuilder;
//
//     /**  */
//     password(password: JString): ExcelWriterBuilder;
//
//     /**  */
//     inMemory(inMemory: JBoolean): ExcelWriterBuilder;
//
//     /**  */
//     writeExcelOnException(writeExcelOnException: JBoolean): ExcelWriterBuilder;
//
//     /**
//      * 设置读取的页签
//      * @param sheetNo 页签编号(从0开始)
//      */
//     sheet(sheetNo: JInt): ExcelWriterSheetBuilder;
//
//     /**
//      * 设置读取的页签
//      * @param sheetName 页签名称(xlsx格式才支持)
//      */
//     sheet(sheetName: JString): ExcelWriterSheetBuilder;
// }

interface AnalysisContext {
}

//---------------------------------------------------------------------------------------------------------------------------------------------- Excel配置

interface ExcelProperty {
    /** 列名称 */
    column: JString | JString[];
    /** 是否忽略当前列 */
    ignore?: JBoolean;
    /** 定义列的排序顺序 */
    index?: JInt;
}

interface DateTimeFormat {
    /** 时间格式化的格式定义 */
    dateFormat: JString;
    /** 如果日期使用1904窗口，则为True；如果使用1900日期窗口，则为false */
    use1904windowing?: JBoolean;
}

interface NumberFormat {
    /** 数字格式化 */
    numberFormat: JString;
    /** 四舍五入模式 */
    roundingMode?: JRoundingMode;
}

interface ColumnWidth {
    /** 列宽 */
    columnWidth: JInt;
}

interface ExcelFontStyle {
    /** 字体的名称（如: Arial） */
    fontName?: JString;
    /** 以熟悉的测量单位表示的高度- points */
    fontHeightInPoints?: JShort;
    /** 是否使用斜体 */
    italic?: JBoolean;
    /** 是否在文本中使用删除线水平线 */
    strikeout?: JBoolean;
    /** 字体的颜色 */
    color?: IndexedColors;
    /** 设置normal、super或subscript */
    typeOffset?: ExcelTypeOffset;
    /** 设置要使用的带下划线的文本类型 */
    underline?: ExcelUnderline;
    /** 设置要使用的字符集 */
    charset?: ExcelFontCharset;
    /** 粗体 */
    bold?: JBoolean;
}

interface ContentFontStyle extends ExcelFontStyle {
}

/** 合并区域配置 */
interface ContentLoopMerge {
    /** 行 */
    eachRow: JInt;
    /** 列 */
    columnExtend: JInt;
}

/** 行高 */
interface ContentRowHeight {
    /** 行高 */
    rowHeight: JShort;
}

interface ExcelCellStyle {
    /** 设置数据格式（必须是有效格式）。内置格式在内置信息中定义 */
    dataFormat?: BuiltinFormats;
    /** 将单元格使用此样式设置为隐藏 */
    hidden?: JBoolean;
    /** 将单元格使用此样式设置为锁定 */
    locked?: JBoolean;
    /**
     * 打开或关闭样式的“Quote Prefix”或“123 Prefix”，
     * 用于告诉Excel，看起来像数字或公式的内容不应被视为打开。
     * 打开此选项有点（但不是完全打开，请参见IgnoredErrorType）类似于在Excel中为单元格值添加前缀
     */
    quotePrefix?: JBoolean;
    /** 设置单元格的水平对齐方式 */
    horizontalAlignment?: HorizontalAlignment;
    /** 设置是否应该换行。将此标志设置为true可以通过在多行上显示所有内容来使其在一个单元格中可见 */
    wrapped?: JBoolean;
    /** 设置单元格的垂直对齐方式 */
    verticalAlignment?: VerticalAlignment;
    /**
     * 设置单元格中文本的旋转度<br />
     * 注意：HSSF使用-90至90度的值，而XSSF使用0至180度的值。
     * 此方法的实现将在这两个值范围之间进行映射，
     * 但是，相应的getter返回此CellStyle所应用的当前Excel文件格式类型所要求的范围内的值。
     */
    rotation?: JShort;
    /** 设置空格数以缩进单元格中的文本 */
    indent?: JShort;
    /** 设置要用于单元格左边框的边框类型 */
    borderLeft?: BorderStyle;
    /** 设置用于单元格右边框的边框类型 */
    borderRight?: BorderStyle;
    /** 设置要用于单元格顶部边框的边框类型 */
    borderTop?: BorderStyle;
    /** 设置用于单元格底部边框的边框类型 */
    borderBottom?: BorderStyle;
    /**  设置用于左边框的颜色*/
    leftBorderColor?: IndexedColors;
    /** 设置用于右边框的颜色 */
    rightBorderColor?: IndexedColors;
    /** 设置要用于顶部边框的颜色 */
    topBorderColor?: IndexedColors;
    /** 设置用于底边框的颜色 */
    bottomBorderColor?: IndexedColors;
    /** 设置为1会使单元格充满前景色...不知道其他值 */
    fillPatternType?: FillPatternType;
    /**  设置背景填充颜色*/
    fillBackgroundColor?: IndexedColors;
    /**
     * 设置前景色填充颜色<br />
     * 注意：确保将前景色设置为背景颜色之前
     */
    fillForegroundColor?: IndexedColors;
    /** 控制如果文本太长，是否应自动调整单元格的大小以缩小以适合 */
    shrinkToFit?: JBoolean;
}

interface ContentStyle extends ExcelCellStyle {
}

interface HeadFontStyle extends ExcelFontStyle {
}

interface HeadRowHeight {
    /** 表格头行高 */
    headRowHeight?: JShort;
}

interface HeadStyle extends ExcelCellStyle {
}

interface OnceAbsoluteMerge {
    /** 第一行 */
    firstRowIndex: JInt;
    /** 最后一行 */
    lastRowIndex: JInt;
    /** 第一列 */
    firstColumnIndex: JInt;
    /** 最后一列 */
    lastColumnIndex: JInt;
}

class ExcelFillConfig {
    /** 数据填充方向 */
    direction: WriteDirectionEnum = WriteDirectionEnum.VERTICAL;
    /** 每次使用list参数时都要创建一个新行。必要时使用默认值。警告：如果使用forceNewRow设置为true，将无法使用异步写入文件，只是说整个文件将存储在内存中 */
    forceNewRow?: JBoolean = false;
}

//---------------------------------------------------------------------------------------------------------------------------------------------- 读取Excel数据的返回值

interface ExcelHead {
    /**
     * 字段位置
     */
    getIndex(): JInt;

    /**
     * Excel表头名称(允许多级表头)
     */
    getHeads(): JList<JString>;

    /**
     * 对应实体类字段名
     */
    getColumnName(): JString;

    /**
     * 第一级表头
     */
    getFirstHead(): JString;

    /**
     * 最后一级表头(建议前端使用此值)
     */
    getLastHead(): JString;
}

interface ExcelRow<T> {
    /**
     * 数据在Excel文件中的行号
     */
    getExcelRowNum(): JInt;

    /**
     * 读取的原始数据
     */
    getData(): T;

    /**
     * 数据签名
     */
    getDataSignature(): JString;

    /**
     * 列错误
     */
    getColumnError(): JMap<JString, JList<JString>>;

    /**
     * 行错误
     */
    getRowError(): JList<JString>;

    /**
     * 当前数据是否有解析错误
     */
    hasError(): JBoolean;

    /**
     * 增加数据列错误
     */
    addErrorInColumn(columnName: JString, msg: JString): void;

    /**
     * 增加数据行错误
     */
    addErrorInRow(msg: JString): void;
}

interface ExcelImportState {
    /**
     * 导入是否成功
     */
    getSuccess(): JBoolean;

    /**
     * 总数据量
     */
    getTotalRows(): JInt;

    /**
     * 成功数据量
     */
    getSuccessRows(): JInt;

    /**
     * 失败数据量
     */
    getFailRows(): JInt;

    /**
     * 错误数量
     */
    getErrorCount(): JInt;

    /**
     * 重复数据量
     */
    getRepeat(): JInt;

    /**
     * 处理耗时(单位毫秒)
     */
    getTakeTime(): JLong;
}

interface ExcelData<T> {
    /**
     * 数据类型
     */
    getClazz(): JClass;

    /**
     * 页签名称
     */
    getSheetName(): JString | undefined;

    /**
     * 页签编号
     */
    getSheetNo(): JInt | undefined;

    /**
     * 表头信息
     */
    getHeads(): JList<ExcelHead>;

    /**
     * Excel行数据(Excel所有数据)
     */
    getRows(): JList<ExcelRow<T>>;

    /**
     * 开始解析的时间
     */
    getStartTime(): JLong | undefined;

    /**
     * 解析完成时间
     */
    getEndTime(): JLong | undefined;

    /**
     * 读取中断在指定Excel行，为null表示未中断(行号从1开始)
     */
    getInterruptByRowNum(): JLong | undefined;

    /**
     * Excel表格头所占行数(复杂嵌套表格头行数大于1)
     */
    getHeadRowNum(): JInt;

    /**
     * 当前解析的数据是否有失败的
     */
    hasError(): JBoolean;

    /**
     * 返回导入成功的数据
     */
    getImportData(): JList<T>;

    /**
     * 返回导入失败的数据
     */
    getFailRows(): JList<ExcelRow<T>>;

    /**
     * 返回Excel导入状态
     */
    getExcelImportState(): ExcelImportState;

    /**
     * 清除导入数据
     */
    clearData(): void;
}

interface ExcelDataMap<T> {
    /**
     * 返回第一个页签数据
     */
    getFirstExcelData(): ExcelData<T>;

    /**
     * 根据页签编号返回页签数据
     */
    getExcelData(sheetNo: JInt): ExcelData<T>;

    /**
     * 根据页签名称返回页签数据
     */
    getExcelData(sheetName: JString): ExcelData<T>;

    /**
     * Excel读取结果
     */
    getExcelSheetMap(): JMap<JString, ExcelData<T>>;
}

interface ExcelDataReader<T> extends ExcelDataMap<T> {
    /**
     * Excel文件名称
     */
    getFilename(): JString;

    /**
     * 读取Excel文件最大行数
     */
    getLimitRows(): JInt;

    /**
     * 是否缓存读取的数据结果到内存中(默认启用)
     */
    isEnableExcelData(): JBoolean;

    /**
     * 返回Excel文件读取器
     */
    read(): ExcelReaderBuilder<T>;
}

interface ExcelWriter<T> {
    /**
     * 写入数据
     */
    write(listData: JList<T | JMap<JString, any>>): void;

    /**
     * 根据模块填充数据
     */
    fill(data: T | JMap<JString, any>, fillConfig: ExcelFillConfig): void;

    /**
     * 根据模块填充数据
     */
    fill(data: T | JMap<JString, any>): void;

    /**
     * 根据模块填充数据
     */
    fill(listData: JList<T | JMap<JString, any>>, fillConfig: ExcelFillConfig): void;

    /**
     * 根据模块填充数据
     */
    fill(listData: JList<T | JMap<JString, any>>): void;

    /**
     * 写入完成操作
     */
    finish(): void;
}

//---------------------------------------------------------------------------------------------------------------------------------------------- ExcelUtils API设计

/** 读取Excel时的表头配置 */
interface ExcelReaderHeadConfig extends ExcelProperty, Partial<DateTimeFormat>, Partial<NumberFormat> {
    /** 列的数据类型 */
    dataType: ExcelDataType;

    // TODO 读取数据校验配置
}

/** 写入Excel时的表头配置 */
interface ExcelWriterHeadConfig extends ExcelProperty, Partial<DateTimeFormat>, Partial<NumberFormat>, Partial<ColumnWidth> {
    /** 表头字体样式 */
    headFontStyle?: HeadFontStyle;
    /** 表头样式 */
    headStyle?: HeadStyle;

    /** 内容字体样式 */
    contentFontStyle?: ContentFontStyle;
    /** 内容样式 */
    contentStyle?: ContentStyle;

    /** 行列合并配置 */
    contentLoopMerge?: ContentLoopMerge;
}

/** 写入Excel时的全局样式配置 */
interface ExcelWriterStyleConfig extends Partial<HeadRowHeight>, Partial<ContentRowHeight> {
    /** 表头字体样式 */
    headFontStyle?: HeadFontStyle;
    /** 表头样式 */
    headStyle?: HeadStyle;

    /** 内容字体样式 */
    contentFontStyle?: ContentFontStyle;
    /** 内容样式 */
    contentStyle?: ContentStyle;

    /** 行列合并配置 */
    onceAbsoluteMerge?: OnceAbsoluteMerge;
}

interface ExcelReaderExceptionHand {
    /**
     * 处理读取Excel异常
     * <pre>
     *  context.readRowHolder()                     当前行相关信息
     *  context.readSheetHolder()                   当前页签相关信息
     *  context.readRowHolder().getCellMap()        行数据
     *  context.readRowHolder().getRowIndex() + 1   行号
     * </pre>
     *
     * @param throwable 当前行异常
     * @param context   Excel读取上下文信息
     */
    exceptionHand(throwable: JThrowable, context: AnalysisContext): void;
}

interface ExcelRowReader<T> {
    /**
     * 处理Excel数据行(用于自定义校验)
     * <pre>
     *  excelRow.addErrorInColumn() 增加列错误
     *  excelRow.addErrorInRow()    增加行错误
     * </pre>
     *
     * @param data     校验通过的数据
     * @param excelRow 数据行对象
     * @param context  Excel读取上下文信息
     */
    readRow(data: T, excelRow: ExcelRow<T>, context: AnalysisContext): void;

    /**
     * 读取结束时调用
     */
    readEnd?(context: AnalysisContext): void;
}

/** 读取Excel时的初始化配置 */
class ExcelReaderConfig<T extends object = BaseEntity> {
    /** Excel文件上传的请求对象 */
    request?: JHttpServletRequest;
    /** Excel文件名称(或者Excel文件路径) */
    filename?: JString;
    /** 文件输入流 */
    inputStream?: JInputStream;
    /**  读取Excel文件最大行数(默认: 2000)，小于0表示不限制 */
    limitRows?: JInt = 2000;
    /** 是否缓存读取的数据结果到内存中(默认启用) */
    enableExcelData?: JBoolean = true;
    /** 是否启用数据校验(默认启用) */
    enableValidation?: JBoolean = true;
    /** 处理读取Excel异常 */
    excelReaderExceptionHand?: ExcelReaderExceptionHand;
    /** 处理Excel数据行 */
    excelRowReader?: ExcelRowReader<T>;
    // ----------------------------------------------------------------------
    /** 是否自动关闭输入流 */
    autoCloseStream?: JBoolean = false;
    /** 读取扩展信息配置 */
    extraRead?: CellExtraTypeEnum[] = [];
    /** 是否忽略空行 */
    ignoreEmptyRow?: JBoolean = false;
    /** 强制使用输入流，如果为false，则将“inputStream”传输到临时文件以提高效率 */
    mandatoryUseInputStream?: JBoolean = false;
    /** Excel文件密码 */
    password?: JString;
    /** Excel页签编号(从0开始) */
    sheetNo?: JInt;
    /** Excel页签名称(xlsx格式才支持) */
    sheetName?: JString;
    /** 表头行数 */
    headRowNumber?: JInt = 1;
    /** 使用科学格式 */
    useScientificFormat?: JBoolean = false;
    /** 如果日期使用1904窗口，则为True；如果使用1900日期窗口，则为false */
    use1904windowing?: JBoolean = false;
    /** Locale对象表示特定的地理、政治或文化区域。设置日期和数字格式时使用此参数 */
    locale?: ExcelLocale = ExcelLocale.SIMPLIFIED_CHINESE;
    /** 自动删除空格字符 */
    autoTrim?: JBoolean = true;
    /** 设置一个自定义对象，可以在侦听器中读取此对象(AnalysisContext.getCustom()) */
    customObject?: any;
    /** Excel列配置(表头) */
    columns?: { [column in keyof T]?: ExcelReaderHeadConfig; } | { [column: string]: ExcelReaderHeadConfig; };

    /**
     * @param sheet           页签编号或名称
     * @param headRowNumber   表头行数
     * @param autoTrim        自动删除空格字符
     * @param ignoreEmptyRow  是否忽略空行
     * @param limitRows       读取Excel文件最大行数(默认: 2000)，小于0表示不限制
     * @param locale          Locale对象表示特定的地理、政治或文化区域。设置日期和数字格式时使用此参数
     * @param password        Excel文件密码
     */
    constructor(sheet?: JInt | JString, headRowNumber?: JInt, autoTrim?: JBoolean, ignoreEmptyRow?: JBoolean, limitRows?: JInt, locale?: ExcelLocale, password?: JString) {
        if (isString(sheet)) {
            this.sheetName = sheet;
        } else if (isNumber(sheet)) {
            this.sheetNo = sheet;
        } else {
            this.sheetNo = 0;
        }
        this.headRowNumber = headRowNumber ?? 1;
        this.autoTrim = autoTrim ?? true;
        this.ignoreEmptyRow = ignoreEmptyRow ?? false;
        this.limitRows = limitRows ?? 2000;
        this.locale = locale ?? ExcelLocale.SIMPLIFIED_CHINESE;
        this.password = password ?? undefined;
    }
}

class ExcelWriterConfig<T extends object> {
    /** Excel导出请求对象 */
    request?: JHttpServletRequest
    /** Excel导出响应对象 */
    response?: JHttpServletResponse;
    /** Excel导出文件名(或者Excel文件路径) */
    fileName?: JString;
    /** Excel文件对应输出流 */
    outputStream?: JOutputStream;
    // ----------------------------------------------------------------------
    /** 是否自动关闭输入流 */
    autoCloseStream?: JBoolean = false;
    /** 在内存中编写excel。默认为false，则创建缓存文件并最终写入excel。仅在内存模式下支持Comment和RichTextString */
    inMemory?: JBoolean = false;
    /** Excel模板文件路径 */
    template?: JString;
    /** Excel模板文件输入流 */
    templateInputStream?: JInputStream;
    /** 写入Excel时出现异常是否仍然继续导出 */
    writeExcelOnException?: JBoolean = false;
    /** 是否自动合并表头 */
    automaticMergeHead?: JBoolean = true;
    /** 忽略自定义列 */
    excludeColumnFiledNames?: JString[];
    /** 忽略自定义列 */
    excludeColumnIndexes?: JInt[];
    /** 只输出自定义列 */
    includeColumnFiledNames?: JString[];
    /** 只输出自定义列 */
    includeColumnIndexes?: JInt[];
    /** 是否输出表头 */
    needHead?: JBoolean = true;
    /** 输出第一行的位置 */
    relativeHeadRowIndex?: JInt = 0;
    /** 是否使用默认样式 */
    useDefaultStyle?: JBoolean = true;
    /** Excel类型 */
    excelType?: ExcelTypeEnum = ExcelTypeEnum.XLSX;
    /** Excel文件密码 */
    password?: JString;
    /** Excel页签编号(从0开始) */
    sheetNo?: JInt;
    /** Excel页签名称(xlsx格式才支持) */
    sheetName?: JString;
    /** 如果日期使用1904窗口，则为True；如果使用1900日期窗口，则为false */
    use1904windowing?: JBoolean = false;
    /** Locale对象表示特定的地理、政治或文化区域。设置日期和数字格式时使用此参数 */
    locale?: ExcelLocale = ExcelLocale.SIMPLIFIED_CHINESE;
    /** 自动删除空格字符 */
    autoTrim?: JBoolean = true;
    /** Excel表头 */
    columns?: { [column in keyof T]: ExcelWriterHeadConfig; } | { [column: string]: ExcelWriterHeadConfig; };
    // ----------------------------------------------------------------------
    /** 全局样式配置 */
    styleConfig?: ExcelWriterStyleConfig;
}

interface ExcelUtils {
    /**
     * 创建Excel数据读取器
     * @param initConfig 初始化配置
     */
    createReader<T extends object = BaseEntity>(initConfig: ExcelReaderConfig<T>): ExcelDataReader<T>;

    /**
     * 读取Excel数据
     * @param config 配置
     */
    read<T extends object = BaseEntity>(config: ExcelReaderConfig<T>): ExcelDataMap<T>;

    /**
     * 创建Excel数据写入器
     * @param initConfig 初始化配置
     */
    createWriter<T extends object = BaseEntity>(initConfig: ExcelWriterConfig<T>): ExcelWriter<T>;

    /**
     * 直接一次性写入数据
     */
    write<T extends object = BaseEntity>(config: ExcelWriterConfig<T>, listData: JList<T | JMap<JString, any>>): void;
}

declare const ExcelUtils: ExcelUtils;
