import java.util.Comparator;
import java.util.TreeSet;
import java.util.HashMap;

/**
 * A convenient class that stores a pair of integers.
 * DO NOT MODIFY THIS CLASS.
 */

class IntPair {
    // Make the fields final to ensure they cannot be changed after initialization
    public final int first;
    public final int second;

    public IntPair(int first, int second) {
        this.first = first;
        this.second = second;
    }

    public String toString() {
        return "(" + first + "," + second + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        IntPair other = (IntPair) obj;
        return first == other.first && second == other.second;
    }

    @Override
    public int hashCode() {
        return 31 * first + second;
    }
}

class Node {
    IntPair pair;
    Node prev;
    Node next;
    int depth;

    public Node(IntPair data) {
        this.pair = data;
        this.prev = null;
        this.next = null;
        this.depth = -1;
    }
}

class DublyList {
    Node head;
    Node tail;

    public DublyList() {
        this.head = null;
        this.tail = null;
    }
}

class Sortbysecond implements Comparator<Node> {

    @Override
    public int compare(Node a, Node b)
    {
        return a.pair.second - b.pair.second;
    }
}

/**
 * TreasureValleyExplorer class operates on a landscape of Numerica,
 * selectively modifying the most and least valuable valleys of a specified
 * depth.
 * 
 * DO NOT MODIFY THE SIGNATURE OF THE METHODS PROVIDED IN THIS CLASS.
 * You are encouraged to add methods and variables in the class as needed.
 *
 * @author Shasank S. Patel
 */
public class TreasureValleyExplorer {

    // Create instance variables here.
    DublyList landscape;
    HashMap<Integer, TreeSet> depthMap;

    public int findDepth(Node curr) {
        int count = 0;
        while (curr.prev != null && curr.prev.pair.first > curr.pair.first) {
            count++;
            curr = curr.prev;
        }
        return count;
    }

    /**
     * Constructor to initialize the TreasureValleyExplorer with the given heights
     * and values
     * of points in Numerica.
     *
     * @param heights An array of distinct integers representing the heights of
     *                points in the landscape.
     * @param values  An array of distinct integers representing the treasure value
     *                of points in the landscape.
     */
    public TreasureValleyExplorer(int[] heights, int[] values) {
        // TODO: Implement the constructor.
        super();
        this.landscape = new DublyList();
        this.depthMap = new HashMap<>(heights.length);

        for (int i = 0; i < heights.length; i++) {
            Node curr = new Node(new IntPair(heights[i], values[i]));

            /* Add pair to landscape */
            if (this.landscape.head == null) {
                this.landscape.head = curr;
                this.landscape.tail = this.landscape.head;
            } else {
                this.landscape.tail.next = curr;
                curr.prev = this.landscape.tail;
                this.landscape.tail = curr;
            }

            /* Readjust valleys */
            if (curr.prev == null) {
                curr.depth = 0;
                TreeSet<Node> currTree = depthMap.get(curr.depth);
                if (currTree == null) {
                    currTree = new TreeSet<>(new Sortbysecond());
                    currTree.add(curr);
                    depthMap.put(curr.depth, currTree);
                } else {
                    currTree.add(curr);
                }

            } else if (curr.prev.pair.first > curr.pair.first) {
                /* Update depths */
                curr.depth = findDepth(curr);
                /* Remove previous valley */
                if (curr.prev.depth != -1) {
                    TreeSet<Node> prevTree = depthMap.get(curr.prev.depth);
                    prevTree.remove(curr.prev);
                    curr.prev.depth = -1;
                }
                /* Add new valley */
                TreeSet<Node> currTree = depthMap.get(curr.depth);
                if (currTree == null) {
                    currTree = new TreeSet<>(new Sortbysecond());
                    depthMap.put(curr.depth, currTree);
                    currTree.add(curr);
                } else {
                    currTree.add(curr);
                }

            } else {
                curr.depth = -1;
            }
        }
    }

    /**
     * Checks if the entire landscape is excavated (i.e., there are no points
     * left).
     *
     * @return true if the landscape is empty, false otherwise.
     */
    public boolean isEmpty() {
        // TODO: Implement the isEmpty method.
        return this.landscape.head == null;
    }

    /**
     * A method to insert a new landform prior to the most valuable valley of the
     * specified depth
     *
     * @param height The height of the new landform
     * @param value  The treasure value of the new landform
     * @param depth  The depth of the valley we wish to insert at
     *
     * @return true if the insertion is successful, false otherwise
     */
    public boolean insertAtMostValuableValley(int height, int value, int depth) {
        // TODO: Implement the insertAtMostValuableValley method
        return false;
    }

    /**
     * A method to insert a new landform prior to the least valuable valley of the
     * specified depth
     *
     * @param height The height of the new landform
     * @param value  The treasure value of the new landform
     * @param depth  The depth of the valley we wish to insert at
     *
     * @return true if the insertion is successful, false otherwise
     */
    public boolean insertAtLeastValuableValley(int height, int value, int depth) {
        // TODO: Implement the insertAtLeastValuableValley method
        return false;
    }

    /**
     * A method to remove the most valuable valley of the specified depth
     *
     * @param depth The depth of the valley we wish to remove
     *
     * @return An IntPair where the first field is the height and the second field
     *         is the treasure value of the removed valley
     * @return null if no valleys of the specified depth exist
     */
    public IntPair removeMostValuableValley(int depth) {
        // TODO: Implement the removeMostValuableValley method
        return null;
    }

    /**
     * A method to remove the least valuable valley of the specified depth
     *
     * @param depth The depth of the valley we wish to remove
     *
     * @return An IntPair where the first field is the height and the second field
     *         is the treasure value of the removed valley
     * @return null if no valleys of the specified depth exist
     */
    public IntPair removeLeastValuableValley(int depth) {
        // TODO: Implement the removeLeastValuableValley method
        return null;
    }

    /**
     * A method to get the treasure value of the most valuable valley of the
     * specified depth
     *
     * @param depth The depth of the valley we wish to find the treasure value of
     *
     * @return An IntPair where the first field is the height and the second field
     *         is the treasure value of the found valley
     * @return null if no valleys of the specified depth exist
     */
    public IntPair getMostValuableValley(int depth) {
        // TODO: Implement the getMostValuableValleyValue method
        IntPair valley = null;
        TreeSet<Node> currTree = depthMap.get(depth);
        if (currTree != null) {
            valley = currTree.last().pair;
        }
        return valley;
    }

    /**
     * A method to get the treasure value of the least valuable valley of the
     * specified depth
     *
     * @param depth The depth of the valley we wish to find the treasure value of
     *
     * @return An IntPair where the first field is the height and the second field
     *         is the treasure value of the found valley
     * @return null if no valleys of the specified depth exist
     */
    public IntPair getLeastValuableValley(int depth) {
        // TODO: Implement the getLeastValuableValleyValue method
        IntPair valley = null;
        TreeSet<Node> currTree = depthMap.get(depth);
        if (currTree != null) {
            valley = currTree.first().pair;
        }
        return valley;
    }

    /**
     * A method to get the number of valleys of a given depth
     *
     * @param depth The depth that we want to count valleys for
     *
     * @return The number of valleys of the specified depth
     */
    public int getValleyCount(int depth) {
        // TODO: Implement the getValleyCount method
        int count = 0;
        TreeSet<Node> currTree = depthMap.get(depth);
        if (currTree != null) {
            count = currTree.size();
        }
        return count;
    }
}
