package ru.ifmo.enf.plotnikov.t04;

import java.util.Random;

public class PrimalityTestImpl implements ProbablyPrimeCalculator {
    private Random rnd = new Random();

    private long modularPow(long base, long exponent, long modulus) {
        long c = 1;
        for (int ePrime = 1; ePrime <= exponent; ePrime++) {
            c = (c * base) % modulus;
        }
        return c;
    }

    @Override
    public boolean isProbablyPrime(long m) {
        if (m == 2 || m == 3) return true;
        if (m == 1 || m == 0) return false;
        long r = (long) Math.log(m);

        long t = m - 1; // не удобно выносить в отдельный метд, хотя хорошо бы, поскольку метод работает как с примитивами а не как с указателем
        long s = 0;
        while (t % 2 == 0) {
            s++;
            t /= 2;
        }

        while (r-- > 0) {
            long a = 2 + rnd.nextInt((int) (m - 3)); //нет метода nextLong(long n)
            long x = modularPow(a, t, m);
            if (x == 1 || x == m - 1) continue;
            for (int i = 1; i < s; i++) {
                x = modularPow(x, 2, m);
                if (x == 1) return false;
                if (x == m - 1) break;
            }
            if (x == m-1) continue;
            return false;

        }
        return true;
    }
}
