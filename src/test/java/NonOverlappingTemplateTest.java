
import org.apache.mahout.math.jet.stat.Gamma;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class NonOverlappingTemplateTest {

    private static final String PATH_TO_FILE = "src/test/resources/templates_3_bits.txt";
    private static final int m = 3;
    private static final int N = 8;
    private static final int TEMPLATE_LENGTH = 3;

    @Test
    public void testNonOverlappingTemplateMatching() {
        List<String> lfsr = TestUtils.getLfsrNumbers();
        List<String> templates = getNumbersFromFile();
        for (String lfsrValue : lfsr) {
            System.out.println("\t\t============== Result ================");
            nonOverlappingTemplateMatchingAlgorithm(lfsrValue, templates);
        }
    }

    private String getTenNumbersValue(String number) {
        StringBuffer sb = new StringBuffer(number);
        while (number.length() < TEMPLATE_LENGTH) {
            sb.insert(0, "0");
            number = sb.toString();
        }
        return number;
    }

    private List<String> getNumbersFromFile() {
        List<String> resultList = new ArrayList<>();
        try {
            FileReader reader = new FileReader(new File(PATH_TO_FILE));
            BufferedReader bufferedReader = new BufferedReader(reader);
            resultList = bufferedReader.lines().toList();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    private void nonOverlappingTemplateMatchingAlgorithm(String lfsrValue, List<String> templates) {
        List<String> blocks = getBlocks(lfsrValue);
        int M = blocks.get(0).length();
        for (String template : templates) {
            template = getTenNumbersValue(template);
            List<Integer> wValues = new ArrayList<>();
            for (String block : blocks) {
                int startPosition = 0;
                int endPosition = TEMPLATE_LENGTH;
                int wValue = 0;
                while (endPosition < block.length()) {
                    if (block.substring(startPosition, endPosition).equals(template)) {
                        ++wValue;
                        endPosition += TEMPLATE_LENGTH - 1;
                        startPosition += TEMPLATE_LENGTH - 1;
                    } else {
                        ++endPosition;
                        ++startPosition;
                    }
                }
                wValues.add(wValue);
            }
            double mu = (M - m + 1)/Math.pow(2, m);
            double sigmaSquared = M * ((1 / Math.pow(2, m)) - ((2 * m - 1) / Math.pow(2, 2 * m)));
            double chiSquaredNumerator = 0.0;
            for (Integer wValue : wValues) {
                chiSquaredNumerator += Math.pow((wValue - mu),2);
            }
            double chiSquared = chiSquaredNumerator/sigmaSquared;
            double pValue = Gamma.incompleteGamma((N / 2), chiSquared / 2);
            printResult(template, mu, sigmaSquared, chiSquared, pValue);
        }
    }

    private List<String> getBlocks(String lfsrValue) {
        List<String> result = new ArrayList<>();
        int blockLength = lfsrValue.length() / 8;
        for (int i = 0; i < N; ++i) {
            String block = lfsrValue.substring(i * blockLength, (i+1) * blockLength);
            result.add(block);
        }
        return result;
    }

    private void printResult(String template, double mu, double sigmaSquared, double chiSquared, double pValue) {
        System.out.println("==============================================================");
        System.out.println("template: " + template);
        System.out.println("mu value: " + mu);
        System.out.println("sigmaSquared value: " + sigmaSquared);
        System.out.println("chiSquared value: " + chiSquared);
        System.out.println("pValue value: " + pValue);
    }

}
