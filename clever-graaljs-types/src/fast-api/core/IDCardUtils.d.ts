interface IDCardUtils {
    /**
     * 将15位身份证号码转换为18位
     *
     * @param idCard 15位身份编码
     * @return 18位身份编码，失败返回null
     */
    convert15CardTo18(idCard: JString): JString;

    /**
     * 验证身份证是否合法
     */
    validateCard(idCard: JString): JBoolean;

    /**
     * 验证18位身份编码是否合法
     *
     * @param idCard 身份编码
     * @return 是否合法
     */
    validateIdCard18(idCard: JString): JBoolean;

    /**
     * 验证15位身份编码是否合法
     *
     * @param idCard 身份编码
     * @return 是否合法
     */
    validateIdCard15(idCard: JString): JBoolean;

    /**
     * 验证10位身份编码是否合法
     *
     * @param idCard 身份编码
     * @return 身份证信息数组
     * <p>
     * [0] - 台湾、澳门、香港 <br/>
     * [1] - 性别(男M,女F,未知N) <br/>
     * [2] - 是否合法(合法true,不合法false) <br/>
     * 若不是身份证件号码则返回null <br/>
     * </p>
     */
    validateIdCard10(idCard: JString): JString[];

    /**
     * 验证台湾身份证号码
     *
     * @param idCard 身份证号码
     * @return 验证码是否符合
     */
    validateTWCard(idCard: JString): JBoolean;

    /**
     * 验证香港身份证号码(存在Bug，部份特殊身份证无法检查)<br/>
     * <p>
     * 身份证前2位为英文字符，如果只出现一个英文字符则表示第一位是空格，
     * 对应数字58 前2位英文字符A-Z分别对应数字10-35
     * 最后一位校验码为0-9的数字加上字符"A"，"A"代表10
     * </p>
     * <p>
     * 将身份证号码全部转换为数字，分别对应乘9-1相加的总和，整除11则证件号码有效
     * </p>
     *
     * @param idCard 身份证号码
     * @return 验证码是否符合
     */
    validateHKCard(idCard: JString): JBoolean;

    /**
     * 根据身份编号获取年龄
     *
     * @param idCard 身份编号
     * @return 年龄
     */
    getAgeByIdCard(idCard: JString): JInt;

    /**
     * 根据身份编号获取生日
     *
     * @param idCard 身份编号
     * @return 生日(yyyyMMdd)
     */
    getBirthByIdCard(idCard: JString): JString;

    /**
     * 根据身份编号获取生日年
     *
     * @param idCard 身份编号
     * @return 生日(yyyy)
     */
    getYearByIdCard(idCard: JString): JShort;

    /**
     * 根据身份编号获取生日月
     *
     * @param idCard 身份编号
     * @return 生日(MM)
     */
    getMonthByIdCard(idCard: JString): JShort;

    /**
     * 根据身份编号获取生日天
     *
     * @param idCard 身份编号
     * @return 生日(dd)
     */
    getDateByIdCard(idCard: JString): JShort;

    /**
     * 根据身份编号获取性别
     *
     * @param idCard 身份编号
     * @return 性别(M 男, F 女, N 未知)
     */
    getGenderByIdCard(idCard: JString): JString;

    /**
     * 根据身份编号获取户籍省份
     *
     * @param idCard 身份编码
     * @return 省级编码。
     */
    getProvinceByIdCard(idCard: JString): JString;
}

const IDCardUtils: IDCardUtils;
