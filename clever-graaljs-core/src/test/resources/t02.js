var JavaInterop = Java.type("org.clever.graaljs.core.JavaInteropTest").Instance;

console.log("----------------------------------------------------------------------------------------")
console.log("# -> getByte:      | ", JavaInterop.getByte());
console.log("# -> getShort:     | ", JavaInterop.getShort());
console.log("# -> getInt:       | ", JavaInterop.getInt());
console.log("# -> getLong:      | ", JavaInterop.getLong());
console.log("# -> getFloat:     | ", JavaInterop.getFloat());
console.log("# -> getDouble:    | ", JavaInterop.getDouble());
console.log("# -> getBoolean:   | ", JavaInterop.getBoolean());
console.log("# -> getChar:      | ", JavaInterop.getChar());
console.log("# -> getString:    | ", JavaInterop.getString());
console.log("# -> getDate:      | ", JavaInterop.getDate());
console.log("# -> getArray:     | ", JavaInterop.getArray());
console.log("# -> getList:      | ", JavaInterop.getList());
console.log("# -> getSet:       | ", JavaInterop.getSet());
console.log("# -> getMap:       | ", JavaInterop.getMap());
console.log("----------------------------------------------------------------------------------------")
console.log("# -> String:       | ", JavaInterop.getString().repeat(3));
console.log("# -> Date:         | ", JavaInterop.getDate().getTimezoneOffset());
console.log("# -> Array:        | ", JavaInterop.getArray()[0], " | ", JavaInterop.getArray().length);
console.log("# -> List:         | ", JavaInterop.getList().contains("bbb"));
console.log("# -> Set:          | ", JavaInterop.getSet().contains("ccc"));
console.log("# -> Map:          | ", JavaInterop.getMap().keySet().contains("string"));
console.log("# -> Map:          | ", JavaInterop.getMap().string);
console.log("# -> getProxyMap:  | ", JavaInterop.getProxyMap().string);
console.log("# -> getProxyMap:  | ", JavaInterop.getProxyMap());
console.log("# -> getProxyMap:  | ", Interop.asJMap(JavaInterop.getProxyMap()));
// 结论：Java对象在JavaScript环境中仍然是Java对象，只能调用Java对象存在的方法

console.log("----------------------------------------------------------------------------------------")
console.log("----------------------------------------------------------------------------------------")

JavaInterop.setByte(5);
JavaInterop.setShort(128);
JavaInterop.setInt(123456);
JavaInterop.setLong(123456789);
JavaInterop.setFloat(123);                 // 调用成功
// JavaInterop.setFloat(123.456);          // 调用失败
JavaInterop.setDouble(123.456789);
JavaInterop.setBoolean(false);
JavaInterop.setChar('a');
JavaInterop.setString('aaa');
JavaInterop.setDate(new Date());
JavaInterop.setArray(["aaa", "bbb", "ccc"]);
JavaInterop.setList(["aaa", "bbb", "ccc"]);
JavaInterop.setSet(["aaa", "bbb", "ccc"]);
JavaInterop.setMap({
    str: "aaa", int: 123, b: false, d: 123.456, date: new Date(), fuc: function (a, b) {
        return a + b;
    }
});
JavaInterop.setMap(JavaInterop.getProxyMap());
console.log("----------------------------------------------------------------------------------------")
JavaInterop.setValue(new Date());
JavaInterop.setValue(["aaa", "bbb", "ccc"]);
JavaInterop.setValue(["aaa", "bbb", "ccc"]);
JavaInterop.setValue(["aaa", "bbb", "ccc"]);
JavaInterop.setValue({
    str: "aaa", int: 123, b: false, d: 123.456, date: new Date(), fuc: function (a, b) {
        return a + b;
    }
});
JavaInterop.setValue(function (a, b) {
    return a + b;
});
console.log("----------------------------------------------------------------------------------------")
var map = JavaInterop.getProxyMap2();

console.log("# -> getProxyMap2: | ", map);
console.log("# -> getProxyMap2: | ", map.string);
console.log("# -> getProxyMap2: | ", Interop.asJMap(map));

var list = JavaInterop.getProxyMap3();
console.log("# -> list: | ", list);
JavaInterop.setValue(list);
JavaInterop.setList2(list);

list = Interop.asJList(map, map, map, map);
console.log("# -> list: | ", list);
JavaInterop.setValue(list)
console.log("----------------------------------------------------------------------------------------");