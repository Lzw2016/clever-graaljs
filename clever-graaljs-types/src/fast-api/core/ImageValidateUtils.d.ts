interface ImageValidateUtils {
    /**
     * 生成随机的字符串
     *
     * @param length 设置随机字符串的长度
     * @return 随机字符串
     */
    getRandString(length: JInt): JString;

    // 常规图片验证码生成
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 创建验证码图片，并把图片数据写到输出流中
     *
     * @param code         要生成的验证码字符串
     * @param outputStream 输出流
     * @return 成功返回true，失败返回false
     */
    createImageStream(code: JString, outputStream: JOutputStream): boolean;

    /**
     * 创建验证码图片，并把图片数据写到输出流中<br/>
     * 图片验证码随机生成，长度是4位<br/>
     *
     * @param outputStream 输出流
     * @return 成功返回图片验证码，失败返回null
     */
    createImageStream(outputStream: JOutputStream): JString;

    /**
     * 创建验证码图片，并返回图片数据<br/>
     *
     * @param code 要生成的验证码字符串
     * @return 成功返回图片数据
     */
    createImage(code: JString): JByte[];

    /**
     * 创建验证码图片，并返回图片数据<br/>
     * 图片验证码随机生成，长度是4位<br/>
     *
     * @return 成功返回图片数据
     */
    createImage(): JByte[];

    // 使用 Cage 生成图片验证码
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 创建验证码图片，并把图片数据写到输出流中
     *
     * @param code         要生成的验证码字符串
     * @param outputStream 输出流
     * @return 成功返回true，失败返回false
     */
    createImageStreamUseCage(code: JString, outputStream: JOutputStream): JBoolean;

    /**
     * 创建验证码图片，并把图片数据写到输出流中<br/>
     * 图片验证码随机生成，长度是4位<br/>
     *
     * @param outputStream 输出流
     * @return 成功返回图片验证码，失败返回null
     */
    createImageStreamUseCage(outputStream: JOutputStream): JString;

    /**
     * 创建验证码图片，并返回图片数据<br/>
     *
     * @param code 要生成的验证码字符串
     * @return 成功返回图片数据
     */
    createImageUseCage(code: JString): JByte[];

    /**
     * 创建验证码图片，并返回图片数据<br/>
     * 图片验证码随机生成，长度是4位<br/>
     *
     * @return 成功返回图片数据
     */
    createImageUseCage(): JByte[];

    // 使用 Kaptcha 生成图片验证码
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 创建验证码图片，并把图片数据写到输出流中
     *
     * @param code         要生成的验证码字符串
     * @param outputStream 输出流
     * @return 成功返回true，失败返回false
     */
    createImageStreamUseKaptcha(code: JString, outputStream: JOutputStream): JBoolean;

    /**
     * 创建验证码图片，并把图片数据写到输出流中<br/>
     * 图片验证码随机生成，长度是4位<br/>
     *
     * @param outputStream 输出流
     * @return 成功返回图片验证码，失败返回null
     */
    createImageStreamUseKaptcha(outputStream: JOutputStream): JString;

    /**
     * 创建验证码图片，并返回图片数据<br/>
     *
     * @param code 要生成的验证码字符串
     * @return 成功返回图片数据
     */
    createImageUseKaptcha(code: JString): JByte[];

    /**
     * 创建验证码图片，并返回图片数据<br/>
     * 图片验证码随机生成，长度是4位<br/>
     *
     * @return 成功返回图片数据
     */
    createImageUseKaptcha(): JByte[];

    // 使用 Patchca 生成图片验证码
    //----------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 创建验证码图片，并把图片数据写到输出流中<br/>
     * 图片验证码随机生成，长度是4位<br/>
     *
     * @param outputStream 输出流
     * @return 成功返回图片验证码，失败返回null
     */
    createImageStreamUsePatchca(outputStream: JOutputStream): JString;

    /**
     * 创建验证码图片，并返回图片数据<br/>
     * 图片验证码随机生成，长度是4位<br/>
     *
     * @return 成功返回图片数据
     */
    createImageUsePatchca(): JByte[];
}

declare const ImageValidateUtils: ImageValidateUtils;
