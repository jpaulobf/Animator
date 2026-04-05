package br.com.game.animator.util;

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
	 * @param base
	 */
	public ValueComparator(Map<String, Long> base) {
		this.comparableMap = base;
	}
	
	/**
	 * Compare two values based on the comparableMap
	 * @param value1
	 * @param value2
	 * @return -1 if value1 is greater than or equal to value2, 1 otherwise
	 */
	@Override
	public int compare(String value1, String value2) {
		if (this.comparableMap.get(value1) >= this.comparableMap.get(value2)) {
            return -1;
        } else {
            return 1;
        }
	}

}