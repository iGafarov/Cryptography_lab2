import org.apache.mahout.math.jet.stat.Gamma;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class LinerComplexityTest {

    private static final int M = 500;
    private static final int K = 6;
    private static final double[] PI_VALUES = {0.010417, 0.03125, 0.12500, 0.5000, 0.25000, 0.06250, 0.020833};

    @Test
    public void testLinerComplexity() {
        List<String> lfsrList = TestUtils.getLfsrNumbers();
        for (String lfsr : lfsrList) {
            int n = lfsr.length();
            int N = n / M;
            double[] vValues = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};

            double chi = 0.0;
            double pValue;
            double complexity;
            double T, mu;
            mu = M / 2.0 + (9.0 + ((M % 2 == 0 ? 1.0 : -1.0)) / 36.0) - ((M / 3.0 + 2.0 / 9.0) / Math.pow(2.0, M));
            List<String> blocks = getBlocks(lfsr);
            for (String block : blocks) {
                complexity = linearComplexity(block);
//                System.out.println(complexity);
                T = (M % 2 == 0 ? 1.0 : -1.0) * (complexity - mu) + 2.0 / 9.0;
                if (T <= -2.5) vValues[0]++;
                else if (T <= -1.5) vValues[1]++;
                else if (T <= -0.5) vValues[2]++;
                else if (T <= 0.5) vValues[3]++;
                else if (T <= 1.5) vValues[4]++;
                else if (T <= 2.5) vValues[5]++;
                else if (T > 2.5) vValues[6]++;
            }

            for (int i = 0; i < 6; i++) {
                chi += Math.pow(vValues[i] - N * PI_VALUES[i], 2.0) / (N * PI_VALUES[i]);
            }
            pValue = Gamma.incompleteGammaComplement(K / 2, chi / 2.0);
            print(chi, pValue, vValues);
        }
    }

    private void print(double chi, double pValue, double[] vValues) {
        System.out.println("\n================RESULT================");
        System.out.println("v_table: " + Arrays.toString(vValues));
        System.out.println("chi value: " + chi);
        System.out.println("p value: " + pValue);
    }

    private List<String> getBlocks(String lfsr) {
        List<String> blocks = new ArrayList<>();
        int blockCount = lfsr.length() / M;
        for (int i = 0; i < blockCount; ++i) {
            String block = lfsr.substring(i * M, (i + 1) * M);
            blocks.add(block);
        }
        return blocks;
    }

    public static double linearComplexity(String block) {
        int[] seq = new int[block.length()];
        char[] blockArray = block.toCharArray();
        for (int i = 0; i < blockArray.length; ++i) {
            seq[i] = Integer.parseInt(String.valueOf(blockArray[i]));
        }
        Berlekamp_Massey berlekamp_massey = new Berlekamp_Massey(seq);
        while (berlekamp_massey.getN() < seq.length) {
            berlekamp_massey.iteration();
        }
        berlekamp_massey.correctL();
        berlekamp_massey.print();
//        for (int i = 0; i < berlekamp_massey.getC().length; i++) {
//            System.out.println("index: " + i + " elem: " + berlekamp_massey.getC()[i]);
//        }
//        System.out.println("L: " + berlekamp_massey.getL());
        return berlekamp_massey.getL();
//        int L = 0;
//        char[] blockElems = block.toCharArray();
//        if (blockElems[0] == '1') {
//            ++L;
//        }
//        for (int i = 1; i < blockElems.length; ++i) {
//            if (blockElems[i] == '1' && blockElems[i-1] == '0') {
//                ++L;
//            }
//        }
//        return L;
    }
}
