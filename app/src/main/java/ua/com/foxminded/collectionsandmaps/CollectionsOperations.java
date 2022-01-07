package ua.com.foxminded.collectionsandmaps;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class CollectionsOperations {

    private static final Random random = new Random();

    public static Items measureTime(int operation, int listType, int size) {
        float time;
        Items item = null;
        if (operation == R.string.addToStart) {
            if (listType == R.string.arrayList) {
                time = calcAddingToStart(size, new ArrayList<>());
                item = new Items(operation, listType, String.valueOf(time), false);
            } else if (listType == R.string.linkedList) {
                time = calcAddingToStart(size, new LinkedList<>());
                item = new Items(operation, listType, String.valueOf(time), false);
            } else if (listType == R.string.copyOnWriterList) {
                time = calcAddingToStart(size, new CopyOnWriteArrayList<>());
                item = new Items(operation, listType, String.valueOf(time), false);
            }
        } else if (operation == R.string.addToMiddle) {
            if (listType == R.string.arrayList) {
                time = calcAddingToMiddle(size, new ArrayList<>());
                item = new Items(operation, listType, String.valueOf(time), false);
            } else if (listType == R.string.linkedList) {
                time = calcAddingToMiddle(size, new LinkedList<>());
                item = new Items(operation, listType, String.valueOf(time), false);
            } else if (listType == R.string.copyOnWriterList) {
                time = calcAddingToMiddle(size, new CopyOnWriteArrayList<>());
                item = new Items(operation, listType, String.valueOf(time), false);
            }
        } else if (operation == R.string.addToEnd) {
            if (listType == R.string.arrayList) {
                time = calcAddingToEnd(size, new ArrayList<>());
                item = new Items(operation, listType, String.valueOf(time), false);
            } else if (listType == R.string.linkedList) {
                time = calcAddingToEnd(size, new LinkedList<>());
                item = new Items(operation, listType, String.valueOf(time), false);
            } else if (listType == R.string.copyOnWriterList) {
                time = calcAddingToEnd(size, new CopyOnWriteArrayList<>());
                item = new Items(operation, listType, String.valueOf(time), false);
            }
        } else if (operation == R.string.search) {
            if (listType == R.string.arrayList) {
                time = searchByValue(size, new ArrayList<>());
                item = new Items(operation, listType, String.valueOf(time), false);
            } else if (listType == R.string.linkedList) {
                time = searchByValue(size, new LinkedList<>());
                item = new Items(operation, listType, String.valueOf(time), false);
            } else if (listType == R.string.copyOnWriterList) {
                time = searchByValue(size, new CopyOnWriteArrayList<>());
                item = new Items(operation, listType, String.valueOf(time), false);
            }
        } else if (operation == R.string.remFromStart) {
            if (listType == R.string.arrayList) {
                time = calcRemovingFromBeginning(size, new ArrayList<>());
                item = new Items(operation, listType, String.valueOf(time), false);
            } else if (listType == R.string.linkedList) {
                time = calcRemovingFromBeginning(size, new LinkedList<>());
                item = new Items(operation, listType, String.valueOf(time), false);
            } else if (listType == R.string.copyOnWriterList) {
                time = calcRemovingFromBeginning(size, new CopyOnWriteArrayList<>());
                item = new Items(operation, listType, String.valueOf(time), false);
            }
        } else if (operation == R.string.remFromMiddle) {
            if (listType == R.string.arrayList) {
                time = calcRemovingFromMiddle(size, new ArrayList<>());
                item = new Items(operation, listType, String.valueOf(time), false);
            } else if (listType == R.string.linkedList) {
                time = calcRemovingFromMiddle(size, new LinkedList<>());
                item = new Items(operation, listType, String.valueOf(time), false);
            } else if (listType == R.string.copyOnWriterList) {
                time = calcRemovingFromMiddle(size, new CopyOnWriteArrayList<>());
                item = new Items(operation, listType, String.valueOf(time), false);
            }
        } else if (operation == R.string.remFromEnd) {
            if (listType == R.string.arrayList) {
                time = calcRemovingFromEnd(size, new ArrayList<>());
                item = new Items(operation, listType, String.valueOf(time), false);
            } else if (listType == R.string.linkedList) {
                time = calcRemovingFromEnd(size, new LinkedList<>());
                item = new Items(operation, listType, String.valueOf(time), false);
            } else if (listType == R.string.copyOnWriterList) {
                time = calcRemovingFromEnd(size, new CopyOnWriteArrayList<>());
                item = new Items(operation, listType, String.valueOf(time), false);
            }
        }
        return item;
    }

    private static void fillNewList(int size, List<Integer> array) {
        array.addAll(Collections.nCopies(size, random.nextInt(size)));
    }

    public static float calcAddingToStart(int size, List<Integer> array) {

        fillNewList(size, array);
        long start = System.nanoTime();
        array.add(0, random.nextInt(size));
        long end = System.nanoTime() - start;

        return (float)end / 1000000;
    }

    public static float calcAddingToMiddle(int size, List<Integer> array) {

        fillNewList(size, array);
        long start = System.nanoTime();
        array.add(array.size() / 2, random.nextInt(size));
        long end = System.nanoTime() - start;

        return (float)end / 1000000;
    }

    public static float calcAddingToEnd(int size, List<Integer> array) {

        fillNewList(size, array);
        long start = System.nanoTime();
        array.add(array.size(), random.nextInt(size));
        long end = System.nanoTime() - start;

        return (float)end / 1000000;
    }

    public static float searchByValue(int size, List<Integer> array) {
        // TODO: fix random
        fillNewList(size, array);
        long start = System.nanoTime();
        array.indexOf(random.nextInt(size));
        long end = System.nanoTime() - start;

        return (float) end / 1000000;
    }

    public static float calcRemovingFromBeginning(int size, List<Integer> array) {

        fillNewList(size, array);
        long start = System.nanoTime();
        array.remove(0);
        long end = System.nanoTime() - start;

        return (float) end / 1000000;
    }

    public static float calcRemovingFromMiddle(int size, List<Integer> array) {

        fillNewList(size, array);
        long start = System.nanoTime();
        array.remove(array.size() / 2);
        long end = System.nanoTime() - start;

        return (float)end / 1000000;
    }

    public static float calcRemovingFromEnd(int size, List<Integer> array) {

        fillNewList(size, array);
        long start = System.nanoTime();
        array.remove(array.size() - 1);
        long end = System.nanoTime() - start;

        return (float)end / 1000000;
    }

}