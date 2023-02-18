package function.W;

import java.util.Random;

public class RandW implements W{
    private int min;
    private int max;
    private double N;
    private Random random = new Random();

    public RandW(int min, int max, double N) {
        this.min = min;
        this.max = max;
        this.N = N;
    }

    @Override
    public double init() {
        int tmp = (int)(random.nextInt(max) + min);
        return tmp/N;
    }
}
