package org.clever.graaljs.fast.api.service;

import org.clever.graaljs.core.exception.BusinessException;
import org.clever.graaljs.core.utils.mapper.JacksonMapper;
import org.clever.graaljs.fast.api.config.FastApiConfig;
import org.clever.graaljs.fast.api.dto.request.AddHttpGlobalRequestDataReq;
import org.clever.graaljs.fast.api.dto.response.HttpGlobalRequestDataRes;
import org.clever.graaljs.fast.api.entity.HttpGlobalRequestData;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/07/10 21:27 <br/>
 */
@Service
public class HttpGlobalRequestDataManageService {
    private static final String QUERY_ALL = "select * from http_global_request_data where namespace=? order by title";
    private static final String INSERT = "" +
            "insert into http_global_request_data " +
            "(namespace, title, request_data) " +
            "values " +
            "(:namespace, :title, :requestData) ";
    private static final String UPDATE = "update http_global_request_data set title=:title, request_data=:requestData where namespace=:namespace and id=:id";
    private static final String DELETE = "delete from http_global_request_data where namespace=? and id=?";

    private final String namespace;
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public HttpGlobalRequestDataManageService(FastApiConfig fastApiConfig) {
        this.namespace = fastApiConfig.getNamespace();
    }

    public List<HttpGlobalRequestDataRes> getAll() {
        List<HttpGlobalRequestData> dataList = jdbcTemplate.query(QUERY_ALL, DataClassRowMapper.newInstance(HttpGlobalRequestData.class), namespace);
        List<HttpGlobalRequestDataRes> list = new ArrayList<>(dataList.size());
        dataList.forEach(data -> {
            HttpGlobalRequestDataRes res = JacksonMapper.getInstance().fromJson(data.getRequestData(), HttpGlobalRequestDataRes.class);
            res.setId(data.getId());
            res.setTitle(data.getTitle());
            list.add(res);
        });
        return list;
    }

    @Transactional
    public HttpGlobalRequestDataRes saveOrUpdate(AddHttpGlobalRequestDataReq req) {
        HttpGlobalRequestData data = new HttpGlobalRequestData();
        data.setId(req.getId());
        data.setNamespace(namespace);
        data.setTitle(req.getTitle());
        req.setId(null);
        req.setTitle(null);
        data.setRequestData(JacksonMapper.getInstance().toJson(req));
        MapSqlParameterSource parameter = new MapSqlParameterSource();
        parameter.addValue("title", data.getTitle());
        parameter.addValue("requestData", data.getRequestData());
        parameter.addValue("namespace", namespace);
        if (data.getId() == null) {
            // 新增
            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
            namedParameterJdbcTemplate.update(INSERT, parameter, keyHolder);
            data.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        } else {
            // 更新
            parameter.addValue("id", data.getId());
            int count = namedParameterJdbcTemplate.update(UPDATE, parameter);
            if (count <= 0) {
                throw new BusinessException("数据不存在");
            }
        }
        HttpGlobalRequestDataRes res = new HttpGlobalRequestDataRes();
        res.setId(data.getId());
        res.setTitle(data.getTitle());
        res.setParams(req.getParams());
        res.setHeaders(req.getHeaders());
        res.setCookies(req.getCookies());
        return res;
    }

    @Transactional
    public int delete(Long id) {
        return jdbcTemplate.update(DELETE, namespace, id);
    }
}
