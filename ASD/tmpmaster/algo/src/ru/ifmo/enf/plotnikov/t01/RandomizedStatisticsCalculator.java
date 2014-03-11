package ru.ifmo.enf.plotnikov.t01;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomizedStatisticsCalculator<T extends Comparable<T>> implements StatisticsCalculator<T> {
    private List<T> array;
    private Random rnd = new Random();

    public RandomizedStatisticsCalculator(ArrayList<T> array) {
        this.array = (List<T>) array.clone();
    }

    private T partitioning (int l, int r, int k) {
        T median = r-l==0 ? array.get(l) : array.get(rnd.nextInt(r-l)+l);
        //T median = array.get(l + (r-l)/2);
        int i = l;
        int j = r;

        while (i<=j) {
            while (median.compareTo(array.get(i)) > 0) {
                i++;
            }
            while (median.compareTo(array.get(j)) < 0) {
                j--;
            }
            if (i<=j) {
                Collections.swap(array, i, j);
                i++;
                j--;
            }
        }

        if (i == k){
            return array.get(i);
        } else if(j == k) {
            return array.get(j);
        } else if (k > j && i <= r) {
            return partitioning(i, r, k);
        } else if (l <= j){
            return partitioning(l, j, k);
        } else {
            return null;
        }
    }

    @Override
    public T getStatistics(int k) {
        return partitioning(0, array.size() - 1, k-1);
    }
}
