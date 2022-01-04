package ua.com.foxminded.collectionsandmaps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class CollectionsOperations {

    public static Items measureTime(int position, int size) {
        long time;
        Items item = null;
        switch (position) {
            case 0:
                time = calcAddingToStart(size, new ArrayList<>());
                item = new Items(R.string.arrayList, String.valueOf(time), false);
                break;
            case 1:
                time = calcAddingToStart(size, new LinkedList<>());
                item = new Items(R.string.linkedList, String.valueOf(time), false);
                break;
            case 2:
                time = calcAddingToStart(size, new CopyOnWriteArrayList<>());
                item = new Items(R.string.copyOnWriterList, String.valueOf(time), false);
                break;
            case 3:
                time = calcAddingToMiddle(size, new ArrayList<>());
                item = new Items(R.string.arrayList, String.valueOf(time), false);
                break;
            case 4:
                time = calcAddingToMiddle(size, new LinkedList<>());
                item = new Items(R.string.linkedList, String.valueOf(time), false);
                break;
            case 5:
                time = calcAddingToMiddle(size, new CopyOnWriteArrayList<>());
                item = new Items(R.string.copyOnWriterList, String.valueOf(time), false);
                break;
            case 6:
                time = calcAddingToEnd(size, new ArrayList<>());
                item = new Items(R.string.arrayList, String.valueOf(time), false);
                break;
            case 7:
                time = calcAddingToEnd(size, new LinkedList<>());
                item = new Items(R.string.linkedList, String.valueOf(time), false);
                break;
            case 8:
                time = calcAddingToEnd(size, new CopyOnWriteArrayList<>());
                item = new Items(R.string.copyOnWriterList, String.valueOf(time), false);
                break;
            case 9:
                time = searchByValue(size, new ArrayList<>());
                item = new Items(R.string.arrayList, String.valueOf(time), false);
                break;
            case 10:
                time = searchByValue(size, new LinkedList<>());
                item = new Items(R.string.linkedList, String.valueOf(time), false);
                break;
            case 11:
                time = searchByValue(size, new CopyOnWriteArrayList<>());
                item = new Items(R.string.copyOnWriterList, String.valueOf(time), false);
                break;
            case 12:
                time = calcRemovingFromBeginning(size, new ArrayList<>());
                item = new Items(R.string.arrayList, String.valueOf(time), false);
                break;
            case 13:
                time = calcRemovingFromBeginning(size, new LinkedList<>());
                item = new Items(R.string.linkedList, String.valueOf(time), false);
                break;
            case 14:
                time = calcRemovingFromBeginning(size, new CopyOnWriteArrayList<>());
                item = new Items(R.string.copyOnWriterList, String.valueOf(time), false);
                break;
            case 15:
                time = calcRemovingFromMiddle(size, new ArrayList<>());
                item = new Items(R.string.arrayList, String.valueOf(time), false);
                break;
            case 16:
                time = calcRemovingFromMiddle(size, new LinkedList<>());
                item = new Items(R.string.linkedList, String.valueOf(time), false);
                break;
            case 17:
                time = calcRemovingFromMiddle(size, new CopyOnWriteArrayList<>());
                item = new Items(R.string.copyOnWriterList, String.valueOf(time), false);
                break;
            case 18:
                time = calcRemovingFromEnd(size, new ArrayList<>());
                item = new Items(R.string.arrayList, String.valueOf(time), false);
                break;
            case 19:
                time = calcRemovingFromEnd(size, new LinkedList<>());
                item = new Items(R.string.linkedList, String.valueOf(time), false);
                break;
            case 20:
                time = calcRemovingFromEnd(size, new CopyOnWriteArrayList<>());
                item = new Items(R.string.copyOnWriterList, String.valueOf(time), false);
                break;

        }
        return item;
    }

    private static int getRandomNumber() {
        return new Random().nextInt(1000);
    }

    private static void fillNewList(int size, List<Integer> array) {
        array.addAll(Collections.nCopies(size, getRandomNumber()));
    }

    public static long calcAddingToStart(int size, List<Integer> array){
        long start = System.currentTimeMillis();

        fillNewList(size, array);
        array.add(0, getRandomNumber());

        return System.currentTimeMillis() - start;
    }

    public static long calcAddingToMiddle(int size, List<Integer> array) {
        long start = System.currentTimeMillis();

        fillNewList(size, array);
        array.add(array.size()/2, getRandomNumber());

        return System.currentTimeMillis() - start;
    }

    public static long calcAddingToEnd(int size, List<Integer> array) {
        long start = System.currentTimeMillis();

        fillNewList(size, array);
        array.add(array.size(), getRandomNumber());

        return System.currentTimeMillis() - start;
    }

    public static long searchByValue(int size, List<Integer> array) {
        long start = System.currentTimeMillis();

        fillNewList(size, array);
        array.indexOf(getRandomNumber());

        return System.currentTimeMillis() - start;
    }

    public static long calcRemovingFromBeginning(int size, List<Integer> array) {
        long start = System.currentTimeMillis();

        fillNewList(size, array);
        array.remove(0);

        return System.currentTimeMillis() - start;
    }

    public static long calcRemovingFromMiddle(int size, List<Integer> array) {
        long start = System.currentTimeMillis();

        fillNewList(size, array);
        array.remove(array.size()/2);

        return System.currentTimeMillis() - start;
    }

    public static long calcRemovingFromEnd(int size, List<Integer> array) {
        long start = System.currentTimeMillis();

        fillNewList(size, array);
        array.remove(array.size() - 1);

        return System.currentTimeMillis() - start;
    }

}