import function.Culc.*;
import function.W.*;

public class BinaryNet {
    private double train_data[][];
    private int tlabel[];
    private int Mlayer[];
    private int epoch;
    private double eta;
    
    private final double N = 100000;
    private W wf = new RandW(-99, 199, 100);
    private Unit Input[];
    private Unit Middle[][];
    private Unit Out[];

    public BinaryNet(double train_data[][], int tlabel[], int Mlayer[], int epoch, double eta) {
        this.train_data = train_data;
        this.tlabel = tlabel;
        this.Mlayer = Mlayer;
        this.epoch = epoch;
        this.eta = eta;

        int i, j;
        Input = new Unit[train_data[0].length + 1];
        for ( i = 0; i < Input.length; i++) {
            Input[i] = new Unit(Mlayer[0] - 1, new Signal(), wf);
        }

        Middle = new Unit[Mlayer.length][];
        for ( i = 0; i < Mlayer.length - 1; i++) {
            Middle[i] = new Unit[Mlayer[i]];
            for ( j = 0; j < Mlayer[i] - 1; j++) {
                Middle[i][j] = new Unit(Mlayer[i + 1] - 1, new Sigmoid(), wf);
            }
            Middle[i][j] = new Unit(Mlayer[i + 1], new Signal(), wf);
        }
        
        Middle[Mlayer.length - 1] = new Unit[Mlayer[Mlayer.length - 1]];
        for ( i = 0; i < Mlayer[Mlayer.length - 1] - 1; i++) {
            Middle[Mlayer.length - 1][i] = new Unit(1, new Sigmoid(), wf);
        }
        Middle[Mlayer.length - 1][i] = new Unit(1, new Signal(), wf);

        Out = new Unit[1];
        Out[0] = new Unit(new Sigmoid());
    }

    public String train() {
        double z;
        
        for (int ite = 0; ite < epoch; ite++) {
            for (int n = 0; n < train_data.length; n++) {
                inputData(n);
                z = propagation();
                backPropagation(z, tlabel[n]);
            }
            printResult();
            System.out.println("epoch: " + ite);
        }
        System.out.println("---result---");
        printResult();

        return makeWString();
    }

    private double propagation() {
        forWard(Input, Middle[0]);
        for (int i = 0; i < Mlayer.length - 1; i++) {
            forWard(Middle[i], Middle[i + 1]);
        }
        forWard(Middle[Mlayer.length - 1], Out);

        return Out[0].Culc();
    }

    private void forWard(Unit ford[], Unit crnt[]) {
        double u[] = new double[ford[0].getWlength()];

        for (int i = 0; i < u.length; i++) {
            for (int j = 0; j < ford.length; j++) {
                u[i] += ford[j].signal(i);
            }
            crnt[i].setU(u[i]);
        }
    }

    private void backPropagation(double z, int y) {
        Out[0].setDelta(z - y);

        backWard(Middle[Mlayer.length - 1], Out);
        for (int i = Middle.length - 1; 0 < i; i--) {
            backWard(Middle[i - 1], Middle[i]);
        }
        backWard(Input, Middle[0]);
    }

    private void backWard(Unit ford[], Unit crnt[]) {
        double d[] = new double[ford.length];

        for (int i = 0; i < ford.length; i++) {
            for (int j = 0; j < ford[i].getWlength(); j++) {
                d[i] += crnt[j].getDelta() * ford[i].getW(j);
            }
            d[i] *= ford[i].CuclV();
            ford[i].setDelta(d[i]);
        }

        for (int i = 0; i < ford.length; i++) {
            for (int j = 0; j < ford[i].getWlength(); j++) {
                ford[i].wUpdata(eta, ford[i].Culc(), crnt[j].getDelta(), j);
            }
        }
    }

    private void inputData(int n) {
        for (int i = 0; i < Input.length - 1; i++) {
            Input[i].setU(train_data[n][i]);
        }
    }

    private void printResult() {
        String res;

        System.out.print("z = \t");
        for (int i = 0; i < train_data.length; i++) {
            inputData(i);
            res = String.valueOf(((int)(propagation() * N))/ N);
            System.out.print(res + "\t");
        }
    }

    private String makeWString() {
        String str = "";

        str += "---Input layer---\n";
        for (int i = 0; i < Input.length; i++) {
            str += "i("+i+") → x: " + Input[i].getU() + "\t";
            for (int j = 0; j < Input[i].getWlength(); j++) {
                str += "w(" + i + j + "): " + Input[i].getW(j) + "\t";
            }
            str += "\n";
        }

        str += "---Middle layer---\n";
        for (int i = 0; i < Mlayer.length; i++) {
            str += "Middle(第" + i + "層): \n";
            for (int j = 0; j < Mlayer[i] ; j++) {
                str += "i("+j+") → x: " + Middle[i][j].getU() + "\t";
                for (int c = 0; c < Middle[i][j].getWlength(); c++) {
                    str += "w(" + i + j + "): " + Middle[i][j].getW(c) + "\t";
                }
                str += "\n";
            }
        }
        return str;
    }
}