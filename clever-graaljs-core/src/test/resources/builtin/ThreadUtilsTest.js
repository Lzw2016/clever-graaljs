const log = LoggerFactory.getLogger("logger");

log.info("track         -> {}", ThreadUtils.track());
log.info("printTrack    -> {}", ThreadUtils.printTrack());
log.info("sleep         -> {}", ThreadUtils.sleep(100));
log.info("yield         -> {}", ThreadUtils.yield());
log.info("currentThread -> {}", CommonUtils.getClassName(ThreadUtils.currentThread()));

