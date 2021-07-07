package org.clever.graaljs.fast.api.service;

import org.clever.graaljs.core.exception.BusinessException;
import org.clever.graaljs.core.utils.mapper.JacksonMapper;
import org.clever.graaljs.data.jdbc.builtin.wrap.JdbcDatabase;
import org.clever.graaljs.data.jdbc.builtin.wrap.support.JdbcConfig;
import org.clever.graaljs.data.jdbc.mybatis.MyBatisMapperSql;
import org.clever.graaljs.data.jdbc.support.JdbcInfo;
import org.clever.graaljs.fast.api.autoconfigure.MultipleJdbcAutoConfigure;
import org.clever.graaljs.fast.api.config.FastApiConfig;
import org.clever.graaljs.fast.api.dto.request.AddJdbcReq;
import org.clever.graaljs.fast.api.dto.response.JdbcInfoRes;
import org.clever.graaljs.fast.api.entity.DataSourceConfig;
import org.clever.graaljs.fast.api.entity.EnumConstant;
import org.springframework.beans.factory.annotation.Autowired;
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
 * 创建时间：2021/07/07 16:00 <br/>
 */
@Service
public class JdbcDatabaseManageService {
    private static final String ALL_DATA_SOURCE_CONFIG = "select * from data_source_config where namespace=? and type=?";
    private static final String EXISTS_SOURCE_CONFIG = "select count(1) from data_source_config where namespace=? and type=? and name=? limit 1";
    private static final String INSERT_INTO_SOURCE_CONFIG = "" +
            "insert into data_source_config " +
            "(namespace, type, name, config, disable) " +
            "values " +
            "(:namespace, :type, :name, :config, :disable)";
    private static final String DEL_SOURCE_CONFIG = "delete from data_source_config where namespace=? and type=? and name=?";

    private final String namespace;
    private final MyBatisMapperSql myBatisMapperSql;
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcDatabaseManageService(FastApiConfig fastApiConfig, @Autowired(required = false) MyBatisMapperSql myBatisMapperSql) {
        this.namespace = fastApiConfig.getNamespace();
        this.myBatisMapperSql = myBatisMapperSql;
    }

    public List<JdbcInfoRes> getAll() {
        Map<String, JdbcInfoRes> resMap = new HashMap<>();
        List<DataSourceConfig> configList = jdbcTemplate.query(
                ALL_DATA_SOURCE_CONFIG, DataClassRowMapper.newInstance(DataSourceConfig.class),
                namespace, EnumConstant.TYPE_JDBC
        );
        configList.forEach(config -> {
            JdbcInfoRes jdbcInfoRes = resMap.computeIfAbsent(config.getName(), name -> {
                JdbcInfoRes res = new JdbcInfoRes();
                res.setName(name);
                return res;
            });
            jdbcInfoRes.setDataSourceConfig(config);
        });
        Map<String, JdbcInfo> useMap = JdbcDatabase.Instance.allInfos();
        useMap.forEach((name, jdbcInfo) -> {
            JdbcInfoRes jdbcInfoRes = resMap.computeIfAbsent(name, n -> {
                JdbcInfoRes res = new JdbcInfoRes();
                res.setName(n);
                return res;
            });
            jdbcInfoRes.setJdbcInfo(jdbcInfo);
        });
        JdbcInfoRes jdbcInfoRes = resMap.get(JdbcDatabase.Instance.getDefaultName());
        if (jdbcInfoRes != null) {
            jdbcInfoRes.setDef(true);
        }
        resMap.values().forEach(res -> res.setImmutable(MultipleJdbcAutoConfigure.isImmutable(res.getName())));
        List<JdbcInfoRes> resList = new ArrayList<>(resMap.values());
        resList.sort(Comparator.comparing(JdbcInfoRes::getName));
        return resList;
    }

    @Transactional
    public JdbcInfoRes addJdbc(AddJdbcReq req) {
        if (MultipleJdbcAutoConfigure.isImmutable(req.getName())) {
            throw new BusinessException("数据源名称冲突");
        }
        Integer count = jdbcTemplate.queryForObject(
                EXISTS_SOURCE_CONFIG, Integer.class,
                namespace, EnumConstant.TYPE_JDBC, req.getName()
        );
        if (count != null && count > 0) {
            throw new BusinessException("数据源名称冲突");
        }
        JdbcConfig jdbcConfig = req.getJdbcConfig();
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setNamespace(namespace);
        dataSourceConfig.setType(EnumConstant.TYPE_JDBC);
        dataSourceConfig.setName(req.getName());
        dataSourceConfig.setConfig(JacksonMapper.getInstance().toJson(jdbcConfig));
        dataSourceConfig.setDisable(EnumConstant.DISABLE_REQUEST_0);
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(INSERT_INTO_SOURCE_CONFIG, new BeanPropertySqlParameterSource(dataSourceConfig), keyHolder);
        dataSourceConfig.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        MultipleJdbcAutoConfigure.addJdbcDataSource(req.getName(), jdbcConfig, myBatisMapperSql);
        JdbcInfoRes res = new JdbcInfoRes();
        res.setName(req.getName());
        res.setDef(false);
        res.setImmutable(false);
        res.setJdbcInfo(JdbcDatabase.Instance.getInfo(req.getName()));
        res.setDataSourceConfig(dataSourceConfig);
        return res;
    }

    @Transactional
    public JdbcInfo delJdbc(String name) {
        if (MultipleJdbcAutoConfigure.isImmutable(name)) {
            throw new BusinessException("当前数据源不可删除");
        }
        int count = jdbcTemplate.update(DEL_SOURCE_CONFIG, namespace, EnumConstant.TYPE_JDBC, name);
        if (count <= 0) {
            throw new BusinessException("数据源不存在");
        }
        JdbcInfo jdbcInfo = JdbcDatabase.Instance.getInfo(name);
        MultipleJdbcAutoConfigure.delJdbcDataSource(name);
        return jdbcInfo;
    }
}
