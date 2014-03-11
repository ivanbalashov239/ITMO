package ru.ifmo.enf.plotnikov.t02;

import org.junit.Assert;
import org.junit.Test;

public class FastPairStatisticsCalculatorTest {
    @Test
    public void testGetStatistics() {
        int[] a = {1, 2, 3, 3, 4};
        int[] b = {5, 6, 7};
        PairStatisticsCalculator calculator = new FastPairStatisticsCalculator(a, b);

        //Первая
        Assert.assertEquals(calculator.getStatistics(1).getSum(), 6);
        Assert.assertEquals(calculator.getStatistics(1).getElementIndexFromA(), 0);
        Assert.assertEquals(calculator.getStatistics(1).getElementIndexFromB(), 0);
        //Последняя
        Assert.assertEquals(calculator.getStatistics(12).getSum(), 11);
        Assert.assertEquals(calculator.getStatistics(12).getElementIndexFromA(), 2);
        Assert.assertEquals(calculator.getStatistics(12).getElementIndexFromB(), 4);
        //Парочка с одинаковыми суммами из середины
        Assert.assertEquals(calculator.getStatistics(4).getSum(), 8);
        Assert.assertEquals(calculator.getStatistics(4).getElementIndexFromA(), 0);
        Assert.assertEquals(calculator.getStatistics(4).getElementIndexFromB(), 3);
        Assert.assertEquals(calculator.getStatistics(5).getSum(), 8);
        Assert.assertEquals(calculator.getStatistics(5).getElementIndexFromA(), 1);
        Assert.assertEquals(calculator.getStatistics(5).getElementIndexFromB(), 1);
    }
}
