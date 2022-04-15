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
            int complexity;
            double T, mu;
            mu = M / 2.0 + (9.0 + (M % 2 == 0 ? 1.0 : -1.0)) / 36.0 - (M / 3.0 + 2.0 / 9.0) / Math.pow(2.0, M);
            List<String> blocks = getBlocks(lfsr);
            for (String block : blocks) {
                complexity = linearComplexity(block, M);
                T = (M % 2 == 0 ? 1.0 : -1.0) * (complexity - mu) + 2.0 / 9.0;
                if (T <= -2.5) vValues[0]++;
                else if (T <= -1.5) vValues[1]++;
                else if (T <= -0.5) vValues[2]++;
                else if (T <= 0.5) vValues[3]++;
                else if (T <= 1.5) vValues[4]++;
                else if (T <= 2.5) vValues[5]++;
                else vValues[6]++;
            }

            for (int i = 0; i < 7; i++) {
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

    public static int linearComplexity(String block, int M) {
        char[] a = block.toCharArray();
        int N_ = 0, L = 0, m = -1, d = 0;
        int[] B_, C, P, T;
        B_ = new int[M];
        C = new int[M];
        P = new int[M];
        T = new int[M];
        for (int i = 0; i < M; i++) {
            B_[i] = 0;
            C[i] = 0;
            T[i] = 0;
            P[i] = 0;
        }
        C[0] = 1;
        B_[0] = 1;
        while (N_ < M) {
            d = b2i(a[N_]);
            for (int i = 1; i <= L; i++)
                d += C[i] * b2i(a[N_ - i]);
            d = d % 2;
            if (d == 1) {
                for (int i = 0; i < M; i++) {
                    T[i] = C[i];
                    P[i] = 0;
                }
                for (int j = 0; j < M; j++)
                    if (B_[j] == 1)
                        P[j + N_ - m] = 1;
                for (int i = 0; i < M; i++)
                    C[i] = (C[i] + P[i]) % 2;
                if (L <= N_ / 2) {
                    L = N_ + 1 - L;
                    m = N_;
                    for (int i = 0; i < M; i++)
                        B_[i] = T[i];
                }
            }
            N_++;
        }
        return L;
    }

    private static int b2i(char b) {
        return b == '1' ? 1 : 0;
    }
}
