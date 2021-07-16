package org.clever.graaljs.spring.core.builtin.constant;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/07/16 16:13 <br/>
 */
public interface ExcelEnum {
    /**
     * Excel文件格式
     */
    final class ExcelTypeEnum {
        public static final ExcelTypeEnum Instance = new ExcelTypeEnum();

        private ExcelTypeEnum() {
        }

        /**
         * .xls
         */
        public final String XLS = "XLS";
        /**
         * .xlsx
         */
        public final String XLSX = "XLSX";
    }

    /**
     * Excel扩展信息
     */
    final class CellExtraTypeEnum {
        public static final CellExtraTypeEnum Instance = new CellExtraTypeEnum();

        private CellExtraTypeEnum() {
        }

        /**
         * 批注信息
         */
        public final String COMMENT = "COMMENT";
        /**
         * 超链接
         */
        public final String HYPERLINK = "HYPERLINK";
        /**
         * 合并单元格
         */
        public final String MERGE = "MERGE";
    }

    /**
     * 单元格类型
     */
    final class CellDataTypeEnum {
        public static final CellDataTypeEnum Instance = new CellDataTypeEnum();

        private CellDataTypeEnum() {
        }

        /**
         * 字符串
         */
        public final String STRING = "STRING";
        /**
         * 不需要在sharedStrings.xml，它只用于过度使用，并且数据将存储为字符串
         */
        public final String DIRECT_STRING = "DIRECT_STRING";
        /**
         * 数字
         */
        public final String NUMBER = "NUMBER";
        /**
         * boolean值
         */
        public final String BOOLEAN = "BOOLEAN";
        /**
         * 空单元格
         */
        public final String EMPTY = "EMPTY";
        /**
         * 错误格
         */
        public final String ERROR = "ERROR";
        /**
         * 当前仅在写入时支持图像
         */
        public final String IMAGE = "IMAGE";
    }

    /**
     * 语言选项
     */
    final class ExcelLocale {
        public static final ExcelLocale Instance = new ExcelLocale();

        private ExcelLocale() {
        }

        /**
         * 英语
         */
        public final String ENGLISH = "ENGLISH";
        /**
         * 中文
         */
        public final String CHINESE = "CHINESE";
        /**
         * 简体中文
         */
        public final String SIMPLIFIED_CHINESE = "SIMPLIFIED_CHINESE";
        /**
         * 繁体中文
         */
        public final String TRADITIONAL_CHINESE = "TRADITIONAL_CHINESE";
    }

    /**
     * 内置格式化选项
     */
    final class BuiltinFormats {
        public static final BuiltinFormats Instance = new BuiltinFormats();

        private BuiltinFormats() {
        }

        byte General = 0;
        /**
         * "0"
         */
        public final byte Fmt_1 = 1;
        /**
         * "0.00"
         */
        public final byte Fmt_2 = 2;
        /**
         * "#;##0"
         */
        public final byte Fmt_3 = 3;
        /**
         * "#;##0.00"
         */
        public final byte Fmt_4 = 4;
        /**
         * "$#;##0_);($#;##0)"
         */
        public final byte Fmt_5 = 5;
        /**
         * "$#;##0_);[Red]($#;##0)"
         */
        public final byte Fmt_6 = 6;
        /**
         * "$#;##0.00);($#;##0.00)"
         */
        public final byte Fmt_7 = 7;
        /**
         * "$#;##0.00_);[Red]($#;##0.00)"
         */
        public final byte Fmt_8 = 8;
        /**
         * "0%"
         */
        public final byte Fmt_9 = 9;
        /**
         * "0.00%"
         */
        public final byte Fmt_10 = 0xa;
        /**
         * "0.00E+00"
         */
        public final byte Fmt_11 = 0xb;
        /**
         * "# ?/?"
         */
        public final byte Fmt_12 = 0xc;
        /**
         * "# ??/??"
         */
        public final byte Fmt_13 = 0xd;
        /**
         * "m/d/yy"
         */
        public final byte Fmt_14 = 0xe;
        /**
         * "d-mmm-yy"
         */
        public final byte Fmt_15 = 0xf;
        /**
         * "d-mmm"
         */
        public final byte Fmt_16 = 0x10;
        /**
         * "mmm-yy"
         */
        public final byte Fmt_17 = 0x11;
        /**
         * "h:mm AM/PM"
         */
        public final byte Fmt_18 = 0x12;
        /**
         * "h:mm:ss AM/PM"
         */
        public final byte Fmt_19 = 0x13;
        /**
         * "h:mm"
         */
        public final byte Fmt_20 = 0x14;
        /**
         * "h:mm:ss"
         */
        public final byte Fmt_21 = 0x15;
        /**
         * "m/d/yy h:mm"
         */
        public final byte Fmt_22 = 0x16;
        // 0x17 - 0x24 reserved for international and undocume
        /**
         * "#;##0_);(#;##0)"
         */
        public final byte Fmt_37 = 0x25;
        /**
         * "#;##0_);[Red](#;##0)"
         */
        public final byte Fmt_38 = 0x26;
        /**
         * "#;##0.00_);(#;##0.00)"
         */
        public final byte Fmt_39 = 0x27;
        /**
         * "#;##0.00_);[Red](#;##0.00)"
         */
        public final byte Fmt_40 = 0x28;
        /**
         * "_(* #;##0_);_(* (#;##0);_(* \"-\"_);_(@_)"
         */
        public final byte Fmt_41 = 0x29;
        /**
         * "_($* #;##0_);_($* (#;##0);_($* \"-\"_);_(@_)"
         */
        public final byte Fmt_42 = 0x2a;
        /**
         * "_(* #;##0.00_);_(* (#;##0.00);_(* \"-\"??_);_(@_)"
         */
        public final byte Fmt_43 = 0x2b;
        /**
         * "_($* #;##0.00_);_($* (#;##0.00);_($* \"-\"??_);_(@_)"
         */
        public final byte Fmt_44 = 0x2c;
        /**
         * "mm:ss"
         */
        public final byte Fmt_45 = 0x2d;
        /**
         * "[h]:mm:ss"
         */
        public final byte Fmt_46 = 0x2e;
        /**
         * "mm:ss.0"
         */
        public final byte Fmt_47 = 0x2f;
        /**
         * "##0.0E+0"
         */
        public final byte Fmt_48 = 0x30;
        /**
         * "@" - This is text format.
         */
        public final byte Fmt_49 = 0x31;
        /**
         * "text" - Alias for "@"
         */
        public final byte Fmt_50 = 0x31;
    }

    /**
     * 水平对齐选项
     */
    final class HorizontalAlignment {
        public static final HorizontalAlignment Instance = new HorizontalAlignment();

        private HorizontalAlignment() {
        }

        public final String GENERAL = "GENERAL";
        public final String LEFT = "LEFT";
        public final String CENTER = "CENTER";
        public final String RIGHT = "RIGHT";
        public final String FILL = "FILL";
        public final String JUSTIFY = "JUSTIFY";
        public final String CENTER_SELECTION = "CENTER_SELECTION";
        public final String DISTRIBUTED = "DISTRIBUTED";
    }

    /**
     * 垂直对齐选项
     */
    final class VerticalAlignment {
        public static final VerticalAlignment Instance = new VerticalAlignment();

        private VerticalAlignment() {
        }

        public final String TOP = "TOP";
        public final String CENTER = "CENTER";
        public final String BOTTOM = "BOTTOM";
        public final String JUSTIFY = "JUSTIFY";
        public final String DISTRIBUTED = "DISTRIBUTED";
    }

    /**
     * 边框样式
     */
    final class BorderStyle {
        public static final BorderStyle Instance = new BorderStyle();

        private BorderStyle() {
        }

        public final byte NONE = (0x0);
        public final byte THIN = (0x1);
        public final byte MEDIUM = (0x2);
        public final byte DASHED = (0x3);
        public final byte DOTTED = (0x4);
        public final byte THICK = (0x5);
        public final byte DOUBLE = (0x6);
        public final byte HAIR = (0x7);
        public final byte MEDIUM_DASHED = (0x8);
        public final byte DASH_DOT = (0x9);
        public final byte MEDIUM_DASH_DOT = (0xA);
        public final byte DASH_DOT_DOT = (0xB);
        public final byte MEDIUM_DASH_DOT_DOT = (0xC);
        public final byte SLANTED_DASH_DOT = (0xD);
    }

    /**
     * 颜色
     */
    final class IndexedColors {
        public static final IndexedColors Instance = new IndexedColors();

        private IndexedColors() {
        }

        public final byte BLACK1 = (0);
        public final byte WHITE1 = (1);
        public final byte RED1 = (2);
        public final byte BRIGHT_GREEN1 = (3);
        public final byte BLUE1 = (4);
        public final byte YELLOW1 = (5);
        public final byte PINK1 = (6);
        public final byte TURQUOISE1 = (7);
        public final byte BLACK = (8);
        public final byte WHITE = (9);
        public final byte RED = (10);
        public final byte BRIGHT_GREEN = (11);
        public final byte BLUE = (12);
        public final byte YELLOW = (13);
        public final byte PINK = (14);
        public final byte TURQUOISE = (15);
        public final byte DARK_RED = (16);
        public final byte GREEN = (17);
        public final byte DARK_BLUE = (18);
        public final byte DARK_YELLOW = (19);
        public final byte VIOLET = (20);
        public final byte TEAL = (21);
        public final byte GREY_25_PERCENT = (22);
        public final byte GREY_50_PERCENT = (23);
        public final byte CORNFLOWER_BLUE = (24);
        public final byte MAROON = (25);
        public final byte LEMON_CHIFFON = (26);
        public final byte LIGHT_TURQUOISE1 = (27);
        public final byte ORCHID = (28);
        public final byte CORAL = (29);
        public final byte ROYAL_BLUE = (30);
        public final byte LIGHT_CORNFLOWER_BLUE = (31);
        public final byte SKY_BLUE = (40);
        public final byte LIGHT_TURQUOISE = (41);
        public final byte LIGHT_GREEN = (42);
        public final byte LIGHT_YELLOW = (43);
        public final byte PALE_BLUE = (44);
        public final byte ROSE = (45);
        public final byte LAVENDER = (46);
        public final byte TAN = (47);
        public final byte LIGHT_BLUE = (48);
        public final byte AQUA = (49);
        public final byte LIME = (50);
        public final byte GOLD = (51);
        public final byte LIGHT_ORANGE = (52);
        public final byte ORANGE = (53);
        public final byte BLUE_GREY = (54);
        public final byte GREY_40_PERCENT = (55);
        public final byte DARK_TEAL = (56);
        public final byte SEA_GREEN = (57);
        public final byte DARK_GREEN = (58);
        public final byte OLIVE_GREEN = (59);
        public final byte BROWN = (60);
        public final byte PLUM = (61);
        public final byte INDIGO = (62);
        public final byte GREY_80_PERCENT = (63);
        public final byte AUTOMATIC = (64);
    }

    /**
     * 填充模式
     */
    final class FillPatternType {
        public static final FillPatternType Instance = new FillPatternType();

        private FillPatternType() {
        }

        public final byte NO_FILL = (0);
        public final byte SOLID_FOREGROUND = (1);
        public final byte FINE_DOTS = (2);
        public final byte ALT_BARS = (3);
        public final byte SPARSE_DOTS = (4);
        public final byte THICK_HORZ_BANDS = (5);
        public final byte THICK_VERT_BANDS = (6);
        public final byte THICK_BACKWARD_DIAG = (7);
        public final byte THICK_FORWARD_DIAG = (8);
        public final byte BIG_SPOTS = (9);
        public final byte BRICKS = (10);
        public final byte THIN_HORZ_BANDS = (11);
        public final byte THIN_VERT_BANDS = (12);
        public final byte THIN_BACKWARD_DIAG = (13);
        public final byte THIN_FORWARD_DIAG = (14);
        public final byte SQUARES = (15);
        public final byte DIAMONDS = (16);
        public final byte LESS_DOTS = (17);
        public final byte LEAST_DOTS = (18);
    }

    /**
     * 单元格数据类型
     */
    final class ExcelDataType {
        public static final ExcelDataType Instance = new ExcelDataType();

        private ExcelDataType() {
        }

        public final String JString = "JString";
        public final String JBigDecimal = "JBigDecimal";
        public final String JBoolean = "JBoolean";
        public final String JDate = "JDate";
        public final String JInteger = "JInteger";
        public final String JDouble = "JDouble";
        public final String JLong = "JLong";
        public final String JFloat = "JFloat";
        public final String JShort = "JShort";
        public final String JByte = "JByte";
        public final String JByteArray = "JByte[]";
    }

    /**
     * 下划线类型
     */
    final class ExcelUnderline {
        public static final ExcelUnderline Instance = new ExcelUnderline();

        private ExcelUnderline() {
        }

        /**
         * 不加下划线
         */
        public final byte U_NONE = 0;
        /**
         * 单下划线
         */
        public final byte U_SINGLE = 1;
        /**
         * 双下划线
         */
        public final byte U_DOUBLE = 2;
        /**
         * 会计风格单下划线
         */
        public final byte U_SINGLE_ACCOUNTING = 0x21;
        /**
         * 会计风格双下划线
         */
        public final byte U_DOUBLE_ACCOUNTING = 0x22;
    }

    /**
     * 普通，上标或下标
     */
    final class ExcelTypeOffset {
        public static final ExcelTypeOffset Instance = new ExcelTypeOffset();

        private ExcelTypeOffset() {
        }

        /**
         * 没有类型偏移（不是上标或下标）
         */
        public final byte SS_NONE = 0;
        /**
         * 上标
         */
        public final byte SS_SUPER = 1;
        /**
         * 下标
         */
        public final byte SS_SUB = 2;
    }

    final class ExcelFontCharset {
        public static final ExcelFontCharset Instance = new ExcelFontCharset();

        private ExcelFontCharset() {
        }

        public final String ANSI = "ANSI";
        public final String DEFAULT = "DEFAULT";
        public final String SYMBOL = "SYMBOL";
        public final String MAC = "MAC";
        public final String SHIFTJIS = "SHIFTJIS";
        public final String HANGUL = "HANGUL";
        public final String JOHAB = "JOHAB";
        public final String GB2312 = "GB2312";
        public final String CHINESEBIG5 = "CHINESEBIG5";
        public final String GREEK = "GREEK";
        public final String TURKISH = "TURKISH";
        public final String VIETNAMESE = "VIETNAMESE";
        public final String HEBREW = "HEBREW";
        public final String ARABIC = "ARABIC";
        public final String BALTIC = "BALTIC";
        public final String RUSSIAN = "RUSSIAN";
        public final String THAI_ = "THAI_";
        public final String EASTEUROPE = "EASTEUROPE";
        public final String OEM = "OEM";
    }

    final class WriteDirectionEnum {
        public static final WriteDirectionEnum Instance = new WriteDirectionEnum();

        private WriteDirectionEnum() {
        }

        /**
         * 垂直写入
         */
        public final String VERTICAL = "VERTICAL";
        /**
         * 横向写
         */
        public final String HORIZONTAL = "HORIZONTAL";
    }
}
