/**
 * 响应数据类型
 */
enum MediaType {
    // ----------------------------------------------------------------------------------------- 文本
    /** json */
    Json = "application/json;charset=UTF-8",
    /** xml */
    Xml = "application/xml;charset=UTF-8",
    /** xhtml + xml */
    XhtmlXml = "application/xhtml+xml",
    /** html */
    Html = "text/html",
    /** plain */
    Plain = "text/plain;charset=UTF-8",
    // ----------------------------------------------------------------------------------------- 图片
    /** git */
    Gif = "image/gif",
    /** jpeg */
    Jpeg = "image/jpeg",
    /** png */
    Png = "image/png",
    // ----------------------------------------------------------------------------------------- 音频、视频
    /** x-musicnet-stream */
    AudioMusicNet = "audio/x-musicnet-stream",
    /** mp3 */
    Mp3 = "audio/mp3",
    /** mpeg4 */
    Mpeg4 = "video/mpeg4",
    /** mpg */
    Mpeg = "video/mpg",
    /** rmvb */
    Rmvb = "application/vnd.rn-realmedia-vbr",
    // ----------------------------------------------------------------------------------------- 常见文件类型
    /** pdf */
    Pdf = "application/pdf",
    /** excel */
    Excel = "application/vnd.ms-excel",
    /** word */
    Word = "application/msword",
    /** ppt */
    Ppt = "application/x-ppt",
    // ----------------------------------------------------------------------------------------- 文件
    /** 二进制流 */
    OctetStream = "application/octet-stream",
}
