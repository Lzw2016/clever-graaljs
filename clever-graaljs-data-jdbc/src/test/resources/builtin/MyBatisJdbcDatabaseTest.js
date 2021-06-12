const log = LoggerFactory.getLogger("logger");
const mybatis = MyBatisJdbcDatabase.getDefault();

const res = mybatis.queryList("sql01.t01", {purchaseMobile: "13006155525"})
log.info("res -> {}", [res]);











