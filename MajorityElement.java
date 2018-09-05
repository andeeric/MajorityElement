import java.util.HashMap;
import java.util.Random;
import java.util.ArrayList;

/**
 * A couple of algorithms for determining if an array has a majority element and then some test cases. An element is
 * considered a majority element if it occurs more than N/2 times in an array of length N.
 *
 * @author Eric Andersson
 * @version 2018-05-03
 */
public class MajorityElement
{
    private static Random random = new Random();
    
    private MajorityElement() {}
    
    /**
     * Quadratic implementation: For every element in an array of length N, iterate over the array, counting the amount of
     * times the element occurs. If the element occurs more than N/2 times, return true.
     * 
     * Time Complexity: O(N^2)
     * Auxiliary Space: O(1)
     */
    public static <T> boolean hasMajorityElementQuadratic(T[] a) {
        if(a == null || a.length == 0)
            return false;
        
        for(T x : a)
            if(count(a,x) > a.length / 2)
                return true;
                
        return false;
    }
    
    private static <T> int count(T[] a, T x) {
        int n = 0;
        for(T y : a)
            if(y.equals(x))
                n++;
        
        return n;
    }
    
    /**
     * Linear HashMap implementation: Iterates over an array of length N and puts every unique element in a HashMap with
     * the element as key and a counter as value. Every time an element that already exists in the map occurs, the counter
     * is incremented. If the counter value is larger than N/2, the loop breaks and returns true.
     * 
     * Time Complexity: O(N)
     * Auxiliary Space: O(N)
     */
    public static <T> boolean hasMajorityElementLinearHashMap(T[] a) {
        if(a == null || a.length == 0)
            return false;
        
        HashMap<T, Integer> m = new HashMap<T, Integer>();
        for(T x : a)
            if(m.containsKey(x)) {
                int times = m.get(x) + 1;
                if(times > a.length / 2)
                    return true;
                m.replace(x, times);
            } else
                m.put(x, 1);
                
        return false;
    }
    
    /**
     * Linear implementation: Iterate over an array of length N to determine a candidate for the majority element. A counter
     * keeps track of occurences. If the counter is zero, the current element will be chosen as candidate. If the next
     * element is a match, the counter is incremented, if not it is decremented. If a majority element exists, the candidate
     * will be that element when the end of the array is reached.
     * 
     * The candidate is then tested by iterating over the array once more and counting the amount of occurences. If it's
     * higher than N/2, return true.
     * 
     * Time Complexity: O(N)
     * Auxiliary Space: O(1)
     */
    public static <T> boolean hasMajorityElementLinear(T[] a) {
        if(a == null || a.length == 0)
            return false;
        
        T candidate = null;
        int count = 0;
        for(T x : a) {
            if(count == 0) {
                candidate = x;
                count++;
            } else {
                if(candidate.equals(x))
                    count++;
                else
                    count--;
            }
        }
  
        return count(a, candidate) > a.length / 2;
    }
    
    private static Integer[] randomArray(int n) {
        Integer[] a = new Integer[n];
        for(int i = 0; i < a.length; i++)
            a[i] = new Integer(random.nextInt(100));
        return a;
    }
    
    private static Integer[] arrayWithMajorityElement(int n) {
        Integer[] a = randomArray(n);
        for(int i = 0; i < a.length; i+=2)
            a[i] = new Integer(1);
        return a;
    }
    
    private static <T> void test(T[] a) {
        long start = System.currentTimeMillis();
        System.out.println("Linear (" + a.length + " elements): " + hasMajorityElementLinear(a));
        System.out.println(System.currentTimeMillis() - start + " ms");
        
        start = System.currentTimeMillis();
        System.out.println("Linear HashMap implementation (" + a.length + " elements): " + hasMajorityElementLinearHashMap(a));
        System.out.println(System.currentTimeMillis() - start + " ms");

        start = System.currentTimeMillis();
        System.out.println("Quadratic (" + a.length + " elements): " + hasMajorityElementQuadratic(a));
        System.out.println(System.currentTimeMillis() - start + " ms\n");
    }
    
    public static void main(String[] args) {
        System.out.println("Testing Majority Element Algorithms\n");
        
        // Simple tests with Integers
        Integer[] sampleArray = {1,2,1,2,1,2,1};
        test(sampleArray);          // Yes
        
        Integer[] sampleArray2 = {1,2,1,2,1,2,3};
        test(sampleArray2);         // No
        
        // Test with some other object
        ArrayList<Boolean> l = new ArrayList();
        l.add(true);
        ArrayList<Boolean> l2 = new ArrayList();
        l2.add(true);
        l2.add(false);
        Object[] a = {l, l2, l, l2, new Object()};
        test(a);                    // No
        
        a[4] = l;
        test(a);                    // Yes
        
        // Test with large input
        Integer[] sampleArray1000 = arrayWithMajorityElement(1000);
        test(sampleArray1000);      // Yes
        
        // Test with growing input
        for(int i = 1000; i < 1000000; i*=2) {
            Integer[] testArray = randomArray(i);
            test(testArray);
        }
    }
}
