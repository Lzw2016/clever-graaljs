const log = LoggerFactory.getLogger("logger");

const File = Java.type("java.io.File");
log.info("generateSalt              --------------->{}", [DigestUtils.generateSalt(20)]);
log.info("hmacSha1                  --------------->{}", [DigestUtils.hmacSha1([1, 2, 3, 4], [1, 2, 3, 4])]);
log.info("isHmacSha1Valid           --------------->{}", [DigestUtils.isHmacSha1Valid([3, 8], [1, 2, 3, 4], [1, 2, 3, 4])]);
log.info("generateHmacSha1Key       --------------->{}", [DigestUtils.generateHmacSha1Key()]);
log.info("sha1                      --------------->{}", [DigestUtils.sha1([1, 2, 3, 4])]);
log.info("sha1                      --------------->{}", [DigestUtils.sha1([1, 2, 3, 4], [1, 2, 3, 4])]);
log.info("sha1                      --------------->{}", [DigestUtils.sha1([1, 2, 3, 4], [1, 2, 3, 4], 20)]);
log.info("sha1                      --------------->{}", [DigestUtils.sha1(Java.type("org.apache.commons.io.FileUtils").openInputStream(new File("C:\\Windows\\System32\\drivers\\etc\\hosts")))]);
log.info("md5                       --------------->{}", [DigestUtils.md5([1, 2, 3, 4])]);
log.info("md5                       --------------->{}", [DigestUtils.md5([1, 2, 3, 4], [1, 2, 3, 4])]);
log.info("md5                       --------------->{}", [DigestUtils.md5([1, 2, 3, 4], [1, 2, 3, 4], 20)]);
log.info("md5                       --------------->{}", [DigestUtils.md5(Java.type("org.apache.commons.io.FileUtils").openInputStream(new File("C:\\Windows\\System32\\drivers\\etc\\hosts")))]);
