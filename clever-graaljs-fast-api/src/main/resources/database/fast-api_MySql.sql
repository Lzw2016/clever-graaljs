create database if not exists `fast-api` default character set = utf8 collate = utf8_general_ci;
use `fast-api`;


/* ====================================================================================================================
    data_source_config -- 数据源配置
==================================================================================================================== */
create table data_source_config
(
    id                  bigint          not null        auto_increment                          comment '主键id',
    namespace           varchar(63)     not null                                                comment '命名空间',
    type                varchar(31)     not null                                                comment '数据源类型，jdbc redis rabbitmq rocketmq kafka elasticsearch ...',
    name                varchar(63)     not null                                                comment '数据源名称',
    config              text            not null                                                comment '数据源配置',
    disable             tinyint         not null        default 0                               comment '禁用：0-启用，1-禁用',
    create_at           datetime(3)     not null        default current_timestamp(3)            comment '创建时间',
    update_at           datetime(3)                     on update current_timestamp(3)          comment '更新时间',
    primary key (id)
) comment = '数据源配置';
create index idx_data_source_config_namespace on data_source_config (namespace);
create index idx_data_source_config_name on data_source_config (name);
create index idx_data_source_config_create_at on data_source_config (create_at);
create index idx_data_source_config_update_at on data_source_config (update_at);
/*------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------*/


/* ====================================================================================================================
    file_resource -- 资源文件
==================================================================================================================== */
create table file_resource
(
    id                  bigint          not null        auto_increment                          comment '主键id',
    namespace           varchar(63)     not null                                                comment '命名空间',
    module              tinyint         not null                                                comment '所属模块：0-自定义扩展，1-初始化脚本，2-HTTP API，3-定时任务',
    path                varchar(511)    not null        collate utf8_bin                        comment '文件路径(以"/"结束)',
    name                varchar(127)    not null        collate utf8_bin                        comment '文件名称',
    content             text                                                                    comment '文件内容',
    is_file             tinyint         not null        default 1                               comment '数据类型：0-文件夹，1-文件',
    `read_only`         tinyint         not null        default 0                               comment '读写权限：0-可读可写，1-只读',
    description         varchar(511)                                                            comment '说明',
    create_at           datetime(3)     not null        default current_timestamp(3)            comment '创建时间',
    update_at           datetime(3)                     on update current_timestamp(3)          comment '更新时间',
    primary key (id)
) comment = '资源文件';
create index idx_file_resource_namespace on file_resource (namespace);
create index idx_file_resource_path on file_resource (path(127));
create index idx_file_resource_name on file_resource (name(63));
create index idx_file_resource_create_at on file_resource (create_at);
create index idx_file_resource_update_at on file_resource (update_at);
/*------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------*/


/* ====================================================================================================================
    file_resource_history -- 资源文件修改历史
==================================================================================================================== */
create table file_resource_history
(
    id                  bigint          not null        auto_increment                          comment '主键id',
    namespace           varchar(63)     not null                                                comment '命名空间',
    module              tinyint         not null                                                comment '所属模块：0-自定义扩展，1-初始化脚本，2-HTTP API，3-定时任务',
    path                varchar(511)    not null        collate utf8_bin                        comment '文件路径(以"/"结束)',
    name                varchar(127)    not null        collate utf8_bin                        comment '文件名称',
    content             text                                                                    comment '文件内容',
    description         varchar(511)                                                            comment '说明',
    create_at           datetime(3)     not null        default current_timestamp(3)            comment '创建时间',
    update_at           datetime(3)                     on update current_timestamp(3)          comment '更新时间',
    primary key (id)
) comment = '资源文件修改历史';
create index idx_file_resource_history_namespace on file_resource_history (namespace);
create index idx_file_resource_history_path on file_resource_history (path(127));
create index idx_file_resource_history_name on file_resource_history (name(63));
create index idx_file_resource_history_create_at on file_resource_history (create_at);
create index idx_file_resource_history_update_at on file_resource_history (update_at);
/*------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------*/


/* ====================================================================================================================
    http_api -- HTTP接口
==================================================================================================================== */
create table http_api
(
    id                  bigint          not null        auto_increment                          comment '主键id',
    namespace           varchar(63)     not null                                                comment '命名空间',
    file_resource_id    bigint          not null                                                comment '资源文件id',
    request_mapping     varchar(511)    not null        collate utf8_bin                        comment 'http请求路径',
    request_method      varchar(15)     not null                                                comment 'http请求method，ALL GET HEAD POST PUT DELETE CONNECT OPTIONS TRACE PATCH',
    disable_request     tinyint         not null        default 0                               comment '禁用http请求：0-启用，1-禁用',
    create_at           datetime(3)     not null        default current_timestamp(3)            comment '创建时间',
    update_at           datetime(3)                     on update current_timestamp(3)          comment '更新时间',
    primary key (id)
) comment = 'HTTP接口';
create index idx_http_api_namespace on http_api (namespace);
create index idx_http_api_file_resource_id on http_api (file_resource_id);
create index idx_http_api_request_mapping on http_api (request_mapping(127));
create index idx_http_api_create_at on http_api (create_at);
create index idx_http_api_update_at on http_api (update_at);
/*------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------*/


/* ====================================================================================================================
    TODO access_log -- 接口访问日志
==================================================================================================================== */
create table access_log
(
    id                  bigint          not null        auto_increment                          comment '主键id',
    namespace           varchar(63)     not null                                                comment '命名空间',
    file_resource_id    bigint          not null                                                comment '资源文件id',
    request_mapping     varchar(511)    not null        collate utf8_bin                        comment 'http请求路径',
    request_method      varchar(15)     not null                                                comment 'http请求method，ALL GET HEAD POST PUT DELETE CONNECT OPTIONS TRACE PATCH',
    run_start           datetime(3)     not null                                                comment '运行开始时间',
    run_end             datetime(3)                                                             comment '运行结束时间',
    return_data         varchar(1023)                                                           comment '运行返回数据',
    status              tinyint         not null        default 1                               comment '脚本运行状态：1-运行中，2-成功，3-异常，4-超时，...',
    primary key (id)
) comment = '接口访问日志';
create index idx_access_log_namespace on access_log (namespace);
create index idx_access_log_file_resource_id on access_log (file_resource_id);
create index idx_access_log_request_mapping on access_log (request_mapping(127));
create index idx_access_log_run_start on access_log (run_start);
create index idx_access_log_run_end on access_log (run_end);
/*------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------*/

