package ru.ifmo.enf.plotnikov.t03;

public interface ParadoxModel {
 
    ProbabilityPair getProbability(int repeats);
 
    interface ProbabilityPair {
        double getIfChange();
        double getIfNotChange();
    }
}