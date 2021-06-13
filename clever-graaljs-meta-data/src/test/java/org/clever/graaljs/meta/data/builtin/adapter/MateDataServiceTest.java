package org.clever.graaljs.meta.data.builtin.adapter;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.clever.graaljs.meta.data.BaseTest;
import org.clever.graaljs.meta.data.model.DataBaseSummary;
import org.clever.graaljs.meta.data.model.TableSchema;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/06/13 12:27 <br/>
 */
@Slf4j
public class MateDataServiceTest {
    private static HikariDataSource dataSource;
    private static MateDataService mateDataService;

    @BeforeAll
    public static void init() {
        dataSource = BaseTest.newHikariDataSource();
        mateDataService = new MateDataService(dataSource);
        mateDataService.reload();
    }

    @AfterAll
    public static void close() {
        dataSource.close();
    }

    @Test
    public void t01() {
        List<DataBaseSummary> dataBaseSummaryList = mateDataService.getDataBaseSummaryList();
        log.info("list -> {}", dataBaseSummaryList);
    }

    @Test
    public void t02() {
        DataBaseSummary dataBaseSummary = mateDataService.getDataBaseSummary("test-data");
        log.info("###dataBaseSummary -> {}", dataBaseSummary);
        TableSchema tableSchema = mateDataService.getTableSchema("test-data", "tb_merchandise");
        log.info("###tableSchema -> {}", tableSchema);
    }
}
