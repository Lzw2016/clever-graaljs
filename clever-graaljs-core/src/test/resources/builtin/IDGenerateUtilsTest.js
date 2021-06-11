const log = LoggerFactory.getLogger("logger");

log.info("uuid                  --------------->{}", IDGenerateUtils.uuid());
log.info("uuidNotSplit          --------------->{}", IDGenerateUtils.uuidNotSplit());
log.info("shortUuid             --------------->{}", IDGenerateUtils.shortUuid());
log.info("objectId              --------------->{}", IDGenerateUtils.objectId());
log.info("snowFlakeId           --------------->{}", IDGenerateUtils.snowFlakeId());