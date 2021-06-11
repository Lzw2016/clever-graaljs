const log = LoggerFactory.getLogger("logger");

const code = ImageValidateUtils.getRandString(4);
log.info("code -> {}", code);
const byteArr = ImageValidateUtils.createImage();
log.info("byteArr -> {}", [byteArr]);