/* ====================================================================================================================
    scheduler -- 调度器
==================================================================================================================== */
create table scheduler
(
    id                  bigint          not null        auto_increment                          comment '主键id',
    namespace           varchar(63)     not null                                                comment '命名空间(同一个namespace的不同调度器属于同一个集群)',
    instance_name       varchar(127)    not null                                                comment '调度器实例名称',
    last_heartbeat_time datetime(3)     not null                                                comment '最后心跳时间',
    heartbeat_interval  bigint          not null        default 3000                            comment '心跳频率(单位：毫秒)',
    config              text            not null                                                comment '调度器配置，线程池大小、负载权重、最大并发任务数...',
    -- TODO runtime_info        text                                                                    comment '调度器运行时信息',
    description         varchar(511)                                                            comment '描述',
    create_at           datetime(3)     not null        default current_timestamp(3)            comment '创建时间',
    update_at           datetime(3)                     on update current_timestamp(3)          comment '更新时间',
    primary key (id)
) comment = '调度器';
create index idx_scheduler_instance_name on scheduler (instance_name(63));
/*------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------*/


/* ====================================================================================================================
    scheduler_lock -- 调度器集群锁
==================================================================================================================== */
create table scheduler_lock
(
    id                  bigint          not null        auto_increment                          comment '主键id',
    namespace           varchar(63)     not null                                                comment '命名空间',
    lock_name           varchar(63)     not null                                                comment '锁名称',
    description         varchar(511)                                                            comment '描述',
    create_at           datetime(3)     not null        default current_timestamp(3)            comment '创建时间',
    update_at           datetime(3)                     on update current_timestamp(3)          comment '更新时间',
    primary key (id)
) comment = '调度器集群锁';
create index idx_scheduler_lock_lock_name on scheduler_lock (lock_name);
/*------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------*/


/* ====================================================================================================================
    job -- 定时任务
==================================================================================================================== */
create table job
(
    id                      bigint          not null        auto_increment                          comment '主键id',
    namespace               varchar(63)     not null                                                comment '命名空间',
    name                    varchar(127)    not null                                                comment '任务名称',
    type                    tinyint         not null                                                comment '任务类型，1：http调用，2：java调用，3：js脚本，4：shell脚本',
    max_reentry             tinyint         not null        default 0                               comment '最大重入执行数量(对于单个节点当前任务未执行完成就触发了下一次执行导致任务重入执行)，小于等于0：表示禁止重入执行',
    allow_concurrent        tinyint         not null        default 1                               comment '是否允许多节点并发执行，使用悲观锁实现，不建议禁止，0：禁止，1：允许',
    lock_version            bigint          not null        default 0                               comment '悲观锁',
    max_retry_count         int             not null        default 0                               comment '执行失败时的最大重试次数',
    route_strategy          tinyint         not null        default 0                               comment '路由策略，0：不启用，1：指定节点优先，2：固定节点白名单，3：固定节点黑名单',
    first_scheduler         varchar(2047)                                                           comment '路由策略，1-指定节点优先，调度器名称集合',
    whitelist_scheduler     varchar(2047)                                                           comment '路由策略，2-固定节点白名单，调度器名称集合',
    blacklist_scheduler     varchar(2047)                                                           comment '路由策略，3-固定节点黑名单，调度器名称集合',
    load_balance            tinyint         not null        default 1                               comment '负载均衡策略，1：抢占，2：随机，3：轮询，4：一致性HASH',
    is_update_data          tinyint         not null        default 1                               comment '是否更新任务数据，0：不更新，1：更新',
    job_data                text                                                                    comment '任务数据(json格式)',
    disable                 tinyint         not null        default 0                               comment '是否禁用：0-启用，1-禁用',
    description             varchar(511)                                                            comment '描述',
    create_at               datetime(3)     not null        default current_timestamp(3)            comment '创建时间',
    update_at               datetime(3)                     on update current_timestamp(3)          comment '更新时间',
    primary key (id)
) comment = '定时任务';
create index idx_job_name on job (name(63));
create index idx_job_create_at on job (create_at);
create index idx_job_update_at on job (update_at);
/*------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------*/


/* ====================================================================================================================
    http_job -- Http任务
==================================================================================================================== */
create table http_job
(
    id                  bigint          not null        auto_increment                          comment '主键id',
    namespace           varchar(63)     not null                                                comment '命名空间',
    job_id              bigint          not null                                                comment '任务ID',
    request_method      varchar(15)     not null                                                comment 'http请求method，ALL GET HEAD POST PUT DELETE CONNECT OPTIONS TRACE PATCH',
    request_url         varchar(511)    not null                                                comment 'Http请求地址',
    request_data        mediumtext                                                              comment 'Http请求数据json格式，包含：params、headers、body',
    success_check       text                                                                    comment 'Http请求是否成功校验(js脚本)',
    create_at           datetime(3)     not null        default current_timestamp(3)            comment '创建时间',
    update_at           datetime(3)                     on update current_timestamp(3)          comment '更新时间',
    primary key (id)
) comment = 'Http任务';
create index idx_http_job_job_id on http_job (job_id);
create index idx_http_job_request_url on http_job (request_url(63));
create index idx_http_job_create_at on http_job (create_at);
create index idx_http_job_update_at on http_job (update_at);
/*------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------*/


/* ====================================================================================================================
    js_job -- js脚本任务
==================================================================================================================== */
create table js_job
(
    id                  bigint          not null        auto_increment                          comment '主键id',
    namespace           varchar(63)     not null                                                comment '命名空间',
    job_id              bigint          not null                                                comment '任务ID',
    file_resource_id    bigint          not null                                                comment 'js文件id',
    create_at           datetime(3)     not null        default current_timestamp(3)            comment '创建时间',
    update_at           datetime(3)                     on update current_timestamp(3)          comment '更新时间',
    primary key (id)
) comment = 'js脚本任务';
create index idx_js_job_job_id on js_job (job_id);
create index idx_js_job_file_resource_id on js_job (file_resource_id);
create index idx_js_job_create_at on js_job (create_at);
create index idx_js_job_update_at on js_job (update_at);
/*------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------*/


/* ====================================================================================================================
    java_job -- java调用任务
==================================================================================================================== */
create table java_job
(
    id                  bigint          not null        auto_increment                          comment '主键id',
    namespace           varchar(63)     not null                                                comment '命名空间',
    job_id              bigint          not null                                                comment '任务ID',
    is_static           tinyint         not null        default 1                               comment '是否是静态方法(函数)，0：非静态，1：静态',
    class_name          varchar(255)    not null                                                comment 'java class全路径',
    class_method        varchar(63)     not null                                                comment 'java class method',
    create_at           datetime(3)     not null        default current_timestamp(3)            comment '创建时间',
    update_at           datetime(3)                     on update current_timestamp(3)          comment '更新时间',
    primary key (id)
) comment = 'js脚本任务';
create index idx_js_job_job_id on java_job (job_id);
create index idx_js_job_class_name on java_job (class_name(63));
create index idx_js_job_class_method on java_job (class_method);
create index idx_js_job_create_at on java_job (create_at);
create index idx_js_job_update_at on java_job (update_at);
/*------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------*/


/* ====================================================================================================================
    shell_job -- shell脚本任务
==================================================================================================================== */
create table shell_job
(
    id                  bigint          not null        auto_increment                          comment '主键id',
    namespace           varchar(63)     not null                                                comment '命名空间',
    job_id              bigint          not null                                                comment '任务ID',
    shell_type          varchar(15)     not null                                                comment 'shell脚本类型：bash|sh|ash|powershell|cmd|python|node|deno|php',
    file_resource_id    bigint          not null                                                comment 'shell文件id',
    create_at           datetime(3)     not null        default current_timestamp(3)            comment '创建时间',
    update_at           datetime(3)                     on update current_timestamp(3)          comment '更新时间',
    primary key (id)
) comment = 'js脚本任务';
create index idx_shell_job_job_id on shell_job (job_id);
create index idx_shell_job_file_resource_id on shell_job (file_resource_id);
create index idx_shell_job_create_at on shell_job (create_at);
create index idx_shell_job_update_at on shell_job (update_at);
/*------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------*/


/* ====================================================================================================================
    trigger -- 任务触发器
==================================================================================================================== */
create table job_trigger
(
    id                  bigint          not null        auto_increment                          comment '主键id',
    namespace           varchar(63)     not null                                                comment '命名空间',
    job_id              bigint          not null                                                comment '任务ID',
    name                varchar(127)    not null                                                comment '触发器名称',
    start_time          datetime(3)     not null                                                comment '触发开始时间',
    end_time            datetime(3)                                                             comment '触发结束时间',
    last_fire_time      datetime(3)                                                             comment '上一次触发时间',
    next_fire_time      datetime(3)                                                             comment '下一次触发时间',
    misfire_strategy    tinyint         not null        default 2                               comment '错过触发策略，1：忽略，2：立即补偿触发一次',
    allow_concurrent    tinyint         not null        default 0                               comment '是否允许多节点并行触发，使用悲观锁实现，不建议允许，0：禁止，1：允许',
    lock_version        bigint          not null        default 0                               comment '悲观锁',
    type                tinyint         not null                                                comment '任务类型，1：cron触发，2：固定间隔触发',
    cron                varchar(511)                                                            comment 'cron表达式',
    fixed_interval      bigint                                                                  comment '固定间隔触发，间隔时间(单位：秒)',
    disable             tinyint         not null        default 0                               comment '是否禁用：0-启用，1-禁用',
    description         varchar(511)                                                            comment '描述',
    create_at           datetime(3)     not null        default current_timestamp(3)            comment '创建时间',
    update_at           datetime(3)                     on update current_timestamp(3)          comment '更新时间',
    primary key (id)
) comment = '任务触发器';
create index idx_job_trigger_job_id on job_trigger (job_id);
create index idx_job_trigger_name on job_trigger (name(63));
create index idx_job_trigger_last_fire_time on job_trigger (last_fire_time);
create index idx_job_trigger_next_fire_time on job_trigger (next_fire_time);
create index idx_job_trigger_create_at on job_trigger (create_at);
create index idx_job_trigger_update_at on job_trigger (update_at);
/*------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------*/


/* ====================================================================================================================
    scheduler_log -- 调度器事件日志
==================================================================================================================== */
create table scheduler_log
(
    id                  bigint          not null    auto_increment                              comment '编号',
    namespace           varchar(63)     not null                                                comment '命名空间',
    instance_name       varchar(127)    not null                                                comment '调度器实例名称',
    event_name          varchar(63)     not null                                                comment '事件名称',
    log_data            text                                                                    comment '事件日志数据',
    create_at           datetime(3)     not null        default current_timestamp(3)            comment '创建时间',
    primary key (id)
) comment = '调度器事件日志';
create index idx_scheduler_log_instance_name on scheduler_log (instance_name(63));
create index idx_scheduler_log_create_at on scheduler_log (create_at);
/*------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------*/


/* ====================================================================================================================
    job_trigger_log -- 任务触发器日志
==================================================================================================================== */
create table job_trigger_log
(
    id                  bigint          not null                                                comment '主键id',
    namespace           varchar(63)     not null                                                comment '命名空间',
    instance_name       varchar(127)    not null                                                comment '调度器实例名称',
    job_trigger_id      bigint                                                                  comment '任务触发器ID',
    job_id              bigint          not null                                                comment '任务ID',
    trigger_name        varchar(127)    not null                                                comment '触发器名称',
    fire_time           datetime(3)     not null                                                comment '触发时间',
    is_manual           tinyint         not null                                                comment '是否是手动触发，0：系统自动触发，1：用户手动触发',
    trigger_time        int             not null                                                comment '触发耗时(单位：毫秒)',
    last_fire_time      datetime(3)                                                             comment '上一次触发时间',
    next_fire_time      datetime(3)                                                             comment '下一次触发时间',
    fire_count          bigint          not null                                                comment '触发次数',
    mis_fired           tinyint         not null                                                comment '是否错过了触发，0：否，1：是',
    trigger_msg         varchar(511)                                                            comment '触发器消息',
    create_at           datetime(3)     not null        default current_timestamp(3)            comment '创建时间',
    primary key (id)
) comment = '任务触发器日志';
create index idx_job_trigger_log_instance_name on job_trigger_log (instance_name(31));
create index idx_job_trigger_log_job_id on job_trigger_log (job_id);
create index idx_job_trigger_log_trigger_name on job_trigger_log (trigger_name(31));
create index idx_job_trigger_log_fire_time on job_trigger_log (fire_time);
create index idx_job_trigger_log_last_fire_time on job_trigger_log (last_fire_time);
create index idx_job_trigger_log_next_fire_time on job_trigger_log (next_fire_time);
create index idx_job_trigger_log_create_at on job_trigger_log (create_at);
/*------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------*/


/* ====================================================================================================================
    job_log -- 任务执行日志
==================================================================================================================== */
create table job_log
(
    id                  bigint          not null        auto_increment                          comment '主键id',
    namespace           varchar(63)     not null                                                comment '命名空间',
    instance_name       varchar(127)    not null                                                comment '调度器实例名称',
    job_trigger_log_id  bigint          not null                                                comment '对应的触发器日志ID',
    job_trigger_id      bigint                                                                  comment '任务触发器ID',
    job_id              bigint          not null                                                comment '任务ID',
    fire_time           datetime(3)     not null                                                comment '触发时间',
    start_time          datetime(3)     not null                                                comment '开始执行时间',
    end_time            datetime(3)                                                             comment '执行结束时间',
    run_time            int                                                                     comment '执行耗时(单位：毫秒)',
    status              tinyint                                                                 comment '任务执行结果，0：成功，1：失败，2：取消',
    retry_count         int             not null                                                comment '重试次数',
    exception_info      varchar(2047)                                                           comment '异常信息',
    run_count           bigint          not null                                                comment '执行次数',
    before_job_data     text                                                                    comment '执行前的任务数据',
    after_job_data      text                                                                    comment '执行后的任务数据',
    primary key (id)
) comment = '任务执行日志';
create index idx_job_log_instance_name on job_log (instance_name(31));
create index idx_job_log_job_trigger_log_id on job_log (job_trigger_log_id);
create index idx_job_log_job_trigger_id on job_log (job_trigger_id);
create index idx_job_log_job_id on job_log (job_id);
create index idx_job_log_fire_time on job_log (fire_time);
create index idx_job_log_start_time on job_log (start_time);
create index idx_job_log_end_time on job_log (end_time);
/*------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------*/

