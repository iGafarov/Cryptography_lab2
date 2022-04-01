import java.util.ArrayList;
import java.util.List;

public class TestUtils {

    //private static final int ITERATION_COUNT = 6272;
    private static final int ITERATION_COUNT = 750000;

    public static List<String> getLfsrNumbers() {
        List<String> lfsrValues = new ArrayList<>();
        List<Integer> first = LFSR.firstTaskLFSR(ITERATION_COUNT);
        List<Integer> second = LFSR.secondTaskLFSR(ITERATION_COUNT);
        List<Integer> third = LFSR.thirdTaskLFSR(ITERATION_COUNT);
        lfsrValues.add(getStringFromIntegerList(first));
        lfsrValues.add(getStringFromIntegerList(second));
        lfsrValues.add(getStringFromIntegerList(third));
        return lfsrValues;
    }

    private static String getStringFromIntegerList(List<Integer> integerList) {
        String string = "";
        for(Integer integer : integerList) {
            string = string.concat(String.valueOf(integer));
        }
        return string;
    }
}
