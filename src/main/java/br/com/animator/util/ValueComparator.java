package br.com.animator.util;

import java.util.Comparator;
import java.util.Map;

/**
 * author: Joao Paulo Faria
 */
public class ValueComparator implements Comparator<String> {

    //--- Properties
    Map<String, Long> comparableMap;

    /**
     * Constructor
     *
     * @param base
     */
    public ValueComparator(Map<String, Long> base) {
        this.comparableMap = base;
    }

    /**
     * Compares two keys based on their values in comparableMap in descending
     * order. If the values are equal, breaks the tie using the keys' natural
     * order to prevent key collisions and preserve consistency with equals().
     *
     * @param value1 First key
     * @param value2 Second key
     * @return Negative if value1 has higher score than value2, positive if
     * lower, or 0 if equal keys
     */
    @Override
    public int compare(String value1, String value2) {
        Long s1 = this.comparableMap.get(value1);
        Long s2 = this.comparableMap.get(value2);

        long score1 = (s1 != null) ? s1 : 0L;
        long score2 = (s2 != null) ? s2 : 0L;

        int scoreComparison = Long.compare(score2, score1);
        if (scoreComparison != 0) {
            return scoreComparison;
        }

        return value1.compareTo(value2);
    }

}
