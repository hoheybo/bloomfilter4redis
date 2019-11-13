package bloomfiter;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.google.common.primitives.Longs;

/**
 * 用于计算位的策略
 */
public class JredisBloomFilterStrategies implements Strategy{


    /**
     * 生成位运算的具体算法 根据不同的策略，可以有不同的实现方式
     * @param obj
     * @param numHashFunctions
     * @param bitmaps
     * @return
     */
    public long[]  generateOffsets(String obj, int numHashFunctions,AbstractBitmaps bitmaps){
        long bitSize = bitmaps.bitSize();
        byte[] bytes = Hashing.murmur3_128().hashString(obj, Charsets.UTF_8).asBytes();
        long hash1 = lowerEight(bytes);
        long hash2 = upperEight(bytes);
        boolean bitsChanged = false;
        long combinedHash = hash1;
        long[] offsets = new long[numHashFunctions];
        for (int i = 0; i < numHashFunctions; i++) {
            offsets[i] = (combinedHash & Long.MAX_VALUE) % bitSize;
            combinedHash += hash2;
        }
        return offsets;
    }
    /**
     * 获取低位的算法
     */
    private long lowerEight(byte[] bytes) {
        return Longs.fromBytes(bytes[7], bytes[6], bytes[5], bytes[4], bytes[3], bytes[2], bytes[1], bytes[0]);
    }

    /**
     * 获取高位的算法
     * @param bytes
     * @return
     */
    private long upperEight(byte[] bytes) {
        return Longs.fromBytes(bytes[15], bytes[14], bytes[13], bytes[12], bytes[11], bytes[10], bytes[9], bytes[8]);
    }
}
