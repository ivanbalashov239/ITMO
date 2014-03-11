package ru.ifmo.enf.plotnikov.t03;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

public class ParadoxModelImplTest {
    public static ParadoxModelImpl paradoxModel;
    @BeforeClass
    public static void beforeClass() throws NoSuchFieldException, IllegalAccessException {
        paradoxModel = new ParadoxModelImpl();
        Class primalityTestClass = paradoxModel.getClass();
        Field random = primalityTestClass.getDeclaredField("random");
        random.setAccessible(true);
        random.set(paradoxModel, new Random(12345678));
    }
    @Test
    public void testGetProbability() {
        ParadoxModel.ProbabilityPair pair;
        for (int i = 1000; i < 1000000; i*=10) {
            pair = paradoxModel.getProbability(i);
            Assert.assertTrue(pair.getIfChange() - pair.getIfNotChange() > 0.25);
        }
    }

    @Test
    public void testGenerate() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class paradoxModelClass = paradoxModel.getClass();
        Method generate = paradoxModelClass.getDeclaredMethod("generate");
        generate.setAccessible(true);

        ParadoxModelImpl.Data data = (ParadoxModelImpl.Data) generate.invoke(paradoxModel);
        Assert.assertTrue((data.getMyChange() != data.getOpenedDoor()) &&
                          (data.getMyChange() != data.getPrizeDoor()) &&
                          (data.getOpenedDoor() != data.getPrizeDoor()));


    }
}
