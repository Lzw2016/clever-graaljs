/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/28 22:34 <br/>
 */
interface RMBUtils extends JObject {
    /**
     * 数字金额大写转换，思想先写个完整的然后将如零拾替换成零 要用到正则表达式
     */
    digitUppercase(n: JDouble): JString;
}

const RMBUtils: RMBUtils;
