package org.clever.graaljs.fast.api.controller;

import org.clever.graaljs.data.jdbc.support.JdbcInfo;
import org.clever.graaljs.fast.api.dto.request.AddJdbcReq;
import org.clever.graaljs.fast.api.dto.response.JdbcInfoRes;
import org.clever.graaljs.fast.api.service.JdbcDatabaseManageService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/07/07 15:11 <br/>
 */
@RequestMapping("/fast_api/jdbc_database_manage")
@RestController
public class JdbcDatabaseManageController {
    @Resource
    private JdbcDatabaseManageService jdbcDatabaseManageService;

    @GetMapping("/all")
    public List<JdbcInfoRes> getAll() {
        return jdbcDatabaseManageService.getAll();
    }

    @PostMapping("/add")
    public JdbcInfoRes addJdbc(@RequestBody @Validated AddJdbcReq req) {
        return jdbcDatabaseManageService.addJdbc(req);
    }

    @DeleteMapping("/del")
    public JdbcInfo delJdbc(@RequestParam("name") String name) {
        return jdbcDatabaseManageService.delJdbc(name);
    }
}
