package Java.LeetCode.flatten;

import java.util.List;

/**
 * Author: Nitin Gupta(nitin.gupta@walmart.com)
 * Date: 2019-08-31
 * Description:
 */
public class Triple<T> {

    T head;
    List<Integer> levelWise;
    List<Integer> depthWise;

    public Triple() {

    }

    public Triple(T head, List<Integer> l, List<Integer> d) {
        this.head = head;
        this.levelWise = l;
        this.depthWise = d;
    }
}
