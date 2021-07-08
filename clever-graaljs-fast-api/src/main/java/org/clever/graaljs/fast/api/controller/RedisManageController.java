package org.clever.graaljs.fast.api.controller;

import org.clever.graaljs.data.redis.builtin.wrap.RedisDatabase;
import org.clever.graaljs.data.redis.support.RedisDataSourceStatus;
import org.clever.graaljs.data.redis.support.RedisInfo;
import org.clever.graaljs.fast.api.dto.request.AddRedisReq;
import org.clever.graaljs.fast.api.dto.request.UpdateRedisReq;
import org.clever.graaljs.fast.api.dto.response.RedisInfoRes;
import org.clever.graaljs.fast.api.service.RedisManageService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/07/08 10:03 <br/>
 */
@RequestMapping("/fast_api/redis_manage")
@RestController
public class RedisManageController {
    @Resource
    private RedisManageService redisManageService;

    @GetMapping("/all")
    public List<RedisInfoRes> getAll() {
        return redisManageService.getAll();
    }

    @PostMapping("/add")
    public RedisInfoRes addRedis(@RequestBody @Validated AddRedisReq req) {
        return redisManageService.addRedis(req);
    }

    @DeleteMapping("/del")
    public RedisInfo delRedis(@RequestParam("name") String name) {
        return redisManageService.delRedis(name);
    }

    @PutMapping("/update")
    public RedisInfoRes updateRedis(@RequestBody @Validated UpdateRedisReq req) {
        return redisManageService.updateRedis(req);
    }

    @GetMapping("/status")
    public RedisDataSourceStatus getStatus(@RequestParam("name") String name) {
        return RedisDatabase.Instance.getStatus(name);
    }
}
