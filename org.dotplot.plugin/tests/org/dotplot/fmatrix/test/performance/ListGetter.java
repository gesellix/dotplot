/*
 * Created on 20.06.2004
 */
package org.dotplot.fmatrix.test.performance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

/**
 * @author Constantin von Zitzewitz
 */
public final class ListGetter {
	public void addToArrayList(ArrayList arrayList, int numberOfElements) {
		for (int i = 0; i < numberOfElements; i++) {
			arrayList.add(new Integer(i));
		}
	}

	public void addToHashMap(HashMap hashMap, int numberOfElements) {
		for (int i = 0; i < numberOfElements; i++) {
			hashMap.put(new Integer(i), new Integer(i));
		}
	}

	public void addToHashtable(Hashtable hashtable, int numberOfElements) {
		for (int i = 0; i < numberOfElements; i++) {
			hashtable.put(new Integer(i), new Integer(i));
		}
	}

	public void addToVector(Vector vector, int numberOfElements) {
		for (int i = 0; i < numberOfElements; i++) {
			vector.add(new Integer(i));
		}
	}

	public int getFromArrayList(ArrayList arrayList, int index) {
		return ((Integer) (arrayList.get(index))).intValue();
	}

	public int getFromHashMap(HashMap hashMap, int index) {
		return ((Integer) (hashMap.get(new Integer(index)))).intValue();
	}

	public int getFromHashtable(Hashtable hashTable, int index) {
		return ((Integer) (hashTable.get(new Integer(index)))).intValue();
	}

	public int getFromVector(Vector vector, int index) {
		return ((Integer) (vector.get(index))).intValue();
	}

	public void iterateHashMapWithGet(HashMap hashMap) {
		int size = hashMap.size();
		for (int i = 0; i < size; i++) {
			((Integer) (hashMap.get(new Integer(i)))).intValue();
		}
	}

	public void iterateHashMapWithIterator(HashMap hashMap) {
		Iterator iterator = hashMap.entrySet().iterator();
		while (iterator.hasNext()) {
			// res = ((Integer).intValue();
			((Integer) (((Map.Entry) (iterator.next())).getValue())).intValue();
		}
	}

	public void iterateHashtableWithGet(Hashtable hashtable) {
		int size = hashtable.size();
		for (int i = 0; i < size; i++) {
			((Integer) (hashtable.get(new Integer(i)))).intValue();
		}
	}

	public void iterateHashtableWithIterator(Hashtable hashMap) {
		Iterator iterator = hashMap.entrySet().iterator();
		while (iterator.hasNext()) {
			// res = ((Integer).intValue();
			((Integer) (((Map.Entry) (iterator.next())).getValue())).intValue();
		}
	}
}
