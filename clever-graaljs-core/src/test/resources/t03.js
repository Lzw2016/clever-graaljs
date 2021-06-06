console.log("#console.log Test");
console.trace("#console.log Test");
console.debug("#console.log Test");
console.info("#console.log Test");
console.warn("#console.log Test");
console.error("#console.log Test");
console.log("----------------------------------------------------------------------------------------")
console.count();
console.count();
console.countReset();
console.count();
console.count();
console.log("----------------------------------------------------------------------------------------")
var label = "count_1";
console.count(label);
console.count(label);
console.countReset(label);
console.count(label);
console.count(label);
console.log("----------------------------------------------------------------------------------------")

console.time();
console.timeLog(null, "#time_1 Test");
console.timeLog(null, "#time_1 Test");
console.timeEnd();
console.log("----------------------------------------------------------------------------------------")
var label = "count_1";
console.time(label);
console.timeLog(label, "#time_1 Test");
console.timeLog(label, "#time_1 Test");
console.timeEnd(label);

console.log("----------------------------------------------------------------------------------------")
// 各种不同的JS数据类型的打印输出
var fuc = function () {
    return 1 + 6;
};
var array = [1, 2.2, true, "nashorn", null, new Date(), undefined, fuc];
var object = {
    "string": "aaa",
    "number1": 12,
    "number2": 12.345,
    "boolean": true,
    "null": null,
    "date": new Date(),
    "undefined": undefined,
    "function": fuc
};
console.log("打印JS变量 | undefined ", undefined, " | 行尾");
console.log("打印JS变量 | null ", null, " | 行尾");
console.log("打印JS变量 | int ", 1, " | 行尾");
console.log("打印JS变量 | float ", 2.2, " | 行尾");
console.log("打印JS变量 | boolean ", true, " | 行尾");
console.log("打印JS变量 | string ", "nashorn", " | 行尾");
console.log("打印JS变量 | date ", new Date(), " | 行尾");
console.log("打印JS变量 | array ", array, " | 行尾");
console.log("打印JS变量 | object ", object, " | 行尾");
console.log("打印JS变量 | function ", fuc, " | 行尾");
console.log("打印JS变量 | JSON.stringify ", JSON.stringify({date: new Date()}), " | 行尾");
console.log("----------------------------------------------------------------------------------------")
print("#print Test");
print("# -> ", 1, " | ", 2.2, " | ", true, " | ", undefined, " | ", null, " | ", new Date(), " |");
print("%d + %d = %s", 3, 5, 8);
console.log("----------------------------------------------------------------------------------------")
var log = LoggerFactory.getLogger("/t03.js");

var logger_1 = function () {
    log.trace("#console.log Test");
    log.debug("#console.log Test");
    log.info("#console.log Test");
    log.warn("#console.log Test");
    log.error("#console.log Test");
}

var logger_2 = function () {
    log.info("{} + {} = {}", 3, 5, 8);
}

var logger_3 = function () {
    var fuc = function () {
        return 1 + 6;
    };
    var array = [1, 2.2, true, "nashorn", null, new Date(), undefined, fuc];
    var object = {
        "string": "aaa",
        "number1": 12,
        "number2": 12.345,
        "boolean": true,
        "null": null,
        "date": new Date(),
        "undefined": undefined,
        "function": fuc
    };
    log.info("-----> 开始输出");
    log.info("打印JS变量 | undefined {} | 行尾", undefined);
    log.info("打印JS变量 | null {} | 行尾", null);
    log.info("打印JS变量 | int {} | 行尾", 1);
    log.info("打印JS变量 | float {} | 行尾", 2.2);
    log.info("打印JS变量 | boolean {} | 行尾", true);
    log.info("打印JS变量 | string {} | 行尾", "nashorn");
    log.info("打印JS变量 | date {} | 行尾", new Date());
    log.info("打印JS变量 | array {} | 行尾", array);
    log.info("打印JS变量 | object {} | 行尾", object);
    log.info("打印JS变量 | function {} | 行尾", fuc);
    log.info("打印JS变量 | JSON.stringify {} | 行尾", JSON.stringify({date: new Date()}));
}
logger_1()
console.log("----------------------------------------------------------------------------------------")
logger_2()
console.log("----------------------------------------------------------------------------------------")
logger_3()
console.log("----------------------------------------------------------------------------------------")


