import org.junit.*;

import spatial.knnutils.BoundedPriorityQueue;
import spatial.knnutils.PriorityQueueNode;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;

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

    }

}
