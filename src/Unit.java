

import function.Culc.Func;
import function.W.W;

public class Unit {
    private double u = -1;
    private double w[];
    private double delta;
    private Func f;
    private W wf;

    public Unit(Func f) {
        this.f = f;
    }

    public Unit(int c, Func f, W wf) {
        this.w = new double[c];
        this.f = f;
        this.wf = wf;

        setRand();
    }

    public double getU() {
        return u;
    }

    public void setU(double u) {
        this.u = u;
    }

    public void setW(double[] w) {
        this.w = w;
    }

    public void setW(int c, double w) {
        this.w[c] = w;
    }

    public double getW(int c) {
        if (w == null) return 1;
        return w[c];
    }

    public int getWlength() {
        return w.length;
    }

    public double Culc() {
        return f.Culc(u);
    }

    public double CuclV() {
        return f.CulcV(u);
    }

    public double signal(int c) {
        return f.Culc(u) * w[c];
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }

    public double getDelta() {
        return delta;
    }

    public void wUpdata(double eta, double u, double delta, int c) {
        w[c] -= eta * delta * u;
    }

    private void setRand() {
        for (int i = 0; i < w.length; i++) {
            w[i] = wf.init();
        }
    }
}