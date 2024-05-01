import org.junit.*;

import spatial.knnutils.BoundedPriorityQueue;
import spatial.knnutils.PriorityQueueNode;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Iterator;

public class myTests {
    
    @Test
    public void testQueuing() {
        BoundedPriorityQueue<String> queue = new BoundedPriorityQueue<>(4);
        queue.enqueue("a", 0);
        queue.enqueue("b", 1);
        queue.enqueue("c", 2);
        queue.enqueue("d", -1);
        queue.enqueue("e", 3);
        queue.enqueue("f", -2);
        String x = queue.dequeue();
    }

    @Test
    public void testIteratorBPQ() {
        BoundedPriorityQueue<String> queue = new BoundedPriorityQueue<>(5);
        queue.enqueue("a", 0);
        queue.enqueue("b", 1);
        queue.enqueue("c", 2);
        queue.enqueue("d", -1);
        queue.enqueue("e", 3);
        queue.enqueue("f", -2);
        Iterator<String> iterator = queue.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

}
