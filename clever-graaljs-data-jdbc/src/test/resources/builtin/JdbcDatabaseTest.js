const log = LoggerFactory.getLogger("logger");
const jdbc = JdbcDatabase.getDefault();

const sql = "select baseprice from  tb_gos_sale_saleorderdet where baseprice>0 limit 1";
log.info("String        --> {}", jdbc.queryString(sql));
log.info("Double        --> {}", jdbc.queryDouble(sql));
log.info("BigDecimal    --> {}", jdbc.queryBigDecimal(sql));

const sql2 = "select issettlement from  tb_gos_sale_saleorderdet where issettlement is not null limit 1";
log.info("Boolean   --> {}", jdbc.queryBoolean(sql2));

const sql3 = "select createtime from  tb_gos_sale_saleorderdet where issettlement is not null limit 1";
log.info("Date   --> {}", jdbc.queryDate(sql3));

const sql4 = "select * from  tb_merchandise_ext where update_at>:startDate";
log.info("Count  --> {}", jdbc.queryCount(sql4, {startDate: DateUtils.parseDate("2021-01-27 15:38:31")}));

const sql5 = "select prod_no,branch_id,merchandise_type,merchandise_state from tb_merchandise_ext limit 3";
log.info("queryList --> {}", [jdbc.queryList(sql5)]);

const sql6 = "select prod_no,branch_id,merchandise_type,merchandise_state from tb_merchandise_ext where prod_no=:prodNo";
log.info("queryList --> {}", [jdbc.queryList(sql6, {prodNo: "A000212131002"})]);
log.info("queryMap  --> {}", jdbc.queryMap(sql6, {prodNo: "A000212131002"}));

const sql7 = "select prod_no,branch_id,merchandise_type,merchandise_state from tb_merchandise_ext where prod_no='A000212131002'";
log.info("queryMap  --> {}", jdbc.queryMap(sql7));

const res1 = jdbc.queryTableMap("tb_merchandise_ext", {prod_no: 'A000212131002'}, false);
const res2 = jdbc.queryTableMap("tb_merchandise_ext", {prodNo: 'A000212131002'}, true);
log.info("res1  -> {}", res1);
log.info("res2  -> {}", res2);

const res3 = jdbc.queryTableList("tb_merchandise_ext", {purchaseMobile: '13006155525'}, true);
log.info("res3  -> {}", res3.size());

const sql8 = "select prod_no,branch_id,merchandise_type,merchandise_state from  tb_merchandise_ext where update_at>:startDate"
log.info("queryByPage   -> {}", jdbc.queryByPage(sql8, {pageNo: 5, pageSize: 3}, {startDate: DateUtils.parseDate("2021-01-27 15:38:31")}));








