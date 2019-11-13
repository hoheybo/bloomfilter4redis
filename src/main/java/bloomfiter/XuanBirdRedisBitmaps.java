package bloomfiter;

import cn.com.libertymutual.xuanbird.redis.util.RedisUtils;

import java.util.List;

/**
 * 依赖RedisUtils 对数据库进行访问
 */
public class XuanBirdRedisBitmaps extends AbstractBitmaps<RedisUtils>{
    private RedisUtils redisUtils;

    public XuanBirdRedisBitmaps(){

    }

    /**
     *
     * @param bits  期望的位数据的大小
     */
    public XuanBirdRedisBitmaps(long bits) {
        this.setBits(bits);
    }

    /**
     *
     * @param key
     * @param offset  偏移量
     * @param value
     */
    @Override
    public void setBitValue(String key, long offset, boolean value) {
        this.redisUtils.setBitValue(key,offset,value);
    }

    @Override
    public Long incr(String key) {
        return this.redisUtils.incr(key);
    }

    @Override
    String getString(String key) {
        return this.redisUtils.getString(key);
    }

    @Override
    Boolean getBitValue(String key, long offset) {
        return this.redisUtils.getBitValue(key,offset);
    }

    @Override
    long bitCount(String key) {
        return this.redisUtils.bitCount(key);
    }

    @Override
    void deleteForKeys(List<String> keys) {
        this.redisUtils.deleteForKeys(keys);
    }

    @Override
    void set(String key, String value) {
        this.redisUtils.set(key,value);
    }

    @Override
    public void setRedis(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }
}

