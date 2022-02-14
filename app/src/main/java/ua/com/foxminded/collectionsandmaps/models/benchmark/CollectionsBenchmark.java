package ua.com.foxminded.collectionsandmaps.models.benchmark;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import ua.com.foxminded.collectionsandmaps.R;

public class CollectionsBenchmark implements Benchmark {

    private final Random randomNumber = new Random();

    public CollectionsBenchmark() {
    }

    @Override
    public Items measureTime(Items item, int size) {
        final List<Integer> list;
        if (item.name == R.string.arrayList) {
            list = new ArrayList<>(Collections.nCopies(size, 0));
        } else if (item.name == R.string.linkedList) {
            list = new LinkedList<>(Collections.nCopies(size, 0));
        } else if (item.name == R.string.copyOnWriterList) {
            list = new CopyOnWriteArrayList<>(Collections.nCopies(size, 0));
        } else {
            throw new RuntimeException("Unknown type: " + item.name);
        }
        list.add(randomNumber.nextInt(size), 1);
        float time;
        if (item.operation == R.string.addToStart) {
            time = calcAddingToStart(size, list);
        } else if (item.operation == R.string.addToMiddle) {
            time = calcAddingToMiddle(size, list);
        } else if (item.operation == R.string.addToEnd) {
            time = calcAddingToEnd(size, list);
        } else if (item.operation == R.string.searchByValue) {
            time = searchByValue(list);
        } else if (item.operation == R.string.remFromStart) {
            time = calcRemovingFromBeginning(list);
        } else if (item.operation == R.string.remFromMiddle) {
            time = calcRemovingFromMiddle(list);
        } else if (item.operation == R.string.remFromEnd) {
            time = calcRemovingFromEnd(list);
        } else {
            throw new RuntimeException("Unknown operation: " + item.operation);
        }
        return new Items(item.operation, item.name, String.valueOf(time), false);
    }

    @Override
    public List<Items> generateCollectionItems(boolean visibilityFlag) {
        final int[] idArrOperations = new int[]{R.string.addToStart, R.string.addToMiddle,
                R.string.addToEnd, R.string.searchByValue, R.string.remFromStart,
                R.string.remFromMiddle, R.string.remFromEnd};
        final int[] idArrType = new int[]{R.string.arrayList, R.string.linkedList, R.string.copyOnWriterList};
        final List<Items> items = new ArrayList<>(idArrOperations.length * idArrType.length);
        for (int operation : idArrOperations) {
            for (int listType : idArrType) {
                items.add(new Items(operation, listType, "N/A"/*naMS*/, visibilityFlag));
            }
        }
        return items;
    }

    @Override
    public int getSpanCount() {
        return 3;
    }

    private float calcAddingToStart(int size, List<Integer> array) {
        final long start = System.nanoTime();
        array.add(0, randomNumber.nextInt(size));

        return (System.nanoTime() - start) / 1000000F;
    }

    private float calcAddingToMiddle(int size, List<Integer> array) {
        final long start = System.nanoTime();
        array.add(array.size() / 2, randomNumber.nextInt(size));

        return (System.nanoTime() - start) / 1000000F;
    }

    private float calcAddingToEnd(int size, List<Integer> array) {
        final long start = System.nanoTime();
        array.add(array.size(), randomNumber.nextInt(size));

        return (System.nanoTime() - start) / 1000000F;
    }

    private float searchByValue(List<Integer> array) {
        final long start = System.nanoTime();
        array.indexOf(1);

        return (System.nanoTime() - start) / 1000000F;
    }

    private float calcRemovingFromBeginning(List<Integer> array) {
        final long start = System.nanoTime();
        array.remove(0);

        return (System.nanoTime() - start) / 1000000F;
    }

    private float calcRemovingFromMiddle(List<Integer> array) {
        final long start = System.nanoTime();
        array.remove(array.size() / 2);

        return (System.nanoTime() - start) / 1000000F;
    }

   private float calcRemovingFromEnd(List<Integer> array) {
       final long start = System.nanoTime();
        array.remove(array.size() - 1);

       return (System.nanoTime() - start) / 1000000F;
    }
}
