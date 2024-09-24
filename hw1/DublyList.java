public class DublyList {
    Node head;
    Node tail;
    Node valleyPoint;

    public DublyList() {
        this.head = null; 
        this.tail = null;
        this.valleyPoint = null;
    }

    public DublyList(int[] landscape) {
        this.head = null; 
        this.tail = null;
        this.valleyPoint = null;

        for (int data : landscape) {
            Node temp = new Node(data);

            if (head == null) {
                head = temp;
                tail = temp;
                valleyPoint = temp;

            } else {
                tail.next = temp;
                temp.prev = tail;
                tail = temp;

                if (valleyPoint == tail.prev) {
                    if (valleyPoint.data > tail.data) {
                        valleyPoint = tail;
                    }
                }

            }
        }
    }
}
