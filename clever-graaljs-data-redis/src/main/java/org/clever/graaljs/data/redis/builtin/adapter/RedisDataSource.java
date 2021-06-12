package org.clever.graaljs.data.redis.builtin.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.clever.graaljs.core.utils.DateTimeUtils;
import org.clever.graaljs.data.common.AbstractDataSource;
import org.clever.graaljs.data.redis.support.*;
import org.springframework.boot.autoconfigure.data.redis.LettuceClientConfigurationBuilderCustomizer;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.util.Assert;

import java.io.IOException;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 作者： lzw<br/>
 * 创建时间：2019-10-06 22:01 <br/>
 */
@Slf4j
public class RedisDataSource extends AbstractDataSource {
    private final LettuceClientBuilder lettuceClientBuilder;
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * @param redisTemplate RedisTemplate对象
     */
    public RedisDataSource(RedisTemplate<String, Object> redisTemplate) {
        Assert.notNull(redisTemplate, "RedisTemplate不能为空");
        this.lettuceClientBuilder = null;
        this.redisTemplate = redisTemplate;
    }

    /**
     * @param properties            Redis配置
     * @param sentinelConfiguration Redis Sentinel配置
     * @param clusterConfiguration  Redis集群配置
     * @param builderCustomizers    自定义配置
     * @param objectMapper          JSON序列化反序列化对象
     */
    public RedisDataSource(
            RedisProperties properties,
            RedisSentinelConfiguration sentinelConfiguration,
            RedisClusterConfiguration clusterConfiguration,
            List<LettuceClientConfigurationBuilderCustomizer> builderCustomizers,
            ObjectMapper objectMapper) {
        this.lettuceClientBuilder = new LettuceClientBuilder(properties, sentinelConfiguration, clusterConfiguration, builderCustomizers, objectMapper);
        this.redisTemplate = this.lettuceClientBuilder.getRedisTemplate();
        initCheck();
    }

    /**
     * @param properties           Redis配置
     * @param clusterConfiguration Redis集群配置
     * @param builderCustomizers   自定义配置
     * @param objectMapper         JSON序列化反序列化对象
     */
    public RedisDataSource(
            RedisProperties properties,
            RedisClusterConfiguration clusterConfiguration,
            List<LettuceClientConfigurationBuilderCustomizer> builderCustomizers,
            ObjectMapper objectMapper) {
        this(properties, null, clusterConfiguration, builderCustomizers, objectMapper);
    }

    /**
     * @param properties         Redis配置
     * @param builderCustomizers 自定义配置
     * @param objectMapper       JSON序列化反序列化对象
     */
    public RedisDataSource(RedisProperties properties, List<LettuceClientConfigurationBuilderCustomizer> builderCustomizers, ObjectMapper objectMapper) {
        this(properties, null, null, builderCustomizers, objectMapper);
    }

    /**
     * @param properties        Redis配置
     * @param builderCustomizer 自定义配置
     * @param objectMapper      JSON序列化反序列化对象
     */
    public RedisDataSource(RedisProperties properties, LettuceClientConfigurationBuilderCustomizer builderCustomizer, ObjectMapper objectMapper) {
        this(properties, null, null, Collections.singletonList(builderCustomizer), objectMapper);
    }

    /**
     * @param properties   Redis配置
     * @param objectMapper JSON序列化反序列化对象
     */
    public RedisDataSource(RedisProperties properties, ObjectMapper objectMapper) {
        this(properties, null, null, null, objectMapper);
    }

    /**
     * @param properties Redis配置
     */
    public RedisDataSource(RedisProperties properties) {
        Assert.notNull(properties, "RedisProperties不能为空");
        this.lettuceClientBuilder = new LettuceClientBuilder(properties);
        this.redisTemplate = this.lettuceClientBuilder.getRedisTemplate();
        initCheck();
    }

    /**
     * @param redisConnectionFactory Redis Connection 工厂
     * @param objectMapper           JSON序列化反序列化对象
     */
    public RedisDataSource(RedisConnectionFactory redisConnectionFactory, ObjectMapper objectMapper) {
        Assert.notNull(redisConnectionFactory, "RedisConnectionFactory不能为空");
        this.lettuceClientBuilder = new LettuceClientBuilder(redisConnectionFactory, objectMapper);
        this.redisTemplate = this.lettuceClientBuilder.getRedisTemplate();
        initCheck();
    }

    /**
     * @param redisConnectionFactory Redis Connection 工厂
     */
    public RedisDataSource(RedisConnectionFactory redisConnectionFactory) {
        Assert.notNull(redisConnectionFactory, "RedisConnectionFactory不能为空");
        this.lettuceClientBuilder = new LettuceClientBuilder(redisConnectionFactory);
        this.redisTemplate = this.lettuceClientBuilder.getRedisTemplate();
        initCheck();
    }

    /**
     * 校验数据源是否可用
     */
    @Override
    public void initCheck() {
        RedisCallback<Void> callback = connection -> {
            String res = connection.ping();
            log.debug("ping -> {}", res);
            return null;
        };
        try {
            redisTemplate.execute(callback);
        } catch (Exception e) {
            throw new RuntimeException("RedisDataSource创建失败", e);
        }
    }

    @Override
    public void close() {
        if (closed) {
            return;
        }
        super.close();
        lettuceClientBuilder.destroy();
    }

    // --------------------------------------------------------------------------------------------
    // Key 操作
    // --------------------------------------------------------------------------------------------

    /**
     * 删除 key
     *
     * @param key key
     */
    public Boolean kDelete(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 删除 key
     *
     * @param keys keys
     */
    public Long kDelete(Collection<String> keys) {
        return redisTemplate.delete(keys);
    }

    /**
     * 删除 key
     *
     * @param keys keys
     */
    public Long kDelete(String... keys) {
        return redisTemplate.delete(Arrays.asList(keys));
    }

    /**
     * 序列化给定 key ，并返回被序列化的值
     *
     * @param key key
     */
    public byte[] kDump(String key) {
        return redisTemplate.dump(key);
    }

    /**
     * 检查给定 key 是否存在
     *
     * @param key key
     */
    public Boolean kHasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 为给定 key 设置过期时间，以毫秒计
     *
     * @param key     key
     * @param timeout timeout以毫秒计
     */
    public Boolean kExpire(String key, Number timeout) {
        return redisTemplate.expire(key, timeout.longValue(), TimeUnit.MILLISECONDS);
    }

    /**
     * 为给定 key 设置过期时间
     *
     * @param key  key
     * @param date 过期时间
     */
    public Boolean kExpireAt(String key, Date date) {
        return redisTemplate.expireAt(key, date);
    }

    /**
     * 为给定 key 设置过期时间
     *
     * @param key     key
     * @param dateStr 过期时间
     */
    public Boolean kExpireAt(String key, String dateStr) {
        Date date = DateTimeUtils.parseDate(dateStr);
        if (date == null) {
            throw new IllegalArgumentException("过期时间必须是一个时间字符串");
        }
        return redisTemplate.expireAt(key, date);
    }

    /**
     * 查找所有符合给定模式( pattern)的 key
     *
     * @param pattern 模式( pattern)
     */
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 将当前数据库的 key 移动到给定的数据库 db 当中
     *
     * @param key     key
     * @param dbIndex dbIndex
     */
    public Boolean kMove(String key, int dbIndex) {
        return redisTemplate.move(key, dbIndex);
    }

    /**
     * 移除 key 的过期时间，key 将持久保持
     *
     * @param key key
     */
    public Boolean kPersist(String key) {
        return redisTemplate.persist(key);
    }

    /**
     * 以毫秒为单位返回 key 的剩余的过期时间
     *
     * @param key key
     */
    public Long kGetExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.MILLISECONDS);
    }

    /**
     * 从当前数据库中随机返回一个 key
     */
    public String kRandomKey() {
        return redisTemplate.randomKey();
    }

    /**
     * 修改 key 的名称
     *
     * @param oldKey oldKey
     * @param newKey newKey
     */
    public void kRename(String oldKey, String newKey) {
        redisTemplate.rename(oldKey, newKey);
    }

    /**
     * 仅当 newKey 不存在时，将 key 改名为 newKey
     *
     * @param oldKey oldKey
     * @param newKey newKey
     */
    public Boolean kRenameIfAbsent(String oldKey, String newKey) {
        return redisTemplate.renameIfAbsent(oldKey, newKey);
    }

    /**
     * 返回 key 所储存的值的类型
     *
     * @param key key
     */
    public DataType kType(String key) {
        return redisTemplate.type(key);
    }


    // --------------------------------------------------------------------------------------------
    // String 操作
    // --------------------------------------------------------------------------------------------

    /**
     * 设置指定 key 的值
     *
     * @param key   key
     * @param value value
     */
    public void vSet(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 将值 value 关联到 key ，并将 key 的过期时间设为 seconds (以毫秒为单位)
     *
     * @param key     key
     * @param value   value
     * @param timeout 过期时间毫秒
     */
    public void vSet(String key, Object value, Number timeout) {
        redisTemplate.opsForValue().set(key, value, Duration.ofMillis(timeout.longValue()));
    }

    /**
     * 只有在 key 不存在时设置 key 的值
     *
     * @param key   key
     * @param value value
     */
    public Boolean vSetIfAbsent(String key, Object value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    /**
     * @param key     key
     * @param value   value
     * @param timeout 过期时间毫秒
     */
    public Boolean vSetIfAbsent(String key, Object value, Number timeout) {
        return redisTemplate.opsForValue().setIfAbsent(key, value, Duration.ofMillis(timeout.longValue()));
    }

    /**
     * 获取Value的值
     *
     * @param key key
     */
    public Object vGet(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 返回 key 中字符串值的子字符
     *
     * @param key   key
     * @param start start
     * @param end   end
     */
    public String vGet(String key, Number start, Number end) {
        return redisTemplate.opsForValue().get(key, start.longValue(), end.longValue());
    }

    /**
     * 将给定 key 的值设为 value ，并返回 key 的旧值(old value)
     *
     * @param key   key
     * @param value value
     */
    public Object vGetAndSet(String key, Object value) {
        return redisTemplate.opsForValue().getAndSet(key, value);
    }

    /**
     * 对 key 所储存的字符串值，获取指定偏移量上的位(bit)
     *
     * @param key    key
     * @param offset 偏移量
     */
    public Boolean vGetBit(String key, Number offset) {
        return redisTemplate.opsForValue().getBit(key, offset.longValue());
    }

    /**
     * 获取所有(一个或多个)给定 key 的值
     *
     * @param keys keys
     */
    public List<Object> vMultiGet(Collection<String> keys) {
        return redisTemplate.opsForValue().multiGet(keys);
    }

    /**
     * 获取所有(一个或多个)给定 key 的值
     *
     * @param keys keys
     */
    public List<Object> vMultiGet(String... keys) {
        return redisTemplate.opsForValue().multiGet(Arrays.asList(keys));
    }

    /**
     * 对 key 所储存的字符串值，设置或清除指定偏移量上的位(bit)
     *
     * @param key    key
     * @param offset 偏移量
     * @param value  值
     */
    public Boolean vSetBit(String key, Number offset, boolean value) {
        return redisTemplate.opsForValue().setBit(key, offset.longValue(), value);
    }

    /**
     * 用 value 参数覆写给定 key 所储存的字符串值，从偏移量 offset 开始
     *
     * @param key    key
     * @param value  value
     * @param offset 偏移量
     */
    public void vSetRange(String key, Object value, Number offset) {
        redisTemplate.opsForValue().set(key, value, offset.longValue());
    }

    /**
     * 返回 key 所储存的字符串值的长度
     *
     * @param key key
     */
    public Long vSize(String key) {
        return redisTemplate.opsForValue().size(key);
    }

    /**
     * 同时设置一个或多个 key-value 对
     *
     * @param map 多个 key-value 对
     */
    public void vMultiSet(Map<String, Object> map) {
        redisTemplate.opsForValue().multiSet(map);
    }

    /**
     * 同时设置一个或多个 key-value 对，当且仅当所有给定 key 都不存在
     *
     * @param map 多个 key-value 对
     */
    public void vMultiSetIfAbsent(Map<String, Object> map) {
        redisTemplate.opsForValue().multiSetIfAbsent(map);
    }

    /**
     * 将 key 中储存的数字值增 1
     *
     * @param key key
     */
    public Long vIncrement(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    /**
     * 将 key 所储存的值加上给定的增量值（increment）
     *
     * @param key   key
     * @param delta 增量值
     */
    public Long vIncrement(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 将 key 所储存的值加上给定的增量值（increment）
     *
     * @param key   key
     * @param delta 增量值
     */
    public Long vIncrement(String key, Integer delta) {
        return redisTemplate.opsForValue().increment(key, delta.longValue());
    }

    /**
     * 将 key 所储存的值加上给定的增量值（increment）
     *
     * @param key   key
     * @param delta 增量值
     */
    public Double vIncrement(String key, double delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 将 key 中储存的数字值减 1
     *
     * @param key key
     */
    public Long vDecrement(String key) {
        return redisTemplate.opsForValue().decrement(key);
    }

    /**
     * key 所储存的值减去给定的减量值（decrement）
     *
     * @param key   key
     * @param delta 减量值
     */
    public Long vDecrement(String key, long delta) {
        return redisTemplate.opsForValue().decrement(key, delta);
    }

    /**
     * key 所储存的值减去给定的减量值（decrement）
     *
     * @param key   key
     * @param delta 减量值
     */
    public Long vDecrement(String key, Integer delta) {
        return redisTemplate.opsForValue().decrement(key, delta.longValue());
    }

    /**
     * key 所储存的值减去给定的减量值（decrement）
     *
     * @param key   key
     * @param delta 减量值
     */
    public Long vDecrement(String key, Double delta) {
        return redisTemplate.opsForValue().decrement(key, delta.longValue());
    }

    /**
     * 如果 key 已经存在并且是一个字符串， APPEND 命令将指定的 value 追加到该 key 原来值（value）的末尾
     *
     * @param key   key
     * @param value value
     */
    public Integer vAppend(String key, String value) {
        return redisTemplate.opsForValue().append(key, value);
    }

    // --------------------------------------------------------------------------------------------
    // Hash 操作
    // --------------------------------------------------------------------------------------------

    /**
     * 删除一个或多个哈希表字段
     *
     * @param key      key
     * @param hashKeys hashKeys
     */
    public Long hDelete(String key, Object... hashKeys) {
        return redisTemplate.opsForHash().delete(key, hashKeys);
    }

    /**
     * 删除一个或多个哈希表字段
     *
     * @param key      key
     * @param hashKeys hashKeys
     */
    public Long hDelete(String key, Collection<Object> hashKeys) {
        return redisTemplate.opsForHash().delete(key, hashKeys.toArray());
    }

    /**
     * 查看哈希表 key 中，指定的字段是否存在
     *
     * @param key     key
     * @param hashKey hashKey
     */
    public Boolean hHasKey(String key, Object hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    /**
     * 获取存储在哈希表中指定字段的值
     *
     * @param key     key
     * @param hashKey hashKey
     */
    public Object hGet(String key, Object hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * 获取所有给定字段的值
     *
     * @param key      key
     * @param hashKeys hashKeys
     */
    public List<Object> hMultiGet(String key, Collection<Object> hashKeys) {
        return redisTemplate.opsForHash().multiGet(key, hashKeys);
    }

    /**
     * 获取所有给定字段的值
     *
     * @param key      key
     * @param hashKeys hashKeys
     */
    public List<Object> hMultiGet(String key, Object... hashKeys) {
        return redisTemplate.opsForHash().multiGet(key, Arrays.asList(hashKeys));
    }

    /**
     * 为哈希表 key 中的指定字段的整数值加上增量 increment
     *
     * @param key     key
     * @param hashKey hashKey
     * @param delta   增量
     */
    public Long hIncrement(String key, Object hashKey, long delta) {
        return redisTemplate.opsForHash().increment(key, hashKey, delta);
    }

    /**
     * 为哈希表 key 中的指定字段的整数值加上增量 increment
     *
     * @param key     key
     * @param hashKey hashKey
     * @param delta   增量
     */
    public Long hIncrement(String key, Object hashKey, Integer delta) {
        return redisTemplate.opsForHash().increment(key, hashKey, delta.longValue());
    }

    /**
     * 为哈希表 key 中的指定字段的整数值加上增量 increment
     *
     * @param key     key
     * @param hashKey hashKey
     * @param delta   增量
     */
    public Double hIncrement(String key, Object hashKey, double delta) {
        return redisTemplate.opsForHash().increment(key, hashKey, delta);
    }

    /**
     * 获取所有哈希表中的字段
     *
     * @param key key
     */
    public Set<Object> hKeys(String key) {
        return redisTemplate.opsForHash().keys(key);
    }

    /**
     * 返回与hashKey关联的值的长度。如果键或hashKey不存在，则返回0
     *
     * @param key     key
     * @param hashKey hashKey
     */
    public Long hLengthOfValue(String key, Object hashKey) {
        return redisTemplate.opsForHash().lengthOfValue(key, hashKey);
    }

    /**
     * 获取哈希表中字段的数量
     *
     * @param key key
     */
    public Long hSize(String key) {
        return redisTemplate.opsForHash().size(key);
    }

    /**
     * 同时将多个 field-value (域-值)对设置到哈希表 key 中
     *
     * @param key key
     * @param m   field-value
     */
    public void hPutAll(String key, Map<Object, Object> m) {
        redisTemplate.opsForHash().putAll(key, m);
    }

    /**
     * 将哈希表 key 中的字段 field 的值设为 value
     *
     * @param key     key
     * @param hashKey field
     * @param value   value
     */
    public void hPut(String key, Object hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    /**
     * 只有在字段 field 不存在时，设置哈希表字段的值
     *
     * @param key     key
     * @param hashKey field
     * @param value   字段的值
     */
    public Boolean hPutIfAbsent(String key, Object hashKey, Object value) {
        return redisTemplate.opsForHash().putIfAbsent(key, hashKey, value);
    }

    /**
     * 获取哈希表中所有值
     *
     * @param key key
     */
    public List<Object> hValues(String key) {
        return redisTemplate.opsForHash().values(key);
    }

    /**
     * 将整个散列存储在键上
     *
     * @param key key
     */
    public Map<Object, Object> hEntries(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 迭代哈希表中的键值对
     *
     * @param key      key
     * @param count    数量
     * @param pattern  字段匹配字符串
     * @param callback 数据迭代回调
     */
    public void hScan(String key, Number count, String pattern, ScanCallback<Map.Entry<Object, Object>> callback) throws IOException {
        Assert.notNull(callback, "ScanCallback不能为空");
        ScanOptions scanOptions = ScanOptions.scanOptions().count(count.longValue()).match(pattern).build();
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(key, scanOptions);
        cursorForeach(cursor, callback);
    }

    // --------------------------------------------------------------------------------------------
    // List 操作
    // --------------------------------------------------------------------------------------------

    /**
     * 获取列表指定范围内的元素
     *
     * @param key   key
     * @param start start
     * @param end   end
     */
    public List<Object> lRange(String key, Number start, Number end) {
        return redisTemplate.opsForList().range(key, start.longValue(), end.longValue());
    }

    /**
     * 对一个列表进行修剪(trim)，就是说，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除
     *
     * @param key   key
     * @param start start
     * @param end   end
     */
    public void lTrim(String key, Number start, Number end) {
        redisTemplate.opsForList().trim(key, start.longValue(), end.longValue());
    }

    /**
     * 获取列表长度
     *
     * @param key key
     */
    public Long lSize(String key) {
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 将一个或多个值插入到列表头部
     *
     * @param key   key
     * @param value value
     */
    public Long lLeftPush(String key, Object value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 将一个或多个值插入到列表头部
     *
     * @param key    key
     * @param values values
     */
    public Long lLeftPushAll(String key, Object... values) {
        return redisTemplate.opsForList().leftPushAll(key, values);
    }

    /**
     * 将一个或多个值插入到列表头部
     *
     * @param key    key
     * @param values values
     */
    public Long lLeftPushAll(String key, Collection<Object> values) {
        return redisTemplate.opsForList().leftPushAll(key, values);
    }

    /**
     * 将一个值插入到已存在的列表头部
     *
     * @param key   key
     * @param value value
     */
    public Long lLeftPushIfPresent(String key, Object value) {
        return redisTemplate.opsForList().leftPushIfPresent(key, value);
    }

    /**
     * 将值前置到键值之前
     *
     * @param key   key
     * @param pivot pivot
     * @param value value
     */
    public Long lLeftPush(String key, Object pivot, Object value) {
        return redisTemplate.opsForList().leftPush(key, pivot, value);
    }

    /**
     * 在列表中添加一个或多个值
     *
     * @param key   key
     * @param value value
     */
    public Long lRightPush(String key, Object value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 在列表中添加一个或多个值
     *
     * @param key    key
     * @param values values
     */
    public Long lRightPushAll(String key, Object... values) {
        return redisTemplate.opsForList().rightPushAll(key, values);
    }

    /**
     * 在列表中添加一个或多个值
     *
     * @param key    key
     * @param values values
     */
    public Long lRightPushAll(String key, Collection<Object> values) {
        return redisTemplate.opsForList().rightPushAll(key, values);
    }

    /**
     * 仅当列表存在时才向键追加值
     *
     * @param key   key
     * @param value value
     */
    public Long lRightPushIfPresent(String key, Object value) {
        return redisTemplate.opsForList().rightPushIfPresent(key, value);
    }

    /**
     * 在键值之前追加值
     *
     * @param key   key
     * @param pivot pivot
     * @param value value
     */
    public Long lRightPush(String key, Object pivot, Object value) {
        return redisTemplate.opsForList().rightPush(key, pivot, value);
    }

    /**
     * 通过索引设置列表元素的值
     *
     * @param key   key
     * @param index 索引
     * @param value value
     */
    public void lSet(String key, Number index, Object value) {
        redisTemplate.opsForList().set(key, index.longValue(), value);
    }

    /**
     * 移除列表元素，从存储在键上的列表中删除第一次出现的值计数
     *
     * @param key   key
     * @param count count
     * @param value value
     */
    public Long lRemove(String key, Number count, Object value) {
        return redisTemplate.opsForList().remove(key, count.longValue(), value);
    }

    /**
     * 通过索引获取列表中的元素
     *
     * @param key   key
     * @param index 索引
     */
    public Object lIndex(String key, Number index) {
        return redisTemplate.opsForList().index(key, index.longValue());
    }

    /**
     * 移出并获取列表的第一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     *
     * @param key key
     */
    public Object lLeftPop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    /**
     * 移出并获取列表的第一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     *
     * @param key     key
     * @param timeout timeout 毫秒
     */
    public Object lLeftPop(String key, Number timeout) {
        return redisTemplate.opsForList().leftPop(key, timeout.longValue(), TimeUnit.MILLISECONDS);
    }

    /**
     * 移出并获取列表的最后一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     *
     * @param key key
     */
    public Object lRightPop(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    /**
     * 移出并获取列表的最后一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     *
     * @param key     key
     * @param timeout timeout 毫秒
     */
    public Object lRightPop(String key, Number timeout) {
        return redisTemplate.opsForList().rightPop(key, timeout.longValue(), TimeUnit.MILLISECONDS);
    }

    /**
     * 从列表中弹出一个值，将弹出的元素插入到另外一个列表中并返回它； 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     *
     * @param sourceKey      sourceKey
     * @param destinationKey destinationKey
     */
    public Object lRightPopAndLeftPush(String sourceKey, String destinationKey) {
        return redisTemplate.opsForList().rightPopAndLeftPush(sourceKey, destinationKey);
    }

    /**
     * 从列表中弹出一个值，将弹出的元素插入到另外一个列表中并返回它； 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     *
     * @param sourceKey      sourceKey
     * @param destinationKey destinationKey
     * @param timeout        timeout 毫秒
     */
    public Object lRightPopAndLeftPush(String sourceKey, String destinationKey, Number timeout) {
        return redisTemplate.opsForList().rightPopAndLeftPush(sourceKey, destinationKey, timeout.longValue(), TimeUnit.MILLISECONDS);
    }

    // --------------------------------------------------------------------------------------------
    // Set 操作
    // --------------------------------------------------------------------------------------------

    /**
     * 向集合添加一个或多个成员
     *
     * @param key    key
     * @param values values
     */
    public Long sAdd(String key, Object... values) {
        return redisTemplate.opsForSet().add(key, values);
    }

    /**
     * 向集合添加一个或多个成员
     *
     * @param key    key
     * @param values values
     */
    public Long sAdd(String key, Collection<Object> values) {
        return redisTemplate.opsForSet().add(key, values.toArray());
    }

    /**
     * 移除集合中一个或多个成员
     *
     * @param key    key
     * @param values values
     */
    public Long sRemove(String key, Object... values) {
        return redisTemplate.opsForSet().remove(key, values);
    }

    /**
     * 移除集合中一个或多个成员
     *
     * @param key    key
     * @param values values
     */
    public Long sRemove(String key, Collection<Object> values) {
        return redisTemplate.opsForSet().remove(key, values.toArray());
    }

    /**
     * 移除并返回集合中的一个随机元素
     *
     * @param key key
     */
    public Object sPop(String key) {
        return redisTemplate.opsForSet().pop(key);
    }

    /**
     * 移除并返回集合中的count个随机元素
     *
     * @param key   key
     * @param count count
     */
    public List<Object> sPop(String key, Number count) {
        return redisTemplate.opsForSet().pop(key, count.longValue());
    }

    /**
     * 将 value 元素从 key 集合移动到 destKey 集合
     *
     * @param key     key
     * @param value   value
     * @param destKey destKey
     */
    public Boolean sMove(String key, Object value, String destKey) {
        return redisTemplate.opsForSet().move(key, value, destKey);
    }

    /**
     * 获取集合的成员数
     *
     * @param key key
     */
    public Long sSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * 判断 member 元素是否是集合 key 的成员
     *
     * @param key    key
     * @param member member 元素
     */
    public Boolean sIsMember(String key, Object member) {
        return redisTemplate.opsForSet().isMember(key, member);
    }

    /**
     * 返回给定所有集合的交集
     *
     * @param key      key
     * @param otherKey otherKey
     */
    public Set<Object> sIntersect(String key, String otherKey) {
        return redisTemplate.opsForSet().intersect(key, otherKey);
    }

    /**
     * 返回给定所有集合的交集
     *
     * @param key       key
     * @param otherKeys otherKeys
     */
    public Set<Object> sIntersect(String key, Collection<String> otherKeys) {
        return redisTemplate.opsForSet().intersect(key, otherKeys);
    }

    /**
     * 返回给定所有集合的交集
     *
     * @param key       key
     * @param otherKeys otherKeys
     */
    public Set<Object> sIntersect(String key, String... otherKeys) {
        return redisTemplate.opsForSet().intersect(key, Arrays.asList(otherKeys));
    }

    /**
     * 返回给定所有集合的交集并存储在 destination 中
     *
     * @param key      key
     * @param otherKey otherKey
     * @param destKey  destKey
     */
    public Long sIntersectAndStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForSet().intersectAndStore(key, otherKey, destKey);
    }

    /**
     * 返回给定所有集合的交集并存储在 destination 中
     *
     * @param key       key
     * @param otherKeys otherKeys
     * @param destKey   destKey
     */
    public Long sIntersectAndStore(String key, Collection<String> otherKeys, String destKey) {
        return redisTemplate.opsForSet().intersectAndStore(key, otherKeys, destKey);
    }

    /**
     * 返回给定所有集合的交集并存储在 destination 中
     *
     * @param key       key
     * @param otherKeys otherKeys
     * @param destKey   destKey
     */
    public Long sIntersectAndStore(String key, String[] otherKeys, String destKey) {
        return redisTemplate.opsForSet().intersectAndStore(key, Arrays.asList(otherKeys), destKey);
    }

    /**
     * 返回所有给定集合的并集
     *
     * @param key      key
     * @param otherKey otherKey
     */
    public Set<Object> sUnion(String key, String otherKey) {
        return redisTemplate.opsForSet().union(key, otherKey);
    }

    /**
     * 返回所有给定集合的并集
     *
     * @param key       key
     * @param otherKeys otherKey
     */
    public Set<Object> sUnion(String key, Collection<String> otherKeys) {
        return redisTemplate.opsForSet().union(key, otherKeys);
    }

    /**
     * 返回所有给定集合的并集
     *
     * @param key       key
     * @param otherKeys otherKeys
     */
    public Set<Object> sUnion(String key, String... otherKeys) {
        return redisTemplate.opsForSet().union(key, Arrays.asList(otherKeys));
    }

    /**
     * 所有给定集合的并集存储在 destKey 集合中
     *
     * @param key      key
     * @param otherKey otherKey
     * @param destKey  destKey
     */
    public Long sUnionAndStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForSet().unionAndStore(key, otherKey, destKey);
    }

    /**
     * 所有给定集合的并集存储在 destKey 集合中
     *
     * @param key       key
     * @param otherKeys otherKeys
     * @param destKey   destKey
     */
    public Long sUnionAndStore(String key, Collection<String> otherKeys, String destKey) {
        return redisTemplate.opsForSet().unionAndStore(key, otherKeys, destKey);
    }

    /**
     * 所有给定集合的并集存储在 destKey 集合中
     *
     * @param key       key
     * @param otherKeys otherKeys
     * @param destKey   destKey
     */
    public Long sUnionAndStore(String key, String[] otherKeys, String destKey) {
        return redisTemplate.opsForSet().unionAndStore(key, Arrays.asList(otherKeys), destKey);
    }

    /**
     * 返回给定所有集合的差集
     *
     * @param key      key
     * @param otherKey otherKey
     */
    public Set<Object> sDifference(String key, String otherKey) {
        return redisTemplate.opsForSet().difference(key, otherKey);
    }

    /**
     * 返回给定所有集合的差集
     *
     * @param key      key
     * @param otherKey otherKey
     */
    public Set<Object> sDifference(String key, Collection<String> otherKey) {
        return redisTemplate.opsForSet().difference(key, otherKey);
    }

    /**
     * 返回给定所有集合的差集
     *
     * @param key      key
     * @param otherKey otherKey
     */
    public Set<Object> sDifference(String key, String... otherKey) {
        return redisTemplate.opsForSet().difference(key, Arrays.asList(otherKey));
    }

    /**
     * 返回给定所有集合的差集并存储在 destKey 中
     *
     * @param key      key
     * @param otherKey otherKey
     * @param destKey  destKey
     */
    public Long sDifferenceAndStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForSet().differenceAndStore(key, otherKey, destKey);
    }

    /**
     * 返回给定所有集合的差集并存储在 destKey 中
     *
     * @param key       key
     * @param otherKeys otherKeys
     * @param destKey   destKey
     */
    public Long sDifferenceAndStore(String key, Collection<String> otherKeys, String destKey) {
        return redisTemplate.opsForSet().differenceAndStore(key, otherKeys, destKey);
    }

    /**
     * 返回给定所有集合的差集并存储在 destKey 中
     *
     * @param key       key
     * @param otherKeys otherKeys
     * @param destKey   destKey
     */
    public Long sDifferenceAndStore(String key, String[] otherKeys, String destKey) {
        return redisTemplate.opsForSet().differenceAndStore(key, Arrays.asList(otherKeys), destKey);
    }

    /**
     * 返回集合中的所有成员
     *
     * @param key key
     */
    public Set<Object> sMembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 返回集合中一个或多个随机数
     *
     * @param key key
     */
    public Object sRandomMember(String key) {
        return redisTemplate.opsForSet().randomMember(key);
    }

    /**
     * 从集合中获取不同的随机元素
     *
     * @param key   key
     * @param count 数量
     */
    public Set<Object> sDistinctRandomMembers(String key, Number count) {
        return redisTemplate.opsForSet().distinctRandomMembers(key, count.longValue());
    }

    /**
     * 返回集合中一个或多个随机数
     *
     * @param key   key
     * @param count 数量
     */
    public List<Object> sRandomMembers(String key, Number count) {
        return redisTemplate.opsForSet().randomMembers(key, count.longValue());
    }

    /**
     * 迭代集合中的元素
     *
     * @param key      key
     * @param count    count
     * @param pattern  pattern
     * @param callback 数据迭代回调函数
     */
    public void sScan(String key, Number count, String pattern, ScanCallback<Object> callback) throws IOException {
        Assert.notNull(callback, "ScanCallback不能为空");
        ScanOptions scanOptions = ScanOptions.scanOptions().count(count.longValue()).match(pattern).build();
        Cursor<Object> cursor = redisTemplate.opsForSet().scan(key, scanOptions);
        cursorForeach(cursor, callback);
    }

    // --------------------------------------------------------------------------------------------
    // Sorted Set 操作
    // --------------------------------------------------------------------------------------------

    /**
     * 向有序集合添加一个或多个成员，或者更新已存在成员的分数
     *
     * @param key   key
     * @param value value
     * @param score score
     */
    public Boolean zsAdd(String key, Object value, Number score) {
        return redisTemplate.opsForZSet().add(key, value, score.doubleValue());
    }

    /**
     * 向有序集合添加一个或多个成员，或者更新已存在成员的分数
     *
     * @param key    key
     * @param values values
     */
    public Long zsAdd(String key, Collection<ZSetValue> values) {
        Set<ZSetOperations.TypedTuple<Object>> tuples = new HashSet<>(values.size());
        values.forEach(zSetValue -> {
            ZSetOperations.TypedTuple<Object> tuple = getZSetValue(zSetValue);
            tuples.add(tuple);
        });
        return redisTemplate.opsForZSet().add(key, tuples);
    }

    /**
     * 向有序集合添加一个或多个成员，或者更新已存在成员的分数
     *
     * @param key    key
     * @param values values
     */
    public Long zsAdd(String key, ZSetValue[] values) {
        Set<ZSetOperations.TypedTuple<Object>> tuples = new HashSet<>(values.length);
        for (ZSetValue zSetValue : values) {
            ZSetOperations.TypedTuple<Object> tuple = getZSetValue(zSetValue);
            tuples.add(tuple);
        }
        return redisTemplate.opsForZSet().add(key, tuples);
    }

    /**
     * 移除有序集合中的一个或多个成员
     *
     * @param key    key
     * @param values values
     */
    public Long zsRemove(String key, Object... values) {
        return redisTemplate.opsForZSet().remove(key, values);
    }

    /**
     * 移除有序集合中的一个或多个成员
     *
     * @param key    key
     * @param values values
     */
    public Long zsRemove(String key, Collection<Object> values) {
        return redisTemplate.opsForZSet().remove(key, values.toArray());
    }

    /**
     * 有序集合中对指定成员的分数加上增量 increment
     *
     * @param key   key
     * @param value value
     * @param delta increment
     */
    public Double zsIncrementScore(String key, Object value, Number delta) {
        return redisTemplate.opsForZSet().incrementScore(key, value, delta.doubleValue());
    }

    /**
     * 返回有序集合中指定成员的索引
     *
     * @param key key
     * @param o   o
     */
    public Long zsRank(String key, Object o) {
        return redisTemplate.opsForZSet().rank(key, o);
    }

    /**
     * 确定元素的索引值在排序集时得分从高到低
     *
     * @param key key
     * @param o   o
     */
    public Long zsReverseRank(String key, Object o) {
        return redisTemplate.opsForZSet().reverseRank(key, o);
    }

    /**
     * 从已排序集获取开始和结束之间的元素
     *
     * @param key   key
     * @param start start
     * @param end   end
     */
    public Set<Object> zsRange(String key, Number start, Number end) {
        return redisTemplate.opsForZSet().range(key, start.longValue(), end.longValue());
    }

    /**
     * 从已排序集获取开始和结束之间的元素
     *
     * @param key   key
     * @param start start
     * @param end   end
     */
    public List<ZSetValue> zsRangeWithScores(String key, Number start, Number end) {
        Set<ZSetOperations.TypedTuple<Object>> set = redisTemplate.opsForZSet().rangeWithScores(key, start.longValue(), end.longValue());
        return zSetToList(set);
    }

    /**
     * 从排序后的集合中获取得分介于最小值和最大值之间的元素
     *
     * @param key key
     * @param min min
     * @param max max
     */
    public Set<Object> zsRangeByScore(String key, Number min, Number max) {
        return redisTemplate.opsForZSet().rangeByScore(key, min.doubleValue(), max.doubleValue());
    }

    /**
     * 从排序后的集合中获取得分介于最小值和最大值之间的元素
     *
     * @param key key
     * @param min min
     * @param max max
     */
    public List<ZSetValue> zsRangeByScoreWithScores(String key, Number min, Number max) {
        Set<ZSetOperations.TypedTuple<Object>> set = redisTemplate.opsForZSet().rangeByScoreWithScores(key, min.doubleValue(), max.doubleValue());
        return zSetToList(set);
    }

    /**
     * 获取从开始到结束的范围内的元素，其中得分在排序集的最小值和最大值之间
     *
     * @param key    key
     * @param min    min
     * @param max    max
     * @param offset offset
     * @param count  count
     */
    public Set<Object> zsRangeByScore(String key, Number min, Number max, Number offset, Number count) {
        return redisTemplate.opsForZSet().rangeByScore(key, min.doubleValue(), max.doubleValue(), offset.longValue(), count.longValue());
    }

    /**
     * 获取从开始到结束的范围内的元素，其中得分在排序集的最小值和最大值之间
     *
     * @param key    key
     * @param min    min
     * @param max    max
     * @param offset offset
     * @param count  count
     */
    public List<ZSetValue> zsRangeByScoreWithScores(String key, Number min, Number max, Number offset, Number count) {
        Set<ZSetOperations.TypedTuple<Object>> set = redisTemplate.opsForZSet().rangeByScoreWithScores(key, min.doubleValue(), max.doubleValue(), offset.longValue(), count.longValue());
        return zSetToList(set);
    }

    /**
     * 获取范围从开始到结束的元素，从高到低排序的集合
     *
     * @param key   key
     * @param start start
     * @param end   end
     */
    public Set<Object> zsReverseRange(String key, Number start, Number end) {
        return redisTemplate.opsForZSet().reverseRange(key, start.longValue(), end.longValue());
    }

    /**
     * 获取范围从开始到结束的元素，从高到低排序的集合
     *
     * @param key   key
     * @param start start
     * @param end   end
     */
    public List<ZSetValue> zsReverseRangeWithScores(String key, Number start, Number end) {
        Set<ZSetOperations.TypedTuple<Object>> set = redisTemplate.opsForZSet().reverseRangeWithScores(key, start.longValue(), end.longValue());
        return zSetToList(set);
    }

    /**
     * 获取得分介于最小值和最大值之间的元素，从高到低排序
     *
     * @param key key
     * @param min min
     * @param max max
     */
    public Set<Object> zsReverseRangeByScore(String key, Number min, Number max) {
        return redisTemplate.opsForZSet().reverseRangeByScore(key, min.doubleValue(), max.doubleValue());
    }

    /**
     * 获取得分介于最小值和最大值之间的元素，从高到低排序
     *
     * @param key key
     * @param min min
     * @param max max
     */
    public List<ZSetValue> zsReverseRangeByScoreWithScores(String key, Number min, Number max) {
        Set<ZSetOperations.TypedTuple<Object>> set = redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, min.doubleValue(), max.doubleValue());
        return zSetToList(set);
    }

    /**
     * 获取从开始到结束的范围内的元素，其中得分在最小和最大之间，排序集高 -> 低
     *
     * @param key    key
     * @param min    min
     * @param max    max
     * @param offset offset
     * @param count  count
     */
    public Set<Object> zsReverseRangeByScore(String key, Number min, Number max, Number offset, Number count) {
        return redisTemplate.opsForZSet().reverseRangeByScore(key, min.doubleValue(), max.doubleValue(), offset.longValue(), count.longValue());
    }

    /**
     * 获取从开始到结束的范围内的元素，其中得分在最小和最大之间，排序集高 -> 低
     *
     * @param key    key
     * @param min    min
     * @param max    max
     * @param offset offset
     * @param count  count
     */
    public List<ZSetValue> zsReverseRangeByScoreWithScores(String key, Number min, Number max, Number offset, Number count) {
        Set<ZSetOperations.TypedTuple<Object>> set = redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, min.doubleValue(), max.doubleValue(), offset.longValue(), count.longValue());
        return zSetToList(set);
    }

    /**
     * 用最小值和最大值之间的值计算排序集中的元素数
     *
     * @param key key
     * @param min min
     * @param max max
     */
    public Long zsCount(String key, Number min, Number max) {
        return redisTemplate.opsForZSet().count(key, min.doubleValue(), max.doubleValue());
    }

    /**
     * 返回按给定键存储的已排序集的元素数
     *
     * @param key key
     */
    public Long zsSize(String key) {
        return redisTemplate.opsForZSet().size(key);
    }

    /**
     * 获取有序集合的成员数
     *
     * @param key key
     */
    public Long zsZCard(String key) {
        return redisTemplate.opsForZSet().zCard(key);
    }

    /**
     * 返回有序集中，成员的分数值
     *
     * @param key key
     * @param o   o
     */
    public Double zsScore(String key, Object o) {
        return redisTemplate.opsForZSet().score(key, o);
    }

    /**
     * 从按键排序的集合中删除开始和结束之间范围内的元素
     *
     * @param key   key
     * @param start start
     * @param end   end
     */
    public Long zsRemoveRange(String key, Number start, Number end) {
        return redisTemplate.opsForZSet().removeRange(key, start.longValue(), end.longValue());
    }

    /**
     * 从按键排序的集合中删除得分在min和max之间的元素
     *
     * @param key key
     * @param min min
     * @param max max
     */
    public Long zsRemoveRangeByScore(String key, Number min, Number max) {
        return redisTemplate.opsForZSet().removeRangeByScore(key, min.doubleValue(), max.doubleValue());
    }

    /**
     * 计算给定的一个或多个有序集的并集，并存储在新的 destKey 中
     *
     * @param key      key
     * @param otherKey otherKey
     * @param destKey  destKey
     */
    public Long zsUnionAndStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForZSet().unionAndStore(key, otherKey, destKey);
    }

    /**
     * 计算给定的一个或多个有序集的并集，并存储在新的 destKey 中
     *
     * @param key       key
     * @param otherKeys otherKeys
     * @param destKey   destKey
     */
    public Long zsUnionAndStore(String key, Collection<String> otherKeys, String destKey) {
        return redisTemplate.opsForZSet().unionAndStore(key, otherKeys, destKey);
    }

    /**
     * 计算给定的一个或多个有序集的并集，并存储在新的 destKey 中
     *
     * @param key       key
     * @param otherKeys otherKeys
     * @param destKey   destKey
     */
    public Long zsUnionAndStore(String key, String[] otherKeys, String destKey) {
        return redisTemplate.opsForZSet().unionAndStore(key, Arrays.asList(otherKeys), destKey);
    }

    /**
     * 计算给定的一个或多个有序集的交集并将结果集存储在新的有序集合 key 中
     *
     * @param key      key
     * @param otherKey otherKey
     * @param destKey  destKey
     */
    public Long zsIntersectAndStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForZSet().intersectAndStore(key, otherKey, destKey);
    }

    /**
     * 计算给定的一个或多个有序集的交集并将结果集存储在新的有序集合 key 中
     *
     * @param key       key
     * @param otherKeys otherKeys
     * @param destKey   destKey
     */
    public Long zsIntersectAndStore(String key, Collection<String> otherKeys, String destKey) {
        return redisTemplate.opsForZSet().intersectAndStore(key, otherKeys, destKey);
    }

    /**
     * 计算给定的一个或多个有序集的交集并将结果集存储在新的有序集合 key 中
     *
     * @param key       key
     * @param otherKeys otherKeys
     * @param destKey   destKey
     */
    public Long zsIntersectAndStore(String key, String[] otherKeys, String destKey) {
        return redisTemplate.opsForZSet().intersectAndStore(key, Arrays.asList(otherKeys), destKey);
    }

    /**
     * 迭代有序集合中的元素（包括元素成员和元素分值）
     *
     * @param key      key
     * @param count    count
     * @param pattern  pattern
     * @param callback 数据迭代回调函数
     */
    public void zsScan(String key, Number count, String pattern, ScanCallback<ZSetValue> callback) throws IOException {
        ScanOptions scanOptions = ScanOptions.scanOptions().count(count.longValue()).match(pattern).build();
        Cursor<ZSetOperations.TypedTuple<Object>> cursor = redisTemplate.opsForZSet().scan(key, scanOptions);
        while (cursor.hasNext()) {
            ZSetOperations.TypedTuple<Object> tuple = cursor.next();
            boolean needBreak;
            try {
                needBreak = callback.next(new ZSetValue(tuple.getValue(), tuple.getScore()));
            } catch (Exception e) {
                needBreak = true;
                log.warn(e.getMessage(), e);
            }
            if (needBreak) {
                cursor.close();
                break;
            }
        }
    }

    /**
     * 通过字典区间返回有序集合的成员
     *
     * @param key       key
     * @param minValue  minValue
     * @param equalsMin equalsMin
     * @param maxValue  maxValue
     * @param equalsMax equalsMax
     */
    public Set<Object> zsRangeByLex(String key, Object minValue, boolean equalsMin, Object maxValue, boolean equalsMax) {
        RedisZSetCommands.Range range = newRange(minValue, equalsMin, maxValue, equalsMax);
        return redisTemplate.opsForZSet().rangeByLex(key, range);
    }

    /**
     * 通过字典区间返回有序集合的成员
     *
     * @param key       key
     * @param minValue  minValue
     * @param equalsMin equalsMin
     * @param maxValue  maxValue
     * @param equalsMax equalsMax
     * @param count     count
     * @param offset    offset
     */
    public Set<Object> zsRangeByLex(String key, Object minValue, boolean equalsMin, Object maxValue, boolean equalsMax, Number count, Number offset) {
        RedisZSetCommands.Range range = newRange(minValue, equalsMin, maxValue, equalsMax);
        RedisZSetCommands.Limit limit;
        if (count != null || offset != null) {
            limit = RedisZSetCommands.Limit.limit();
            if (count != null) {
                limit.count(count.intValue());
            }
            if (offset != null) {
                limit.offset(offset.intValue());
            }
        } else {
            limit = RedisZSetCommands.Limit.unlimited();
        }
        return redisTemplate.opsForZSet().rangeByLex(key, range, limit);
    }

    // --------------------------------------------------------------------------------------------
    // HyperLogLog  操作
    // --------------------------------------------------------------------------------------------

    /**
     * 添加指定元素到 HyperLogLog 中
     *
     * @param key    key
     * @param values values
     */
    public Long hyperLogLogAdd(String key, Object... values) {
        return redisTemplate.opsForHyperLogLog().add(key, values);
    }

    /**
     * 添加指定元素到 HyperLogLog 中
     *
     * @param key    key
     * @param values values
     */
    public Long hyperLogLogAdd(String key, Collection<Object> values) {
        return redisTemplate.opsForHyperLogLog().add(key, values.toArray());
    }

    /**
     * 获取键中元素的当前数目
     *
     * @param keys keys
     */
    public Long hyperLogLogSize(String... keys) {
        return redisTemplate.opsForHyperLogLog().size(keys);
    }

    /**
     * 获取键中元素的当前数目
     *
     * @param keys keys
     */
    public Long hyperLogLogSize(Collection<String> keys) {
        return redisTemplate.opsForHyperLogLog().size(keys.toArray(new String[0]));
    }

    /**
     * 将多个 HyperLogLog 合并为一个 HyperLogLog
     *
     * @param destination destination
     * @param sourceKeys  sourceKeys
     */
    public Long hyperLogLogUnion(String destination, String... sourceKeys) {
        return redisTemplate.opsForHyperLogLog().union(destination, sourceKeys);
    }

    /**
     * 将多个 HyperLogLog 合并为一个 HyperLogLog
     *
     * @param destination destination
     * @param sourceKeys  sourceKeys
     */
    public Long hyperLogLogUnion(String destination, Collection<String> sourceKeys) {
        return redisTemplate.opsForHyperLogLog().union(destination, sourceKeys.toArray(new String[]{}));
    }

    /**
     * 删除给定的密钥
     *
     * @param key key
     */
    public void hyperLogLogDelete(String key) {
        redisTemplate.opsForHyperLogLog().delete(key);
    }

    // --------------------------------------------------------------------------------------------
    // Geo 操作
    // --------------------------------------------------------------------------------------------

    /**
     * 将指定成员名的点添加到键上
     *
     * @param key    key
     * @param x      x
     * @param y      y
     * @param member member
     */
    public Long geoAdd(String key, Number x, Number y, Object member) {
        return redisTemplate.opsForGeo().add(key, new Point(x.doubleValue(), y.doubleValue()), member);
    }

    /**
     * 将指定成员名的点添加到键上
     *
     * @param key        key
     * @param pointValue pointValue
     */
    public Long geoAdd(String key, PointValue pointValue) {
        return redisTemplate.opsForGeo().add(key, getGeoLocation(pointValue));
    }

    /**
     * 将指定成员名的点添加到键上
     *
     * @param key         key
     * @param pointValues pointValues
     */
    public Long geoAdd(String key, Collection<PointValue> pointValues) {
        Map<Object, Point> memberCoordinateMap = new HashMap<>(pointValues.size());
        pointValues.forEach(pointValue -> {
            RedisGeoCommands.GeoLocation<Object> geoLocation = getGeoLocation(pointValue);
            memberCoordinateMap.put(geoLocation.getName(), geoLocation.getPoint());
        });
        return redisTemplate.opsForGeo().add(key, memberCoordinateMap);
    }

    /**
     * 将指定成员名的点添加到键上
     *
     * @param key         key
     * @param pointValues pointValues
     */
    public Long geoAdd(String key, PointValue[] pointValues) {
        Map<Object, Point> memberCoordinateMap = new HashMap<>(pointValues.length);
        for (PointValue pointValue : pointValues) {
            RedisGeoCommands.GeoLocation<Object> geoLocation = getGeoLocation(pointValue);
            memberCoordinateMap.put(geoLocation.getName(), geoLocation.getPoint());
        }
        return redisTemplate.opsForGeo().add(key, memberCoordinateMap);
    }

    /**
     * 返回两个给定位置之间的距离
     *
     * @param key     key
     * @param member1 member1
     * @param member2 member2
     */
    public Distance geoDistance(String key, Object member1, Object member2) {
        return redisTemplate.opsForGeo().distance(key, member1, member2);
    }

    /**
     * 获取一个或多个成员位置的GeoHash表示
     *
     * @param key     key
     * @param members members
     */
    public List<String> geoHash(String key, Object... members) {
        return redisTemplate.opsForGeo().hash(key, members);
    }

    /**
     * 获取一个或多个成员位置的GeoHash表示
     *
     * @param key     key
     * @param members members
     */
    public List<String> geoHash(String key, Collection<Object> members) {
        return redisTemplate.opsForGeo().hash(key, members.toArray());
    }

    /**
     * 获取一个或多个成员的位置的点表示
     *
     * @param key     key
     * @param members members
     */
    public List<Point> geoPosition(String key, Object... members) {
        return redisTemplate.opsForGeo().position(key, members);
    }

    /**
     * 获取一个或多个成员的位置的点表示
     *
     * @param key     key
     * @param members members
     */
    public List<Point> geoPosition(String key, Collection<Object> members) {
        return redisTemplate.opsForGeo().position(key, members.toArray());
    }

    // --------------------------------------------------------------------------------------------
    // execute 操作
    // --------------------------------------------------------------------------------------------

    public <T> T execute(RedisScript<T> script, List<String> keys, Object... args) {
        return redisTemplate.execute(script, keys, args);
    }

    // 事务，批量处理
    // TODO geo 地理位置操作

    // TODO redisTemplate.execute()

    // TODO redisTemplate.executePipelined()

    // --------------------------------------------------------------------------------------------
    // 其它 操作
    // --------------------------------------------------------------------------------------------

    public RedisInfo getInfo() {
        return RedisUtils.getRedisInfo(redisTemplate);
    }

    public RedisDataSourceStatus getStatus() {
        return RedisUtils.getRedisDataSourceStatus(redisTemplate);
    }

    // --------------------------------------------------------------------------------------------
    //  内部函数
    // --------------------------------------------------------------------------------------------

    /**
     * 迭代Cursor数据
     */
    private <T> void cursorForeach(Cursor<T> cursor, ScanCallback<T> callback) throws IOException {
        while (cursor.hasNext()) {
            T entry = cursor.next();
            boolean needBreak;
            try {
                needBreak = callback.next(entry);
            } catch (Exception e) {
                needBreak = true;
                log.warn(e.getMessage(), e);
            }
            if (needBreak) {
                cursor.close();
                break;
            }
        }
    }

    private <T> List<ZSetValue> zSetToList(Set<ZSetOperations.TypedTuple<T>> set) {
        if (set == null) {
            return new ArrayList<>();
        }
        List<ZSetValue> result = new ArrayList<>(set.size());
        for (ZSetOperations.TypedTuple<T> typedTuple : set) {
            result.add(new ZSetValue(typedTuple.getValue(), typedTuple.getScore()));
        }
        return result;
    }

    private RedisZSetCommands.Range newRange(Object minValue, boolean equalsMin, Object maxValue, boolean equalsMax) {
        RedisZSetCommands.Range range = RedisZSetCommands.Range.range();
        if (minValue != null) {
            if (equalsMin) {
                range.gte(minValue);
            } else {
                range.gt(minValue);
            }
        }
        if (maxValue != null) {
            if (equalsMax) {
                range.lte(maxValue);
            } else {
                range.lt(maxValue);
            }
        }
        return range;
    }

    private RedisGeoCommands.GeoLocation<Object> getGeoLocation(PointValue pointValue) {
        Assert.notNull(pointValue, "PointValue不能为空");
        return new RedisGeoCommands.GeoLocation<>(pointValue.getValue(), new Point(pointValue.getX(), pointValue.getY()));
    }

    private ZSetOperations.TypedTuple<Object> getZSetValue(ZSetValue zSetValue) {
        Assert.notNull(zSetValue, "ZSetValue不能为空");
        return new DefaultTypedTuple<>(zSetValue.getValue(), zSetValue.getScore());
    }
}
