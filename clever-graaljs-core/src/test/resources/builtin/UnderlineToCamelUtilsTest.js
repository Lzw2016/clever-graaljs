const log = LoggerFactory.getLogger("logger");

const data = {
    "a_aaa": "aaa",
    "a_bbb": "bbb",
    "a_ccc": "ccc",
    "a_ddd": "ddd",
    "a_eee": "eee",
    "a_fff": "fff",
};
const dataList = Interop.asJList(
    {id: "#1", "a_aaa": "aaa", "a_eee": "eee"},
    {id: "#2", "a_aaa": "aaa", "a_eee": "eee"},
    {id: "#3", "a_aaa": "aaa", "a_eee": "eee"},
);

log.info("underlineToCamel -> {}", UnderlineToCamelUtils.underlineToCamel(data));
log.info("underlineToCamel -> {}", UnderlineToCamelUtils.underlineToCamel(data, {a_eee: "自定义"}));

log.info("underlineToCamel -> {}", [UnderlineToCamelUtils.underlineToCamel(dataList)]);
log.info("underlineToCamel -> {}", [UnderlineToCamelUtils.underlineToCamel(dataList, {a_eee: "自定义"})]);
