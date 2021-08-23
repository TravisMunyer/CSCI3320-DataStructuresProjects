/****************************************************
 *  Program Title: Pair Sum                         *
 *  Author: Travis Munyer                           *
 *  Class: CSCI3320, Summer 2021                    *
 *  Assignment #1                                   *
 ****************************************************/

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Scanner;

/**
 * Contains the main method for CSCI3320 programming assignment 1.
 */
public class PAssignOne{
    /**
     * The main method.
     * @param args Not used.
     */
    public static void main(String[] args) {
        Integer selectAlgorithm;
        int kValue;
        Integer[] randArray;

        while (true){
            selectAlgorithm = getUserAlgorithmSelection();
            randArray = getUserArraySelection();
            kValue = getKValue();
            if (selectAlgorithm.equals(1))
                quadraticAlgorithm(randArray, kValue);
            else if (selectAlgorithm.equals(2))
                logarithmicAlgorithm(randArray, kValue);
        }
    }

    /**
     * The binary search algorithm. This implementation is from the lecture slides.
     * @param a The array to search.
     * @param x The value to search for.
     * @param low Leftmost index of the array a.
     * @param high Rightmost index of the array a.
     * @return If x occurs, the index where x occurs in a. If x is not found, return -1.
     */
    private static int binarySearch(Comparable[] a, Comparable x, int low, int high){
        int NOT_FOUND = -1;

        if (low > high)
            return NOT_FOUND;

        int mid = (low + high) / 2;

        if (a[mid].compareTo(x) < 0)
            return binarySearch(a, x, mid + 1, high);
        else if (a[mid].compareTo(x) > 0)
            return binarySearch(a, x, low, mid - 1);
        else
            return  mid;
    }

    /**
     * The O(n*log(n)) algorithm required by the assignment.
     * @param randArray An array of integers.
     * @param kValue An integer value. The function checks if two values in randArray add to this value.
     */
    private static void logarithmicAlgorithm(Integer[] randArray, int kValue){
        Integer requiredValue = null;
        int index, sumPartTwo = 02;
        boolean result = false;
        ExecutionTimer timer = new ExecutionTimer();
        System.out.println("Running the O(NLogN) algorithm...");
        timer.start();

        // The official java doc explains that Arrays.sort(object [] a) uses quicksort (Integer is an object) here:
        // https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/Arrays.html#sort(java.lang.Object%5B%5D).
        // The worst case for this implementation is O(nlog(n)).
        Arrays.sort(randArray);

        for (int i = 0; (i < randArray.length - 1) && !result; i++){
            requiredValue = kValue - randArray[i];
            index = binarySearch(randArray, requiredValue, 0, randArray.length-1);

            if (index != -1 && i != index){
                sumPartTwo = randArray[i];
                result = true;
            }
        }

        timer.end();
        if (requiredValue == null) requiredValue = 0;
        outputResult(result, timer.duration(), kValue, requiredValue, sumPartTwo);
    }

    /**
     * The O(n^2) algorithm required by the assignment.
     * @param randArray An array of integers.
     * @param kValue An integer value. The function checks if two values in randArray add to this value.
     */
    private static void quadraticAlgorithm(Integer[] randArray, int kValue){
        Integer total;
        int sumPartOne = 0, sumPartTwo = 0;
        boolean result = false;
        ExecutionTimer timer = new ExecutionTimer();

        System.out.println("Running the O(N^2) algorithm...");
        timer.start();

        if(randArray.length > 1) {
            for (Integer i = 0; i < randArray.length; i++)
                for (Integer j = 0; j < randArray.length; j++) {
                    if (!i.equals(j)) {
                        total = randArray[i] + randArray[j];
                        if (total.equals(kValue)) {
                            result = true;
                            sumPartOne = randArray[i];
                            sumPartTwo = randArray[j];
                            break;
                        }
                    }
                }
        }

        timer.end();
        outputResult(result, timer.duration(), kValue, sumPartOne, sumPartTwo);
    }

    /**
     * Outputs the algorithm results.
     * @param doesKExist Boolean existence flag for the K value.
     * @param nanoRuntime Runtime of algorithm.
     * @param kValue The selected k value.
     * @param sumPartOne One value in the array that adds to the k value.
     * @param sumPartTwo The other value that adds with sumPartOne to the K value.
     */
    private static void outputResult(boolean doesKExist, long nanoRuntime, int kValue, int sumPartOne, int sumPartTwo){
        if (doesKExist){
            System.out.println("k = " + kValue + ", (" + sumPartOne + " + " + sumPartTwo + ")");
            System.out.println("Yes, there are two numbers whose sum equals to K.");
        }
        else
            System.out.println("No, the algorithm could not find two numbers whose sum equals to K.");
        System.out.println("Execution time in nanoseconds: " + nanoRuntime);
    }

    /**
     * Gets and validates user input for the K value.
     * @return An integer input by the user.
     */
    private static int getKValue(){
        Integer kValue;
        String temp;
        Scanner scan = new Scanner(System.in);

        while (true){
            System.out.print("Enter the K value: ");
            temp = scan.next();
            kValue = tryParseInt(temp);

            if (kValue != null)
                return kValue;

            System.out.println("The K value must be an integer.");
        }
    }

    /**
     * A utility method to safely convert a string to integer, similar to the built-in c# function.
     * @param value String value for conversion attempt.
     * @return The converted integer or null if input is incorrect.
     */
    private static Integer tryParseInt(String value){
        try{
            return Integer.parseInt(value);
        }
        catch (NumberFormatException ex){
            return null;
        }
    }

    /**
     * Gets and validates input for array generation.
     * @return A validated array of random integers.
     */
    private static Integer[] getUserArraySelection(){
        Integer[] randArray = null;
        Integer size;
        String temp;
        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.print("Enter size of random array: ");
            temp = scan.next();
            size = tryParseInt(temp);

            if (size != null){
                randArray = generateArray(size);
            }

            if (randArray != null) {
                if (randArray.length < 50){
                    for (int i = 0; i < randArray.length; i++)
                        System.out.print(randArray[i] + " ");
                    System.out.println();
                }
                return randArray;
            }

            System.out.println("Array size must be greater than 0 and less than 2,147,483,647 " +
                    "(upper bound may be reduced based on host machine capability.)");
        }
    }

    /**
     * Gets and validates user algorithm selection input.
     * @return An integer corresponding to an algorithm option.
     */
    private static int getUserAlgorithmSelection(){
        Integer algoSelection;
        String temp;
        Scanner scan = new Scanner(System.in);

        while(true){
            System.out.println("1.  Quadratic algorithm ");
            System.out.println("2.  Logarithmic algorithm ");
            System.out.println("3.  Exit the program ");
            System.out.print("Choose an algorithm: ");

            temp = scan.next();
            algoSelection = tryParseInt(temp);

            if (algoSelection != null){
                if (algoSelection.equals(1)) return algoSelection;
                if (algoSelection.equals(2)) return algoSelection;
                if (algoSelection.equals(3)) System.exit(0);
            }

            System.out.println("Please select a shown option.");
        }
    }

    /**
     * Gets an array of random numbers.
     * @param size The size of the returned array.
     * @return An integer array of specified size that contains random values (value range: -9999 to 9999).
     */
    private static Integer[] generateArray(Integer size){
        if(size <= 0)
            return null;

        // The lower bound of the method ThreadLocalRandom.current().nextInt() is
        // inclusive and the upper bound is exclusive.
        int lowerBound = -9999;
        int upperBound =  10000;
        Integer[] randArray = new Integer[size];

        for (int i = 0; i < size; i++){
            randArray[i] = ThreadLocalRandom.current().nextInt(lowerBound, upperBound);
        }

        return randArray;
    }
}
