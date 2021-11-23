package ua.com.foxminded.collectionsandmaps;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class CollectionsOperations {

    public static int getRandomNumber() {
        Random random = new Random();
        return random.nextInt(1000);
    }

    public static List<Integer> fillNewList(int size, List<Integer> array) {
        for (int i = 0; i < size; i++) {
            array.add(i, getRandomNumber());
        }
        return array;
    }

    public static long calcAddingToStart(int size, List<Integer> array){
        long start = System.currentTimeMillis();

        fillNewList(size, array);
        for (int i = 0; i < size; i++) {
            array.add(i, getRandomNumber());
        }

        return System.currentTimeMillis() - start;
    }

    public static long calcAddingToMiddle(int size, List<Integer> array) {
        long start = System.currentTimeMillis();

        fillNewList(size, array);

        List<Integer> tmp = new ArrayList<>();
        if (array instanceof LinkedList) {
            tmp = new LinkedList<>();
        } else if (array instanceof CopyOnWriteArrayList){
            tmp = new CopyOnWriteArrayList<>();
        }

        for (int i = 0; i < size; i++) {
            tmp.add(i, getRandomNumber());
        }

        array.addAll(array.size()/2, tmp);

        return System.currentTimeMillis() - start;
    }

    public static long calcAddingToEnd(int size, List<Integer> array) {
        long start = System.currentTimeMillis();

        fillNewList(size, array);

        List<Integer> tmp = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            tmp.add(i, getRandomNumber());
        }

        array.addAll(array.size(), tmp);

        return System.currentTimeMillis() - start;
    }

    public static int searchByValue(List<Integer> array) {
        long start = System.currentTimeMillis();

        int index = array.lastIndexOf(getRandomNumber());

        return index;
    }

}
