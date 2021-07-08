package org.clever.graaljs.fast.api.service;

import lombok.extern.slf4j.Slf4j;
import org.clever.graaljs.core.exception.BusinessException;
import org.clever.graaljs.core.utils.mapper.JacksonMapper;
import org.clever.graaljs.data.redis.builtin.wrap.RedisDatabase;
import org.clever.graaljs.data.redis.builtin.wrap.support.RedisConfig;
import org.clever.graaljs.data.redis.support.RedisDataSourceStatus;
import org.clever.graaljs.data.redis.support.RedisInfo;
import org.clever.graaljs.fast.api.autoconfigure.MultipleRedisAutoConfigure;
import org.clever.graaljs.fast.api.config.FastApiConfig;
import org.clever.graaljs.fast.api.dto.request.AddRedisReq;
import org.clever.graaljs.fast.api.dto.request.UpdateRedisReq;
import org.clever.graaljs.fast.api.dto.response.RedisInfoRes;
import org.clever.graaljs.fast.api.entity.DataSourceConfig;
import org.clever.graaljs.fast.api.entity.EnumConstant;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/07/08 10:07 <br/>
 */
@Service
@Slf4j
public class RedisManageService {
    private static final String ALL_DATA_SOURCE_CONFIG = "select * from data_source_config where namespace=? and type=?";
    private static final String EXISTS_SOURCE_CONFIG = "select count(1) from data_source_config where namespace=? and type=? and name=? limit 1";
    private static final String INSERT_INTO_SOURCE_CONFIG = "" +
            "insert into data_source_config " +
            "(namespace, type, name, config, disable) " +
            "values " +
            "(:namespace, :type, :name, :config, :disable)";
    private static final String DEL_SOURCE_CONFIG = "delete from data_source_config where namespace=? and type=? and name=?";
    private static final String UPDATE_SOURCE_CONFIG = "update data_source_config set config=? where namespace=? and type=? and name=?";
    private static final String GET_SOURCE_CONFIG = "select * from data_source_config where namespace=? and type=? and name=?";

    private final String namespace;
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public RedisManageService(FastApiConfig fastApiConfig) {
        this.namespace = fastApiConfig.getNamespace();
    }

    public List<RedisInfoRes> getAll() {
        Map<String, RedisInfoRes> resMap = new HashMap<>();
        List<DataSourceConfig> configList = jdbcTemplate.query(
                ALL_DATA_SOURCE_CONFIG, DataClassRowMapper.newInstance(DataSourceConfig.class),
                namespace, EnumConstant.TYPE_REDIS
        );
        configList.forEach(config -> {
            RedisInfoRes redisInfoRes = resMap.computeIfAbsent(config.getName(), name -> {
                RedisInfoRes res = new RedisInfoRes();
                res.setName(name);
                return res;
            });
            redisInfoRes.setDataSourceConfig(config);
        });
        Map<String, RedisInfo> useMap = RedisDatabase.Instance.allInfos();
        useMap.forEach((name, redisInfo) -> {
            RedisInfoRes redisInfoRes = resMap.computeIfAbsent(name, n -> {
                RedisInfoRes res = new RedisInfoRes();
                res.setName(n);
                return res;
            });
            redisInfoRes.setRedisInfo(redisInfo);
        });
        RedisInfoRes redisInfoRes = resMap.get(RedisDatabase.Instance.getDefaultName());
        if (redisInfoRes != null) {
            redisInfoRes.setDef(true);
        }
        resMap.forEach((name, res) -> {
            res.setImmutable(MultipleRedisAutoConfigure.isImmutable(res.getName()));
            res.setStatus(RedisDatabase.Instance.getStatus(name));
        });
        List<RedisInfoRes> resList = new ArrayList<>(resMap.values());
        resList.sort(Comparator.comparing(RedisInfoRes::getName));
        return resList;
    }

    @Transactional
    public RedisInfoRes addRedis(AddRedisReq req) {
        if (MultipleRedisAutoConfigure.isImmutable(req.getName())) {
            throw new BusinessException("数据源名称冲突");
        }
        Integer count = jdbcTemplate.queryForObject(
                EXISTS_SOURCE_CONFIG, Integer.class,
                namespace, EnumConstant.TYPE_REDIS, req.getName()
        );
        if (count != null && count > 0) {
            throw new BusinessException("数据源名称冲突");
        }
        RedisConfig redisConfig = req.getRedisConfig();
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setNamespace(namespace);
        dataSourceConfig.setType(EnumConstant.TYPE_REDIS);
        dataSourceConfig.setName(req.getName());
        dataSourceConfig.setDisable(EnumConstant.DISABLE_REQUEST_0);
        dataSourceConfig.setConfig(JacksonMapper.getInstance().toJson(redisConfig));
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(INSERT_INTO_SOURCE_CONFIG, new BeanPropertySqlParameterSource(dataSourceConfig), keyHolder);
        dataSourceConfig.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        MultipleRedisAutoConfigure.addRedisDataSource(req.getName(), redisConfig);
        RedisInfoRes res = new RedisInfoRes();
        res.setName(req.getName());
        res.setDef(false);
        res.setImmutable(false);
        res.setRedisInfo(RedisDatabase.Instance.getInfo(req.getName()));
        res.setStatus(RedisDatabase.Instance.getStatus(req.getName()));
        res.setDataSourceConfig(dataSourceConfig);
        return res;
    }

    @Transactional
    public RedisInfo delRedis(String name) {
        if (MultipleRedisAutoConfigure.isImmutable(name)) {
            throw new BusinessException("当前数据源不可删除");
        }
        int count = jdbcTemplate.update(DEL_SOURCE_CONFIG, namespace, EnumConstant.TYPE_REDIS, name);
        if (count <= 0) {
            throw new BusinessException("数据源不存在");
        }
        RedisInfo redisInfo = RedisDatabase.Instance.getInfo(name);
        MultipleRedisAutoConfigure.delRedisDataSource(name);
        return redisInfo;
    }

    @Transactional
    public RedisInfoRes updateRedis(UpdateRedisReq req) {
        if (MultipleRedisAutoConfigure.isImmutable(req.getName())) {
            throw new BusinessException("当前数据源不可修改");
        }
        List<DataSourceConfig> configList = jdbcTemplate.query(
                GET_SOURCE_CONFIG, DataClassRowMapper.newInstance(DataSourceConfig.class),
                namespace, EnumConstant.TYPE_REDIS, req.getName()
        );
        if (configList.isEmpty()) {
            throw new BusinessException("数据源不存在");
        }
        DataSourceConfig oldConfig = configList.get(0);
        // 更新数据库配置数据
        int count = jdbcTemplate.update(
                UPDATE_SOURCE_CONFIG,
                JacksonMapper.getInstance().toJson(req.getRedisConfig()),
                namespace, EnumConstant.TYPE_REDIS, req.getName()
        );
        if (count <= 0) {
            throw new BusinessException("数据源不存在");
        }
        final RedisInfo oldRedisInfo = RedisDatabase.Instance.getInfo(oldConfig.getName());
        final RedisDataSourceStatus oldStatus = RedisDatabase.Instance.getStatus(oldConfig.getName());
        // 先删除数据源，再加入数据源
        MultipleRedisAutoConfigure.delRedisDataSource(oldConfig.getName());
        try {
            MultipleRedisAutoConfigure.addRedisDataSource(req.getName(), req.getRedisConfig());
        } catch (Exception e) {
            // 加入失败，再加入之前的数据源
            MultipleRedisAutoConfigure.addRedisDataSource(
                    oldConfig.getName(),
                    JacksonMapper.getInstance().fromJson(oldConfig.getConfig(), RedisConfig.class)
            );
            log.error("更新数据源失败", e);
            throw new BusinessException("更新数据源失败", e);
        }
        // 返回数据
        RedisInfoRes res = new RedisInfoRes();
        res.setName(oldConfig.getName());
        res.setDef(Objects.equals(oldConfig.getName(), RedisDatabase.Instance.getDefaultName()));
        res.setImmutable(false);
        res.setRedisInfo(oldRedisInfo);
        res.setStatus(oldStatus);
        res.setDataSourceConfig(oldConfig);
        return res;
    }
}
