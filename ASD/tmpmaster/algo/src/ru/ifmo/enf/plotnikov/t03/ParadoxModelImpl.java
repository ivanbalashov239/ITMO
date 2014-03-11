package ru.ifmo.enf.plotnikov.t03;

import java.util.Random;

public class ParadoxModelImpl implements ParadoxModel{
    private Random random = new Random();

    @Override
    public ProbabilityPair getProbability(int repeats) {
        double okIfNotChange = 0;
        for (int i = 0; i < repeats; i++){
            Data data = generate();

            if (data.getMyChange() == data.getPrizeDoor()) {
                okIfNotChange++;
            }
        }
        return new Pair(1 - okIfNotChange/repeats, okIfNotChange/repeats);
    }

    private Data generate() {
        int prizeDoor = 1 + random.nextInt(3);
        int myChange = 1 + random.nextInt(3);
        int openedDoor;
        for (openedDoor = 1; openedDoor == prizeDoor || openedDoor == myChange; ++openedDoor);
        return new Data(prizeDoor, myChange, openedDoor);
    }

    class Data{
        private int prizeDoor;
        private int myChange;

        int getPrizeDoor() {
            return prizeDoor;
        }

        int getMyChange() {
            return myChange;
        }

        int getOpenedDoor() {
            return openedDoor;
        }

        Data(int prizeDoor, int myChange, int openedDoor) {

            this.prizeDoor = prizeDoor;
            this.myChange = myChange;
            this.openedDoor = openedDoor;
        }

        private int openedDoor;
    }

    private class Pair implements ProbabilityPair{
        private double ifChange;
        private double ifNotChange;

        public Pair(double ifChange, double ifNotChange) {
            this.ifChange = ifChange;
            this.ifNotChange = ifNotChange;
        }

        @Override
        public double getIfChange() {
            return ifChange;
        }

        @Override
        public double getIfNotChange() {
            return ifNotChange;
        }
    }
}
