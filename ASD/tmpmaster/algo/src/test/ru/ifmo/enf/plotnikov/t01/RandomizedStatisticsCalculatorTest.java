package ru.ifmo.enf.plotnikov.t01;

import org.junit.Assert;

import java.util.ArrayList;

public class RandomizedStatisticsCalculatorTest {

    @org.junit.Test
    public void testSimpleGetStatistics() {
        ArrayList<Integer> list = new ArrayList<Integer>(){{
            add(4);
            add(2);
            add(5);
            add(6);
            add(3);
            add(1);
        }};

        StatisticsCalculator<Integer> calculate = new RandomizedStatisticsCalculator<>(list);

        Assert.assertEquals((Integer)3, calculate.getStatistics(3));
        Assert.assertEquals((Integer)6, calculate.getStatistics(6));
        Assert.assertEquals((Integer)1, calculate.getStatistics(1));
    }

    @org.junit.Test
    public void testSmallArrayGetStatistics() {
        ArrayList<Integer> list = new ArrayList<Integer>(){{
            add(4);
            add(2);
        }};

        StatisticsCalculator<Integer> calculate = new RandomizedStatisticsCalculator<>(list);

        Assert.assertEquals((Integer)4, calculate.getStatistics(2));
        Assert.assertEquals((Integer)2, calculate.getStatistics(1));
    }
}
