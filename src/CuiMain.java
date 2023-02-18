public class CuiMain {
    public static void main(String[] args) {
        double train_data[][] = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
        int tlabel[] = {0, 1, 1, 0};
        int layer[] = {3};
        int evoked = 100000;
        double ε = 0.1;
        
        BinaryNet net = new BinaryNet(train_data, tlabel, layer, evoked, ε);
        System.out.println(net.train());
    }    
}