package org.clever.graaljs.fast.api.demo.controller

import org.clever.graaljs.data.jdbc.builtin.wrap.JdbcDatabase
import org.clever.graaljs.spring.mvc.builtin.adapter.HttpContext

class Test01 {
    static Object t01() {
        def map = [a: 111, b: "abc", ok: true]
        map.a = 333
        return map
    }

    static Object t02(HttpContext ctx) {
        def request = ctx.request
        // 定义请求参数类型
        def params = [
                branchId: "FDG",
                limit   : 10
        ]
        // 填充参数
        request.fillFromAny(params, false)
        if (params.limit && params.limit > 100) {
            params.limit = 100;
        }
        // Thread.sleep(3_000)
        // 查询数据
        def ds = JdbcDatabase.Instance.getDefault()
        def sql = """
            select * from tb_merchandise where BRANCHID=:branchId limit :limit
        """
        def data = ds.queryList(sql, params);
        // def data = ds.queryByPage(sql, request.getQueryByPage(), [branchId: "FDG"], true)
        return data
    }
}
