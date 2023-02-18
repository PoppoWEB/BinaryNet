package function.Culc;

public class Signal implements Func {
    @Override
    public double Culc(double u) {
        return u;
    }

    @Override @Deprecated
    public double CulcV(double u) {
        return 1;
    }
}
