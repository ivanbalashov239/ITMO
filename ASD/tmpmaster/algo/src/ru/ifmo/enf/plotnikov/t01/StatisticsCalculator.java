package ru.ifmo.enf.plotnikov.t01;

public interface StatisticsCalculator<E extends Comparable<E>> {
    E getStatistics(int k);
}