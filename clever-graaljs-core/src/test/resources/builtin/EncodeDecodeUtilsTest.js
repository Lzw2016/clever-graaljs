const log = LoggerFactory.getLogger("logger");

const hex = "89ac8acc41ab3bc2c56c4f89c5c1a23a567a"
log.info("isHexCode     -> {}", EncodeDecodeUtils.isHexCode(hex));
const data = EncodeDecodeUtils.decodeHex(hex);
log.info("decodeHex     -> {}", [data]);
log.info("encodeHex     -> {}", EncodeDecodeUtils.encodeHex(data));
log.info("encodeBase62  -> {}", EncodeDecodeUtils.encodeBase62(data));
log.info("encodeBase64  -> {}", EncodeDecodeUtils.encodeBase64(data));