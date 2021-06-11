const log = LoggerFactory.getLogger("logger");

const ipSet = IPAddressUtils.getInet4Address();
log.info("ipSet -> {}", ipSet);
