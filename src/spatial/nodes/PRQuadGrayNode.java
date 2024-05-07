package spatial.nodes;

import spatial.exceptions.UnimplementedMethodException;
import spatial.kdpoint.KDPoint;
import spatial.knnutils.BoundedPriorityQueue;
import spatial.knnutils.NNData;
import spatial.trees.CentroidAccuracyException;
import spatial.trees.PRQuadTree;

import java.util.ArrayList;
import java.util.Collection;

/** <p>A {@link PRQuadGrayNode} is a gray (&quot;mixed&quot;) {@link PRQuadNode}. It
 * maintains the following invariants: </p>
 * <ul>
 *      <li>Its children pointer buffer is non-null and has a length of 4.</li>
 *      <li>If there is at least one black node child, the total number of {@link KDPoint}s stored
 *      by <b>all</b> of the children is greater than the bucketing parameter (because if it is equal to it
 *      or smaller, we can prune the node.</li>
 * </ul>
 *
 * <p><b>YOU ***** MUST ***** IMPLEMENT THIS CLASS!</b></p>
 *
 *  @author --- YOUR NAME HERE! ---
 */
public class PRQuadGrayNode extends PRQuadNode{


    /* ******************************************************************** */
    /* *************  PLACE ANY  PRIVATE FIELDS AND METHODS HERE: ************ */
    /* ********************************************************************** */
    PRQuadNode[] nodes;

    /* *********************************************************************** */
    /* ***************  IMPLEMENT THE FOLLOWING PUBLIC METHODS:  ************ */
    /* *********************************************************************** */

    /**
     * Creates a {@link PRQuadGrayNode}  with the provided {@link KDPoint} as a centroid;
     * @param centroid A {@link KDPoint} that will act as the centroid of the space spanned by the current
     *                 node.
     * @param k The See {@link PRQuadTree#PRQuadTree(int, int)} for more information on how this parameter works.
     * @param bucketingParam The bucketing parameter fed to this by {@link PRQuadTree}.
     * @see PRQuadTree#PRQuadTree(int, int)
     */
    public PRQuadGrayNode(KDPoint centroid, int k, int bucketingParam){
        super(centroid, k, bucketingParam); // Call to the super class' protected constructor to properly initialize the object!
        nodes = new PRQuadNode[4];
    }


    /**
     * <p>Insertion into a {@link PRQuadGrayNode} consists of navigating to the appropriate child
     * and recursively inserting elements into it. If the child is a white node, memory should be allocated for a
     * {@link PRQuadBlackNode} which will contain the provided {@link KDPoint} If it's a {@link PRQuadBlackNode},
     * refer to {@link PRQuadBlackNode#insert(KDPoint, int)} for details on how the insertion is performed. If it's a {@link PRQuadGrayNode},
     * the current method would be called recursively. Polymorphism will allow for the appropriate insert to be called
     * based on the child object's runtime object.</p>
     * @param p A {@link KDPoint} to insert into the subtree rooted at the current {@link PRQuadGrayNode}.
     * @param k The side length of the quadrant spanned by the <b>current</b> {@link PRQuadGrayNode}. It will need to be updated
     *          per recursive call to help guide the input {@link KDPoint}  to the appropriate subtree.
     * @return The subtree rooted at the current node, potentially adjusted after insertion.
     * @see PRQuadBlackNode#insert(KDPoint, int)
     */
    @Override
    public PRQuadNode insert(KDPoint p, int k) {
        // Determine appropriate child node
        int child;
        if (p.coords[0] >= centroid.coords[0]) {
            if (p.coords[1] >= centroid.coords[1]) {
                child = 1;
            } else {
                child = 3;
            }
        } else {
            if (p.coords[1] >= centroid.coords[1]) {
                child = 0;
            } else {
                child = 2;
            }        
        }
        // Insert point
        if (nodes[child] == null) {
            KDPoint childCentroid = computeCentroid(child, new KDPoint(centroid), k);
            nodes[child] = new PRQuadBlackNode(childCentroid, k, bucketingParam, p);
        } else {
            nodes[child] = nodes[child].insert(p, (k - 1));
        }
        return this;
    }

    private KDPoint computeCentroid(int child, KDPoint centroid, int k) {
        int xcoord = centroid.coords[0];
        int ycoord = centroid.coords[1];

        if (child == 0) {
            xcoord = xcoord - (int) Math.pow(2, (k - 2));
            ycoord = ycoord + (int) Math.pow(2, (k - 2));
        } else if (child == 1) {
            xcoord = xcoord + (int) Math.pow(2, (k - 2));
            ycoord = ycoord + (int) Math.pow(2, (k - 2));
        } else if (child == 2) {
            xcoord = xcoord - (int) Math.pow(2, (k - 2));
            ycoord = ycoord - (int) Math.pow(2, (k - 2));
        } else {
            xcoord = xcoord + (int) Math.pow(2, (k - 2));
            ycoord = ycoord - (int) Math.pow(2, (k - 2));
        }
        return new KDPoint(xcoord, ycoord);
    }

    /**
     * <p>Deleting a {@link KDPoint} from a {@link PRQuadGrayNode} consists of recursing to the appropriate
     * {@link PRQuadBlackNode} child to find the provided {@link KDPoint}. If no such child exists, the search has
     * <b>necessarily failed</b>; <b>no changes should then be made to the subtree rooted at the current node!</b></p>
     *
     * <p>Polymorphism will allow for the recursive call to be made into the appropriate delete method.
     * Importantly, after the recursive deletion call, it needs to be determined if the current {@link PRQuadGrayNode}
     * needs to be collapsed into a {@link PRQuadBlackNode}. This can only happen if it has no gray children, and one of the
     * following two conditions are satisfied:</p>
     *
     * <ol>
     *     <li>The deletion left it with a single black child. Then, there is no reason to further subdivide the quadrant,
     *     and we can replace this with a {@link PRQuadBlackNode} that contains the {@link KDPoint}s that the single
     *     black child contains.</li>
     *     <li>After the deletion, the <b>total</b> number of {@link KDPoint}s contained by <b>all</b> the black children
     *     is <b>equal to or smaller than</b> the bucketing parameter. We can then similarly replace this with a
     *     {@link PRQuadBlackNode} over the {@link KDPoint}s contained by the black children.</li>
     *  </ol>
     *
     * @param p A {@link KDPoint} to delete from the tree rooted at the current node.
     * @return The subtree rooted at the current node, potentially adjusted after deletion.
     */
    @Override
    public PRQuadNode delete(KDPoint p) {
        int child;
        if (p.coords[0] >= centroid.coords[0]) {
            if (p.coords[1] >= centroid.coords[1]) {
                child = 1;
            } else {
                child = 3;
            }
        } else {
            if (p.coords[1] >= centroid.coords[1]) {
                child = 0;
            } else {
                child = 2;
            }
        }

        if (nodes[child] == null) {
            return this;
        } else {
            nodes[child] = nodes[child].delete(p);
            ArrayList<KDPoint> children = new ArrayList<>();
            int blackChildCount = 0;
            PRQuadBlackNode blackChild = null;
            for (PRQuadNode node : nodes) {
                if (node instanceof PRQuadGrayNode) {
                    return this;
                } else if (node instanceof PRQuadBlackNode) {
                    PRQuadBlackNode newNode = (PRQuadBlackNode) node;
                    blackChildCount++;
                    blackChild = newNode;
                    children.addAll(newNode.getPoints());
                }
            }
            if (blackChildCount == 1) {
                return blackChild;
            }
            if ()

        }
    }

    @Override
    public boolean search(KDPoint p){
        int child;
        if (p.coords[0] >= centroid.coords[0]) {
            if (p.coords[1] >= centroid.coords[1]) {
                child = 1;
            } else {
                child = 3;
            }
        } else {
            if (p.coords[1] >= centroid.coords[1]) {
                child = 0;
            } else {
                child = 2;
            }
        }
        if (nodes[child] == null) {
            return false;
        } else {
            return nodes[child].search(p);
        }
    }

    @Override
    public int height(){
        int height0, height1, height2, height3;
        if (nodes[0] == null) {
            height0 = -1;
        } else {
            height0 = nodes[0].height();
        }
        if (nodes[1] == null) {
            height1 = -1;
        } else {
            height1 = nodes[1].height();
        }
        if (nodes[2] == null) {
            height2 = -1;
        } else {
            height2 = nodes[2].height();
        }
        if (nodes[3] == null) {
            height3 = -1;
        } else {
            height3 = nodes[3].height();
        }
        return (1 + Math.max(height0, Math.max(height1, Math.max(height2, height3))));
    }

    @Override
    public int count(){
        int count0, count1, count2, count3;
        if (nodes[0] == null) {
            count0 = 0;
        } else {
            count0 = nodes[0].count();
        }
        if (nodes[1] == null) {
            count1 = 0;
        } else {
            count1 = nodes[1].count();
        }
        if (nodes[2] == null) {
            count2 = 0;
        } else {
            count2 = nodes[2].count();
        }
        if (nodes[3] == null) {
            count3 = 0;
        } else {
            count3 = nodes[3].count();
        }
        return count0 + count1 + count2 + count3;
    }

    /**
     * Returns the children of the current node in the form of a Z-ordered 1-D array.
     * @return An array of references to the children of {@code this}. The order is Z (Morton), like so:
     * <ol>
     *     <li>0 is NW</li>
     *     <li>1 is NE</li>
     *     <li>2 is SW</li>
     *     <li>3 is SE</li>
     * </ol>
     */
    public PRQuadNode[] getChildren(){
        return nodes;
    }

    @Override
    public void range(KDPoint anchor, Collection<KDPoint> results,
                      double range) {
        throw new UnimplementedMethodException(); // ERASE THIS LINE AFTER YOU IMPLEMENT THIS METHOD!
    }

    @Override
    public NNData<KDPoint> nearestNeighbor(KDPoint anchor, NNData<KDPoint> n)  {
        throw new UnimplementedMethodException(); // ERASE THIS LINE AFTER YOU IMPLEMENT THIS METHOD!
    }

    @Override
    public void kNearestNeighbors(int k, KDPoint anchor, BoundedPriorityQueue<KDPoint> queue) {
        throw new UnimplementedMethodException(); // ERASE THIS LINE AFTER YOU IMPLEMENT THIS METHOD!
    }
}

