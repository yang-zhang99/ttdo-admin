package com.me.redis.safe;

import com.me.redis.RedisHelper;
import com.me.redis.convertor.ApplicationContextHelper;

/**
 * 提供一个便捷的方式安全的切换 Redis db
 *
 * Note：必须在程序启动好之后才能使用此类
 *
 */
public class SafeRedisHelper {

    private static RedisHelper redisHelper;

    static {
        ApplicationContextHelper.asyncStaticSetter(RedisHelper.class, SafeRedisHelper.class, "redisHelper");
    }

    /**
     * 无返回值 Redis 操作
     *
     * @param db Redis db
     * @param executor Redis 操作
     */
    public static void execute(int db, ExecuteNoneResult executor) {
        try {
            redisHelper.setCurrentDatabase(db);
            executor.accept();
        } finally {
            redisHelper.clearCurrentDatabase();
        }
    }

    /**
     * 有返回值 Redis 操作
     *
     * @param db Redis db
     * @param executor Redis 操作
     * @param <T> 返回类型
     * @return Redis 操作返回值
     */
    public static <T> T execute(int db, ExecuteWithResult<T> executor) {
        try {
            redisHelper.setCurrentDatabase(db);
            return executor.get();
        } finally {
            redisHelper.clearCurrentDatabase();
        }
    }

    /**
     * 无返回值 Redis 操作
     *
     * @param db Redis db
     * @param redisHelper 一般在程序启动期间，可自行传入 redisHelper
     * @param executor Redis 操作
     */
    public static void execute(int db, RedisHelper redisHelper, ExecuteNoneResult executor) {
        try {
            redisHelper.setCurrentDatabase(db);
            executor.accept();
        } finally {
            redisHelper.clearCurrentDatabase();
        }
    }

    /**
     * 有返回值 Redis 操作
     *
     * @param db Redis db
     * @param redisHelper 一般在程序启动期间，可自行传入 redisHelper
     * @param executor Redis 操作
     * @param <T> 返回类型
     * @return Redis 操作返回值
     */
    public static <T> T execute(int db, RedisHelper redisHelper, ExecuteWithResult<T> executor) {
        try {
            redisHelper.setCurrentDatabase(db);
            return executor.get();
        } finally {
            redisHelper.clearCurrentDatabase();
        }
    }

    public static void setRedisHelper(RedisHelper redisHelper) {
        SafeRedisHelper.redisHelper = redisHelper;
    }
}
