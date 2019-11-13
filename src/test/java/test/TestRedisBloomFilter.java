package test;

import cn.com.libertymutual.xuanbird.platform.bloomfiter.JredisBloomFilter;
import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.google.common.hash.Hashing;
import org.junit.Test;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestRedisBloomFilter {
    private static final int TOTAL = 10000;

    private static final double FPP = 0.0005;



    @Test
    public void test() {
        JredisBloomFilter redisBloomFilter = JredisBloomFilter.create(TOTAL, FPP);
        redisBloomFilter.resetBitmap();

        BloomFilter<String> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), TOTAL, FPP);
        IntStream.range(0, /* 3* */TOTAL)
                .boxed()
                .map(i -> Hashing.md5().hashInt(i).toString())
                .collect(Collectors.toList()).forEach(s -> {
                    redisBloomFilter.put(s);
                    bloomFilter.put(s);
        });
        String str1 = Hashing.md5().hashInt(99999).toString();
        String str2 = Hashing.md5().hashInt(9999).toString();
        String str3 = "abcdefghijklmnopqrstuvwxyz123456";
        System.out.println(redisBloomFilter.contain(str1) + ":"
                + bloomFilter.mightContain(str1));
        System.out.println(redisBloomFilter.contain(str2) + ":"
                + bloomFilter.mightContain(str2));
        System.out.println(redisBloomFilter.contain(str3) + ":"
                + bloomFilter.mightContain(str3));

    }
}
