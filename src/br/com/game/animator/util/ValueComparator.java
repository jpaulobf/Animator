package br.com.game.animator.util;

import java.util.Comparator;
import java.util.Map;

/**
 * @author Jo�o Paulo
 *
 */
public class ValueComparator implements Comparator<String> {
	
	//----------------------------------------------------------------------//
	//--------   PROPRIEDADES 				--------------------------------//
	//----------------------------------------------------------------------//
	Map<String, Long> comparableMap;
	
	public ValueComparator(Map<String, Long> base) {
		this.comparableMap = base;
	}
	
	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(String value1, String value2) {
		if (this.comparableMap.get(value1) >= this.comparableMap.get(value2)) {
            return -1;
        } else {
            return 1;
        }
	}

}