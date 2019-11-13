package test;

import bloomfiter.*;
import cn.com.libertymutual.xuanbird.redis.annotation.EnableXuanBirdRedis;
import cn.com.libertymutual.xuanbird.redis.util.RedisUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@Configuration
// @ComponentScan("cn.com.libertymutual.xuanbird")
@EnableXuanBirdRedis
// @SpringBootTest(classes = RedessionClientTest.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//指定配置文件路径
@ContextConfiguration(classes ={RedessionClientTest.class},initializers = {ConfigFileApplicationContextInitializer.class} )
// @PropertySource(value = "classpath:application.yml")
@EnableConfigurationProperties
public class RedessionClientTest {
    public static void main(String[] args) {
        SpringApplication.run(RedessionClientTest.class, args);
    }

   /* @Autowired
    private RedissonClient redissonClient;*/

    @Autowired
    private RedisUtils redisUtils;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private Environment environment;
    @Before
    public void setUp() throws Exception {

    }

    /**
     * 向"/test"地址发送请求，并打印返回结果
     * @throws Exception
     */
    @Test
    public void test1() throws Exception {
        System.out.println(environment.getProperty("spring.application.name"));
        System.out.println(environment.getProperty("spring.application.description"));
        System.out.println(redisUtils.bitCount("bits-2"));
        System.out.println(environment.getProperty("spring.application.description"));

        Strategy strategy = new JredisBloomFilterStrategies();
        // AbstractBitmaps bitmaps = new XuanBirdRedisBitmaps();
        // bitmaps.setRedis(redisUtils);
        AbstractBitmaps bitmaps = new RedisBitmaps();
        bitmaps.setRedis(redisTemplate);
        bitmaps.setBaseKey("bit");

        JredisBloomFilter filter = JredisBloomFilterBuilder
                .create()
                .setBitmaps(bitmaps)
                .setExpectedInsertions(5)
                .setFpp(0.0005)
                .setStrategy(strategy)
                .build();

        /*boolean result = filter.put("hello");
        boolean result1 = filter.put("hello1");
        boolean result2 = filter.put("hello2");
        boolean result3 = filter.put("hello3");*/
        boolean respnseResult = filter.contain("hello");
        boolean respnseResult2 = filter.contain("hello1");
        System.out.println(environment.getProperty("spring.application.name"));
        System.out.println(environment.getProperty("spring.application.name"));

        /*RBloomFilter<Object> bloomFilter = redissonClient.getBloomFilter("bloomFilter");

        bloomFilter.tryInit(10, 0.01);


        List<Integer> list = Arrays.asList(1, 11, 111);
       // bloomFilter.add(map);
        bloomFilter.add(list);
        bloomFilter.add(true);
        bloomFilter.add("bloomFilter");

        System.out.println("contains true=" + bloomFilter.contains(true));
        System.out.println("contains false=" + bloomFilter.contains(false));
        // System.out.println("contains map=" + bloomFilter.contains(map));
        System.out.println("contains list=" + bloomFilter.contains(list));

        System.out.println("size=" + bloomFilter.getSize());
        System.out.println("count=" + bloomFilter.count());*/
    }

    @Test
    public void testQuery(){
       // String obj = redisUtils.getString("test");
       // Object obj1 = redisUtils.get("test");
        // redisUtils.setString("hello","hello123456");
        String obj1 = redisUtils.getString("hello");
        // redisUtils.setBitValue("hello123",1,true);
        // boolean res = redisUtils.getBitValue("bit-0",27);
       // boolean res = redisUtils.getBitValue("hello",2);
       /* boolean res = redisUtils.getBitValue("bit-0",2);
        boolean res1 = redisUtils.getBitValue("bit-0",27);
        boolean res2 = redisUtils.getBitValue("bit-0",52);
        boolean res3 = redisUtils.getBitValue("bit-0",102);
        boolean res4 = redisUtils.getBitValue("bit-0",127);
        boolean res5 = redisUtils.getBitValue("bit-0",126);
        long bitC = redisUtils.bitCount("bit-0");




        redisUtils.setBitValue("he-0",1,true);
        redisUtils.setBitValue("he-0",2,true);
        redisUtils.setBitValue("he-0",3,true);
        redisUtils.setBitValue("he-0",4,true);
        redisUtils.setBitValue("he-0",5,true);
        redisUtils.setBitValue("he-0",6,true);*/
        boolean res = redisUtils.getBitValue("he-0",4);
        long bitC = redisUtils.bitCount("he-0");
        long bitC1 = redisUtils.bitCount("bit-0");
        System.out.println(obj1);
        System.out.println(res);
    }

}
