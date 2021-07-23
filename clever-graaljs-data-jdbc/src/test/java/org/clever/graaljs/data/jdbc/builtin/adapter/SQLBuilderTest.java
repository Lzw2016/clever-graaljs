package org.clever.graaljs.data.jdbc.builtin.adapter;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/07/23 16:33 <br/>
 */
@Slf4j
public class SQLBuilderTest {

    @Test
    public void t01() {
        SQLBuilder.SelectBuilder selectBuilder = SQLBuilder.newSelectBuilder()
                .setSelect("a, a1, a2,")
                .addColumn("b")
                .addColumn("c", true)
                .addColumn("d", false)
                .setTable("ta")
                .addTable("tb")
                .addTable("tc", true)
                .addTable("td", false)
                .leftJoin("te", "on (tc.id=te.id)")
                .setWhere("a=1 and a1=2")
                .addWhere("b=1")
                .addWhere("c=1", null, true)
                .addWhere("c=2", null, false)
                .setGroupBy("a, a1")
                .addGroupBy("a2", true)
                .addGroupBy("b", false)
                .setHaving("a=1 and a1=2")
                .setHaving("b=1")
                .setHaving("c=1", null, true)
                .setHaving("c=2", null, false)
                .setOrderBy("a")
                .addOrderBy("a1", "ASC", false)
                .addOrderByDesc("a2", true)
                .setPagination(2, 10);
        log.info("--> {}", selectBuilder.buildSql());
        log.info("--> {}", selectBuilder.buildCountSql());
    }

    @Test
    public void t02() {
        SQLBuilder.SelectBuilder selectBuilder = SQLBuilder.newSelectBuilder()
                .setSelect("a, a1, a2,")
                .addColumn("b")
                .addColumn("c", true)
                .addColumn("d", false)
                .setTable("ta")
                .leftJoin("tb", "on (ta.id=tb.id)")
                .setWhere("a=1 and a1=2")
                .addWhere("b=1")
                .addWhere("c=1", null, true)
                .addWhere("c=2", null, false)
                .setGroupBy("a, a1")
                .addGroupBy("a2", true)
                .addGroupBy("c", true)
                .addGroupBy("b", false)
                .setHaving("max(a)=1 and max(a1)=2")
                .addHaving("max(a2)=1")
                .addHaving("max(c)=1", null, true)
                .addHaving("max(c)=2", null, false)
                .setOrderBy("a")
                .addOrderBy("a1", "ASC", false)
                .addOrderByDesc("a2", true)
                .setPagination(2, 10);
        log.info("--> {}", selectBuilder.buildSql());
        log.info("--> {}", selectBuilder.buildCountSql());
    }

    @Test
    public void t03() {
        SQLBuilder.SelectBuilder selectBuilder = SQLBuilder.newSelectBuilder()
                .setSelect("a, a1, a2,")
                .addColumn("b")
                .addColumn("c", true)
                .addColumn("d", false)
                .setTable("ta")
                .leftJoin("tb", "on (ta.id=tb.id)")
                .setWhere("a=1 AND a1=2")
                .addWhere("b=1")
                .addWhere("c=1", null, true)
                .addWhere("c=2", null, false)
                .setOrderBy("a")
                .addOrderBy("a1", "ASC", false)
                .addOrderByDesc("a2", true)
                .setPagination(2, 10);
        log.info("--> {}", selectBuilder.buildSql());
        log.info("--> {}", selectBuilder.buildCountSql());
    }
}
