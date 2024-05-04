import org.junit.*;

import spatial.kdpoint.KDPoint;
import spatial.knnutils.BoundedPriorityQueue;
import spatial.knnutils.PriorityQueueNode;
import spatial.trees.KDTree;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Iterator;

public class myTests {

    @Test
    public void test() {
        KDTree kdTree = new KDTree(2);
        kdTree.insert(new KDPoint(0, -2));
        kdTree.insert(new KDPoint(-2, 1));
        kdTree.insert(new KDPoint(7, 0));
        kdTree.insert(new KDPoint(4, -3));
        kdTree.insert(new KDPoint(10, 4));
        kdTree.insert(new KDPoint(8, -3));
        kdTree.insert(new KDPoint(4, 5));
        kdTree.insert(new KDPoint(9, -6));
        kdTree.insert(new KDPoint(7, -3));
        kdTree.insert(new KDPoint(5, -5));
        kdTree.insert(new KDPoint(12, -7));
        kdTree.insert(new KDPoint(11, -2));
        kdTree.insert(new KDPoint(7, -7));
        kdTree.insert(new KDPoint(13, -4));

        KDPoint x = kdTree.nearestNeighbor(new KDPoint(7, 3));
        
    }



}
