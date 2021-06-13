const log = LoggerFactory.getLogger("logger");

const url1 = "https://3xsw4ap8wah59.cfc-execute.bj.baidubce.com/api/amis-mock/sample";
const res1 = HttpUtils.getStr(url1, {keyWord: "轮椅"});
log.info("res1  -> {}", res1);

const res2 = HttpUtils.getMap(url1, {keyWord: "体温计"});
log.info("res2  -> {}", res2.data.rows[0]);

const url2 = "https://3xsw4ap8wah59.cfc-execute.bj.baidubce.com/api/amis-mock/sample";
const res3 = HttpUtils.execRequest("GET", url2, null, {keyWord: "体温计"}, {});
log.info("getStatus         -> {}", res3.getStatus());
log.info("getStatusMessage  -> {}", res3.getStatusMessage());
log.info("getBody           -> {}", res3.getBody());
log.info("getHeaders        -> {}", res3.getHeaders());
log.info("isRedirect        -> {}", res3.isRedirect());
log.info("isSuccessful      -> {}", res3.isSuccessful());
log.info("getHeaderNames    -> {}", res3.getHeaderNames());
log.info("getBodyMap        -> {}", res3.getBodyMap());
log.info("getFirstHeader    -> {}", res3.getFirstHeader("Content-Type"));
