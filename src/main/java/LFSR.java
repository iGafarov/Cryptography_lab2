import java.util.ArrayList;
import java.util.List;

public class LFSR {

    public static List<Integer> firstTaskLFSR(int iterationCount) {
        int period = 0;
        int currentIteration = 0;
        boolean flag = false;
        int startPosition = Constants.FIRST_TASK_START_POSITION;
        List<Integer> resultNumber = new ArrayList<>();
        resultNumber.add((startPosition & 1));
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
        /*System.out.println("\nFirst task LFSR (x^6 + x^3 + 1): \n");
        resultNumber.forEach(System.out::print);*/
        return resultNumber;
    }

    public static List<Integer> secondTaskLFSR(int iterationCount) {
        int period = 0;
        int currentIteration = 0;
        boolean flag = false;
        int startPosition = Constants.SECOND_TASK_START_POSITION;
        List<Integer> resultNumber = new ArrayList<>();
        resultNumber.add((startPosition & 1));
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
       /* System.out.println("\nSecond task LFSR (x^6 + x^2 + x + 1): \n");
        resultNumber.forEach(System.out::print);*/
        return resultNumber;
    }

    public static List<Integer> thirdTaskLFSR(int iterationCount) {
        int period = 0;
        int currentIteration = 0;
        boolean flag = false;
        int startPosition = Constants.THIRD_TASK_START_POSITION;
        List<Integer> resultNumber = new ArrayList<>();
        resultNumber.add((startPosition & 1));
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
        /*System.out.println("\nThird task LFSR (x^6 + x + 1): \n");
        resultNumber.forEach(System.out::print);*/
        return resultNumber;
    }

    public static void main(String[] args) {
        //firstTaskLFSR(1000000);
        //secondTaskLFSR(1000000);
        //secondTaskLFSR(1000000);
    }
}
