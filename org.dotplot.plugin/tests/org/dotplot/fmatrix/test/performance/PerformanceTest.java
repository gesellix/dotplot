/*
 * Created on 20.06.2004
 */
package org.dotplot.fmatrix.test.performance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

/**
 * Conclusion:
 * 
 * @author Constantin von Zitzewitz
 */
public class PerformanceTest {

    public static void main(String args[]) {

	Vector vector = new Vector();

	ArrayList arrayList = new ArrayList();
	ListGetter getter = new ListGetter();

	Hashtable hashtable = new Hashtable();
	HashMap hashMap = new HashMap();

	int numberOfElements = 500000;

	// fill them
	// getter.addToArrayList(arrayList, numberOfElements);

	// getter.addToVector(vector, numberOfElements);

	getter.addToHashMap(hashMap, numberOfElements);
	getter.addToHashtable(hashtable, numberOfElements);

	// iterate with iterator
	getter.iterateHashMapWithGet(hashMap);
	getter.iterateHashMapWithIterator(hashMap);
	// iterate with get
	getter.iterateHashtableWithGet(hashtable);
	getter.iterateHashtableWithIterator(hashtable);

	/*
	 * int res1; int res2; int res3; int res4; // single calls
	 * 
	 * for (int i = 0; i < numberOfElements; i++) { // res1 =
	 * getter.getFromVector(vector, i); //res2 =
	 * getter.getFromArrayList(arrayList, i); // res3 =
	 * getter.getFromHashtable(hashtable, i); // res4 =
	 * getter.getFromHashMap(hashMap, i); }
	 */
    }
}
