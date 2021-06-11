const log = LoggerFactory.getLogger("CommonUtilsTest.js");

log.info("sleep             --------------->{}", CommonUtils.sleep(3000));
log.info("yield             --------------->{}", CommonUtils.yield());
log.info("hashCode          --------------->{}", CommonUtils.hashCode("log"));
log.info("equals            --------------->{}", CommonUtils.equals("1", "1"));
log.info("same              --------------->{}", CommonUtils.same("log", "log"));
log.info("currentTimeMillis --------------->{}", CommonUtils.currentTimeMillis());
log.info("now               --------------->{}", CommonUtils.now());
log.info("getClass          --------------->{}", CommonUtils.getClass(log));
log.info("getClassName      --------------->{}", CommonUtils.getClassName([]));
log.info("toString          --------------->{}", CommonUtils.toString(["", ""]));
log.info("toString          --------------->{}", CommonUtils.toString(null, "123"));
