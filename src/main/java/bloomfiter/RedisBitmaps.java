package bloomfiter;

import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.List;

public class RedisBitmaps  extends AbstractBitmaps<RedisTemplate> {

    private RedisTemplate<String,Object> redisTemplate;

    @Override
    void setBitValue(String key, long offset, boolean value) {
        redisTemplate.opsForValue().setBit(key, offset, value);
    }

    @Override
    Boolean getBitValue(String key, long offset) {
        return redisTemplate.opsForValue().getBit(key, offset);
    }

    @Override
    Long incr(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    @Override
    String getString(String key) {
        return (String)redisTemplate.opsForValue().get(key);
    }

    @Override
    long bitCount(String key) {
        RedisSerializer redisSerializer = redisTemplate.getKeySerializer();
        byte[] keyBytes = key.getBytes();
        if(redisSerializer != null){
            keyBytes = redisSerializer.serialize(key);
        }
        final byte[] keys = keyBytes;
        RedisCallback<Long> action = con -> con.bitCount(keys) ;
        return redisTemplate.execute(action);
    }

    @Override
    void deleteForKeys(List<String> keys) {
        redisTemplate.delete(keys);
    }

    @Override
    void set(String key, String value) {
        redisTemplate.opsForValue().set(key,value);
    }

    @Override
    public void setRedis(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
