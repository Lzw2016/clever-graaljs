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
