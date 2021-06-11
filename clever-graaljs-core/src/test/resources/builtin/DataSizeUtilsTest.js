const log = LoggerFactory.getLogger("logger");

const res = DataSizeUtils.getHumanReadableSize(168595631);
log.info("res -> {}", res);