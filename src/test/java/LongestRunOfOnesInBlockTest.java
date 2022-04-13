import org.apache.mahout.math.jet.stat.Gamma;
import org.junit.Test;

import java.util.*;

public class LongestRunOfOnesInBlockTest {

    private static final int K_3 = 6;
    private static final int K_2 = 5;
    private static final int M_2 = 128;
    private static final int M_3 = 10000;
    private static final int[] V_VALUES_ETALON_1 = {10, 11, 12, 13, 14, 15, 16};
    private static final double[] PI_VALUES_1 = {0.0882, 0.2092, 0.2483, 0.1933, 0.1208, 0.0675, 0.0727};
    private static final int[] V_VALUES_ETALON_2 = {4, 5, 6, 7, 8, 9};
    private static final double[] PI_VALUES_2 = {0.1174, 0.2430, 0.2493, 0.1752, 0.1027, 0.1124};

    private static final int K = 6;
    private static final int M = M_3;
    private static final double[] PI_VALUES = PI_VALUES_1;
    private static final int[] V_VALUES_ETALON = V_VALUES_ETALON_1;

    @Test
    public void testLongestRunOfOnesInBlock() {
        List<String> lfsrList = TestUtils.getLfsrNumbers();
        for (String lfsr : lfsrList) {
            List<String> blocks = getBlocks(lfsr);
            int N = lfsr.length() / M;
            List<Integer> onesBlocksCounts = getBlocksOnesCount(blocks);

            double[] vValues = new double[K + 1];
            getVvalues(onesBlocksCounts, vValues);
            double chi = calculateChiSquared(vValues, N);
            double pValue = Gamma.incompleteGammaComplement(K / 2, chi / 2);
            print(chi, pValue, vValues);
        }
    }

    private void print(double chi, double pValue, double[] vValues) {
        System.out.println("\n================RESULT================");
        System.out.println("v_table: " + Arrays.toString(vValues));
        System.out.println("chi value: " + chi);
        System.out.println("p value: " + pValue);
    }

    private double calculateChiSquared(double[] vValues, int N) {
        double result = 0.0;
        for (int i = 0; i < vValues.length; ++i) {
            result += (Math.pow((vValues[i] - (N * PI_VALUES[i])), 2)) / (N * PI_VALUES[i]);
        }
        return result;
    }

    private void getVvalues(List<Integer> onesBlocksCount, double[] vValues) {
        double firstVvalue = 0.0;
        double lastVvalue = 0.0;
        int max = Collections.max(onesBlocksCount);
        int min = Collections.min(onesBlocksCount);
        for (Integer i = min; i <= max; ++i) {
            if (i <= V_VALUES_ETALON[0]) {
                if (onesBlocksCount.contains(i)) {
                    for (Integer blockOnesCount : onesBlocksCount) {
                        if (i.equals(blockOnesCount)) {
                            ++firstVvalue;
                        }
                    }
                }
            } else if (i >= V_VALUES_ETALON[K]){
                if (onesBlocksCount.contains(i)) {
                    for (Integer blockOnesCount : onesBlocksCount) {
                        if (i.equals(blockOnesCount)) {
                            ++lastVvalue;
                        }
                    }
                }
            }
            else {
                int repeatCount = 0;
                for (int j = 1; j < K; ++j) {
                    if (i == V_VALUES_ETALON[j]) {
                        if (onesBlocksCount.contains(i)) {
                            for (Integer blockOnesCount : onesBlocksCount) {
                                if (i.equals(blockOnesCount)) {
                                    ++repeatCount;
                                }
                            }
                        }
                        vValues[j] = repeatCount;
                    }
                }

            }
        }
        vValues[0] = firstVvalue;
        vValues[K] = lastVvalue;
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

    private List<Integer> getBlocksOnesCount(List<String> blocks) {
        List<Integer> onesValues = new ArrayList<>();
        for (String block : blocks) {
            int repeatOnesCount = 0;
            int maxLengthOnes = 0;
            for (char number : block.toCharArray()) {
                if (number == '1') {
                    ++repeatOnesCount;
                    if (repeatOnesCount > maxLengthOnes) {
                        maxLengthOnes = repeatOnesCount;
                    }
                } else {
                    repeatOnesCount = 0;
                }
            }
            onesValues.add(maxLengthOnes);
        }
        return onesValues;
    }
}
