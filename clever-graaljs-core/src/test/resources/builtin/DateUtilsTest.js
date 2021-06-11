const log = LoggerFactory.getLogger("logger");

log.info("currentTimeMillis          --------------->{}", DateUtils.currentTimeMillis());
log.info("now                        --------------->{}", DateUtils.now());
log.info("getCurrentDate(string)     --------------->{}", DateUtils.getCurrentDate("2020-08-22 10:20:30"));
log.info("getCurrentDate             --------------->{}", DateUtils.getCurrentDate());
log.info("getCurrentTime             --------------->{}", DateUtils.getCurrentTime());
log.info("getCurrentDateTime         --------------->{}", DateUtils.getCurrentDateTime());
log.info("getYear                    --------------->{}", DateUtils.getYear());
log.info("getMonth                   --------------->{}", DateUtils.getMonth());
log.info("getDay                     --------------->{}", DateUtils.getDay());
log.info("getWeek                    --------------->{}", DateUtils.getWeek());
log.info("getDate                    --------------->{}", DateUtils.getDate(DateUtils.currentTimeMillis(), "yyyy-MM-dd hh:mm:ss"));
log.info("getDate                    --------------->{}", DateUtils.getDate(DateUtils.currentTimeMillis()));
log.info("parseDate                  --------------->{}", DateUtils.parseDate(DateUtils.currentTimeMillis()));