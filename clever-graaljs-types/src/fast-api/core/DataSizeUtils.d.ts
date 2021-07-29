/**
 * 作者：lizw <br/>
 * 创建时间：2020/07/28 22:34 <br/>
 */
interface DataSizeUtils extends JObject {
    /**
     * 返回容易读取的数据大小,如：15B、12.36KB、1.58MB、25.3GB
     *
     * @param size 文件大小 单位：bit
     * @return 返回容易读取的文件大小数据, 参数是null，返回null
     */
    getHumanReadableSize(size: JLong): JString;
}

declare const DataSizeUtils: DataSizeUtils;
