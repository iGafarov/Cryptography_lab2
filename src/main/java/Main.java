package main.java;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final int FIRST_TASK_START_POSITION = 0b1001001;
    private static final int SECOND_TASK_START_POSITION = 0b1000111;
    private static final int THIRD_TASK_START_POSITION = 0b1000011;

    public static void firstTaskLFSR(int iterationCount) {
        int currentIteration = 0;
        int startPosition = FIRST_TASK_START_POSITION;
        List<Integer> resultNumber = new ArrayList<>();
        resultNumber.add((startPosition & 1));
        while (currentIteration < iterationCount) {
            int nextNumber = (((startPosition >> 6) ^ (startPosition >> 3) ^ startPosition) & 1);
            startPosition = ((startPosition >> 1) | (nextNumber << 5));
            resultNumber.add((startPosition & 1));
            ++currentIteration;
        }
        resultNumber.forEach(System.out::print);
    }

    public static void secondTaskLFSR(int iterationCount) {
        int currentIteration = 0;
        int startPosition = SECOND_TASK_START_POSITION;
        List<Integer> resultNumber = new ArrayList<>();
        resultNumber.add((startPosition & 1));
        while (currentIteration < iterationCount) {
            int nextNumber = (((startPosition >> 6) ^ (startPosition >> 2) ^ (startPosition >> 1) ^ startPosition) & 1);
            startPosition = ((startPosition >> 1) | (nextNumber << 5));
            resultNumber.add((startPosition & 1));
            ++currentIteration;
        }
        resultNumber.forEach(System.out::print);
    }

    public static void thirdTaskLFSR(int iterationCount) {
        int currentIteration = 0;
        int startPosition = THIRD_TASK_START_POSITION;
        List<Integer> resultNumber = new ArrayList<>();
        resultNumber.add((startPosition & 1));
        while (currentIteration < iterationCount) {
            int nextNumber = (((startPosition >> 6) ^ (startPosition >> 1) ^ startPosition) & 1);
            startPosition = ((startPosition >> 1) | (nextNumber << 5));
            resultNumber.add((startPosition & 1));
            ++currentIteration;
        }
        resultNumber.forEach(System.out::print);
    }

    public static void main(String[] args) {
        //firstTaskLFSR(1000000);
        //secondTaskLFSR(1000000);
        //secondTaskLFSR(1000000);
    }
}
