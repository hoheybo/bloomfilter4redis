package bloomfiter;

public class JredisBloomFilterBuilder {
    private  Strategy strategy;
    private  AbstractBitmaps bitmaps ;
    private int expectedInsertions;
    private double fpp;

    public static JredisBloomFilterBuilder create() {
        return new JredisBloomFilterBuilder();
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public JredisBloomFilterBuilder setStrategy(Strategy strategy) {
        this.strategy = strategy;
        return this;
    }

    public AbstractBitmaps getBitmaps() {
        return bitmaps;
    }

    public JredisBloomFilterBuilder setBitmaps(AbstractBitmaps bitmaps) {
        this.bitmaps = bitmaps;
        return this;
    }

    public int getExpectedInsertions() {
        return expectedInsertions;
    }

    public JredisBloomFilterBuilder setExpectedInsertions(int expectedInsertions) {
        this.expectedInsertions = expectedInsertions;
        return this;
    }




    public double getFpp() {
        return fpp;
    }

    public JredisBloomFilterBuilder setFpp(double fpp) {
        this.fpp = fpp;
        return this;
    }

    public JredisBloomFilter build(){
        return JredisBloomFilter.create(expectedInsertions,fpp,strategy,bitmaps);
    }
}
