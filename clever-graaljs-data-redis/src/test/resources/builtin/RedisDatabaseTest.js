const log = LoggerFactory.getLogger("logger");
const redis = RedisDatabase.getDefault();

redis.vSet("string_key_0", "123abc", 1000 * 60);
redis.vSet(
    "string_key_2",
    {a: 1, b: 2.002, c: true, d: "abcde", e: Interop.asJBigDecimal("123.456"), f: CommonUtils.now()},
    1000 * 60
);
const a = redis.vGet("string_key_0");
const b = redis.vGet("string_key_2");
const c = {a: b.a, b: b.b, c: b.c, d: b.d, e: b.e, f: b.f};
log.info("ClassName -> {}", CommonUtils.getClassName(b));
const iterator = b.entrySet().iterator();
while (iterator.hasNext()) {
    const entry = iterator.next();
    log.info(
        "key -> {} | value -> {} | value ClassName -> {}",
        entry.getKey(),
        entry.getValue(),
        CommonUtils.getClassName(entry.getValue())
    );
}

log.info("#-> {}", {a, b, c})


