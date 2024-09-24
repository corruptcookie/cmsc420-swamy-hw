/**
 * ValleyTraveler class represents a magical map that can identify and modify
 * valley points in the landscape of Numerica.
 * 
 * @author Shasank Patel
 */
public class ValleyTraveler {

    // Create instance variables here.
    DublyList landscape;

    /**
     * Constructor to initialize the magical map with the given landscape of
     * Numerica.
     * 
     * @param landscape An array of distinct integers representing the landscape.
     */
    public ValleyTraveler(int[] landscape) {
        // TODO: Implement the constructor.
        super();
        this.landscape = new DublyList(landscape);
    }

    /**
     * Checks if the entire landscape is excavated (i.e., there are no landforms
     * left).
     * 
     * @return true if the landscape is empty, false otherwise.
     */
    public boolean isEmpty() {
        // TODO: Implement the isEmpty method.
        boolean bool = false;
        if (landscape.head == null) {
            bool = true;
        }
        return bool;
    }

    /**
     * Locates the first valley point in the landscape of Numerica.
     * 
     * @return The first valley point in the landscape.
     */
    public int getFirst() {
        // TODO: Implement the getFirst method.
        return landscape.valleyPoint.data;
    }

    /**
     * Excavates the first valley point, removing it from the landscape of Numerica.
     * 
     * @return The excavated valley point.
     */
    public int remove() {
        // TODO: Implement the remove method.
        int removed = landscape.valleyPoint.data;

        if (landscape.valleyPoint == landscape.head) {

            if (landscape.head == landscape.tail) {
                landscape.head = null;
                landscape.tail = null;
                landscape.valleyPoint = null;
            } else {
                landscape.head = landscape.head.next;
                landscape.head.prev = null;

                landscape.valleyPoint = null;
                Node current = landscape.head;
                while ((landscape.valleyPoint == null) && (current.next != null)) {

                    if (current.data < current.next.data) {
                        if (current == landscape.head) {
                            landscape.valleyPoint = current;
                        } else if (current.prev.data > current.data){
                            landscape.valleyPoint = current;
                        }
                    }
                    current = current.next;
                }
                if (landscape.valleyPoint == null) {
                    landscape.valleyPoint = landscape.tail;
                }
            }

        } else if (landscape.valleyPoint == landscape.tail) {
            landscape.tail = landscape.tail.prev;
            landscape.tail.next = null;
            landscape.valleyPoint = landscape.tail;

        } else {
            Node current = landscape.valleyPoint.prev;
            landscape.valleyPoint.prev.next = landscape.valleyPoint.next;
            landscape.valleyPoint.next.prev = landscape.valleyPoint.prev;
            landscape.valleyPoint = null;

            while ((landscape.valleyPoint == null) && (current.next != null)) {
                if (current == landscape.head) {
                    if (current.data < current.next.data) {
                        landscape.valleyPoint = current;
                    } 
                } else {
                    if (current.prev.data > current.data) {
                        if (current.next.data > current.data) {
                            landscape.valleyPoint = current;
                        }
                    }
                }
                current = current.next;
            }
            if (landscape.valleyPoint == null) {
                landscape.valleyPoint = landscape.tail;
            }

        }

        return removed;
    }

    /**
     * Creates a new landform at the first valley point.
     * 
     * @param num The height of the new landform.
     */
    public void insert(int height) {
        // TODO: Implement the insert method.
        Node newNode = new Node(height);

        if (landscape.valleyPoint == landscape.head) {
            if (landscape.head == null) {
                landscape.head = newNode;
                landscape.tail = newNode;
                landscape.valleyPoint = newNode;
            } else {
                newNode.next = landscape.head;
                landscape.head.prev = newNode;
                landscape.head = newNode;
                landscape.valleyPoint = null;
            }

            Node current = landscape.head;
            while ((landscape.valleyPoint == null) && (current.next != null)) {

                if (current.data < current.next.data) {
                    if (current == landscape.head) {
                        landscape.valleyPoint = current;
                    } else if (current.prev.data > current.data){
                        landscape.valleyPoint = current;
                    }
                }
                current = current.next;
            }
            if (landscape.valleyPoint == null) {
                landscape.valleyPoint = landscape.tail;
            }
        } else if (landscape.valleyPoint == landscape.tail) {
            landscape.tail.next = newNode;
            newNode.prev = landscape.tail;
            landscape.tail = newNode;

            if (landscape.tail.data < landscape.valleyPoint.data) {
                landscape.valleyPoint = landscape.tail;
            }

        } else {
            newNode.prev = landscape.valleyPoint.prev;
            newNode.next = landscape.valleyPoint;
            landscape.valleyPoint.prev.next = newNode;
            landscape.valleyPoint.prev = newNode;
            Node current = newNode.prev;
            landscape.valleyPoint = null;

            while ((landscape.valleyPoint == null) && (current.next != null)) {
                if (current == landscape.head) {
                    if (current.data < current.next.data) {
                        landscape.valleyPoint = current;
                    } 
                } else {
                    if (current.prev.data > current.data) {
                        if (current.next.data > current.data) {
                            landscape.valleyPoint = current;
                        }
                    }
                }
                current = current.next;
            }
            if (landscape.valleyPoint == null) {
                landscape.valleyPoint = landscape.tail;
            }

        }
    }

}