import java.util.ArrayList;
import java.util.List;

public class LFSR {

    public static List<Integer> firstTaskLFSR(int iterationCount) {
        int period = 0;
        int currentIteration = 0;
        boolean flag = false;
        int startPosition = Constants.FIRST_TASK_START_POSITION;
        List<Integer> resultNumber = new ArrayList<>();
//        resultNumber.add((startPosition & 1));
        while (currentIteration < iterationCount) {
            int nextNumber = (((startPosition >> 6) ^ (startPosition >> 3) ^ startPosition) & 1);
            startPosition = ((startPosition >> 1) | (nextNumber << 5));
            resultNumber.add((startPosition & 1));
            ++currentIteration;
            if (startPosition == Constants.FIRST_TASK_START_POSITION && flag == false) {
                flag = true;
                period = currentIteration;
            }
        }
        System.out.println("\nFirst task LFSR (x^6 + x^3 + 1): \n");
        resultNumber.forEach(System.out::print);
        return resultNumber;
    }

    public static List<Integer> secondTaskLFSR(int iterationCount) {
        int period = 0;
        int currentIteration = 0;
        boolean flag = false;
        int startPosition = Constants.SECOND_TASK_START_POSITION;
        List<Integer> resultNumber = new ArrayList<>();
//        resultNumber.add((startPosition & 1));
        while (currentIteration < iterationCount) {
            int nextNumber = (((startPosition >> 6) ^ (startPosition >> 2) ^ (startPosition >> 1) ^ startPosition) & 1);
            startPosition = ((startPosition >> 1) | (nextNumber << 5));
            resultNumber.add((startPosition & 1));
            ++currentIteration;
            if (startPosition == Constants.SECOND_TASK_START_POSITION && flag == false) {
                flag = true;
                period = currentIteration;
            }
        }
        System.out.println("\nSecond task LFSR (x^6 + x^2 + x + 1): \n");
        resultNumber.forEach(System.out::print);
        return resultNumber;
    }

    public static List<Integer> thirdTaskLFSR(int iterationCount) {
        int period = 0;
        int currentIteration = 0;
        boolean flag = false;
        int startPosition = Constants.THIRD_TASK_START_POSITION;
        List<Integer> resultNumber = new ArrayList<>();
//        resultNumber.add((startPosition & 1));
        while (currentIteration < iterationCount) {
            int nextNumber = (((startPosition >> 6) ^ (startPosition >> 1) ^ startPosition) & 1);
            startPosition = ((startPosition >> 1) | (nextNumber << 5));
            resultNumber.add((startPosition & 1));
            ++currentIteration;
            if (startPosition == Constants.THIRD_TASK_START_POSITION && flag == false) {
                flag = true;
                period = currentIteration;
            }
        }
        System.out.println("\nThird task LFSR (x^6 + x + 1): \n");
        resultNumber.forEach(System.out::print);
        return resultNumber;
    }

    //              1110001010110100001100100111110
    //              1110001010110100001100100111110
    //              1110001010110100001100100111110
    //              1110001010110100001100100111110
    // periodString 1110001
    // bufferString

    private static Integer calculatePeriod(List<Integer> list) {
        List<String> sequence = getStringFromIntegerList(list);
        List<String> periodString = new ArrayList<>();
        List<String> bufferString = new ArrayList<>();
        int period = 0;
        for (int i = 0; i < sequence.size(); ++i) {
            if (i == 0) {
                periodString.add(sequence.get(i));
                ++period;
            }
            else if (periodString.size() == 1) {
                if (periodString.get(0).equals(sequence.get(i))) {
                    bufferString.add(sequence.get(i));
                }
                else {
                    if (i > 1) {
                        bufferString.add(sequence.get(i));
                        periodString.addAll(bufferString);
                        period += bufferString.size();
                        bufferString.clear();
                    } else if (sequence.get(0).equals("1")) {
                        periodString.add("0");
                        ++period;
                    }
                    else {
                        periodString.add("1");
                        ++period;
                    }
                }
            }
            else {
                int k = i;
                boolean flag = true;
                int periodStringIndex = 0;
                while (flag) {
                    if (k >= sequence.size()) break;
                    if (periodString.size() > bufferString.size()) {
                        if (periodString.get(periodStringIndex).equals(sequence.get(k))) {
                            bufferString.add(sequence.get(k));
                            ++k;
                            ++periodStringIndex;
                        } else {
                            if (bufferString.size() > 0) {
                                periodString.add(bufferString.get(0));
                                bufferString.clear();
                            } else {
                                periodString.add(sequence.get(k));
                            }
                            ++period;
                            flag = false;
                        }
                    } else {
                        flag = false;
                    }
                }
            }
        }
        return period;
    }

    private static List<String> getStringFromIntegerList(List<Integer> integerList) {
        List<String> resultList = new ArrayList<>();
        for(Integer integer : integerList) {
            resultList.add(integer.toString());
        }
        return resultList;
    }

    public static void main(String[] args) {
//        System.out.println(calculatePeriod(firstTaskLFSR(200)));
//        System.out.println(calculatePeriod(secondTaskLFSR(1000)));
        System.out.println(calculatePeriod(thirdTaskLFSR(100000)));
        //firstTaskLFSR(1000000);
        //secondTaskLFSR(1000000);
        //secondTaskLFSR(1000000);
    }
}
