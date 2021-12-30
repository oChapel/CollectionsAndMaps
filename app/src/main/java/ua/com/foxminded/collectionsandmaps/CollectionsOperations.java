package ua.com.foxminded.collectionsandmaps;

import java.util.List;
import java.util.Random;

public class CollectionsOperations {

    private static boolean isStopped;

    private static int getRandomNumber() {
        Random random = new Random();
        return random.nextInt(1000);
    }

    private static void fillNewList(int size, List<Integer> array) {
        for (int i = 0; i < size; i++) {
            array.add(i, getRandomNumber());
        }
    }

    public static long calcAddingToStart(int size, List<Integer> array){
        long start = System.currentTimeMillis();

        fillNewList(size, array);
        for (int i = 0; i < size; i++) {
            if (i % 10 == 0 & isStopped()) {
                break;
            } else {
                array.add(i, getRandomNumber());
            }
        }

        return System.currentTimeMillis() - start;
    }

    public static long calcAddingToMiddle(int size, List<Integer> array) {
        long start = System.currentTimeMillis();

        fillNewList(size, array);
        int halfSize = array.size()/2;
        for (int i = halfSize; i < halfSize + size; i++) {
            if (i % 10 == 0 & isStopped()) {
                break;
            } else {
                array.add(i, getRandomNumber());
            }
        }

        return System.currentTimeMillis() - start;
    }

    public static long calcAddingToEnd(int size, List<Integer> array) {
        long start = System.currentTimeMillis();

        fillNewList(size, array);
        for (int i = array.size(); i < size * 2; i++) {
            if (i % 10 == 0 & isStopped()) {
                break;
            } else {
                array.add(i, getRandomNumber());
            }
        }

        return System.currentTimeMillis() - start;
    }

    public static long searchByValue(int size, List<Integer> array) {
        long start = System.currentTimeMillis();

        fillNewList(size, array);
        int number = getRandomNumber();
        for (int i = 0; i < array.size(); i++) {
            if (i % 10 == 0 & isStopped() | number == array.get(i)) {
                break;
            }
        }

        return System.currentTimeMillis() - start;
    }

    public static long calcRemovingFromBeginning(int size, List<Integer> array) {
        long start = System.currentTimeMillis();

        fillNewList(2*size, array);
        for (int i = size - 1; i >= 0; i--) {
            if (i % 10 == 0 & isStopped()) {
                break;
            } else {
                array.remove(i);
            }
        }

        return System.currentTimeMillis() - start;
    }

    public static long calcRemovingFromMiddle(int size, List<Integer> array) {
        long start = System.currentTimeMillis();

        fillNewList(2*size, array);
        int a = (array.size() + size)/2;
        int b = a - size;
        for (int i = a; i > b; i--) {
            if (i % 10 == 0 & isStopped()) {
                break;
            } else {
                array.remove(i);
            }
        }

        return System.currentTimeMillis() - start;
    }

    public static long calcRemovingFromEnd(int size, List<Integer> array) {
        long start = System.currentTimeMillis();

        fillNewList(size + 1000, array);
        int sizeMinusOne = array.size() - 1;
        for (int i = sizeMinusOne; i > sizeMinusOne - size; i--) {
            if (i % 10 == 0 & isStopped()) {
                break;
            } else {
                array.remove(i);
            }
        }

        return System.currentTimeMillis() - start;
    }

    public static void setStopped(boolean stopped) {
        isStopped = stopped;
    }

    public static boolean isStopped() {
        return isStopped;
    }
}
