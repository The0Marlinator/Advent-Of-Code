package aoc.framework.util;

import java.util.*;

public final class MathUtils {

    private MathUtils() {
        // Empty as this is a static class
    }

    public static List<Long> range(long floor, long ceiling) {
        List<Long> result = new LinkedList<>();

        for (long i = floor; i <= ceiling; i++) {
            result.add(i);
        }

        return result;
    }

    public static List<Long> range(long ceiling) {
        return range(0, ceiling);
    }

    public static Set<Long> union(Set<Long> first, Set<Long> second) {
        Set<Long> result = new HashSet<>(first);
        result.addAll(second);
        return result;
    }

    public static Map<Long, Long> getPrimeFactors(long number) {
        long absNumber = Math.abs(number);

        Map<Long, Long> primeFactorsMap = new HashMap<>();

        for (long factor = 2; factor <= absNumber; factor++) {
            while (absNumber % factor == 0) {
                Long power = primeFactorsMap.get(factor);
                if (power == null) {
                    power = 0L;
                }
                primeFactorsMap.put(factor, power + 1);
                absNumber /= factor;
            }
        }

        return primeFactorsMap;
    }

    public static long lcm(List<Long> numbers) {

        Optional<Long> numberisZero = numbers.stream().filter(number -> !number.equals(0L)).findAny();

        if (numberisZero.isEmpty()) {
            return 0;
        }

        List<Map<Long, Long>> primeFactors = numbers.stream()
                .map(MathUtils::getPrimeFactors)
                .toList();

        Set<Long> primeUnions = primeFactors.stream()
                .map(Map::keySet)
                .map(HashSet::new)
                .map(set -> (Set<Long>) set)
                .reduce(MathUtils::union)
                .orElseThrow();

        long lcm = 1;

        for (Long primeFactor : primeUnions) {
            lcm *= (long) Math.pow(primeFactor,
                    primeFactors.stream()
                            .map(map -> map.getOrDefault(primeFactor, 0L))
                            .max(Long::compareTo)
                            .orElseThrow()
            );
        }

        return lcm;
    }

}
