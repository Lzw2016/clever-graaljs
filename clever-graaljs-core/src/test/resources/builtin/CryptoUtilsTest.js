const log = LoggerFactory.getLogger("logger");

const input = StringUtils.getByteFromString("李志伟123");
const key = CryptoUtils.generateAesKey();
const iv = CryptoUtils.generateIV();
// 加密
const data = CryptoUtils.aesEncrypt(input, key, iv);
const dataStr = EncodeDecodeUtils.encodeHex(data);
log.info("加密 ---> {}", dataStr);
// 解密
const str = CryptoUtils.aesDecrypt(EncodeDecodeUtils.decodeHex(dataStr), key, iv);
log.info("解密 ---> {}", str);
