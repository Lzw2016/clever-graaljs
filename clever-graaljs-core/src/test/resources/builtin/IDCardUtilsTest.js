const log = LoggerFactory.getLogger("logger");

const res_1 = IDCardUtils.validateCard("11010119900307475X");
const res_2 = IDCardUtils.validateCard("11010119900307476X");
log.info("res_1 -> {}", res_1);
log.info("res_2 -> {}", res_2);
