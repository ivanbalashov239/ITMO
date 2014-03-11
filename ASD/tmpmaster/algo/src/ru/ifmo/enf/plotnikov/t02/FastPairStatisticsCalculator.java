package ru.ifmo.enf.plotnikov.t02;

import java.util.HashMap;
import java.util.Map;

public class FastPairStatisticsCalculator implements PairStatisticsCalculator {
    private int[] a;
    private int[] b;
    private Map<Integer, Integer> map = new HashMap<>();

    public FastPairStatisticsCalculator(int[] a, int[] b) {
        if (a.length < b.length){
            this.a = a;
            this.b = b;
        } else {
            this.a = b;
            this.b = a;
        }

        for (int i : this.b){
            if (map.containsKey(i)){
                map.put(i, map.get(i) + 1);
            } else {
                map.put(i, 1);
            }
        }
    }

    private int findIndexB(int value) {
        //TODO Переписать на бинпоиск
        int counter = map.get(value);
        int toReturn = -1;
        for (int i = 0; counter != 0 && i < b.length; i++) {
            if (b[i] == value){
                counter--;
                toReturn = i;
            }
        }
        return toReturn;
    }

    @Override
    public Pair getStatistics(int k) {
        int counter = 1;
        for (int i = a[0]+b[0]; i <= a[a.length-1]+b[b.length-1]; i++){
            for (int j = 0; j < a.length && i-a[j] >= 0; j++) {
                if (map.containsKey(i-a[j])) {
                    if (counter == k){
                        int bIndex = findIndexB(i-a[j]);
                        return new SimplePair(j, bIndex, a[j] + b[bIndex]);
                    }
                    counter++;
                }
            }
        }


        return null;
    }

    public class SimplePair implements Pair{
        private int elementIndexFromA;
        private int elementIndexFromB;
        private int sum;

        public SimplePair(int elementIndexFromA, int elementIndexFromB, int sum) {
            this.elementIndexFromA = elementIndexFromA;
            this.elementIndexFromB = elementIndexFromB;
            this.sum = sum;
        }



        @Override
        public int getElementIndexFromA() {
            return elementIndexFromA;
        }

        @Override
        public int getElementIndexFromB() {
            return elementIndexFromB;
        }

        @Override
        public int getSum() {
            return sum;
        }
    }

    //В целом асимтотика O(min(M,N)*(maxSum-minSum)) если не принимать во внимание считывание.
    //Альтернативно можно искать во внешнем цикле бинпоиском по всем суммам и искать нижнюю границу
    //за n*log(m) бинпоиском внутри этого внешнего бинпоиска (или за n+m если использовать указатели) тогда
    //было бы O(n*log(m)*log(maxSum-minSum)) и O((n+m)log(maxSum-minSum)). Оба варианта не очень то устойчивы
    //Но этот мне нравтится больше, если данные будут не сильно разбросаны в бесконечность и сами массивы
    //будут большие.
}
