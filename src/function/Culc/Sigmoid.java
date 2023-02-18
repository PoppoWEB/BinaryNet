package function.Culc;

public class Sigmoid implements Func {
    @Override
    public double Culc(double u) {
        return 1/(1 + Math.exp(-u));
    }

    @Override
    public double CulcV(double u) {
        return 0.1*(1 - Culc(u)) * Culc(u);
    }
}
