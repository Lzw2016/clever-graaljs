const log = LoggerFactory.getLogger("logger");

log.info("getAbsolutePath  -->{}", IOUtils.getAbsolutePath("./"));
log.info("isFile           -->{}", IOUtils.isFile("./pom.xml"));
log.info("isDirectory      -->{}", IOUtils.isDirectory("./src"));
log.info("exists           -->{}", IOUtils.exists("./src1"));
log.info("normalize        -->{}", IOUtils.normalize("./src"));
log.info("getExtension     -->{}", IOUtils.getExtension("./pom.xml"));

