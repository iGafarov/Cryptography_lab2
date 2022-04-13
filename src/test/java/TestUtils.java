import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TestUtils {

    //private static final int ITERATION_COUNT = 6272;
    private static final int ITERATION_COUNT = 1000000;
    private static final String PATH_TO_E = "src/test/resources/data.e";
    private static final String PATH_TO_PI = "src/test/resources/data.pi";

    public static List<String> getLfsrNumbers() {
        List<String> lfsrValues = new ArrayList<>();
//        List<Integer> first = LFSR.firstTaskLFSR(ITERATION_COUNT);
//        List<Integer> second = LFSR.secondTaskLFSR(ITERATION_COUNT);
//        List<Integer> third = LFSR.thirdTaskLFSR(ITERATION_COUNT);
        lfsrValues.add(getEPFiles(ITERATION_COUNT, PATH_TO_E));
        lfsrValues.add(getEPFiles(ITERATION_COUNT, PATH_TO_PI));
//        lfsrValues.add(getStringFromIntegerList(first));
//        lfsrValues.add(getStringFromIntegerList(second));
//        lfsrValues.add(getStringFromIntegerList(third));
        return lfsrValues;
    }

    public static String getEPFiles(int count, String pathToFile) {
        try {
            List<String> lines = Files.lines(Path.of(pathToFile), StandardCharsets.UTF_8)
                    .collect(Collectors.toList());
            String fullLine = "";
            StringBuilder stringBuilder = new StringBuilder();
            for (String line : lines) {
                stringBuilder.append(line);
            }
            fullLine = stringBuilder.toString().replace(" ", "").substring(0, count);
            return fullLine;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getStringFromIntegerList(List<Integer> integerList) {
        String string = "";
        for(Integer integer : integerList) {
            string = string.concat(String.valueOf(integer));
        }
        return string;
    }
}
