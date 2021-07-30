/**
 * Http请求Method
 */
enum HttpMethod {
    GET = "GET",
    POST = "POST",
    DELETE = "DELETE",
    PUT = "PUT",
    HEAD = "HEAD",
    OPTIONS = "OPTIONS",
    PATCH = "PATCH",
    TRACE = "TRACE",
    CONNECT = "CONNECT",
    MOVE = "MOVE",
    PROPPATCH = "PROPPATCH",
    REPORT = "REPORT",
}

/**
 * 拼音大小写类型枚举
 */
enum HanyuPinyinCaseType {
    /** 大写 */
    UPPERCASE = "UPPERCASE",
    /** 小写 */
    LOWERCASE = "LOWERCASE",
}

/**
 * 拼音声调类型枚举
 */
enum HanyuPinyinToneType {
    /** 此选项表示以声调数字输出汉语拼音 */
    WITH_TONE_NUMBER = "WITH_TONE_NUMBER",
    /** 此选项表示输出的汉语拼音没有声调数字或声调标记 */
    WITHOUT_TONE = "WITHOUT_TONE",
    /** 此选项表示输出的汉语拼音带有声调标记 */
    WITH_TONE_MARK = "WITH_TONE_MARK",
}

/**
 * 拼音字母“ü”输出格式枚举
 */
enum HanyuPinyinVCharType {
    /** 该选项表示'ü'的输出是'u:' */
    WITH_U_AND_COLON = "WITH_U_AND_COLON",
    /** 该选项表示'ü'的输出是'v' */
    WITH_V = "WITH_V",
    /** 该选项表示'ü'的输出是'ü' */
    WITH_U_UNICODE = "WITH_U_UNICODE",
}

/**
 * 二维码类型格式
 */
enum BarcodeFormat {
    /** Aztec 2D barcode format. */
    AZTEC = "AZTEC",

    /** CODABAR 1D format. */
    CODABAR = "CODABAR",

    /** Code 39 1D format. */
    CODE_39 = "CODE_39",

    /** Code 93 1D format. */
    CODE_93 = "CODE_93",

    /** Code 128 1D format. */
    CODE_128 = "CODE_128",

    /** Data Matrix 2D barcode format. */
    DATA_MATRIX = "DATA_MATRIX",

    /** EAN-8 1D format. */
    EAN_8 = "EAN_8",

    /** EAN-13 1D format. */
    EAN_13 = "EAN_13",

    /** ITF (Interleaved Two of Five) 1D format. */
    ITF = "ITF",

    /** MaxiCode 2D barcode format. */
    MAXICODE = "MAXICODE",

    /** PDF417 format. */
    PDF_417 = "PDF_417",

    /** QR Code 2D barcode format. */
    QR_CODE = "QR_CODE",

    /** RSS 14 */
    RSS_14 = "RSS_14",

    /** RSS EXPANDED */
    RSS_EXPANDED = "RSS_EXPANDED",

    /** UPC-A 1D format. */
    UPC_A = "UPC_A",

    /** UPC-E 1D format. */
    UPC_E = "UPC_E",

    /** UPC/EAN extension format. Not a stand-alone format. */
    UPC_EAN_EXTENSION = "UPC_EAN_EXTENSION",
}
