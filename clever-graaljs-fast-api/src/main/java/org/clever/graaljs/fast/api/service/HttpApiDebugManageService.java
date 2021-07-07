package org.clever.graaljs.fast.api.service;

import org.apache.commons.lang3.StringUtils;
import org.clever.graaljs.core.exception.BusinessException;
import org.clever.graaljs.core.utils.mapper.JacksonMapper;
import org.clever.graaljs.fast.api.config.FastApiConfig;
import org.clever.graaljs.fast.api.dto.request.AddHttpApiDebugReq;
import org.clever.graaljs.fast.api.dto.request.UpdateHttpApiDebugReq;
import org.clever.graaljs.fast.api.dto.response.HttpApiDebugRes;
import org.clever.graaljs.fast.api.dto.response.HttpApiDebugTitleRes;
import org.clever.graaljs.fast.api.entity.HttpApi;
import org.clever.graaljs.fast.api.entity.HttpApiDebug;
import org.clever.graaljs.fast.api.model.DebugRequestData;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/07/05 10:29 <br/>
 */
@Service
public class HttpApiDebugManageService {
    private static final String QUERY_TITLE_LIST = "select id, title from http_api_debug where namespace=? and http_api_id=? order by title";
    private static final String GET_HTTP_API_DEBUG = "select * from http_api_debug where namespace=? and id=? limit 1";
    private static final String INSERT_HTTP_API_DEBUG = "" +
            "insert into http_api_debug " +
            "(namespace, http_api_id, title, request_data) " +
            "values " +
            "(:namespace, :httpApiId, :title, :requestData)";
    private static final String UPDATE_HTTP_API_DEBUG = "update http_api_debug set title=?, request_data=? where namespace=? and id=?";
    private static final String DEL_HTTP_API_DEBUG = "delete from http_api_debug where namespace=? and id=?";

    private final String namespace;
    private final String prefix;
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Resource
    private HttpApiManageService httpApiManageService;

    public HttpApiDebugManageService(FastApiConfig fastApiConfig) {
        this.namespace = fastApiConfig.getNamespace();
        this.prefix = fastApiConfig.getMvc().getPrefix();
    }

    public List<HttpApiDebugTitleRes> getTitleList(Long httpApiId) {
        return jdbcTemplate.query(
                QUERY_TITLE_LIST, DataClassRowMapper.newInstance(HttpApiDebugTitleRes.class),
                namespace, httpApiId
        );
    }

    public HttpApiDebugRes getHttpApiDebug(Long id) {
        List<HttpApiDebug> list = jdbcTemplate.query(
                GET_HTTP_API_DEBUG, DataClassRowMapper.newInstance(HttpApiDebug.class),
                namespace, id
        );
        if (list.isEmpty()) {
            return null;
        }
        HttpApiDebug httpApiDebug = list.get(0);
        HttpApiDebugRes res = new HttpApiDebugRes();
        res.setId(httpApiDebug.getId());
        res.setNamespace(httpApiDebug.getNamespace());
        res.setHttpApiId(httpApiDebug.getHttpApiId());
        res.setTitle(httpApiDebug.getTitle());
        res.setRequestData(JacksonMapper.getInstance().fromJson(httpApiDebug.getRequestData(), DebugRequestData.class));
        res.setCreateAt(httpApiDebug.getCreateAt());
        res.setUpdateAt(httpApiDebug.getUpdateAt());
        return res;
    }

    @Transactional
    public HttpApiDebug addHttpApiDebug(AddHttpApiDebugReq req) {
        HttpApi httpApi = httpApiManageService.getHttpApi(req.getHttpApiId());
        if (httpApi == null) {
            throw new BusinessException("HttpApi不存在");
        }
        HttpApiDebug httpApiDebug = new HttpApiDebug();
        httpApiDebug.setNamespace(namespace);
        httpApiDebug.setHttpApiId(req.getHttpApiId());
        httpApiDebug.setTitle(req.getTitle());
        if (req.getRequestData() == null) {
            DebugRequestData requestData = new DebugRequestData();
            requestData.setMethod(httpApi.getRequestMethod());
            if (StringUtils.isBlank(prefix)) {
                requestData.setPath(httpApi.getRequestMapping());
            } else {
                requestData.setPath(prefix + httpApi.getRequestMapping());
            }
            req.setRequestData(requestData);
        }
        httpApiDebug.setRequestData(JacksonMapper.getInstance().toJson(req.getRequestData()));
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(INSERT_HTTP_API_DEBUG, new BeanPropertySqlParameterSource(httpApiDebug), keyHolder);
        httpApiDebug.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return httpApiDebug;
    }

    @Transactional
    public HttpApiDebug updateHttpApiDebug(UpdateHttpApiDebugReq req) {
        int count = jdbcTemplate.update(
                UPDATE_HTTP_API_DEBUG,
                req.getTitle(),
                JacksonMapper.getInstance().toJson(req.getRequestData()),
                namespace, req.getId()
        );
        if (count <= 0) {
            throw new BusinessException("数据不存在");
        }
        List<HttpApiDebug> list = jdbcTemplate.query(
                GET_HTTP_API_DEBUG, DataClassRowMapper.newInstance(HttpApiDebug.class),
                namespace, req.getId()
        );
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Transactional
    public HttpApiDebug delHttpApiDebug(Long id) {
        List<HttpApiDebug> list = jdbcTemplate.query(
                GET_HTTP_API_DEBUG, DataClassRowMapper.newInstance(HttpApiDebug.class),
                namespace, id
        );
        if (list.isEmpty()) {
            throw new BusinessException("删除的数据不存在");
        }
        int count = jdbcTemplate.update(DEL_HTTP_API_DEBUG, namespace, id);
        if (count <= 0) {
            throw new BusinessException("数据不存在");
        }
        return list.get(0);
    }
}
