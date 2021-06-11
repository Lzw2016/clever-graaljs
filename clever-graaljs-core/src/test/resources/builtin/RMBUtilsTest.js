const log = LoggerFactory.getLogger("logger");

const res = RMBUtils.digitUppercase(Interop.asJDouble("167545.36"))
log.info("res -> {}", res);