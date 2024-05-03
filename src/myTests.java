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
    public void testKDTreeSearch() {
        KDTree kdTree = new KDTree(2);
        kdTree.insert(new KDPoint(10, 30));
        kdTree.insert(new KDPoint(12, 18));
        kdTree.insert(new KDPoint(-20, 300));
        kdTree.insert(new KDPoint(14, 20));
        kdTree.insert(new KDPoint(-10, -30));
        kdTree.insert(new KDPoint(12, 18));
        kdTree.insert(new KDPoint(85, 215));
        kdTree.insert(new KDPoint(140, 0));
        assertTrue(kdTree.search(new KDPoint(85, 215)) == true);
        assertTrue(kdTree.search(new KDPoint(12, 20)) == false);

        kdTree.delete(new KDPoint(85, 215));
        assertTrue(kdTree.search(new KDPoint(85, 215)) == false);
    }

    @Test
    public void testInsertDeleteKDTree() {
        KDTree kdTree = new KDTree(2);
        kdTree.insert(new KDPoint(10, 10));
        kdTree.insert(new KDPoint(9, 9));
        kdTree.insert(new KDPoint(11, 13));
        kdTree.insert(new KDPoint(9, 8));
        kdTree.insert(new KDPoint(11, 14));
        kdTree.insert(new KDPoint(12, 12));
        kdTree.insert(new KDPoint(6, 10));

        kdTree.delete(new KDPoint(10, 10));
    }



}
