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

interface ZxingUtils {
    /**
     * 生成二维码、条形码
     *
     * @param contents     二维码、条形码内容
     * @param format       二维码、条形码的类型
     * @param width        图片宽
     * @param height       图片高
     * @param outputStream 图片数据输出目标流
     * @return 成功返回true，失败返回false
     */
    createImageStream(contents: JString, format: BarcodeFormat, width: JInt, height: JInt, outputStream: JOutputStream): JBoolean;

    /**
     * 生成二维码、条形码
     *
     * @param contents     二维码、条形码内容
     * @param format       二维码、条形码的类型
     * @param outputStream 图片数据输出目标流
     * @return 成功返回true，失败返回false
     */
    createImageStream(contents: JString, format: BarcodeFormat, outputStream: JOutputStream): JBoolean;

    /**
     * 生成二维码、条形码
     *
     * @param contents 二维码、条形码内容
     * @param format   二维码、条形码的类型
     * @param width    图片宽
     * @param height   图片高
     * @return 成功返回图片数据
     */
    createImage(contents: JString, format: BarcodeFormat, width: JInt, height: JInt): JByte[];

    /**
     * 生成二维码、条形码
     *
     * @param contents 二维码、条形码内容
     * @param format   二维码、条形码的类型
     * @return 成功返回图片数据
     */
    createImage(contents: JString, format: BarcodeFormat): JByte[];

    // /**
    //  * 读取条形码、二维码图片里的数据
    //  *
    //  * @param bufferedImage 条形码、二维码图片对象
    //  * @return 返回读取结果
    //  */
    // readerImage(bufferedImage: BufferedImage):JString;

    /**
     * 读取条形码、二维码图片里的数据
     *
     * @param inputStream 条形码、二维码图片输入流
     * @return 返回读取结果
     */
    readerImage(inputStream: JInputStream): JString;

    /**
     * 读取条形码、二维码图片里的数据
     *
     * @param filePath 条形码、二维码图片路径
     * @return 返回读取结果
     */
    readerImage(filePath: JString): JString;

    /**
     * 读取条形码、二维码图片里的数据
     *
     * @param imageData 条形码、二维码图片数据
     * @return 返回读取结果
     */
    readerImage(imageData: JByte[]): JString;

    /**
     * 读取条形码、二维码图片里的数据
     *
     * @param uriAddress 条形码、二维码图片URI地址
     * @return 返回读取结果
     */
    readerImageByUri(uriAddress: JString): JString
}

declare const ZxingUtils: ZxingUtils;
