package ru.ifmo.enf.plotnikov.t04;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

public class PrimalityTestImplTest {
    public static PrimalityTestImpl primalityTest;
    @BeforeClass
    public static void beforeClass() throws NoSuchFieldException, IllegalAccessException {
        primalityTest = new PrimalityTestImpl();
        Class primalityTestClass = primalityTest.getClass();
        Field rnd = primalityTestClass.getDeclaredField("rnd");
        rnd.setAccessible(true);
        rnd.set(primalityTest, new Random(1234567));
    }
    @Test
    public void testIsProbablyPrime() {
        //прекальк простых
        long[] dataPrime = new long[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157};
        long[] dataComposite = new long[]{4,100,16,50,22,33,111,3452,678,1242,121,0};

        for (long i : dataPrime) {
            Assert.assertTrue(primalityTest.isProbablyPrime(i));
        }

        for (long i : dataComposite) {
            Assert.assertFalse(primalityTest.isProbablyPrime(i));
        }
    }

    @Test
    public void testModularPow() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class primalityTestClass = primalityTest.getClass();
        Method modularPow = primalityTestClass.getDeclaredMethod("modularPow", new Class<?>[]{long.class, long.class, long.class});
        modularPow.setAccessible(true);

        Assert.assertEquals((long)445, modularPow.invoke(primalityTest, 4, 13, 497));
    }
}
