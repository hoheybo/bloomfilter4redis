package bloomfiter;

import java.io.Serializable;

public interface Strategy extends Serializable {
     /**
      *
      * @param obj  插入的可以
      * @param numHashFunctions  有多少个hash分段函数，由filter提供
      * @param bitmaps  用于具体存储数据
      * @return
      */

     public  default boolean put(String obj, int numHashFunctions, AbstractBitmaps bitmaps) {
          boolean bitsChanged = false;
          long[] offsets = this.generateOffsets(obj,numHashFunctions,bitmaps);
          bitsChanged = bitmaps.set(offsets);
          bitmaps.ensureCapacityInternal();//自动扩容
          return bitsChanged;

     }


     public default boolean contain(String obj, int numHashFunctions, AbstractBitmaps bitmaps) {
          long[] offsets = this.generateOffsets(obj,numHashFunctions,bitmaps);
          return bitmaps.get(offsets);
     }

     /**
      * 生成位运算的具体算法
      * @param obj
      * @param numHashFunctions
      * @param bitmaps
      * @return
      */
     public long[]  generateOffsets(String obj, int numHashFunctions, AbstractBitmaps bitmaps);

}
