package bloomfiter;

import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Arrays;


public class JredisBloomFilter implements  Serializable{
    private final int numHashFunctions;
    private final Strategy strategy;
    private final AbstractBitmaps bitmaps ;

    private JredisBloomFilter(AbstractBitmaps bitmaps, int numHashFunctions,  Strategy strategy) {

        Assert.isTrue(numHashFunctions> 0,String.format("numHashFunctions (%s) must be > 0",numHashFunctions));
        Assert.isTrue(numHashFunctions <= 255,String.format("numHashFunctions (%s) must be <= 255",numHashFunctions));
        if(numHashFunctions <= 0){
            throw new IllegalArgumentException(String.format("numHashFunctions (%s) must be > 0",numHashFunctions));
        }
        if(numHashFunctions > 255){
            throw new IllegalArgumentException(String.format("numHashFunctions (%s) must be <= 255",numHashFunctions));
        }
        if(bitmaps == null){
            throw new IllegalArgumentException("AbstractBitmaps 不能为空");
        }
        if(strategy == null){
            throw new IllegalArgumentException("Strategy 不能为空");
        }
        this.bitmaps = bitmaps;
        this.numHashFunctions = numHashFunctions;
        this.strategy = strategy;
    }


    public static JredisBloomFilter create( int expectedInsertions, double fpp) {
        return create( (long)expectedInsertions, fpp,new JredisBloomFilterStrategies(),null);
    }

    public static  JredisBloomFilter create( long expectedInsertions, double fpp, Strategy strategy,AbstractBitmaps bitmaps) {

        if(expectedInsertions < 0){
            throw new IllegalArgumentException(String.format("expectedInsertions (%s) must be > 0",expectedInsertions));
        }
        if(fpp >= 1.0){
            throw new IllegalArgumentException(String.format("fpp (%s) must be < 1",fpp));
        }
        if(fpp <=  0.0){
            throw new IllegalArgumentException(String.format("fpp (%s) must be > 0",fpp));
        }
        if(bitmaps == null){
            throw new IllegalArgumentException("AbstractBitmaps 不能为空");
        }
        if(strategy == null){
            throw new IllegalArgumentException("Strategy 不能为空");
        }
        if (expectedInsertions == 0L) {
            expectedInsertions = 1L;
        }

        long numBits = optimalNumOfBits(expectedInsertions, fpp);
        int numHashFunctions = optimalNumOfHashFunctions(expectedInsertions, numBits);

        try {
            if(bitmaps != null){
                bitmaps.setBits(numBits);
            }
            return new JredisBloomFilter(bitmaps, numHashFunctions,  strategy);
        } catch (IllegalArgumentException var10) {
            throw new IllegalArgumentException("Could not create JredisBloomFilter of " + numBits + " bits", var10);
        }
    }


    public boolean contain(String object) {
        return this.strategy.contain(object,  this.numHashFunctions, this.bitmaps);
    }


    public boolean put(String object) {
        return this.strategy.put(object,  this.numHashFunctions, this.bitmaps);
    }
    public void resetBitmap(){
        this.bitmaps.reset();
    }

    public AbstractBitmaps getBitmaps() {
        return bitmaps;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.numHashFunctions,  this.strategy, this.bitmaps});
    }



    private static int optimalNumOfHashFunctions(long n, long m) {
        return Math.max(1, (int)Math.round((double)m / (double)n * Math.log(2.0D)));
    }

    private static long optimalNumOfBits(long n, double p) {
        if (p == 0.0D) {
            p = 4.9E-324D;
        }
        return (long)((double)(-n) * Math.log(p) / (Math.log(2.0D) * Math.log(2.0D)));
    }


}
