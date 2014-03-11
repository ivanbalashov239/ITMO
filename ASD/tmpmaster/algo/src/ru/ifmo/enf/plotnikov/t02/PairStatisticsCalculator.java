package ru.ifmo.enf.plotnikov.t02;

public interface PairStatisticsCalculator {
 
    Pair getStatistics(int k);
 
    interface Pair {
        int getElementIndexFromA();
        int getElementIndexFromB();
        int getSum();
    }
 
}