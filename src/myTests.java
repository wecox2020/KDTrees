import org.junit.*;

import spatial.kdpoint.KDPoint;
import spatial.knnutils.BoundedPriorityQueue;
import spatial.knnutils.PriorityQueueNode;
import spatial.trees.KDTree;

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

    @Test
    public void testInsertDeleteKDTree() {
        KDTree kdTree = new KDTree(2);
        kdTree.insert(new KDPoint(10, 30));
        kdTree.insert(new KDPoint(12, 18));
        kdTree.insert(new KDPoint(-20, 300));
        kdTree.insert(new KDPoint(14, 20));
        kdTree.delete(new KDPoint(12, 18));
    }



}
